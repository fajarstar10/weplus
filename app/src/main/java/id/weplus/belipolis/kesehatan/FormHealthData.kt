package id.weplus.belipolis.kesehatan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.helper.*
import id.weplus.model.BaseFilter
import id.weplus.model.City
import id.weplus.model.Product
import id.weplus.model.Province
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.response.CityListResponse
import id.weplus.model.response.ProvinceListResponse
import id.weplus.model.response.RelationResponse
import id.weplus.model.response.insureduser.InsuredUser
import id.weplus.model.response.insureduser.InsuredUserResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_health_data.*
import kotlinx.android.synthetic.main.view_back_transparent.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FormHealthData : BaseActivity(), OnDateSetListener, OnOptionsSelect {
    private lateinit var product: Product
    private lateinit var dataTertanggung: DataTertanggungRequest
    private var cities = java.util.ArrayList<City>()
    private var mapProvince = HashMap<Int, String>()

    private val itemType = object : TypeToken<ArrayList<DataTertanggungRequest>>() {}.type
    private var itemList = ArrayList<DataTertanggungRequest>()
    private val tag = "FormHealthData"
    private val gson = Gson()
    private var isEdit=false
    private var dob: String = ""
    private var sex: String = ""
    private var province: String = ""
    private var city: String = ""
    private var bmi: Double = 0.0
    private var relation: String = ""
    private var healthIndex = 0
    private var insuredUsers = ArrayList<InsuredUser>()
    private lateinit var insuredBottomSheet:InsuredUserBottomSheet
    private var TAG = "FormHealthData";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_health_data)
        setupToolbar()
        getIntentData()
        fetchRelation()
        setupDatePicker()
        setupGenderSpinner()
        fetchProvince()
        setTextWatcher()
        setupButtonNext()
        getInsuredUsers()
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
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text = "Tertanggung"
        viewback_description.text = "Silahkan lengkapi data tertanggung"

    }

    private fun getIntentData() {
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!
        healthIndex = intent.getIntExtra("health_index", 0)
        Log.d("partner_id", "partner id: " + product.partner_id);
        if(product.partner_id=="18"){
            relationWrapper.visibility=View.GONE
        }
        Log.d("form health data", "health index: $healthIndex")
        if (healthIndex == 0) {
            postalWrapper.visibility=View.VISIBLE
            setupFormBasedOnIndex(dataTertanggung)
        } else {
            postalWrapper.visibility=View.GONE
            if (dataTertanggung.additionData != null) {

                itemList = gson.fromJson<ArrayList<DataTertanggungRequest>>(dataTertanggung.additionData, itemType)

                if (itemList.isNotEmpty()){
                    val idx = itemList.indexOfFirst { it.categoryIndex ==healthIndex }
                    if(idx!=-1){
                        isEdit=true
                        setupFormBasedOnIndex(itemList[idx])
                    }
                }
            }
        }
    }

    private fun setupFormBasedOnIndex(dataTertanggung: DataTertanggungRequest) {
        if (dataTertanggung.fullname != null) {
            etFullName.setText(dataTertanggung.fullname)
        }
        if (dataTertanggung.address != null) {
            etAddress.setText(dataTertanggung.address)
        }
        if(dataTertanggung.pob!=null){
            etBirthPlace.setText(dataTertanggung.pob)
        }
        if(dataTertanggung.zipCode!=null){
            etPostal.setText(dataTertanggung.zipCode)
        }
        if (!dataTertanggung.dob.isNullOrBlank()) {
            dob = dataTertanggung.dob
            val date = dob.split("-")
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, date[0].toInt())
            cal.set(Calendar.MONTH, date[1].toInt())
            cal.set(Calendar.DAY_OF_MONTH, date[2].toInt())
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(cal.time)
            tvBirthDate.text = newDateStr
        }
        if (dataTertanggung.sex != null) {
            sex = dataTertanggung.sex
            Log.d("sex", "sex is $sex")
            tvGender.setSelection(if (sex == "m") {
                1
            } else {
                2
            })
        }
        if (dataTertanggung.email != null) {
            etEmail.setText(dataTertanggung.email)
        }
        if (dataTertanggung.idNo != null) {
            erIdNo.setText(dataTertanggung.idNo)
        }
        if (dataTertanggung.relation != null) {
            relation = dataTertanggung.relation
            tvRelation.text = relation
        }
        if (dataTertanggung.province != null) {
            province = dataTertanggung.province
            tvProvince.text = province
        }
        if (dataTertanggung.city != null) {
            city = dataTertanggung.city
            tvCity.text = city
        }
        if (dataTertanggung.phone != null) {
            etPhoneNumber.setText(dataTertanggung.phone)
        }
        if (dataTertanggung.height != 0) {
            etHeight.setText("${dataTertanggung.height}")
        }
        if (dataTertanggung.height != 0) {
            etWeight.setText("${dataTertanggung.weight}")
        }
        try{
            countBMI(etHeight.text.toString().toDouble(), etWeight.text.toString().toDouble())
        }catch (e: NumberFormatException){}

    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun setupButtonNext() {
        btnNext.setOnClickListener {
            if (validate()) {
                if (healthIndex == 0) {
                    assignData()
                } else {
                    createAdditionData(healthIndex)
                    parseAdditionData()
                }
                intent.putExtra("data_tertanggung", dataTertanggung)
                intent.putExtra("health_index", healthIndex)
                setResult(12, intent)
                finish()
            }
        }
    }

    private fun parseAdditionData() {
        val additionData = gson.toJson(itemList)
        dataTertanggung.additionData = additionData
    }

    private fun assignData() {
        dataTertanggung.fullname = etFullName.text.toString()
        dataTertanggung.idNo = erIdNo.text.toString()
        dataTertanggung.pob = etBirthPlace.text.toString()
        dataTertanggung.dob = dob
        dataTertanggung.height = etHeight.text.toString().toInt()
        dataTertanggung.weight = etWeight.text.toString().toInt()
        dataTertanggung.phone = etPhoneNumber.text.toString()
        dataTertanggung.email = etEmail.text.toString()
        dataTertanggung.address = etAddress.text.toString()
        dataTertanggung.province = province
        dataTertanggung.city = city
        dataTertanggung.relation = relation
        dataTertanggung.sex=sex
        dataTertanggung.zipCode=etPostal.text.toString()
    }

    private fun createAdditionData(healthIndex: Int) {
        val additionData = DataTertanggungRequest()
        additionData.fullname = etFullName.text.toString()
        additionData.idNo = erIdNo.text.toString()
        additionData.pob = etBirthPlace.text.toString()
        additionData.dob = dob
        additionData.height = etHeight.text.toString().toInt()
        additionData.weight = etWeight.text.toString().toInt()
        additionData.phone = etPhoneNumber.text.toString()
        additionData.email = etEmail.text.toString()
        additionData.address = etAddress.text.toString()
        additionData.province = province
        additionData.city = city
        additionData.relation = relation
        additionData.categoryIndex = healthIndex
        additionData.sex=sex
        additionData.jobDeclaration="1"
        if(isEdit){
            val idx = itemList.indexOfFirst { it.categoryIndex ==healthIndex }
            itemList[idx] = additionData
        }else {
            itemList.add(additionData)
        }
    }

    private fun validate(): Boolean {
        val nameValidate = !etFullName.text.isNullOrBlank()
        val idValidate = erIdNo.text.length == 16
        val birthPlaceValidate = !etBirthPlace.text.isNullOrBlank()
        val birthDateValidate = !dob.isBlank()
        val genderValidate = !sex.isBlank()
        val bmiValidate = if (product.partner_id == "13") {
            bmi > 18.5 && bmi < 29.9
        } else {
            bmi > 18.5 && bmi < 24.9
        }
        val phoneValidate = !etPhoneNumber.text.isNullOrBlank()
        val emailValidate = etEmail.text.toString().isEmailValid()
        val addressValidate = !etAddress.text.isNullOrBlank()
        val provinceValidate = province.isNotBlank()
        val cityValidate = city.isNotBlank()
        val relationValidate = relation.isNotBlank()
        val zipCodeValidate = etPostal.text.length==5

        errorTextFullName.visibility = if (nameValidate) View.GONE else View.VISIBLE
        errorTextIdNo.visibility = if (idValidate) View.GONE else View.VISIBLE
        errorTextBirthPlace.visibility = if (birthPlaceValidate) View.GONE else View.VISIBLE
        errorTextBirthDate.visibility = if (birthDateValidate) View.GONE else View.VISIBLE
        errorTextGender.visibility = if (genderValidate) View.GONE else View.VISIBLE
        errorTextPhoneNumber.visibility = if (phoneValidate) View.GONE else View.VISIBLE
        errorTextEmail.visibility = if (emailValidate) View.GONE else View.VISIBLE
        errorTextAddress.visibility = if (addressValidate) View.GONE else View.VISIBLE
        errorTextProvince.visibility = if (provinceValidate) View.GONE else View.VISIBLE
        errorTextCity.visibility = if (cityValidate) View.GONE else View.VISIBLE
        errorTextRelation.visibility = if (relationValidate) View.GONE else View.VISIBLE
        if(healthIndex==0){
            errorTextPostal.visibility=if(zipCodeValidate)View.GONE else View.VISIBLE
            if(!zipCodeValidate){
                return false
            }
        }
        //todo add error for bmi
        if (!bmiValidate) {
            val message = if (product.partner_id == "13") "BMI harus di antara 18,9 dan 29,9" else "BMI harus di antara 18,9 dan 24,9"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        if(product.partner_id=="18"){
            return (nameValidate && idValidate && birthPlaceValidate
                    && genderValidate && birthDateValidate && bmiValidate
                    && phoneValidate && emailValidate && addressValidate
                    && provinceValidate && cityValidate)
        }

        return (nameValidate && idValidate && birthPlaceValidate
                && genderValidate && birthDateValidate && bmiValidate
                && phoneValidate && emailValidate && addressValidate
                && provinceValidate && cityValidate && relationValidate)
    }

    private fun fetchRelation() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getPartnerRelation(product.category_id, product.partner_id)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), RelationResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            setupRelation(resp.data.relation)
                        } else if (resp.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@FormHealthData, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            showError(this@FormHealthData, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(tag, "awow: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormHealthData, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun setTextWatcher() {
        etHeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrBlank()) {
                    try {
                        countBMI(etHeight.text.toString().toDouble(), etWeight.text.toString().toDouble())
                    } catch (e: NumberFormatException) {
                    }
                }
            }
        })

        etWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrBlank()) {
                    try {
                        countBMI(etHeight.text.toString().toDouble(), etWeight.text.toString().toDouble())
                    } catch (e: NumberFormatException) {
                    }
                }
            }
        })
    }

    private fun countBMI(height: Double, weight: Double) {
        val heightInMeter = height / 100
        bmi = weight / (heightInMeter * heightInMeter)
        tvBmi.text = "${String.format("%.3f", bmi).toDouble()}"
    }


    private fun setupDatePicker() {
//        tvBirthDate.setOnClickListener {
//            val newFragment: DialogFragment = DatePickerFragment.newInstance("dob_life", this)
//            newFragment.show(supportFragmentManager, "datePicker")
//        }
    }

    private fun setupGenderSpinner() {
        tvGender.isEnabled=false
//        tvGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
//                Log.d("test", "testing $pos")
//                when (pos) {
//                    0 -> sex = ""
//                    1 -> sex = "m"
//                    2 -> sex = "f"
//                }
//            }
//        }
    }

    private fun setupRelation(baseFilter: ArrayList<BaseFilter>) {
        when(healthIndex){
            0 -> {
                tvRelation.setOnClickListener {
                    val roundedBottomSheet = RoundedBottomSheet(this)
                    val bundle = Bundle()
                    bundle.putString("title", "Pilih hubungan relasi")
                    bundle.putParcelableArrayList("baseFilter", baseFilter)
                    roundedBottomSheet.arguments = bundle
                    roundedBottomSheet.show(this@FormHealthData.supportFragmentManager, "test")
                }
            }
            1 -> {
                tvRelation.setOnClickListener {
                    val roundedBottomSheet = RoundedBottomSheet(this)
                    val bundle = Bundle()
                    baseFilter.removeAt(0)//remove tertanggung utama from list
                    bundle.putString("title", "Pilih hubungan relasi")
                    bundle.putParcelableArrayList("baseFilter", baseFilter)
                    roundedBottomSheet.arguments = bundle
                    roundedBottomSheet.show(this@FormHealthData.supportFragmentManager, "test")
                }
            }
            else->{
                relation = baseFilter[3].filterText
                tvRelation.text = relation
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
                            for (province in resp.data.countryArrayList) {
                                mapProvince[province.id] = province.name
                            }
                            if (!dataTertanggung.province.isNullOrBlank()) {
                                val keys = mapProvince.filterValues { it == dataTertanggung.province }.keys
                                if (keys.isNotEmpty()) {
                                    fetchCity("${keys.first()}")
                                }
                            }
                            setupSelectProvince(resp.data.countryArrayList)
                        } else {
                            showError(this@FormHealthData, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(tag, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormHealthData, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
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
                            showError(this@FormHealthData, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(tag, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormHealthData, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormHealthData, getString(R.string.network_error))
        }
    }

    private fun setupSelectCity() {
        tvCity.setOnClickListener {
            val cityNames = java.util.ArrayList<String>()
            for (city in cities) {
                cityNames.add(city.name)
            }
            val intentBrand = Intent(this@FormHealthData, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", cityNames)
            intentBrand.putExtra("resultCode", 23)
            startActivityForResult(intentBrand, 13)
        }
    }

    private fun setupSelectProvince(provinces: java.util.ArrayList<Province>) {
        val provinceNames = java.util.ArrayList<String>()
        for (province in provinces) {
            provinceNames.add(province.name)
        }
        tvProvince.setOnClickListener {
            val intentBrand = Intent(this@FormHealthData, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", provinceNames)
            intentBrand.putExtra("resultCode", 22)
            startActivityForResult(intentBrand, 12)
        }
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        relation = baseFilter.filterText
        tvRelation.text = relation
    }

    override fun onDateSet(c: Calendar?, cat: String?) {
        if (c != null) {
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(c.time)
            if (cat == "dob_life") {
                val age = getAge(c[Calendar.YEAR], c[Calendar.MONTH + 1], c[Calendar.DAY_OF_MONTH])?.toInt()?:0
                if(healthIndex==0 || healthIndex ==1){
                        if(age > 59 || age <19){
                            showError(this, "Umur harus berkisar 19 hingga 59 tahun")
                            return
                        }
                }else{
                    if(age >18){
                        showError(this, "Umur harus berkisar 1 hingga 18 tahun")
                        return
                    }
                }
                dob = c[Calendar.YEAR].toString() + "-" + c[Calendar.MONTH + 1] + "-" + c[Calendar.DAY_OF_MONTH]
                tvBirthDate.text = newDateStr
            }
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
                erIdNo.setText(insuredUsers[idx].identification_no)
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
                if (dataTertanggung.sex == "m") {
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
                            showError(this@FormHealthData, response.body()!!.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, e.message!!)
                    }
                }

                override fun onFailure(call: Call<InsuredUserResponse>, t: Throwable) {
                    //loader.setVisibility(View.GONE)
                    showError(this@FormHealthData, "Time Out")
                    Log.i(TAG, "On FAILUR : " + t.message)
                }
            })
        } else {
            //loader.setVisibility(View.GONE)
            showError(this, getString(R.string.network_error))
        }
    }

}