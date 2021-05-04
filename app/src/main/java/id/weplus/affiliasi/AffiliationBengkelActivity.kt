package id.weplus.affiliasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.weplus.R
import id.weplus.model.response.afiliasibengkel.AffiliasiBengkelData
import kotlinx.android.synthetic.main.activity_affiliation_bengkel.*
import kotlinx.android.synthetic.main.view_back.*

class AffiliationBengkelActivity : AppCompatActivity() {
    private var bengkelData:AffiliasiBengkelData= AffiliasiBengkelData()
    private var city:String=""
    private lateinit var adapter : AffiliasiBengkelAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliation_bengkel)
        getIntentData()
        setupToolbar()
        setupListBengkel()
    }

    private fun setupToolbar(){
        viewback_buttonback.setOnClickListener { finish() }
        viewback_title.text="Daftar Bengkel"
        viewback_description.text="Daftar Bengkel Area $city"
    }

    private fun getIntentData(){
        bengkelData = intent.getParcelableExtra("data")!!
        city = intent.getStringExtra("city").toString()
    }
    
    private fun setupListBengkel(){
        adapter = AffiliasiBengkelAdapter(this,bengkelData.bengkel)
        rvBengkel.isNestedScrollingEnabled = true
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvBengkel.layoutManager = mLayoutManager
        rvBengkel.adapter = adapter
        rvBengkel.itemAnimator = DefaultItemAnimator()
    }
}