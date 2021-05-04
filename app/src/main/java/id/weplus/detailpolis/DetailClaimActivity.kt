package id.weplus.detailpolis

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.gson.Gson
import id.weplus.R
import id.weplus.model.LoginData
import id.weplus.net.WeplusSharedPreference
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.view_back.*

class DetailClaimActivity : AppCompatActivity() {
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_claim)
        setupToolbar()
        getIntentData()
    }

    private fun setupToolbar(){
        viewback_title.text="Klaim"
        viewback_description.visibility= View.GONE
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }

    private fun getIntentData() {
        url = intent.getStringExtra("url")!!
        setupWebView(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true;
        webView.settings.domStorageEnabled = true;
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(this@DetailClaimActivity, description, Toast.LENGTH_SHORT).show()
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

        webView.loadUrl(url)
    }
}