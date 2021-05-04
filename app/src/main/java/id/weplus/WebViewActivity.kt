package id.weplus

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.google.gson.Gson
import id.weplus.model.LoginData
import id.weplus.net.WeplusSharedPreference
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.view_back.*


class WebViewActivity : BaseActivity() {
    private var url = ""
    private var title=""
    private var description=""
    private lateinit var code: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        getIntentData()
    }

    private fun getIntentData() {
        url = intent.getStringExtra("url")?:""
        if (intent.getStringExtra("code") != null) {
            code = intent.getStringExtra("code")!!
        }
        if(intent.getStringExtra("title")!=null){
            title= intent.getStringExtra("title")!!
        }else{
            layoutHeader.visibility=View.GONE
        }
        if(intent.getStringExtra("desc")!=null){
            description = intent.getStringExtra("desc")!!
        }
        viewback_title.text=title
        viewback_description.text=description
        viewback_buttonback.setOnClickListener{
            finish()
        }
        val jsonResponse = WeplusSharedPreference.getUser(this)
        val gson = Gson()
        val loginData = gson.fromJson(jsonResponse, LoginData::class.java)
        if (::code.isInitialized && !code.isNullOrBlank()) {
             url = "$url$code/?user_id${loginData.user_id}"
        }
        setupWebView(url)
    }

    override fun onResume() {
        super.onResume()
        Log.d("resume","onresume called")
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true;
        webView.settings.domStorageEnabled = true;
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                //Toast.makeText(this@WebViewActivity, description, Toast.LENGTH_SHORT).show()
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

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.d("webview gopay", "onLoad: $url")
                val intent: Intent
                if (url != null) {
                    if (url.contains("gojek://gopay/")) {
                        webView.stopLoading()
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    }
                }
            }

        }

        webView.loadUrl(url)
    }
}