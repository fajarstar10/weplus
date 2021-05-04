package id.weplus.belipolis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.helper.*
import id.weplus.model.BaseFilter
import id.weplus.model.City
import id.weplus.model.Product
import id.weplus.model.Province
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.response.*
import id.weplus.model.response.insureduser.InsuredUser
import id.weplus.model.response.insureduser.InsuredUserResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_data_tertanggung.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FormDataTertanggung : BaseActivity(), OnDateSetListener, OnOptionsSelect, OnUserSelect {

    private var relation: String=""
    private val tag="FormDataTertanggung"
    private var dob: String = ""
    private var province: String = ""
    private var city: String = ""
    private var district: String = ""
    private var subDistrict: String = ""
    private var TAG = "FormDataTertanggung"
    private var sex = ""
    private var catId = 0
    private lateinit var product: Product
    private var cities = ArrayList<City>()
    private var districts = ArrayList<City>()
    private var subDistricts = ArrayList<City>()
    private var mapProvince = HashMap<Int, String>()
    private var mapCity = HashMap<Int, String>()
    private var mapDistrict = HashMap<Int, String>()
    private var dataTertanggungRequest = DataTertanggungRequest()
    private var insuredUsers = ArrayList<InsuredUser>()
    private lateinit var insuredBottomSheet:InsuredUserBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_tertanggung)
        getData()
        fetchProvince()
        setupToolbar()
        setupDobField()
        setupSubmitButton()
        setupSpinnerGender()
        setupSelectCity()
        setupSelectDistrict()
        setupSelectSubDistrict()
        getInsuredUsers()
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
                if (product.partner_id == "17") {
                    val cityIndex = cities.indexOfFirst { it.name == city }
                    Log.d("city", "index $cityIndex")
                    if (cityIndex != -1) {
                        tvDistrict.isEnabled = true
                        fetchDistrict("" + cities[cityIndex].id)
                    }
                }
            }
            24 -> {
                district = data?.getStringExtra("result").toString()
                tvDistrict.text = district
                val districtIndex = districts.indexOfFirst { it.name == district }
                if (districtIndex != -1) {
                    tvSubDistrict.isEnabled = true
                    fetchSubDistrict("" + districts[districtIndex].id)
                }
            }
            25 -> {
                subDistrict = data?.getStringExtra("result").toString()
                tvSubDistrict.text = subDistrict
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        dataTertanggungRequest = intent.getParcelableExtra("data_tertanggung")!!
        catId = intent.getIntExtra("cat_id", 0)!!
        product = intent.getParcelableExtra("product_detail")!!
        //IF Critical Illness
        if (catId == 12) {
            heightWrapper.visibility = View.VISIBLE
            weightWrapper.visibility = View.VISIBLE
            if (dataTertanggungRequest.height != 0) {
                etHeight.setText("" + dataTertanggungRequest.height)
            }

            if (dataTertanggungRequest.weight != 0) {
                etWeight.setText("" + dataTertanggungRequest.height)
            }
            tvBirthDate.isEnabled = false
            tvGender.isEnabled = false
            dob=dataTertanggungRequest.dob
            sex=dataTertanggungRequest.sex
            Log.d("data tertanggung", "data tertanggung sex : ${dataTertanggungRequest.sex}")

        }
        if(catId ==14){
            val isAbove21:Int = intent.getIntExtra("isAbove21", -1)
            if(isAbove21==1){
                relation="suami";
            }
//            relationWrapper.visibility=View.VISIBLE
//            if(dataTertanggungRequest.relation!=null){
//                relation = dataTertanggungRequest.relation
//                tvRelation.text = dataTertanggungRequest.relation
//            }
//            fetchRelation()
        }

        if (catId == 4) {
            tvGender.isEnabled=false
            tvBirthDate.isEnabled=false
            pobWrapper.visibility = View.VISIBLE
            if (dataTertanggungRequest.pob != null) {
                etPob.setText(dataTertanggungRequest.pob)
            }
            if (product.partner_id == "17") {
                //tvCity.isEnabled=true
                districtWrapper.visibility = View.VISIBLE
                if (dataTertanggungRequest.city != null) {
                    fetchAllCity()
                }
                if (dataTertanggungRequest.district != null) {
                    tvDistrict.text = dataTertanggungRequest.district
                }

                subDistrictWrapper.visibility = View.VISIBLE
                if (dataTertanggungRequest.subDistrict != null) {
                    tvSubDistrict.text = dataTertanggungRequest.district
                }
            }
            if(product.partner_id =="18"){
                pobWrapper.visibility = View.GONE
                relationWrapper.visibility=View.GONE
                if(dataTertanggungRequest.relation!=null){
                    relation = dataTertanggungRequest.relation
                    tvRelation.text = dataTertanggungRequest.relation
                }
                fetchRelation()
            }
        }

        if (catId == 6 && product.partner_id == "3") {
            jobWrapper.visibility = View.VISIBLE
            if (dataTertanggungRequest.job != null) {
                etJob.setText(dataTertanggungRequest.job)
            }
            pobWrapper.visibility = View.VISIBLE
            relationWrapper.visibility=View.GONE
            if (dataTertanggungRequest.pob != null) {
                etPob.setText(dataTertanggungRequest.pob)
            }
        }

        if (catId == 6 && product.partner_id == "20") {
            pobWrapper.visibility = View.VISIBLE
            relationWrapper.visibility=View.GONE
            if (dataTertanggungRequest.pob != null) {
                etPob.setText(dataTertanggungRequest.pob)
            }
            if(dataTertanggungRequest.relation!=null){
                relation = dataTertanggungRequest.relation
                tvRelation.text = dataTertanggungRequest.relation
            }
            postalWrapper.visibility=View.GONE
            fetchRelation()
        }

        if(catId == 15 || catId==10){
            postalWrapper.visibility=View.GONE
        }

        if (dataTertanggungRequest.fullname != null) etFullName.setText(dataTertanggungRequest.fullname)
        if (dataTertanggungRequest.idNo != null) etIdNumber.setText(dataTertanggungRequest.idNo)
        if (dataTertanggungRequest.zipCode != null) etPostal.setText(dataTertanggungRequest.zipCode)
        if (dataTertanggungRequest.email != null) etEmail.setText(dataTertanggungRequest.email)
        if (dataTertanggungRequest.phone != null) etPhoneNumber.setText(dataTertanggungRequest.phone)
        if (dataTertanggungRequest.address != null) etAddress.setText(dataTertanggungRequest.address)
        if (dataTertanggungRequest.sex != null) {
            if (dataTertanggungRequest.sex == "m") {
                tvGender.setSelection(1)
            } else {
                tvGender.setSelection(2)
            }
        }
        if (dataTertanggungRequest.province != null) {
            tvProvince.text = dataTertanggungRequest.province
            province = dataTertanggungRequest.province
        }
        if (dataTertanggungRequest.city != null) {
            tvCity.isEnabled=true
            tvCity.text = dataTertanggungRequest.city
            city = dataTertanggungRequest.city
        }

        if (dataTertanggungRequest.dob != null && dataTertanggungRequest.dob.isNotBlank()) {
            dob = dataTertanggungRequest.dob
            val dates = dob.split("-")
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, dates[0].toInt())
            cal.set(Calendar.MONTH, dates[1].toInt())
            cal.set(Calendar.DAY_OF_MONTH, dates[2].toInt())
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(cal.time)
            tvBirthDate.text = newDateStr
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

                dataTertanggungRequest.fullname = etFullName.text.toString()
                dataTertanggungRequest.email = etEmail.text.toString()
                dataTertanggungRequest.dob = dob
                dataTertanggungRequest.phone = etPhoneNumber.text.toString()
                dataTertanggungRequest.idNo = etIdNumber.text.toString()
                dataTertanggungRequest.address = etAddress.text.toString()
                dataTertanggungRequest.province = province
                dataTertanggungRequest.city = city
                dataTertanggungRequest.sex = sex
                dataTertanggungRequest.zipCode = etPostal.text.toString()
                dataTertanggungRequest.job = etJob.text.toString()
                dataTertanggungRequest.pob = etPob.text.toString()
                dataTertanggungRequest.district = district
                dataTertanggungRequest.subDistrict = subDistrict
//                dataTertanggungRequest.relation=if(relation.isBlank())"Kakak" else relation
//                if(insuredUsers.indexOfFirst {
//                            it.email.toLowerCase()==etEmail.text.toString().toLowerCase()
//                        } ==-1){
//                    addInsuredUser()
//                }else {
                    val intent = Intent();
                    intent.putExtra("data_tertanggung", dataTertanggungRequest)
                    setResult(21, intent)
                    finish()
                //}
            }
        }
    }

    private fun addInsuredUser() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .addInsuredUser(dataTertanggungRequest.toInsuredUser())
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), BaseResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            val intent = Intent();
                            intent.putExtra("data_tertanggung", dataTertanggungRequest)
                            setResult(21, intent)
                            finish()
                            //finish()
                        } else {
                            showError(this@FormDataTertanggung, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTertanggung, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun setupFullnameWatcher(){
        val usersName = ArrayList<String>()
        for(user in insuredUsers){
            usersName.add(user.fullname)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, usersName)
        etFullName.setAdapter(adapter)
        etFullName.setOnItemClickListener { _, _, position, _ ->
            // You can get the label or item that the user clicked:
            val value = adapter.getItem(position) ?: ""
            val idx = insuredUsers.indexOfFirst { it.fullname==value }
            if(idx!=-1){
                etIdNumber.setText(insuredUsers[idx].identification_no)
                tvBirthDate.text=insuredUsers[idx].dob
                dob=insuredUsers[idx].dob
                etEmail.setText(insuredUsers[idx].email)
                etPhoneNumber.setText(insuredUsers[idx].phone)
                etAddress.setText(insuredUsers[idx].address)
                tvProvince.text=insuredUsers[idx].province
                province=insuredUsers[idx].province
                tvCity.text=insuredUsers[idx].city
                city = insuredUsers[idx].city
                etPostal.setText(insuredUsers[idx].zip_code)
                if (dataTertanggungRequest.sex == "m") {
                    tvGender.setSelection(1)
                } else {
                    tvGender.setSelection(2)
                }
            }
        }
    }

    private fun getInsuredUsers() {
        val isNetworkAvailable = Util.isNetworkAvailable(this.applicationContext)
        if (isNetworkAvailable) {
            //loader.setVisibility(View.VISIBLE)
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager.getNetworkServiceWithHeader(this, token).getInsuredUsers(1)
            call.enqueue(object : Callback<InsuredUserResponse> {
                override fun onResponse(call: Call<InsuredUserResponse>, response: Response<InsuredUserResponse>) {
                    Log.i(TAG, "Home Response : " + response.body())
                    //loader.setVisibility(View.GONE)
                    val data = response.body()!!.data
                    val code = response.body()!!.code
                    try {
                        if (code == ErrorCode.ERROR_00) {
                           insuredUsers = data.user_insured
                            setupFullnameWatcher()
                        } else {
                            showError(this@FormDataTertanggung, response.body()!!.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, e.message!!)
                    }
                }

                override fun onFailure(call: Call<InsuredUserResponse>, t: Throwable) {
                    //loader.setVisibility(View.GONE)
                    showError(this@FormDataTertanggung, "Time Out")
                    Log.i(TAG, "On FAILUR : " + t.message)
                }
            })
        } else {
            //loader.setVisibility(View.GONE)
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
        if((catId==6 && product.partner_id=="20") || catId==14){
            var type=if(catId==14){
                "dob_covid"
            }else{
                "dob"
            }
            val min = if(catId==6){
                21
            }else{
                18
            }
            val max  = if(catId==14){
                64
            }else{
                59
            }
            tvBirthDate.setOnClickListener {
                val calendar = Calendar.getInstance()
                val minAge = calendar.get(Calendar.YEAR)-min
                val maxAge = calendar.get(Calendar.YEAR)-max
                Log.d("min max age", " min Age $min : $minAge - $maxAge")
                val newFragment: DialogFragment = DatePickerFragment.newInstance(type, maxAge, minAge, this)
                newFragment.show(supportFragmentManager, "datePicker")
            }
        }else {
            tvBirthDate.setOnClickListener {
                val newFragment: DialogFragment = DatePickerFragment.newInstance("dob_life", this)
                newFragment.show(supportFragmentManager, "datePicker")
            }
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
                            showError(this@FormDataTertanggung, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTertanggung, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun fetchCity(provinceId: String) {
        Log.d("fetch City", "fetch city");
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
                            showError(this@FormDataTertanggung, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTertanggung, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormDataTertanggung, getString(R.string.network_error))
        }
    }

    private fun fetchAllCity() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .cityList
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), CityListResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {

                            cities.clear()
                            cities.addAll(resp.data.cityArrayList)
                            if (getCityId(dataTertanggungRequest.city) != -1) {
                                tvDistrict.isEnabled = true
                                fetchDistrict("" + getCityId(dataTertanggungRequest.city));
                            }
                        } else {
                            showError(this@FormDataTertanggung, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTertanggung, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormDataTertanggung, getString(R.string.network_error))
        }
    }

    private fun fetchDistrict(cityId: String) {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getDistrictListByCity(cityId)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), DistrictListResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {

                            districts.clear()
                            districts.addAll(resp.data.cityArrayList)
                        } else {
                            showError(this@FormDataTertanggung, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTertanggung, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormDataTertanggung, getString(R.string.network_error))
        }
    }

    private fun fetchSubDistrict(districtId: String) {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getSubDistrictListByDistrict(districtId)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), SubDistrictListResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            tvDistrict.isEnabled = true
                            subDistricts.clear()
                            subDistricts.addAll(resp.data.cityArrayList)
                        } else {
                            showError(this@FormDataTertanggung, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTertanggung, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormDataTertanggung, getString(R.string.network_error))
        }
    }


    private fun setupSelectCity() {
        tvCity.setOnClickListener {
            val cityNames = ArrayList<String>()
            for (city in cities) {
                cityNames.add(city.name)
            }
            val intentBrand = Intent(this@FormDataTertanggung, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", cityNames)
            intentBrand.putExtra("resultCode", 23)
            startActivityForResult(intentBrand, 13)
        }
    }

    private fun setupSelectDistrict() {
        tvDistrict.setOnClickListener {
            val cityNames = ArrayList<String>()
            for (city in districts) {
                cityNames.add(city.name)
            }
            val intentBrand = Intent(this@FormDataTertanggung, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", cityNames)
            intentBrand.putExtra("resultCode", 24)
            startActivityForResult(intentBrand, 14)
        }
    }

    private fun setupSelectSubDistrict() {
        tvSubDistrict.setOnClickListener {
            val cityNames = ArrayList<String>()
            for (city in subDistricts) {
                cityNames.add(city.name)
            }
            val intentBrand = Intent(this@FormDataTertanggung, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", cityNames)
            intentBrand.putExtra("resultCode", 25)
            startActivityForResult(intentBrand, 15)
        }
    }

    private fun setupSelectProvince(provinces: ArrayList<Province>) {
        val provinceNames = ArrayList<String>()
        mapProvince.clear()
        for (province in provinces) {
            provinceNames.add(province.name)
            mapProvince[province.id] = province.name
        }

        if(!dataTertanggungRequest.province.isNullOrBlank()){
            val keys = mapProvince.filterValues { it == dataTertanggungRequest.province }.keys
            if(keys.isNotEmpty()){
                fetchCity("${keys.first()}")
            }
        }

        tvProvince.setOnClickListener {
            val intentBrand = Intent(this@FormDataTertanggung, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", provinceNames)
            intentBrand.putExtra("resultCode", 22)
            startActivityForResult(intentBrand, 12)
        }
    }

    private fun getCityId(name: String): Int {
        return cities.indexOfFirst { it.name == name }
    }

    @SuppressLint("SetTextI18n")
    private fun validate(): Boolean {
        var result = true
        val fullNameValidate = etFullName.text.isNotBlank()
        var idNoValidate = etIdNumber.text.length == 16
        val dobValidate = dob.isNotBlank()
        val genderValidate = tvGender.selectedItemPosition != 0
        val phoneValidate = etPhoneNumber.text.isNotBlank()
        val emailValidate = etEmail.text.isNotBlank()
        val addressValidate = etAddress.text.isNotBlank()
        val provinceValidate = province.isNotBlank()
        val cityValidate = city.isNotBlank()
        val postalCodeValidate = etPostal.text.length == 5
        val heightValidate = (etHeight.text.isNotBlank() && etHeight.text.toString().toInt() > 0)
        val weightValidate = (etWeight.text.isNotBlank() && etHeight.text.toString().toInt() > 0)
        val jobValidate = etJob.text.isNotBlank()
        val pobValidate = etPob.text.isNotBlank()
        val subDistrictValidate = subDistrict.isNotBlank()
        val districtValidate = district.isNotBlank()
        val relationValidate = relation.isNotBlank()

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
        errorTextPob.visibility = if (pobValidate) {
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
        errorTextPostal.visibility = if (postalCodeValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        errorTextHeight.visibility = if (heightValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        errorTextWeight.visibility = if (weightValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        errorTextDistrict.visibility = if (districtValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        errorTextSubDistrict.visibility = if (subDistrictValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        errorTextRelation.visibility= if (subDistrictValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        if (etIdNumber.text.length > 16) {
            idNoValidate = false;
            errorTextIdNo.text = "Maksimum 16 Angka"
            errorTextIdNo.visibility = View.VISIBLE
        }
        if (catId == 6 && product.partner_id == "3") {
            errorTextJob.visibility = if (jobValidate) {
                View.GONE
            } else {
                View.VISIBLE
            }
            errorTextPob.visibility = if (pobValidate) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        result = if (catId == 12) {
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate && postalCodeValidate && heightValidate && weightValidate)
        } else if (catId == 14) {
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate && postalCodeValidate)
        } else if (catId == 6 && product.partner_id == "3") {
            // if PA - ZURICH
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate && postalCodeValidate && jobValidate && pobValidate)
        } else if (catId == 6 && product.partner_id == "20") {
            // PA - BCALIFE
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate && pobValidate )
        } else if (catId == 4 && product.partner_id == "17") {
            //LIFE - STAR INVESTAMA
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate && postalCodeValidate && pobValidate && districtValidate && subDistrictValidate)
        } else if (catId == 4 && product.partner_id == "20") {
            //LIFE - BCA LIFE
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate && postalCodeValidate && pobValidate)
        }else if(catId == 4 && product.partner_id == "18"){
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate && postalCodeValidate)
        }else if(catId==15 || catId==10){
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate)
        }else{
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate && provinceValidate
                    && cityValidate && postalCodeValidate)
        }

        return result
    }

    private fun fetchRelation() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .relationHeir
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), RelationResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            setupRelation(resp.data.relation)
                        } else {
                            showError(this@FormDataTertanggung, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(tag, "awow: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTertanggung, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun setupRelation(baseFilter: ArrayList<BaseFilter>) {
        tvRelation.setOnClickListener {
            val roundedBottomSheet = RoundedBottomSheet(this)
            val bundle = Bundle()
            bundle.putString("title", "Pilih hubungan relasi")
            bundle.putParcelableArrayList("baseFilter", baseFilter)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormDataTertanggung.supportFragmentManager, "test")
        }
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        relation = baseFilter.filterText
        tvRelation.text = relation
    }

    override fun selectUser(user: InsuredUser) {

    }
}