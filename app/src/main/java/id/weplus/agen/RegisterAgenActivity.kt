package id.weplus.agen

import android.Manifest
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.weplus.R
import id.weplus.model.LoginData
import id.weplus.net.WeplusSharedPreference
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.view_back.*
import java.io.File
import java.io.IOException
import java.security.Permissions
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RegisterAgenActivity : AppCompatActivity() {
    private var url = ""
    private lateinit var code: String
    private val TAG = "RegisterAgentActivity"
    private var requiredPermissions = arrayOf<String>(CAMERA,WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE)

    private val REQUEST_SELECT_FILE = 100
    private val FILECHOOSER_RESULTCODE = 1
    private var uploadMessage: ValueCallback<Array<Uri>>? = null
    private var imagePathCallback: ValueCallback<Array<Uri>>? = null
    private var cameraImagePath: String? = null

    companion object {
        private const val CAMERA_REQUEST_CODE = 113
        private const val REQUEST_SELECT_FILE = 13
        private const val INTENT_FILE_TYPE = "image/*"
        private const val CAMERA_PHOTO_PATH_POSTFIX = "file:"
        private const val PHOTO_NAME_POSTFIX = "JPEG_"
        private const val PHOTO_FORMAT = ".jpg"
    }
    var link: String? = null
    private var mUploadMessage: ValueCallback<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_agen)
        getIntentData()
        setupToolbar()
    }

    private fun setupToolbar(){
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text="WE+ Mitra"
        viewback_description.text=""
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView(url: String) {
        // Create new webview Client to show progress dialog
        // When opening a url or click on link
        // Javascript enabled on webview
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true
        webView.settings.setAppCacheEnabled(false)
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.setGeolocationEnabled(true)      // life saver, do not remove
        //webView.addJavascriptInterface(WebAppInterface(this), "Android")
        webView.webChromeClient = getCustomWebChromeClient()
        webView.webViewClient = object : WebViewClient() {

            // If you will not use this method url links are open in new browser
            // not in webview
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.url.toString())
                }
                return true
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                Log.d(TAG, "onReceivedError ")
            }

            // Show loader on url load
            override fun onLoadResource(view: WebView, url: String) {
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                loadingProgress.visibility = View.GONE
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.d(TAG, "onReceivedHttpError ${errorResponse?.statusCode}")
                }
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                Log.d(TAG, "onReceivedError ")
                WebViewClient.ERROR_AUTHENTICATION
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d(TAG, "error code: ${error.errorCode} " + request.url.toString() + " , " + error.description)
                }
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                super.onReceivedSslError(view, handler, error)
                Log.d(TAG, "SSl error ")
            }
        }

        // Other webview options
        /*
         * webView.getSettings().setLoadWithOverviewMode(true);
         * webView.getSettings().setUseWideViewPort(true);
         * webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
         * webView.setScrollbarFadingEnabled(false);
         * webView.getSettings().setBuiltInZoomControls(true);
         */
        webView.loadUrl(url)

        // Load url in webview
//
//        if (NetworkStatus.isOnline(this)) {
//            Handler().postDelayed({ webView.loadUrl(url) }, 400)
//        } else {
//            util.showToast(this, getString(R.string.no_internet), true)
//        }
    }

    private fun getIntentData() {
        url = intent.getStringExtra("url")!!
        Log.d("url-agen", "url $url")
        if (intent.getStringExtra("code") != null) code = intent.getStringExtra("code")!!
        val jsonResponse = WeplusSharedPreference.getUser(this)
        val gson = Gson()
        val loginData = gson.fromJson(jsonResponse, LoginData::class.java)
        if (::code.isInitialized && !code.isNullOrBlank()) {
            url = "$url$code/?user_id${loginData.user_id}"
        }
        //setupWebView(url)
        askCameraPermission()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(this@RegisterAgenActivity, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingProgress.visibility = View.GONE
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loadingProgress.visibility = View.VISIBLE
            }

        }

        askCameraPermission()


    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        this,
                        "permission grandted",
                        Toast.LENGTH_LONG
                ).show()

                startWebView(url)
            } else {
                Toast.makeText(
                        this,
                        "Permission denied",
                        Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != REQUEST_SELECT_FILE || imagePathCallback == null) return

        var results: Array<Uri>? = null

        if (resultCode == RESULT_OK) {
            if (data == null) {
                if (cameraImagePath != null) results = arrayOf(Uri.parse(cameraImagePath))
            } else {
                val dataString = data.dataString
                if (dataString != null) results = arrayOf(Uri.parse(dataString))
            }
        }

        imagePathCallback?.onReceiveValue(results)
        imagePathCallback = null
    }

    private fun askCameraPermission(){
        Log.d("permission","ask permission")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("permission","ask permission from android M above")
            val hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA)
            val permissions: MutableList<String> = ArrayList()
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                Log.d("permission","ask permission bcoz not granted")
                permissions.add(Manifest.permission.CAMERA)
                if (permissions.isNotEmpty()) {
                    requestPermissions(permissions.toTypedArray(), 111)
                }
            }else{

                Log.d("permission","ask permission and is granted")
                Log.d("permission","permission granted load : $url")
                startWebView(url)
            }

        }
    }

    private fun getCustomWebChromeClient() = object : WebChromeClient() {

        override fun onShowFileChooser(
                view: WebView?,
                filePath: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
        ): Boolean {
            if (if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    } else {
                        TODO("VERSION.SDK_INT < M")
                    })
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
                }

            imagePathCallback?.onReceiveValue(null)
            imagePathCallback = null
            imagePathCallback = filePath

            val takePictureIntent = createImageCaptureIntent()

            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.type = INTENT_FILE_TYPE

            val intentArray: Array<Intent?>
            intentArray = arrayOf(takePictureIntent)

            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Pilih Gambar")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)

            try {
                startActivityForResult(chooserIntent, REQUEST_SELECT_FILE)
            } catch (e: ActivityNotFoundException) {
                imagePathCallback = null
                cameraImagePath = null

                Toast.makeText(
                        this@RegisterAgenActivity,
                        "Tidak dapat membuka Gallery",
                        Toast.LENGTH_LONG
                ).show()

                return false
            }

            return true
        }

        private fun createImageCaptureIntent(): Intent? {
            var captureImageIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (captureImageIntent?.resolveActivity(packageManager) != null) {
                var imageFile: File? = null

                try {
                    imageFile = createImageFile()
                    captureImageIntent.putExtra("CameraImagePath", cameraImagePath)
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }

                if (imageFile != null) {
                    cameraImagePath = CAMERA_PHOTO_PATH_POSTFIX + imageFile.absolutePath
                    captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile))
                } else {
                    captureImageIntent = null
                }
            }

            return captureImageIntent
        }

        private fun createImageFile(): File? {
            val timeStamp = SimpleDateFormat.getDateInstance().format(Date())
            val imageFileName = PHOTO_NAME_POSTFIX + timeStamp + "_"
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            return File.createTempFile(imageFileName, PHOTO_FORMAT, storageDir)
        }

    }
}