package id.weplus.affiliasi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import id.weplus.R
import id.weplus.model.response.affiliasirumahsakit.AffiliasiRsModel
import kotlinx.android.synthetic.main.activity_affiliasi_bengkel_detail.*
import kotlinx.android.synthetic.main.activity_affiliasi_bengkel_detail.callIcon
import kotlinx.android.synthetic.main.activity_affiliasi_bengkel_detail.tvBengkelAddress
import kotlinx.android.synthetic.main.activity_affiliasi_bengkel_detail.tvBengkelName
import kotlinx.android.synthetic.main.activity_affiliasi_bengkel_detail.tvHubungi
import kotlinx.android.synthetic.main.activity_affiliation_hospital_detail.*
import kotlinx.android.synthetic.main.view_back.*


class AffiliasiHospitalDetailActivity : AppCompatActivity() {
    private lateinit var rumahSakitData: AffiliasiRsModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliation_hospital_detail)
        getIntentData()
        setupToolbar()
        setupCallAction()
    }

    private fun getIntentData() {
        rumahSakitData = intent.getParcelableExtra("data")!!
        if (::rumahSakitData.isInitialized) {
            tvBengkelAddress.text = rumahSakitData.address
            tvBengkelName.text = rumahSakitData.name
        }
    }

    private fun setupToolbar() {
        viewback_description.text = "Informasi Rekanan Rumah Sakit"
        viewback_title.text = "Rekanan Rumah Sakit"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun setupCallAction() {
        Log.d("toast", "size phone : " + rumahSakitData.phone.size)

        tvHubungi.setOnClickListener {
            callPartner()
        }

        callIcon.setOnClickListener {
            callPartner()
        }
    }

    private fun callPartner() {
        if (rumahSakitData.phone.size > 0 && rumahSakitData.phone[0].isNotBlank()) {
            MaterialDialog(this).show {

                title(text="Pilih Kontak Rumah Sakit")
                listItemsSingleChoice(items = rumahSakitData.phone) { _, _, text ->
                    val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$text"))
                    startActivity(intentDial)
                }
                positiveButton(text="Panggil")
                negativeButton(text="batal") { dialog ->
                    dialog.dismiss()
                }
            }
        } else {
            Toast.makeText(this, "Tidak ada kontak Rumah Sakit", Toast.LENGTH_SHORT).show()
        }
    }

}