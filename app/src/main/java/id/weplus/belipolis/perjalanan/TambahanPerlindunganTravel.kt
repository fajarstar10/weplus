package id.weplus.belipolis.perjalanan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.OnClick
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.belipolis.motor.AdditionalProtectionAdapter
import id.weplus.model.BaseFilter
import id.weplus.model.request.TravelProductListRequest
import kotlinx.android.synthetic.main.activity_tambahan_perlindungan_travel.*
import java.util.*

class TambahanPerlindunganTravel : BaseActivity() {
    private val additonalProtection = ArrayList<BaseFilter>()
    private var request: TravelProductListRequest? = null
    private var adapter: AdditionalProtectionAdapter? = null
    private var isCruise=0
    private var isSport=0
    private var isActivity=0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambahan_perlindungan_travel)
        activityTitle.text = "Tambahan Perlindungan"
        description?.text = "Tambahan Perlidungan (opsional)"
        backButton.setOnClickListener {
            finish()
        }
        data
        setupRecyclerView()
        setupButton()
    }

    private fun setupButton() {
        btnLanjutkan.setOnClickListener {
            request?.is_activity=isActivity
            request?.is_sport=isSport
            request?.is_cruise=isCruise
            request?.addition_protection = selectedProtection
            val intent = Intent(this, PolisPerjalananActivity::class.java)
            intent.putExtra("requestBody", request)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = AdditionalProtectionAdapter(this, additonalProtection)
        rvPerlindungan?.layoutManager = LinearLayoutManager(this)
        rvPerlindungan?.adapter = this.adapter
    }

    private val data: Unit
        get() {
            request = intent.getParcelableExtra("requestBody")!!
            additonalProtection.addAll(intent.getParcelableArrayListExtra("addProtection")!!)
        }

    @OnClick(R.id.viewback_buttonback)
    fun actionBackPembayaran() {
        finish()
    }

    private val selectedProtection: String
        get() {
            var selected = ""
            for (i in 0 until adapter!!.itemCount) {
                if (adapter!!.list[i].isSelected) {
                    val id = adapter!!.list[i].id
                    selected += "$id,"
                    isCruise = if(id==50){ 1 }else{ 0 }
                    isActivity = if(id==52){ 1 }else{ 0 }
                    isSport = if(id==51){ 1 }else{ 0 }
                }
            }
            if (selected != "") {
                selected = selected.substring(0, selected.length - 1)
            }
            return selected
        }

}
