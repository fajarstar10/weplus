package id.weplus.mainfragment

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.google.gson.Gson
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import id.weplus.R
import id.weplus.ResponseBeranda
import id.weplus.VerifikasiOTP
import id.weplus.WelcomeActivity
import id.weplus.model.OTPModel
import id.weplus.net.*
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_web_view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentClaim : Fragment() {

    private var url:String = "https://demo.weplus.id/claim//claim/user/9394"
    private var TAG="FragmentClaim"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_claim, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getHome()
    }
    private fun getHome() {
        loadingProgress.visibility = View.VISIBLE
        val isNetworkAvailable: Boolean = Util.isNetworkAvailable(activity!!.applicationContext)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(activity)
            val call = NetworkManager.getNetworkServiceWithHeader(activity, token).home
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i(TAG, "Home Response : " + response.body())

                    // todo pastikan response apinya sama dengan di postman
                    val gson = Gson()
                    val responseBeranda = gson.fromJson(response.body(), ResponseBeranda::class.java)
                    val berandaData = responseBeranda.getData()
                    try {
                        val job = JSONObject(response.body())
                        val code = job.getString("code")
                        val description = job.getString("message")
                        when (code) {
                            ErrorCode.ERROR_00 -> {
                                url = berandaData.claimWebview
                                setupWebView(url)
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(activity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            ErrorCode.ERROR_13 -> {
                                Facad.getInstance(context).getRequestWithToken(WeplusConfig.DOMAIN_URL + "otp/reset/", Request.Method.POST, token, { result ->
                                    Log.i("resetotp", result)
                                    val gson = Gson()
                                    val otpModel = gson.fromJson<OTPModel>(result, OTPModel::class.java)
                                    if (otpModel.code == ErrorCode.ERROR_00) {
                                        val intent = Intent(context, VerifikasiOTP::class.java)
                                        intent.putExtra("value_otp", otpModel.data.otp)
                                        startActivity(intent)
                                    }
                                }, { })
                            }
                            else -> {
                                SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(description)
                                        .show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, e.message.toString())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show()
                    Log.i(TAG, "On FAILUR : " + t.message)
                }
            })
        } else {
            SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("")
                    .setContentText(getString(R.string.network_error))
                    .show()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true;
        webView.settings.domStorageEnabled = true;
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingProgress.visibility = View.GONE
                Log.d("claim url", "url : $url")
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loadingProgress.visibility = View.VISIBLE
            }
        }

        webView.loadUrl(url)
    }
}