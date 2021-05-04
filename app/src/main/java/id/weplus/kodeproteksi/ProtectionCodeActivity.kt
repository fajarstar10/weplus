package id.weplus.kodeproteksi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.weplus.R
import id.weplus.WebViewActivity
import id.weplus.helper.BarCodeActivity
import id.weplus.utility.Constant
import id.weplus.utility.FirebaseAnalyticsHelper.Factory.logEvent
import kotlinx.android.synthetic.main.activity_protection_code.*
import kotlinx.android.synthetic.main.view_back.*

class ProtectionCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protection_code)
        setToolbar()
        setupButtonAction()
    }

    private fun setupButtonAction() {
        btnUse.setOnClickListener {
            logEvent(this, Constant.ANALYTICS_USE_CODE_PROTECTION)
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", "https://uatprotect.weplus.id/codeprotection/")
            intent.putExtra("code", etCodeProtection.text.toString().toUpperCase())
            startActivity(intent)
        }

        imgQr.setOnClickListener {
            startActivity(Intent(this, BarCodeActivity::class.java))
        }
    }

    private fun setToolbar() {
        viewback_title.text="Kode Proteksi"
        viewback_description.text="Masukkan data dan terproteksi sekarang !"
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }
}