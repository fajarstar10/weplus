package id.weplus.pemegangpolis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.helper.DatePickerFragment
import id.weplus.helper.FullScreenFilterActivity
import id.weplus.helper.OnDateSetListener
import id.weplus.helper.OnOptionsSelect
import id.weplus.model.BaseFilter
import id.weplus.model.City
import id.weplus.model.Province
import id.weplus.model.response.BaseResponse
import id.weplus.model.response.CityListResponse
import id.weplus.model.response.ProvinceListResponse
import id.weplus.model.response.insureduser.InsuredUser
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_pemegang_polis.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FormPemegangPolisActivity : BaseActivity(), OnDateSetListener, OnOptionsSelect {

    private val tag = "FormPemegangPolisActivity"
    private var dob: String = ""
    private var province: String = ""
    private var city: String = ""
    private var TAG = "FormPemegangPolisActivity"
    private var sex = ""
    private var cities = ArrayList<City>()
    private var mapProvince = HashMap<Int, String>()
    private lateinit var insuredUser: InsuredUser
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_tertanggung)
        getIntentData()
        fetchProvince()
        setupToolbar()
        setupDobField()
        setupSubmitButton()
        setupSpinnerGender()
        setupSelectCity()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            22 -> {
                province = data?.getStringExtra("result").toString()
                tvProvince.text = province
                tvCity.text = ""
                city = ""
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

    private fun getIntentData() {
        if (intent.getParcelableExtra<InsuredUser>("insured_user") != null) {
            insuredUser = intent.getParcelableExtra("insured_user")!!
            checkMode()
        }
    }

    private fun checkMode() {
        if (::insuredUser.isInitialized) {
            isEdit = true
            populateView()
        }
    }

    private fun populateView() {
        etPhoneNumber.setText(insuredUser.phone)
        etFullName.setText(insuredUser.fullname)
        etEmail.setText(insuredUser.email)
        etAddress.setText(insuredUser.address)
        etPostal.setText(insuredUser.zip_code)
        etIdNumber.setText(insuredUser.identification_no)
        tvProvince.text = insuredUser.province
        tvCity.text = insuredUser.city
        tvBirthDate.text = insuredUser.dob
        province = insuredUser.province
        city = insuredUser.city
        dob = insuredUser.dob
        sex = insuredUser.sex
        if(insuredUser.sex=="m"){
            tvGender.setSelection(1)
        }else{
            tvGender.setSelection(2)
        }
    }

    private fun setupSpinnerGender() {
        tvGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                when (pos) {
                    0 -> sex = ""
                    1 -> sex = "m"
                    2 -> sex = "f"
                }
            }
        }
    }

    private fun setupSubmitButton() {
        btnNext.setOnClickListener {
            if (validate()) {
                val id = if (isEdit)
                    insuredUser.id else 0f
                insuredUser = InsuredUser(
                        id,
                        etFullName.text.toString(),
                        etPhoneNumber.text.toString(),
                        etEmail.text.toString(),
                        etAddress.text.toString(),
                        dob,
                        sex,
                        etPostal.text.toString(),
                        province,
                        city,
                        etIdNumber.text.toString()
                )
                if (!isEdit)
                    addInsuredUser()
                else
                    updateInsuredUser()
            }
        }
    }

    private fun updateInsuredUser() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .updateInsuredUser("" + insuredUser.id.toInt(), insuredUser)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), BaseResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            Log.d("test", "show dialog")
                            com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog(this@FormPemegangPolisActivity, com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Sukses")
                                    .setContentText("Data Tertanggung berhasil di ubah")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener { sDialog: com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog ->
                                        sDialog.dismissWithAnimation()
                                        finish()
                                    }
                                    .show()

                            // finish()
                        } else {
                            showError(this@FormPemegangPolisActivity, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormPemegangPolisActivity, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun addInsuredUser() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .addInsuredUser(insuredUser)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), BaseResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            Log.d("test", "show dialog sukses")
                            com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog(this@FormPemegangPolisActivity, com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Sukses")
                                    .setContentText("Data Tertanggung berhasil ditambahkan")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener { sDialog: com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog ->
                                        sDialog.dismissWithAnimation()
                                        finish()
                                    }
                                    .show()

                            //finish()
                        } else {
                            showError(this@FormPemegangPolisActivity, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormPemegangPolisActivity, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }


    override fun onDateSet(c: Calendar?, cat: String?) {
        if (c != null) {
            dob = c[Calendar.YEAR].toString() + "-" + c[Calendar.MONTH+1] + "-" + c[Calendar.DAY_OF_MONTH]
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(c.time)
            tvBirthDate.text = newDateStr
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_title.text = "Tertanggung"
        viewback_description.text = "Silahkan lengkapi data tertanggung"
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }

    private fun setupDobField() {
        tvBirthDate.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment.newInstance("dob_life", this)
            newFragment.show(supportFragmentManager, "datePicker")
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
                        if (resp.code == ErrorCode.ERROR_00) {
                            setupSelectProvince(resp.data.countryArrayList)
                        } else {
                            showError(this@FormPemegangPolisActivity, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormPemegangPolisActivity, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun fetchCity(provinceId: String) {
        Log.d("fetch City", "fetch city")
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
                        } else {
                            showError(this@FormPemegangPolisActivity, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormPemegangPolisActivity, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormPemegangPolisActivity, getString(R.string.network_error))
        }
    }

    private fun setupSelectCity() {
        tvCity.setOnClickListener {
            val cityNames = ArrayList<String>()
            for (city in cities) {
                cityNames.add(city.name)
            }
            val intentBrand = Intent(this@FormPemegangPolisActivity, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", cityNames)
            intentBrand.putExtra("resultCode", 23)
            startActivityForResult(intentBrand, 13)
        }
    }

    private fun setupSelectProvince(provinces: ArrayList<Province>) {
        Log.d(tag, "setup Select province")
        val provinceNames = ArrayList<String>()
        mapProvince.clear()
        for (province in provinces) {
            provinceNames.add(province.name)
            mapProvince[province.id] = province.name
        }

        if (::insuredUser.isInitialized && !insuredUser.province.isNullOrBlank()) {
            val keys = mapProvince.filterValues { it == insuredUser.province }.keys
            if (keys.isNotEmpty()) {
                fetchCity("${keys.first()}")
            }
        }

        tvProvince.setOnClickListener {
            Log.d(tag, "setup Select province clicked")
            val intentBrand = Intent(this@FormPemegangPolisActivity, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", provinceNames)
            intentBrand.putExtra("resultCode", 22)
            startActivityForResult(intentBrand, 12)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun validate(): Boolean {
        var result = false
        val fullNameValidate = etFullName.text.isNotBlank()
        var idNoValidate = etIdNumber.text.length == 16
        val dobValidate = dob.isNotBlank()
        val genderValidate = tvGender.selectedItemPosition != 0
        val phoneValidate = etPhoneNumber.text.isNotBlank()
        val emailValidate = etEmail.text.isNotBlank()
        val addressValidate = etAddress.text.isNotBlank()
        val provinceValidate = province.isNotBlank()
        val cityValidate = city.isNotBlank()

        errorTextFullName.visibility = if (fullNameValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }
        errorTextIdNo.visibility = if (idNoValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }
        errorTextPhoneNumber.visibility = if (phoneValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }
        errorTextGender.visibility = if (genderValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }
        errorTextBirthDate.visibility = if (dobValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        errorTextEmail.visibility = if (emailValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }
        errorTextAddress.visibility = if (addressValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }
        errorTextProvince.visibility = if (provinceValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }
        errorTextCity.visibility = if (cityValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        if (etIdNumber.text.length > 16) {
            idNoValidate = false
            errorTextIdNo.text = "Maksimum 16 Angka"
            errorTextIdNo.visibility = View.VISIBLE
        }

        result =
                (fullNameValidate && idNoValidate && dobValidate && genderValidate
                        && phoneValidate && emailValidate && addressValidate && provinceValidate
                        && cityValidate)
        Log.d("result", "validate result : " + result)
        Log.d("result", "validate result fn: " + fullNameValidate)
        Log.d("result", "validate result id: " + idNoValidate)
        Log.d("result", "validate result dob: " + dobValidate)
        Log.d("result", "validate result sex: " + genderValidate)
        Log.d("result", "validate result ohone: " + phoneValidate)
        Log.d("result", "validate result email: " + emailValidate)
        Log.d("result", "validate result address: " + addressValidate)
        Log.d("result", "validate result province: " + provinceValidate)
        Log.d("result", "validate result city: " + cityValidate)
        return result
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        //
    }
}