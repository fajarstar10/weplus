package id.weplus.belipolis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
import id.weplus.model.response.CityListResponse
import id.weplus.model.response.ProvinceListResponse
import id.weplus.model.response.RelationResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.*
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.addressWrapper
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.birthDateWrapper
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.btnNext
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.cityWrapper
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.emailWrapper
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextAddress
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextBirthDate
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextCity
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextEmail
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextFullName
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextGender
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextIdNo
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextPhoneNumber
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.errorTextProvince
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.etAddress
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.etEmail
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.etFullName
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.etPhoneNumber
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.fullNameWrapper
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.genderWrapper
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.phoneNumberWrapper
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.provinceWrapper
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.tvBirthDate
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.tvCity
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.tvGender
import kotlinx.android.synthetic.main.activity_form_ahli_waris2.tvProvince
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FormAhliWaris : BaseActivity(), OnOptionsSelect, OnDateSetListener {
    private lateinit var product: Product
    private lateinit var dataTertanggung: DataTertanggungRequest
    private var cities = java.util.ArrayList<City>()
    private var mapProvince = HashMap<Int, String>()
    private val tag = "FormAhliWaris"
    private var startDate: String = ""
    private var dob: String = ""
    private var sex: String = ""
    private var province: String = ""
    private var city: String = ""

    private var relation: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_ahli_waris2)
        getIntentData()
        setupToolbar()
        setupFormAhliWaris()
        setupButtonNext()
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

    private fun setupButtonNext() {
        btnNext.setOnClickListener {
            if (validate()) {
                val intent = Intent()
                when (product.category_id) {
                    "7" -> {
                        dataTertanggung.beneficiaryName=etFullName.text.toString()
                        dataTertanggung.beneficiaryRelation=relation
                    }
                    "10" -> {
                        dataTertanggung.beneficiaryName=etFullName.text.toString()
                        dataTertanggung.beneficiaryRelation=relation
                    }
                    "8" -> {
                        //if(product.partner_id=="5"){
                            dataTertanggung.beneficiaryName=etFullName.text.toString()
                            dataTertanggung.beneficiaryRelation=relation
                        //}
                    }
                    else -> {
                        when (product.partner_id) {
                            "1" -> {
                                // 6 -> PA - JAGADIRI
                                if (product.category_id == "6" || product.category_id == "3") {
                                    dataTertanggung.beneficiaryName = etFullName.text.toString()
                                    dataTertanggung.beneficiaryDob = dob
                                    dataTertanggung.beneficiarySex = sex
                                    dataTertanggung.beneficiaryEmail = etEmail.text.toString()
                                    dataTertanggung.beneficiaryRelation = relation
                                }
                            }
                            //MALACCA TRUST
                            "2" -> {
                                //PA
                                if (product.category_id == "6") {
                                    dataTertanggung.beneficiaryName = etFullName.text.toString()
                                    dataTertanggung.beneficiaryDob = dob
                                    dataTertanggung.beneficiarySex = sex
                                    dataTertanggung.beneficiaryEmail = etEmail.text.toString()
                                    dataTertanggung.beneficiaryRelation = relation
                                    dataTertanggung.beneficiaryAddress = etAddress.text.toString()
                                    dataTertanggung.beneficiaryIdNo = erIdNo.text.toString()
                                    dataTertanggung.beneficiaryIdentificationNo = erIdNo.text.toString()
                                    dataTertanggung.beneficiaryIdentificationNo = erIdNo.text.toString()
                                    dataTertanggung.beneficiaryPhone = etPhoneNumber.text.toString()
                                }
                                if(product.category_id=="2"){
                                    dataTertanggung.beneficiaryName=etFullName.text.toString()
                                    dataTertanggung.beneficiaryRelation=relation
                                }
                            }
                            "3" -> {
                                dataTertanggung.beneficiaryName = etFullName.text.toString()
                                dataTertanggung.beneficiaryRelation = relation
                            }
                            "4" -> {
                                if (product.category_id == "6") {
                                    dataTertanggung.beneficiaryName = etFullName.text.toString()
                                    dataTertanggung.beneficiaryIdNo = erIdNo.text.toString()
                                    dataTertanggung.beneficiaryIdentificationNo = erIdNo.text.toString()
                                    dataTertanggung.beneficiaryDob = dob
                                    dataTertanggung.beneficiarySex = sex
                                    dataTertanggung.beneficiaryEmail = etEmail.text.toString()
                                    dataTertanggung.beneficiaryRelation = relation
                                    dataTertanggung.beneficiaryAddress = etAddress.text.toString()
                                    dataTertanggung.beneficiaryPhone = etPhoneNumber.text.toString()
                                    dataTertanggung.beneficiaryProvince = province
                                    dataTertanggung.beneficiaryCity = city
                                }
                            }
                            "5" -> {
                                dataTertanggung.beneficiaryName = etFullName.text.toString()
                            }
                            //ACA
                            "9" -> {
                                if (product.category_id == "8" || product.category_id == "6" || product.category_id=="9") {
                                    dataTertanggung.beneficiaryName = etFullName.text.toString()
                                    dataTertanggung.beneficiaryRelation = relation
                                }
                            }
                            "13"->{
                                //HEALTH - MAG
                                if(product.category_id=="2") {
                                    dataTertanggung.beneficiaryAddress = etAddress.text.toString()
                                    dataTertanggung.beneficiaryName = etFullName.text.toString()
                                    dataTertanggung.beneficiaryRelation = relation
                                }
                            }
                            //Starinvestama
                            "17" -> {
                                if (product.category_id == "4") {
                                    dataTertanggung.beneficiaryName = etFullName.text.toString()
                                    dataTertanggung.beneficiaryDob = dob
                                    dataTertanggung.beneficiarySex = sex
                                    dataTertanggung.beneficiaryRelation = relation
                                }
                            }
                            "18" -> {
                                if (product.category_id == "12" || product.category_id == "14" || product.category_id == "4"||product.category_id=="2") {
                                    dataTertanggung.beneficiaryName = etFullName.text.toString()
                                    dataTertanggung.beneficiaryDob = dob
                                    dataTertanggung.beneficiaryRelation = relation
                                }
                            }
                            //BCA LIFE
                            "20" -> {
                                // PA
                                dataTertanggung.beneficiaryName = etFullName.text.toString()
                                dataTertanggung.beneficiaryDob = dob
                                dataTertanggung.beneficiaryRelation = relation
                                dataTertanggung.beneficiarySex = sex
                            }
                        }
                    }
                }

                intent.putExtra("data_tertanggung", dataTertanggung)
                setResult(22, intent)
                finish()
            }
        }
    }

    private fun setupDatePicker() {
        tvBirthDate.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment.newInstance("dob_life", this)
            newFragment.show(supportFragmentManager, "datePicker")
        }

        tvStartDate.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment.newInstance("start_date", this)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun validate(): Boolean {
        var result = true
        Log.d("sex", "sex is $sex")
        val fullNameValidate = !etFullName.text.isNullOrBlank()
        val dobValidate = !dob.isNullOrBlank()
        val genderValidate = !sex.isNullOrBlank()
        val emailValidate = !etEmail.text.toString().isNullOrBlank()
        val relationValidate = !relation.isNullOrBlank()
        var idNoValidate = erIdNo.text.length == 16
        val phoneValidate = !etPhoneNumber.text.toString().isNullOrBlank()
        val addressValidate = !etAddress.text.toString().isNullOrBlank()
        val provinceValidate = !province.isNullOrBlank()
        val cityValidate = !city.isNullOrBlank()
        //travel
        when (product.category_id) {
            "7" -> {
                errorTextFullName.visibility = if (fullNameValidate) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
                errorTextRelation.visibility = if (relationValidate) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
                result = (fullNameValidate && relationValidate)
            }
            "10" -> {
                errorTextFullName.visibility = if (fullNameValidate) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
                errorTextRelation.visibility = if (relationValidate) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
                result = (fullNameValidate && relationValidate)
            }
            "8" -> {
                errorTextFullName.visibility = if (fullNameValidate) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
                errorTextRelation.visibility = if (relationValidate) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
                result = (fullNameValidate && relationValidate)
            }
            else -> {
                when (product.partner_id) {
                    "1" -> {
                        if (product.category_id == "6" || product.category_id == "3") {
                            errorTextFullName.visibility = if (fullNameValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextBirthDate.visibility = if (dobValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextGender.visibility = if (genderValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextEmail.visibility = if (emailValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextRelation.visibility = if (relationValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }

                            result = (fullNameValidate && dobValidate && genderValidate && emailValidate && relationValidate)
                        }
                    }
                    "2" -> {
                        errorTextFullName.visibility = if (fullNameValidate) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                        errorTextRelation.visibility = if (relationValidate) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                        //Malacca Trust
                        if (product.category_id == "6") {
                            errorTextIdNo.visibility = if (idNoValidate) {
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

                            errorTextGender.visibility = if (genderValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextPhoneNumber.visibility = if (phoneValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextAddress.visibility = if (addressValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }

                            result = (fullNameValidate && idNoValidate && dobValidate && emailValidate && relationValidate && genderValidate && phoneValidate && addressValidate)
                        }
                        if(product.category_id=="2"){
                            result = (fullNameValidate && relationValidate)
                        }
                    }
                    "3" -> {
                        errorTextFullName.visibility = if (fullNameValidate) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                        errorTextRelation.visibility = if (relationValidate) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                        result = (fullNameValidate && relationValidate)
                    }
                    "4" -> {
                        if (product.category_id == "6") {
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
                            errorTextRelation.visibility = if (relationValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextGender.visibility = if (genderValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextPhoneNumber.visibility = if (phoneValidate) {
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

                            result = (fullNameValidate && idNoValidate
                                    && dobValidate && emailValidate
                                    && relationValidate && genderValidate
                                    && phoneValidate && addressValidate
                                    && provinceValidate && cityValidate)
                        }
                    }
                    "5" -> {
                        errorTextFullName.visibility = if (fullNameValidate) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                        result = fullNameValidate
                    }
                    //ACA
                    "9" -> {
                        if (product.category_id == "8" || product.category_id == "6" || product.category_id=="9") {
                            errorTextFullName.visibility = if (fullNameValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextRelation.visibility = if(relationValidate){
                                View.GONE
                            }else{
                                View.VISIBLE
                            }
                            result = fullNameValidate && relationValidate
                        }
                    }
                    "13"->{
                        //HEALTH - MAG
                        if(product.category_id=="2"){
                            errorTextFullName.visibility = if (fullNameValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextAddress.visibility = if (addressValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextRelation.visibility = if(relationValidate){
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            result=fullNameValidate && addressValidate && relationValidate
                        }

                    }
                    "17" -> {
                        if (product.category_id == "4") {
                            errorTextFullName.visibility = if (fullNameValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextBirthDate.visibility = if (dobValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextGender.visibility = if (genderValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextRelation.visibility = if (relationValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            result = (fullNameValidate && dobValidate && genderValidate && relationValidate)
                        }
                    }
                    "18" -> {
                        if (product.category_id == "12") {
                            errorTextFullName.visibility = if (fullNameValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextBirthDate.visibility = if (dobValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextRelation.visibility = if (relationValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }

                            result = (fullNameValidate && dobValidate && relationValidate)
                        }
                    }
                    "20" -> {
                        if (product.category_id == "4") {
                            errorTextFullName.visibility = if (fullNameValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextBirthDate.visibility = if (dobValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextRelation.visibility = if (relationValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                            errorTextGender.visibility = if (genderValidate) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }

                            result = (fullNameValidate && dobValidate && relationValidate && genderValidate)
                        }
                    }

                }
            }
        }

        return result
    }

    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text = resources.getString(R.string.isidataahliwaris)
        viewback_description.text = resources.getString(R.string.lakukanpengisiandatauntukmelanjutkan)
    }

    private fun getIntentData() {
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!
        if (dataTertanggung.beneficiaryName != null) {
            etFullName.setText(dataTertanggung.beneficiaryName)
        }
        if (dataTertanggung.beneficiaryAddress != null) {
            etAddress.setText(dataTertanggung.beneficiaryAddress)
        }
        if (dataTertanggung.beneficiaryDob != null) {
            dob = dataTertanggung.beneficiaryDob
            val date = dob.split("-")
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, date[0].toInt())
            cal.set(Calendar.MONTH, date[1].toInt())
            cal.set(Calendar.DAY_OF_MONTH, date[2].toInt())
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(cal.time)
            tvBirthDate.text = newDateStr
        }
        if (dataTertanggung.beneficiarySex != null) {
            sex = dataTertanggung.beneficiarySex
            tvGender.setSelection(if (sex == "m") {
                1
            } else {
                2
            })
        }
        if (dataTertanggung.beneficiaryEmail != null) {
            etEmail.setText(dataTertanggung.beneficiaryEmail)
        }
        if (dataTertanggung.beneficiaryIdNo != null) {
            erIdNo.setText(dataTertanggung.beneficiaryIdNo)
        }
        if (dataTertanggung.beneficiaryIdentificationNo != null) {
            erIdNo.setText(dataTertanggung.beneficiaryIdentificationNo)
        }
        if (dataTertanggung.beneficiaryRelation != null) {
            relation = dataTertanggung.beneficiaryRelation
            tvRelation.text = relation
        }
        if (dataTertanggung.beneficiaryProvince != null) {
            province = dataTertanggung.beneficiaryProvince
            tvProvince.text = province
        }
        if (dataTertanggung.beneficiaryCity != null) {
            city = dataTertanggung.beneficiaryCity
            tvCity.text = city
        }
        if (dataTertanggung.beneficiaryPhone != null) {
            etPhoneNumber.setText(dataTertanggung.beneficiaryPhone)
        }
    }

    private fun setupFormAhliWaris() {
        Log.d("test","pa jagadiri ${product.category_id},${product.partner_id}")
        //TRAVEL
        when (product.category_id) {
            "7" -> {
                fullNameWrapper.visibility = View.VISIBLE
                relationWrapper.visibility = View.VISIBLE
                fetchRelation()
            }
            "10" -> {
                fullNameWrapper.visibility = View.VISIBLE
                relationWrapper.visibility = View.VISIBLE
                fetchRelation()
            }
            "8" -> {
                //if(product.partner_id=="5"){
                    fullNameWrapper.visibility = View.VISIBLE
                    relationWrapper.visibility = View.VISIBLE
                    fetchRelation()
                //}
            }
            else -> {
                when (product.partner_id) {
                    "1" -> {
                        //JAGADIRI
                        if (product.category_id == "6" || product.category_id == "3") {
                            fullNameWrapper.visibility = View.VISIBLE
                            birthDateWrapper.visibility = View.VISIBLE
                            genderWrapper.visibility = View.VISIBLE
                            emailWrapper.visibility = View.VISIBLE
                            relationWrapper.visibility = View.VISIBLE
                            fetchRelation()
                            setupDatePicker()
                            setupGenderSpinner()
                        }
                    }
                    "2" -> {
                        //Malacca Trust
                        if (product.category_id == "6") {
                            fullNameWrapper.visibility = View.VISIBLE
                            idNoWrapper.visibility = View.VISIBLE
                            relationWrapper.visibility = View.VISIBLE
                            birthDateWrapper.visibility = View.VISIBLE
                            genderWrapper.visibility = View.VISIBLE
                            phoneNumberWrapper.visibility = View.VISIBLE
                            emailWrapper.visibility = View.VISIBLE
                            addressWrapper.visibility = View.VISIBLE
                            fetchRelation()
                            setupDatePicker()
                            setupGenderSpinner()
                        }
                        if(product.partner_id=="2"){
                            fullNameWrapper.visibility = View.VISIBLE
                            relationWrapper.visibility=View.VISIBLE
                            fetchRelation()
                        }
                    }
                    "3" -> {
                        //Zurich
                        fullNameWrapper.visibility = View.VISIBLE
                        relationWrapper.visibility = View.VISIBLE
                        fetchRelation()
                    }
                    "4" -> {
                        //FPG-INDONESIA
                        if (product.category_id == "6") {
                            fullNameWrapper.visibility = View.VISIBLE
                            idNoWrapper.visibility = View.VISIBLE
                            birthDateWrapper.visibility = View.VISIBLE
                            genderWrapper.visibility = View.VISIBLE
                            phoneNumberWrapper.visibility = View.VISIBLE
                            emailWrapper.visibility = View.VISIBLE
                            addressWrapper.visibility = View.VISIBLE
                            provinceWrapper.visibility = View.VISIBLE
                            cityWrapper.visibility = View.VISIBLE
                            fetchProvince()
                            setupGenderSpinner()
                            setupDatePicker()
                        }
                    }
                    "5" -> {
                        //ADIRA
                        fullNameWrapper.visibility = View.VISIBLE
                    }
                    "9" -> {
                        //ACA
                        if (product.category_id == "8" || product.category_id == "6" || product.category_id=="9") {
                            //DBD || //PA
                            fullNameWrapper.visibility = View.VISIBLE
                            relationWrapper.visibility = View.VISIBLE
                            fetchRelation()
                        }
                    }
                    "13"->{
                        fullNameWrapper.visibility=View.VISIBLE
                        addressWrapper.visibility=View.VISIBLE
                        relationWrapper.visibility = View.VISIBLE
                        fetchRelation()
                    }
                    "15" -> {
                        //FWD
                    }
                    "17" -> {
                        //Starinvestama
                        if (product.category_id == "4") {
                            fullNameWrapper.visibility = View.VISIBLE
                            birthDateWrapper.visibility = View.VISIBLE
                            genderWrapper.visibility = View.VISIBLE
                            relationWrapper.visibility = View.VISIBLE
                            setupGenderSpinner()
                            fetchRelation()
                            setupDatePicker()
                        }
                    }
                    "18" -> {
                        fullNameWrapper.visibility = View.VISIBLE
                        birthDateWrapper.visibility = View.VISIBLE
                        relationWrapper.visibility = View.VISIBLE
                        fetchRelation()
                        setupDatePicker()
                    }
                    "20" -> {
                        //BCA-LIFE
                        fullNameWrapper.visibility = View.VISIBLE
                        relationWrapper.visibility = View.VISIBLE
                        birthDateWrapper.visibility = View.VISIBLE
                        genderWrapper.visibility = View.VISIBLE
                        setupGenderSpinner()
                        fetchRelation()
                        setupDatePicker()
                    }
                }
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
                        } else {
                            showError(this@FormAhliWaris, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(tag, "awow: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormAhliWaris, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun setupGenderSpinner() {
        tvGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                Log.d("test", "testing $pos")
                when (pos) {
                    0 -> sex = ""
                    1 -> sex = "m"
                    2 -> sex = "f"
                }
            }
        }
    }

    private fun setupRelation(baseFilter: ArrayList<BaseFilter>) {
        tvRelation.setOnClickListener {
            val roundedBottomSheet = RoundedBottomSheet(this)
            val bundle = Bundle()
            bundle.putString("title", "Pilih hubungan relasi")
            bundle.putParcelableArrayList("baseFilter", baseFilter)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormAhliWaris.supportFragmentManager, "test")
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
                            showError(this@FormAhliWaris, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(tag, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormAhliWaris, "Time out")
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
                            showError(this@FormAhliWaris, resp.message)
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i(tag, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormAhliWaris, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormAhliWaris, getString(R.string.network_error))
        }
    }

    private fun setupSelectCity() {
        tvCity.setOnClickListener {
            val cityNames = java.util.ArrayList<String>()
            for (city in cities) {
                cityNames.add(city.name)
            }
            val intentBrand = Intent(this@FormAhliWaris, FullScreenFilterActivity::class.java)
            intentBrand.putStringArrayListExtra("searchList", cityNames)
            intentBrand.putExtra("resultCode", 23)
            startActivityForResult(intentBrand, 13)
        }
    }

    private fun setupSelectProvince(provinces: java.util.ArrayList<Province>) {
        val provinceNames = java.util.ArrayList<String>()
        for (province in provinces) {
            provinceNames.add(province.name)
            mapProvince[province.id]=province.name
        }
        if(!dataTertanggung.province.isNullOrBlank()){
            val keys = mapProvince.filterValues { it == dataTertanggung.province }.keys
            if(keys.isNotEmpty()){
                fetchCity("${keys.first()}")
            }
        }
        tvProvince.setOnClickListener {
            val intentBrand = Intent(this@FormAhliWaris, FullScreenFilterActivity::class.java)
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
                dob = c[Calendar.YEAR].toString() + "-" + c[Calendar.MONTH+1] + "-" + c[Calendar.DAY_OF_MONTH]
                tvBirthDate.text = newDateStr
            } else {
                startDate = c[Calendar.YEAR].toString() + "-" + c[Calendar.MONTH+1] + "-" + c[Calendar.DAY_OF_MONTH]
                tvStartDate.text = newDateStr
            }
        }
    }
}