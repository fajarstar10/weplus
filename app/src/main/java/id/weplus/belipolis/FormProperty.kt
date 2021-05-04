package id.weplus.belipolis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.helper.FullScreenFilterActivity
import id.weplus.helper.OnOptionsSelect
import id.weplus.helper.RoundedBottomSheet
import id.weplus.model.BaseFilter
import id.weplus.model.City
import id.weplus.model.Product
import id.weplus.model.Province
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.response.CityListResponse
import id.weplus.model.response.ProvinceListResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_property.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FormProperty : BaseActivity(), OnOptionsSelect {
    private lateinit var product: Product
    private lateinit var dataTertanggung: DataTertanggungRequest

    private val tag = "formproperty"
    private var cities = ArrayList<City>()
    private var mapProvince = HashMap<Int, String>()
    private var province: String = ""
    private var city: String = ""
    private var property=""
    private val propertyTipeTitle = "Pilih tipe property"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_property)
        setupToolbar()
        getIntentData()
        setupButtonAction()
        fetchProvince()
        setupTipeProperty()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            22 -> {
                province = data?.getStringExtra("result").toString()
                tvProvince.text = province
                val provinceId = mapProvince.filterValues { it == province }.keys.toIntArray()[0]
                fetchCity("" + provinceId)
                tvCity.isEnabled = true
            }
            23 -> {
                city = data?.getStringExtra("result").toString()
                tvCity.text = city
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_title.text = "Data Property"
        viewback_description.text = "Silahkan Lengkapi Data Property yang di asuransikan"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun getIntentData() {
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!

        // hide field based on partner
        if (product.partner_id == "13") {
            nameWrapper.visibility = View.GONE
            tipePropertyWrapper.visibility = View.VISIBLE
        }

        if (product.partner_id == "9") {
            tipePropertyWrapper.visibility = View.GONE
            nameWrapper.visibility=View.GONE
        }

        // autofill for update event
        if (dataTertanggung.beneficiaryName != null) {
            etName.setText(dataTertanggung.beneficiaryName)
        }
        if (dataTertanggung.occupation != null) {
            property = dataTertanggung.occupation
            tvProperty.text = property
        }
        if (dataTertanggung.insuredAddress != null) {
            etAddress.setText(dataTertanggung.insuredAddress)
        }
        if (dataTertanggung.insuredProvince != null) {
            province=dataTertanggung.insuredProvince
            tvProvince.text = dataTertanggung.insuredProvince
        }
        if (dataTertanggung.insuredCity != null) {
            city=dataTertanggung.insuredCity
            tvCity.text = dataTertanggung.insuredCity
        }
        if (dataTertanggung.zipCode != null) {
            etZipCode.setText(dataTertanggung.zipCode)
        }
    }

    private fun fetchProvince() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .provinceList
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), ProvinceListResponse::class.java)
                    try {
                        when (resp.code) {
                            ErrorCode.ERROR_00 -> {
                                setupSelectProvince(resp.data.countryArrayList)
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@FormProperty, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                showError(this@FormProperty, resp.message)
                            }
                        }
                    } catch (e: Exception) {
                        Log.i(tag, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormProperty, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun setupTipeProperty(){
        val baseFilter:ArrayList<BaseFilter>  = ArrayList()
        baseFilter.add(BaseFilter(0, "Rumah Tinggal", "Rumah Tinggal"))
        baseFilter.add(BaseFilter(1, "Apartemen", "Apartemen"))
        tvProperty.setOnClickListener {
            val roundedBottomSheet = RoundedBottomSheet(this)
            val bundle = Bundle()
            bundle.putString("title", propertyTipeTitle)
            bundle.putParcelableArrayList("baseFilter", baseFilter)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormProperty.supportFragmentManager, "test")
        }
    }

    private fun setupButtonAction() {
        btnSimpan.setOnClickListener {
            if (validate()) {
                if (product.partner_id == "13") dataTertanggung.occupation = property
                //if (product.partner_id == "9") dataTertanggung.beneficiaryName = etName.text.toString()
                dataTertanggung.insuredAddress = etAddress.text.toString()
                dataTertanggung.insuredProvince = province
                dataTertanggung.insuredCity = city
                dataTertanggung.zipCode = etZipCode.text.toString()
                intent.putExtra("data_tertanggung", dataTertanggung)
                setResult(72, intent)
                finish()
            }
        }
    }

    private fun validate(): Boolean {
        val nameValidate = !etName.text.isNullOrBlank()
        val occupationValidate = !property.isNullOrBlank()
        val addressValidate = !etAddress.text.isNullOrBlank()
        val provinceValidate = province.isNotBlank()
        val cityValidate = city.isNotBlank()
        val zipCodeValidate = etZipCode.text.length == 5

        //show error text
        if (nameValidate) errorTextName.visibility = View.GONE else errorTextName.visibility = View.VISIBLE
        if (occupationValidate) errorTextTipeProperty.visibility = View.GONE else errorTextTipeProperty.visibility = View.VISIBLE
        if (addressValidate) errorTextAddress.visibility = View.GONE else errorTextAddress.visibility = View.VISIBLE
        if (provinceValidate) errorTextProvince.visibility = View.GONE else errorTextProvince.visibility = View.VISIBLE
        if (cityValidate) errorTextCity.visibility = View.GONE else errorTextCity.visibility = View.VISIBLE
        if (zipCodeValidate) errorTextZipCode.visibility = View.GONE else errorTextZipCode.visibility = View.VISIBLE

        return if (product.partner_id == "13") {
            (occupationValidate && addressValidate && provinceValidate && cityValidate && zipCodeValidate)
        } else {
            //(nameValidate && addressValidate && provinceValidate && cityValidate && zipCodeValidate)
            (addressValidate && provinceValidate && cityValidate && zipCodeValidate)
        }
    }

    private fun fetchCity(provinceId: String) {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getCityListByProvince(provinceId)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), CityListResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            cities.clear()
                            cities.addAll(resp.data.cityArrayList)
                            setupSelectCity()
                        } else {
                            showError(this@FormProperty, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(tag, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormProperty, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormProperty, getString(R.string.network_error))
        }
    }

    private fun setupSelectCity() {
        tvCity.setOnClickListener {
            val cityNames = ArrayList<String>()
            for (city in cities) {
                cityNames.add(city.name)
            }
            val intentBrand = Intent(this@FormProperty, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", cityNames)
            intentBrand.putExtra("resultCode", 23)
            startActivityForResult(intentBrand, 13)
        }
    }

    private fun setupSelectProvince(provinces: ArrayList<Province>) {
        val provinceNames = ArrayList<String>()
        for (province in provinces) {
            provinceNames.add(province.name)
            mapProvince[province.id] = province.name
        }
        tvProvince.setOnClickListener {
            val intentBrand = Intent(this@FormProperty, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", provinceNames)
            intentBrand.putExtra("resultCode", 22)
            startActivityForResult(intentBrand, 12)
        }
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
      property=baseFilter.filterText.toLowerCase()
        tvProperty.text=baseFilter.filterText
    }


}