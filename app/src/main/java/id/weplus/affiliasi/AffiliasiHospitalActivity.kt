package id.weplus.affiliasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.weplus.R
import id.weplus.model.response.affiliasirumahsakit.AffiliasiRsData
import id.weplus.model.response.affiliasirumahsakit.AffiliasiRsModel
import id.weplus.model.response.afiliasibengkel.AffiliasiBengkelData
import kotlinx.android.synthetic.main.activity_affiliation_bengkel.*
import kotlinx.android.synthetic.main.view_back.*

class AffiliasiHospitalActivity : AppCompatActivity() {
    private var rsData: AffiliasiRsData = AffiliasiRsData()
    private var city:String=""
    private lateinit var adapter:AffiliasiHospitalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliasi_hospital)
        getIntentData()
        setupToolbar()
        setupListHospital()
    }

    private fun setupToolbar(){
        viewback_buttonback.setOnClickListener { finish() }
        viewback_title.text="Daftar Rumah Sakit"
        viewback_description.text="Daftar Rumah Sakit Area $city"
    }

    private fun getIntentData(){
        rsData = intent.getParcelableExtra("data")!!
        city = intent.getStringExtra("city").toString()
    }

    private fun setupListHospital(){
        adapter = AffiliasiHospitalAdapter(this,rsData.hospital)
        rvBengkel.isNestedScrollingEnabled = true
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvBengkel.layoutManager = mLayoutManager
        rvBengkel.adapter = adapter
        rvBengkel.itemAnimator = DefaultItemAnimator()
    }
}