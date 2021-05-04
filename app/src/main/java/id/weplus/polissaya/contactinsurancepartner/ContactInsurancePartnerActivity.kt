package id.weplus.polissaya.contactinsurancepartner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.weplus.R
import id.weplus.model.ContactPartner
import kotlinx.android.synthetic.main.activity_contact_insurance_partner.*
import kotlinx.android.synthetic.main.view_back.*

class ContactInsurancePartnerActivity : AppCompatActivity() {
    private lateinit var partner: ContactPartner
    private lateinit var adapter:ContactInsurancePartnerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_insurance_partner)
        setupToolbar()
        getIntentData()
    }

    private fun setupToolbar(){
        viewback_title.text="Hubungi Partner Asuransi"
        viewback_description.text="Hubungi Customer Service Asuransi"
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }

    private fun getIntentData(){
        partner = intent.getParcelableExtra("contact_partner")!!
        setupContactPartnerAdapter()
    }

    private fun setupContactPartnerAdapter(){
        adapter = ContactInsurancePartnerAdapter(this,partner.partner_nama,partner.detail)
        rvPartnerContact.isNestedScrollingEnabled = true
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvPartnerContact.layoutManager = mLayoutManager
        rvPartnerContact.adapter = adapter
        rvPartnerContact.itemAnimator = DefaultItemAnimator()
    }
}