package id.weplus.belipolis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.helper.FullScreenFilterActivity
import id.weplus.model.Plat
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.response.CarBrandResponse
import id.weplus.model.response.CarFilterResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_data_mobil2.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class FormDataMobil : BaseActivity() {
    private lateinit var dataTertanggung: DataTertanggungRequest
    private lateinit var product: Product
    private val TAG ="formdatamobilbelipolis"
    var mapPlate = HashMap<Int, String>()
    private val reqPlate = 10
    private val resPlate = 20
    private val reqBrand = 12
    private val resBrand = 22

    private var plateId = 0
    private var plateName = ""
    private var brand = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_mobil2)
        setupToolbar()
        getIntentData()
        fetchCarFilter()
        fetchCarBrand()
        setupSaveButton()
    }

    private fun setupSaveButton() {
        btnSimpan.setOnClickListener {
            if(validate()){
                dataTertanggung.engineNo = etCarEngine.text.toString()
                dataTertanggung.carFrame = etCarFrame.text.toString()
                dataTertanggung.platNo = plateName
                dataTertanggung.platId=plateId
                dataTertanggung.carSeries = spinnerSeri.text.toString()
                dataTertanggung.carBrand = brand
                intent.putExtra("data_tertanggung", dataTertanggung)
                setResult(62, intent)
                finish()
            }
        }
    }

    private fun validate():Boolean{
        val brandValidate = brand.isNotBlank()
        val seriesValidate = spinnerSeri.text.isNotBlank()
        val platValidate = spinnerPlate.text.isNotBlank()
        val frameValidate = etCarFrame.text.isNotBlank()
        val engineValidate = etCarEngine.text.isNotBlank()

        if(brandValidate){errorTextMerek.visibility=View.GONE}else{errorTextMerek.visibility=View.VISIBLE}
        if(seriesValidate){errorTextSeries.visibility=View.GONE}else{errorTextSeries.visibility=View.VISIBLE}
        if(platValidate){errorTextCarPlate.visibility=View.GONE}else{errorTextCarPlate.visibility=View.VISIBLE}
        if(frameValidate){errorTextRangka.visibility=View.GONE}else{errorTextRangka.visibility=View.VISIBLE}
        if(engineValidate){errorTextCarEngin.visibility=View.GONE}else{errorTextCarEngin.visibility=View.VISIBLE}

        return(brandValidate && seriesValidate && platValidate && frameValidate && engineValidate)
    }

    private fun setupCarBrand(spinnerArray: ArrayList<String>) {
        datamobil_merek_mobil.setOnClickListener {
            val intentBrand = Intent(this@FormDataMobil, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", spinnerArray)
            intentBrand.putExtra("resultCode", resBrand)
            startActivityForResult(intentBrand, reqBrand)
        }

        spinnerSeri.setOnClickListener {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == reqPlate) {
            if (resultCode == resPlate) {
                plateId = data!!.getStringExtra("result")?.let { getPlatId(it) }!!
                plateName = data.getStringExtra("result")!!
                spinnerPlate.text = data.getStringExtra("result")
            }
        } else if (requestCode == reqBrand) {
            if (resultCode == resBrand) {
                brand = data!!.getStringExtra("result")!!
                datamobil_merek_mobil.text = brand
            }
        }
    }

    private fun setupToolbar() {
        viewback_title.text = "Data Mobil"
        viewback_description.text = "Silahkan Lengkapi Data Mobil yang di asuransikan"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun getIntentData() {
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!

        //pre-populate field if not null
        if (dataTertanggung.carBrand != null) {
            brand = dataTertanggung.carBrand
            datamobil_merek_mobil.text=dataTertanggung.carBrand
        }
        if (dataTertanggung.carSeries != null) {
            spinnerSeri.setText(dataTertanggung.carSeries)
        }
        if(dataTertanggung.platNo!=null){
            plateId = dataTertanggung.platId
            plateName=dataTertanggung.platNo
            spinnerPlate.text=dataTertanggung.platNo
        }
        if(dataTertanggung.carFrame!=null){
            etCarFrame.setText(dataTertanggung.carFrame)
        }
        if(dataTertanggung.engineNo!=null){
            etCarEngine.setText(dataTertanggung.engineNo)
        }
    }

    private fun setupCarPlateSpinner(plates: ArrayList<Plat>) {
        val plateList = ArrayList<String>();
        for (item in plates) {
            plateList.add(item.plat);
            mapPlate[item.id] = item.plat
        }

        spinnerPlate.setOnClickListener {
            val intentBrand = Intent(this, FullScreenFilterActivity::class.java);
            intentBrand.putStringArrayListExtra("searchList", plateList);
            intentBrand.putExtra("resultCode", resPlate);
            startActivityForResult(intentBrand, reqPlate);
        }
    }


    private fun fetchCarFilter() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .carFilter
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), CarFilterResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            setupCarPlateSpinner(resp.data.plat)
                        } else if (resp.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@FormDataMobil, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            showError(this@FormDataMobil, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataMobil, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun fetchCarBrand() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .carBrand
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), CarBrandResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            val brands = java.util.ArrayList<String>()
                            for (i in resp.data.brand.indices) {
                                brands.add(resp.data.brand[i].brand)
                            }
                            setupCarBrand(brands)
                        } else {
                            showError(this@FormDataMobil, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataMobil, "Time Out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormDataMobil, getString(R.string.network_error))
        }
    }

    private fun getPlatId(plat: String): Int {
        var platId = 0
        for ((key, value) in mapPlate) {
            if (value == plat) {
                platId = key
                break
            }
        }
        return platId
    }
}