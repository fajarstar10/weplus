package id.weplus.transaksi

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.helper.GetRealPathUtil
import id.weplus.model.request.PaymentNotificationRequest
import id.weplus.model.response.PaymentNotificationResponse
import id.weplus.model.response.UploadImageResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_upload_transfer_file.*
import kotlinx.android.synthetic.main.view_back.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class UploadTransferFileActivity : BaseActivity() {

    private val RESULT_LOAD_IMG = 1
    private var transferPicture: Bitmap? = null
    private var orderCode="0"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_transfer_file)
        getIntentData()
        setupToolbar()
        setupSelectImageButton()
        btnDone.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri = data!!.data
                val imageStream = imageUri?.let { contentResolver.openInputStream(it) }
                transferPicture = BitmapFactory.decodeStream(imageStream)
                //ic_profil.setImageBitmap(selectedImage);
                onFilePicked(GetRealPathUtil.getRealPathFromURI(this@UploadTransferFileActivity, imageUri))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    private fun getIntentData(){
        orderCode= intent.getStringExtra("order_code").toString()
    }

    private fun setupToolbar(){
        viewback_buttonback.setOnClickListener { finish() }
        viewback_title.text="Unggah Bukti Pembayaran"
        viewback_description.text=""
    }
    
    private fun setupSelectImageButton(){
        btnSelectImage.setOnClickListener { 
            takephoto()
        }
    }

    private fun takephoto() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) { /*
                             ... */
                        val photoPickerIntent = Intent(Intent.ACTION_PICK)
                        photoPickerIntent.type = "image/*"
                        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) { /* ... */
                    }
                }).check()
    }

    private fun onFilePicked(filePath: String) {
        val file = File(filePath)
        val fileSizeInBytes = file.length()
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        val fileSizeInKB = fileSizeInBytes / 1024
        //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        val fileSizeInMB = fileSizeInKB / 1024
        if (fileSizeInMB > 10) {
            SweetAlertDialog(this@UploadTransferFileActivity, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("")
                    .setContentText("Ukuran gambar lebih besar dari 1 MB")
                    .show()
        } else {
            val map = HashMap<String, RequestBody>()
            map["type"] = createPartFromString("profile")
            val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("image", file.name, reqFile)
            sendUploadImageRequest(body, map)
        }
    }

    private fun sendUploadImageRequest(image: MultipartBody.Part, requestBody: Map<String, RequestBody>) {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility = View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .uploadImage(image, requestBody)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), UploadImageResponse::class.java)
                    try {
                        when (resp.code) {
                            ErrorCode.ERROR_00 -> {
                                sendPaymentNotification(resp.data.url)
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@UploadTransferFileActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                showError(this@UploadTransferFileActivity, resp.message)
                            }
                        }
                    } catch (e: Exception) {
                        loadingProgress.visibility = View.GONE
                        Log.i("test", "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    loadingProgress.visibility = View.GONE
                    showError(this@UploadTransferFileActivity, "${t.message}")
                    Log.i("test", "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@UploadTransferFileActivity, "..")
        }
    }

    private fun sendPaymentNotification(imageUrl: String){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility = View.VISIBLE
            val requestBody = PaymentNotificationRequest(imageUrl)
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .sendPaymentNotification(orderCode, requestBody)
            call.enqueue(object : Callback<PaymentNotificationResponse?> {
                override fun onResponse(call: Call<PaymentNotificationResponse?>, response: Response<PaymentNotificationResponse?>) {
                    val resp = response.body()
                    try {
                        loadingProgress.visibility = View.GONE
                        if (resp?.code == ErrorCode.ERROR_00) {
                            SweetAlertDialog(this@UploadTransferFileActivity, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Bukti Fisik Berhasil Diunggah")
                                    .setContentText("Bukti pembayaran akan diverifikasi oleh Customer WE+")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener { sDialog ->
                                        sDialog.dismissWithAnimation()
                                        this@UploadTransferFileActivity.finish()
                                    }
                                    .show()
                        } else {
                            showError(this@UploadTransferFileActivity, resp?.message)
                        }
                    } catch (e: Exception) {
                        loadingProgress.visibility = View.GONE
                        Log.i("test", "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<PaymentNotificationResponse?>, t: Throwable) {
                    loadingProgress.visibility = View.GONE
                    showError(this@UploadTransferFileActivity, "Time Out")
                    Log.i("test", "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@UploadTransferFileActivity, "..")
        }
    }

    private fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString)
    }
}