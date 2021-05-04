package id.weplus.affiliasi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.helper.FullScreenFilterActivity
import id.weplus.model.response.AreaModel
import id.weplus.model.response.BengkelAreaResponse
import id.weplus.model.response.BengkelPartnerResponse
import id.weplus.model.response.PartnerModel
import id.weplus.model.response.affiliasirumahsakit.AffiliasiRsResponse
import id.weplus.model.response.affiliasirumahsakit.RsOptionData
import id.weplus.model.response.affiliasirumahsakit.RsOptionResponse
import id.weplus.model.response.afiliasibengkel.AffiliasiBengkelResponse
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_affiliation.*
import kotlinx.android.synthetic.main.activity_affiliation.btnNext
import kotlinx.android.synthetic.main.view_back_transparent.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class AffiliationActivity : BaseActivity() {

    private var type=""
    private var mapHospital = HashMap<String,String>()
    private var optionData: RsOptionData? = RsOptionData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliation)
        setupToolbar()
        setupSubmitBtn()
        getBengkelPartner()
        getHospitalOptions()
        setupSelectAfiliasiType()
        setupHospitalCityOption()
        getIntentData()
    }

    fun getIntentData(){
        Log.d("log","get intent data");
        type = intent.getStringExtra("type").toString()
        if(type!=null && type!="" && type!="null"){
            tvRekanan.isEnabled=false
            tvRekanan.text=type
            tvPartner.text=""
            tvArea.text=""
            Log.d("type","type : $type")
            if(type.toLowerCase()!="bengkel"){
                cityLayout.visibility= View.VISIBLE
            }else{
                cityLayout.visibility=View.GONE
            }
        }else{
            Log.d("log","get  intent data $type");
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==20){
            val partner =  data?.getStringExtra("result").toString()
            tvPartner.text=partner
            tvArea.text=""
            if(isBengkel()) {
                getBengkelArea(partner)
            }else{
                setupSelectBengkelArea(ArrayList())
            }
        }
        if(resultCode==21){
            val area =  data?.getStringExtra("result").toString()
            tvArea.text=area
            if(!isBengkel()){
                setupHospitalCityOption()
            }
        }
        if(resultCode==22){
            type =  data?.getStringExtra("result").toString()
            tvRekanan.text=type
            tvPartner.text=""
            tvArea.text=""
            Log.d("type","type : $type")
            if(type.toLowerCase()!="bengkel"){
                cityLayout.visibility= View.VISIBLE
            }else{
                cityLayout.visibility=View.GONE
            }
        }
        if(resultCode==24){
            val city = data?.getStringExtra("result").toString()
            tvCity.text=city
        }
    }

    private fun isBengkel():Boolean{
        return type.toLowerCase()=="bengkel"
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar(){
        viewback_description.text="Cari daftar rekanan terdekat"
        viewback_title.text="Daftar Rekanan"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun getBengkelArea(partner:String){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getBengkelArea(partner)

            call.enqueue(object : Callback<BengkelAreaResponse?> {
                override fun onResponse(call: Call<BengkelAreaResponse?>, response: Response<BengkelAreaResponse?>) {
                    response.body()?.data?.area?.let { setupSelectBengkelArea(it) }
                }

                override fun onFailure(call: Call<BengkelAreaResponse?>, t: Throwable) {
                    showError(this@AffiliationActivity,getString(R.string.network_error))
                }

            })
        } else {
            showError(this,getString(R.string.network_error))
        }
    }

    private fun getBengkelPartner(){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .bengkelPartner

            call.enqueue(object : Callback<BengkelPartnerResponse?> {
                override fun onResponse(call: Call<BengkelPartnerResponse?>, response: Response<BengkelPartnerResponse?>) {
                    response.body()?.data?.partner?.let { setupSelectPartner(it) }
                }

                override fun onFailure(call: Call<BengkelPartnerResponse?>, t: Throwable) {
                    showError(this@AffiliationActivity,t.message)
                }
            })
        } else {
            showError(this,getString(R.string.network_error))
        }
    }

    private fun getHospitalOptions(){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .hospitalOptions

            call.enqueue(object : Callback<RsOptionResponse?> {
                override fun onResponse(call: Call<RsOptionResponse?>, response: Response<RsOptionResponse?>) {
                    Log.d("hospital","hospital get options ${response.body().toString()}")
                    optionData = response.body()?.data
                    if(optionData!=null && optionData!!.options !=null) {
                        mapHospital.clear()
                        for (options in optionData!!.options) {
                            Log.d("hospital","insert to map ${options.insuranceName}")
                            mapHospital[options.insuranceName] = options.partnerId
                        }
                    }
                }

                override fun onFailure(call: Call<RsOptionResponse?>, t: Throwable) {
                    showError(this@AffiliationActivity,t.message)
                }

            })
        } else {
            showError(this,getString(R.string.network_error))
        }
    }

    private fun setupHospitalCityOption(){
        tvCity.setOnClickListener {
            val province = tvArea.text.toString()
            val provincesName = ArrayList<String>()
            if(optionData!=null && optionData?.options!=null){
                val idx = optionData!!.options.indexOfFirst { it.insuranceName==tvPartner.text.toString() }
                if(idx!=-1){
                    val provinces = optionData!!.options[idx].province
                    val provIdx = provinces.indexOfFirst { it.provinceName==province }
                    if(provIdx!=-1){
                        val cities = provinces[provIdx].city
                        val intentBrand = Intent(this@AffiliationActivity, FullScreenFilterActivity::class.java)
                        intentBrand.putStringArrayListExtra("searchList", cities)
                        intentBrand.putExtra("resultCode", 24)
                        startActivityForResult(intentBrand, 14)
                    }
                }
            }
        }
    }

    private fun setupSelectAfiliasiType(){
        tvRekanan.setOnClickListener {
            val affiliationList = ArrayList<String>();
            affiliationList.add("Bengkel")
            affiliationList.add("Rumah Sakit")

            val intentBrand = Intent(this@AffiliationActivity, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", affiliationList)
            intentBrand.putExtra("resultCode", 22)
            startActivityForResult(intentBrand, 12)
        }
    }

    private fun setupSelectPartner(partners:ArrayList<PartnerModel> ) {
        tvPartner.setOnClickListener {
            Log.d("hospital","size : ${mapHospital.size}")
            val partnersName = ArrayList<String>()
            if(type.toLowerCase()=="bengkel") {
                for (partner in partners) {
                    partnersName.add(partner.partner)
                }
            }else{
                for (partner in mapHospital) {
                    partnersName.add(partner.key)
                }
            }
            val intentBrand = Intent(this@AffiliationActivity, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", partnersName)
            intentBrand.putExtra("resultCode", 20)
            startActivityForResult(intentBrand, 10)
        }
    }

    private fun setupSelectBengkelArea(area:ArrayList<AreaModel>){
        tvArea.setOnClickListener {
            val partnersName = ArrayList<String>()
            if (isBengkel()) {
                for (partner in area) {
                    partnersName.add(partner.area)
                }
            }else{
                val value = mapHospital[tvPartner.text]
                val idx = optionData?.options?.indexOfFirst { it.partnerId==value }
                if(idx!=null && idx!=-1) {
                    val rsOptionData = optionData?.options?.get(idx)
                    if(rsOptionData!=null) {
                        for (option in rsOptionData.province) {
                            partnersName.add(option.provinceName)
                        }
                    }
                }
            }
            val intentBrand = Intent(this@AffiliationActivity, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", partnersName)
            intentBrand.putExtra("resultCode", 21)
            startActivityForResult(intentBrand, 11)
        }
    }

    private fun getAffiliasiBengkel(){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getAffiliasiBengkel(tvPartner.text.toString(),tvArea.text.toString())

            call.enqueue(object : Callback<AffiliasiBengkelResponse?> {
                override fun onResponse(call: Call<AffiliasiBengkelResponse?>, response: Response<AffiliasiBengkelResponse?>) {
                   val intent =Intent(this@AffiliationActivity,AffiliationBengkelActivity::class.java)
                    intent.putExtra("data",response.body()?.data)
                    intent.putExtra("city",tvArea.text.toString())
                    startActivity(intent)
                }

                override fun onFailure(call: Call<AffiliasiBengkelResponse?>, t: Throwable) {
                    showError(this@AffiliationActivity,t.message)
                }
            })
        } else {
            showError(this,getString(R.string.network_error))
        }
    }

    private fun getAffiliasiRumahSakit(){
        //TODO ADD loader
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getAffiliasiRs(mapHospital[tvPartner.text],tvCity.text.toString())

            call.enqueue(object : Callback<AffiliasiRsResponse?> {
                override fun onResponse(call: Call<AffiliasiRsResponse?>, response: Response<AffiliasiRsResponse?>) {
                    val intent =Intent(this@AffiliationActivity,AffiliasiHospitalActivity::class.java)
                    intent.putExtra("data",response.body()?.data)
                    intent.putExtra("city",tvCity.text.toString())
                    startActivity(intent)
                }

                override fun onFailure(call: Call<AffiliasiRsResponse?>, t: Throwable) {
                    showError(this@AffiliationActivity,t.message)
                }

            })
        } else {
            showError(this,getString(R.string.network_error))
        }
    }

    private fun setupSubmitBtn(){
        btnNext.setOnClickListener {
            if(type.toLowerCase()=="bengkel"){
                getAffiliasiBengkel()
            }
            else{
                getAffiliasiRumahSakit()
            }
        }
    }
}