package id.weplus.detailpolis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import id.weplus.R
import id.weplus.WebViewActivity
import kotlinx.android.synthetic.main.activity_detail_polis_contact_us.*
import kotlinx.android.synthetic.main.view_back.*

class DetailPolisContactUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_polis_contact_us)
        setupToolbar()
        setupCallAction()
        sendEmailAction()
        setupChatWebView()
    }

    private fun setupChatWebView(){
        tvChatStart.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url","https://tawk.to/chat/5c5b93816cb1ff3c14cb6f9e/default")
            startActivity(intent)
        }
    }

    private fun setupToolbar(){
        viewback_title.text="Kontak Kami"
        viewback_description.text=""
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }

    private fun setupCallAction(){
        tvPhoneCall.setOnClickListener {
            SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Telepon CS")
                    .setContentText("Apakah anda ingin menghubungi CS(Customer Service) WE+")
                    .setCancelText("Batal")
                    .setConfirmText("Ya")
                    .showCancelButton(true)
                    .setCancelClickListener { sDialog -> sDialog.dismiss() }
                    .setConfirmClickListener {
                        val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.fromParts(
                                "tel", tvPhone.text.toString(), null))
                        startActivity(phoneIntent)
                        it.dismiss()
                    }
                    .show()

        }
    }

    private fun sendEmailAction(){
        tvCsEmail.setOnClickListener {
            SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Kirim Email")
                    .setContentText("Apakah anda ingin Mengirimkan email ke tim  CS WE+")
                    .setCancelText("Batal")
                    .setConfirmText("Ya")
                    .showCancelButton(true)
                    .setCancelClickListener { sDialog -> sDialog.dismiss() }
                    .setConfirmClickListener {
                        val email = Intent(Intent.ACTION_SEND)
                        email.type = "application/octet-stream"
                        startActivity(email)
                        it.dismiss()
                    }
                    .show()

        }
    }
}