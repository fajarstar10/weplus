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
import id.weplus.WelcomeActivity
import id.weplus.helper.*
import id.weplus.model.BaseFilter
import id.weplus.model.City
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
import kotlinx.android.synthetic.main.activity_form_polis_holder.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FormPolisHolder : BaseActivity(),OnOptionsSelect,OnDateSetListener {

    private var dataTertanggungRequest = DataTertanggungRequest()
    private var pob=""
    private var dob=""
    private var sex=""
    private var relation=""
    private var city=""
    private var province=""
    private var cities = ArrayList<City>()
    private var  mapProvince =  HashMap<Int, String>()
    private var TAG = "FormPolisHolder"
    private var catId=0

    private var insuredUsers = ArrayList<InsuredUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_polis_holder)
        setupToolbar()
        getIntentData()
        setupBtnNext()
        setupDatePicker()
        setupGenderSpinner()
        fetchRelation()
        fetchProvince()
        setupSelectCity()
        getInsuredUsers()
    }

    private fun setupDatePicker() {
        tvBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val min  = when (catId) {
                6 -> {
                    0
                }
                //covid
                14 -> {
                    18
                }
                else -> {
                    21
                }
            }


            val max=if(catId==14){
                64
            }else{
                59
            }
            val minAge = calendar.get(Calendar.YEAR)-min
            val maxAge = calendar.get(Calendar.YEAR)-max
            Log.d("min max age", " test $minAge - $maxAge")
            val newFragment: DialogFragment = DatePickerFragment.newInstance("dob", maxAge, minAge, this)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar(){
        viewback_title.text="Pemegang Polis"
        viewback_description.text="Isi seluruh field"
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }

    private fun getIntentData(){
        dataTertanggungRequest = intent.getParcelableExtra("data_tertanggung")!!
        catId= intent.getIntExtra("cat_id", 0)
        if(catId==14 || catId==4){
            zipCodeWrapper.visibility=View.VISIBLE
            relationWrapper.visibility=View.VISIBLE
            fetchRelation()
        }
        if(dataTertanggungRequest.polisHolderFullName !=null) etFullName.setText(dataTertanggungRequest.polisHolderFullName)
        if(dataTertanggungRequest.polisHolderIdNo!=null) etIdNumber.setText(dataTertanggungRequest.polisHolderIdNo)
        if(dataTertanggungRequest.polisHolderAddress!=null) etAddress.setText(dataTertanggungRequest.polisHolderAddress)
        if(dataTertanggungRequest.polisHolderDob!=null){
            dob=dataTertanggungRequest.polisHolderDob
            tvBirthDate.text = dataTertanggungRequest.polisHolderDob
        }
        if(dataTertanggungRequest.polisHolderPob!=null) tvPob.setText(dataTertanggungRequest.polisHolderPob)
        if(dataTertanggungRequest.polisHolderCity!=null) {
            tvCity.text = dataTertanggungRequest.polisHolderCity
            city=dataTertanggungRequest.polisHolderCity
        }
        if(dataTertanggungRequest.polisHolderProvince!=null){
            tvProvince.text=dataTertanggungRequest.polisHolderProvince
            province=dataTertanggungRequest.polisHolderProvince
        }
        if(dataTertanggungRequest.polisHolderRelation!=null){
            relation=dataTertanggungRequest.polisHolderRelation
            tvRelation.text=relation
        }
        if(dataTertanggungRequest.polisHolderEmail!=null)etEmail.setText(dataTertanggungRequest.polisHolderEmail)
        if(dataTertanggungRequest.polisHolderPhone!=null)etPhoneNumber.setText(dataTertanggungRequest.polisHolderPhone)
        if(dataTertanggungRequest.polisHolderSex!=null){
            if(dataTertanggungRequest.polisHolderSex=="m"){
                tvGender.setSelection(1)
            }else{
                tvGender.setSelection(2)
            }
            sex=dataTertanggungRequest.polisHolderSex
        }
        if(dataTertanggungRequest.polisHolderZipCode!=null){
            zipCodeWrapper.visibility=View.VISIBLE
            etZipCode.setText(dataTertanggungRequest.polisHolderZipCode)
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
                        } else if (resp.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@FormPolisHolder, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            showError(this@FormPolisHolder, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormPolisHolder, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
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
                        } else if (resp.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@FormPolisHolder, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            showError(this@FormPolisHolder, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormPolisHolder, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormPolisHolder, getString(R.string.network_error))
        }
    }

    private fun setupSelectCity() {
        tvCity.setOnClickListener {
            val cityNames = ArrayList<String>()
            for (city in cities) {
                cityNames.add(city.name)
            }
            val intentBrand = Intent(this@FormPolisHolder, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", cityNames)
            intentBrand.putExtra("resultCode", 23)
            startActivityForResult(intentBrand, 13)
        }
    }

    private fun setupSelectProvince(provinces: ArrayList<Province>){
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
        tvProvince.setOnClickListener{
            val intentBrand = Intent(this@FormPolisHolder, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", provinceNames)
            intentBrand.putExtra("resultCode", 22)
            startActivityForResult(intentBrand, 12)
        }
    }

    private fun setupBtnNext(){
        btnNext.setOnClickListener {
            if(validate()){
                dataTertanggungRequest.polisHolderAddress = etAddress.text.toString()
                dataTertanggungRequest.polisHolderFullName =etFullName.text.toString()
                dataTertanggungRequest.polisHolderCity=city
                dataTertanggungRequest.polisHolderProvince=province
                dataTertanggungRequest.polisHolderDob=dob
                dataTertanggungRequest.polisHolderPob=tvPob.text.toString()
                dataTertanggungRequest.polisHolderEmail=etEmail.text.toString()
                dataTertanggungRequest.polisHolderIdNo=etIdNumber.text.toString()
                dataTertanggungRequest.polisHolderRelation=tvRelation.text.toString()
                dataTertanggungRequest.polisHolderPhone = etPhoneNumber.text.toString()
                dataTertanggungRequest.polisHolderSex=sex
                dataTertanggungRequest.polisHolderZipCode=etZipCode.text.toString()
                val intent = Intent();
                intent.putExtra("data_tertanggung", dataTertanggungRequest)
                setResult(32, intent)
                finish()
            }
        }
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
                        } else if (resp.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@FormPolisHolder, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            showError(this@FormPolisHolder, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "awow: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormPolisHolder, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun setupGenderSpinner(){
        tvGender.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                when(pos){
                    0 -> sex = ""
                    1 -> sex = "m"
                    2 -> sex = "f"
                }
            }
        }
    }

    private fun setupRelation(baseFilter: ArrayList<BaseFilter>){
        tvRelation.setOnClickListener {
            val roundedBottomSheet = RoundedBottomSheet(this)
            val bundle = Bundle()
            bundle.putString("title", "Pilih hubungan relasi")
            bundle.putParcelableArrayList("baseFilter", baseFilter)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormPolisHolder.supportFragmentManager, "test")
        }
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        relation = baseFilter.filterText
        tvRelation.text=relation
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
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

    private fun validate():Boolean{
        val fullNameValidate = etFullName.text.isNotBlank()
        var idNoValidate = etIdNumber.text.length==16
        val dobValidate = dob.isNotBlank()
        val pobValidate = tvPob.text.toString().isNotBlank()
        val genderValidate = tvGender.selectedItemPosition != 0
        val phoneValidate = etPhoneNumber.text.isNotBlank()
        val emailValidate = etEmail.text.isNotBlank()
        val addressValidate = etAddress.text.isNotBlank()
        val provinceValidate = province.isNotBlank()
        val cityValidate = city.isNotBlank()
        val relationValidate = relation.isNotBlank()
        val zipCodeValidate = etZipCode.text.length==5

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

        errorTextRelation.visibility=if(relationValidate){
            View.GONE
        }else{
            View.VISIBLE
        }

        errorTextZipCode.visibility=if(zipCodeValidate){
            View.GONE
        }else{
            View.VISIBLE
        }

        return if(catId==14 || catId==4){
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate
                    && provinceValidate && cityValidate && pobValidate && zipCodeValidate && relationValidate)
        }else {
            (fullNameValidate && idNoValidate && dobValidate && genderValidate
                    && phoneValidate && emailValidate && addressValidate
                    && provinceValidate && cityValidate && pobValidate)
        }
    }

    override fun onDateSet(c: Calendar?, cat: String?) {
        if (c != null) {
            dob = c[Calendar.YEAR].toString() + "-" + c[Calendar.MONTH] + "-" + c[Calendar.DAY_OF_MONTH]
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(c.time)
            tvBirthDate.text=newDateStr
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
                        when (code) {
                            ErrorCode.ERROR_00 -> {
                                insuredUsers = data.user_insured
                                setupFullnameWatcher()
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@FormPolisHolder, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                showError(this@FormPolisHolder, response.body()!!.message)
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, e.message!!)
                    }
                }

                override fun onFailure(call: Call<InsuredUserResponse>, t: Throwable) {
                    //loader.setVisibility(View.GONE)
                    showError(this@FormPolisHolder, "Time Out")
                    Log.i(TAG, "On FAILUR : " + t.message)
                }
            })
        } else {
            //loader.setVisibility(View.GONE)
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
                etZipCode.setText(insuredUsers[idx].zip_code)
                if (dataTertanggungRequest.sex == "m") {
                    tvGender.setSelection(1)
                } else {
                    tvGender.setSelection(2)
                }
            }
        }
    }
}