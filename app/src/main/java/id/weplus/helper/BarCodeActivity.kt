package id.weplus.helper

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import id.weplus.R
import id.weplus.WebViewActivity
import kotlinx.android.synthetic.main.activity_bar_code.*
import kotlinx.android.synthetic.main.activity_protection_code.*
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.view_back_transparent.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.io.File
import java.util.concurrent.ExecutorService


class BarCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView
    private var flashState=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code)
        setupBackButton()

        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                        setupFlashButton()
                        val contentFrame = findViewById<View>(R.id.content_frame) as ViewGroup
                        mScannerView = ZXingScannerView(this@BarCodeActivity)
                        contentFrame.addView(mScannerView)
                        mScannerView.startCamera()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) { /* ... */
                    }
                }).check()


    }

    private fun setupFlashButton() {
        btnFlash.setOnClickListener {
            if(::mScannerView.isInitialized){
                flashState = !flashState
                mScannerView.flash=flashState
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::mScannerView.isInitialized) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    override fun onPause() {
        super.onPause()
        if(::mScannerView.isInitialized){
            mScannerView.stopCamera()
        }
    }

    override fun handleResult(rawResult: Result?) {
//        Toast.makeText(this, "Contents = " + rawResult!!.text.toString() +
//                ", Format = " + rawResult.barcodeFormat.toString(), Toast.LENGTH_SHORT).show()
        val intent = Intent(this,WebViewActivity::class.java)
        intent.putExtra("url","https://uatprotect.weplus.id/codeprotection/")
        intent.putExtra("code",rawResult?.text.toString().toUpperCase())
        startActivity(intent)
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        val handler = Handler()
        handler.postDelayed({ mScannerView.resumeCameraPreview(this@BarCodeActivity) }, 2000)
    }

    private fun setupBackButton(){
        viewback_description.text="Pengaktifan Kode Proteksi dari WE+"
        viewback_title.text="Pindai QR Code"
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }
}