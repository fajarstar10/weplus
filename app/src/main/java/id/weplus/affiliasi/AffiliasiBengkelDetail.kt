package id.weplus.affiliasi

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import id.weplus.R
import id.weplus.model.response.afiliasibengkel.AffiliasiBengkelModel
import kotlinx.android.synthetic.main.activity_affiliasi_bengkel_detail.*
import kotlinx.android.synthetic.main.view_back.*


class AffiliasiBengkelDetail : AppCompatActivity() {
    private lateinit var bengkelData: AffiliasiBengkelModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliasi_bengkel_detail)
        getIntentData()
        setupToolbar()
        setupCallAction()
    }

    private fun getIntentData() {
        bengkelData = intent.getParcelableExtra("data")!!
        if (::bengkelData.isInitialized) {
            tvBengkelAddress.text = bengkelData.address
            tvBengkelName.text = bengkelData.name
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_description.text = "Informasi Rekanan Bengkel"
        viewback_title.text = "Rekanan Bengkel"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun setupCallAction() {
        callIcon.setOnClickListener {
            callPartner()
        }

        callIcon.setOnClickListener {
            callPartner()
        }
    }

    private fun callPartner() {
        if (bengkelData.phone.size > 0 && bengkelData.phone[0].isNotBlank()) {
            MaterialDialog(this).show {

                title(text="Pilih Kontak Bengkel")
                listItemsSingleChoice(items = bengkelData.phone) { _, _, text ->
                    //Toast.makeText(this@AffiliasiBengkelDetail, "test $text", Toast.LENGTH_LONG).show()
                    val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$text"))
                    startActivity(intentDial)
                }
                positiveButton(text="Panggil")
                negativeButton(text="batal") { dialog ->
                   dialog.dismiss()
                }
            }
        } else {
            Toast.makeText(this, "Tidak ada kontak bengkel", Toast.LENGTH_SHORT).show()
        }
    }
}