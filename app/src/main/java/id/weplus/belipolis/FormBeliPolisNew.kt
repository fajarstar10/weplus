package id.weplus.belipolis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.belipolis.gadget.FormGadgetData
import id.weplus.belipolis.social.FormDataTambahanSocial
import id.weplus.helper.OnDateSetListener
import id.weplus.helper.getAge
import id.weplus.model.LoginData
import id.weplus.model.Product
import id.weplus.model.request.*
import id.weplus.net.WeplusSharedPreference
import id.weplus.pembayaran.PembayaranActivity
import id.weplus.utility.TextHelper.currencyFormatter
import kotlinx.android.synthetic.main.activity_form_beli_polis_new.*
import kotlinx.android.synthetic.main.view_back.*
import java.text.SimpleDateFormat
import java.util.*


class FormBeliPolisNew : BaseActivity(), OnDateSetListener {
    private lateinit var product: Product
    private lateinit var user: LoginData
    private lateinit var travelRequestBody: TravelProductListRequest
    private lateinit var motorRequestBody: MotorProductListRequest
    private lateinit var carRequestBody: CarProductListRequest
    private lateinit var lifeProductListRequest: LifeProductListRequest
    private lateinit var criticalRequestBody: CriticalllnessProductListRequest
    private lateinit var healthRequestBody: HealthProductListRequest
    private lateinit var gadgetRequestBody:GadgetProductRequest
    private val TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT"
    private val myDateFormat = SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault())
    private var dataTertanggung = DataTertanggungRequest()
    private var catId: Int = 0
    private var isAbove21 = -1
    private var dataTertanggungReqCode = 11
    private var ahliWarisReqCode = 21
    private var polisHolderReqCode = 31
    private var formMotorReqCode = 41
    private var formCarReqCode = 51
    private var formVintageCarReqCode = 61
    private var formPropertyReqCode = 71
    private var formSocialReqCode = 81
    private var startDate = ""
    private var isAgent=false
    private var partnerWeplusId=0
    private var nik=""
    private lateinit var dateTimeFragment: SwitchDateTimeDialogFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_beli_polis_new)
        getIntentData()
        getUser()
        setupToolbar()
        populateProductData()
        populateUserData()
        setupToggleButton()
        setupToggleButtonPolis()
        setupBuyButton()
        setupEditDataTertanggung()
        setupEditAhliWaris()
        setupEditPolisHolder()
        setupDateTimePicker()
        tncText.text = Html.fromHtml("Dengan ini saya sudah menyetujui <strong>syarat dan ketentuan</strong> yang berlaku");
    }


    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (dataTertanggung != null && dataTertanggung.sex != null) {
            Log.d("data tertanggung", "on resume ${dataTertanggung.sex}")
        }
        if (requestCode == dataTertanggungReqCode) {
            if (resultCode == 21) {

                Log.d("data tertanggung ","on activity result 21 cat $catId - partner : ${product.partner_id}")
                if (data != null) {
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataTertanggungHint.visibility = View.GONE
                    tvDataTertanggungName.visibility = View.VISIBLE
                    tvDataTertanggungID.visibility = View.VISIBLE
                    tvDataTertanggungName.text = dataTertanggung.fullname
                    tvDataTertanggungID.text = "No KTP: ${dataTertanggung.idNo}"
                    Log.d("data tertanggung ","cat $catId - partner : ${product.partner_id}")
                    if ((catId == 6 && product.partner_id == "20")
                            || (catId == 14)
                                // LIFE - SIMAS
                            || (catId==4 && product.partner_id=="18")
                                //LIFE - BCA LIFE
                            || (catId==4 && product.partner_id=="20")
                                // HEALTH - SIMAS
                            || (catId==2 && product.partner_id=="18")

                    ) {
                        var minAge = if(catId==6){
                            21
                        }else{
                            18
                        }
                        if(catId==6 && product.partner_id=="20"){
                            minAge=18
                        }
                        val birthDate = dataTertanggung.dob.split("-")
                        val age = getAge(birthDate[0].toInt(), birthDate[1].toInt(), birthDate[2].toInt())
                                ?: "-1"
                        Log.d("age", "age value : $age - $minAge")
                        if (age.toInt() >= minAge) {
                            toggleButton.isEnabled = false
                            //imgEditDataTertanggung.isEnabled = false
                            dataTertanggung.polisHolderFullName = dataTertanggung.fullname
                            dataTertanggung.polisHolderIdNo = dataTertanggung.idNo
                            dataTertanggung.polisHolderPob = dataTertanggung.pob
                            dataTertanggung.polisHolderDob = dataTertanggung.dob
                            dataTertanggung.polisHolderSex = dataTertanggung.sex
                            dataTertanggung.polisHolderPhone = dataTertanggung.phone
                            dataTertanggung.polisHolderEmail = dataTertanggung.email
                            dataTertanggung.polisHolderAddress = dataTertanggung.address
                            dataTertanggung.polisHolderProvince = dataTertanggung.province
                            dataTertanggung.polisHolderCity = dataTertanggung.city
                            dataTertanggung.polisHolderRelation = dataTertanggung.relation
                            dataTertanggung.polisHolderZipCode = dataTertanggung.zipCode
                        } else {
                            tvDataTertanggung.visibility = View.VISIBLE
                            tvSameWithOrderName.visibility = View.VISIBLE
                            toggleButton.visibility = View.VISIBLE
                            DataTertanggungWrapper.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        if (requestCode == ahliWarisReqCode) {
            if (resultCode == 22) {
                if (data != null) {
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    if(catId==15){
                        tvAhliWarisHint.visibility = View.GONE
                        tvAhliWarisName.visibility = View.VISIBLE
                        tvAhliWarisName.text = "Serial Number : "+dataTertanggung.serial_number
                    }else {
                        tvAhliWarisHint.visibility = View.GONE
                        tvAhliWarisName.visibility = View.VISIBLE
                        tvAhliWarisName.text = dataTertanggung.beneficiaryName
                    }
                }
            }
        }
        if (requestCode == polisHolderReqCode) {
            if (resultCode == 32) {
                if (data != null) {
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvPolisHolderHint.visibility = View.GONE
                    tvPolisHolderName.visibility = View.VISIBLE
                    tvPolisHolderName.text = dataTertanggung.polisHolderFullName
                    Log.d("age", "ages $catId - ${product.partner_id}")
                }
            }
        }
        if (requestCode == formMotorReqCode) {
            if (resultCode == 42) {
                if (data != null) {
                    tvDataMotorHint.visibility = View.GONE
                    tvDataMotorName.visibility = View.VISIBLE
                    tvDataMotorInfo.visibility = View.VISIBLE
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    dataTertanggung.motorId = motorRequestBody.motorcycle_id
                    dataTertanggung.platId = motorRequestBody.plat_id
                    dataTertanggung.typeInsurance = motorRequestBody.type_insurance
                    Log.d("test","test : ${dataTertanggung.platNo}")
                    tvDataMotorName.text = motorRequestBody.brand
                    tvDataMotorInfo.text = "Seri motor : ${motorRequestBody.series}"
                }
            }
        }
        if (requestCode == formCarReqCode) {
            if (resultCode == 52) {
                if (data != null) {
                    tvDataMotorHint.visibility = View.GONE
                    tvDataMotorName.visibility = View.VISIBLE
                    tvDataMotorInfo.visibility = View.VISIBLE
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    dataTertanggung.carId = "" + carRequestBody.car_id
                    dataTertanggung.carPrice = "" + carRequestBody.car_price
                    dataTertanggung.accPrice = "" + carRequestBody.accessories_price
                    dataTertanggung.acc = carRequestBody.accessories
                    tvDataMotorName.text = carRequestBody.brand
                    tvDataMotorInfo.text = "Seri Mobil : ${carRequestBody.series}"
                }
            }
        }
        if (requestCode == formVintageCarReqCode) {
            if (resultCode == 62) {
                if (data != null) {
                    tvDataMobilHint.visibility = View.GONE
                    tvDataMobilName.visibility = View.VISIBLE
                    tvDataMobilInfo.visibility = View.VISIBLE
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataMobilName.text = dataTertanggung.carBrand
                    tvDataMobilInfo.text = "Seri Mobil : ${dataTertanggung.carSeries}"
                }
            }
        }
        if (requestCode == formPropertyReqCode) {
            if (resultCode == 72) {
                if (data != null) {
                    tvDataPropertyHint.visibility = View.GONE
                    tvDataPropertyName.visibility = View.VISIBLE
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    Log.d("test", "testing ${dataTertanggung.occupation}")
                    if (product.partner_id == "13")
                        tvDataPropertyName.text = dataTertanggung.occupation
                    else
                        tvDataPropertyName.text = dataTertanggung.insuredAddress
                }
            }
        }

        if (requestCode == formSocialReqCode) {
            if (resultCode == 82) {
                if (data != null) {
                    tvDataMobilHint.visibility = View.GONE
                    tvDataMobilName.visibility = View.VISIBLE
                    tvDataMobilInfo.visibility = View.VISIBLE
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    Log.d("social", "Type formbeli: ${dataTertanggung.protectionType}")
                    Log.d("social", "address formbeli: ${dataTertanggung.addressKtp}")
                    tvDataMobilName.text = "Tipe Perlindungan : " + dataTertanggung.protectionType
                    tvDataMobilInfo.text = "Nama Keuskupan : " + dataTertanggung.keuskupan
                }
            }
        }
    }

    private fun setupEditDataTertanggung() {
        imgEditDataTertanggung.setOnClickListener {
            val intent = Intent(this, FormDataTertanggung::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("cat_id", catId)
            intent.putExtra("data_tertanggung", dataTertanggung)
            Log.d("data tertanggung", "click edit data tertangungg se ${dataTertanggung.sex}")
            if (catId == 12) intent.putExtra("request_body", dataTertanggung)
            startActivityForResult(intent, 11)
        }
    }

    private fun setupEditAhliWaris() {
        if(catId==15){
            tvAhliWaris.text="Data Gadget"
            tvAhliWarisHint.text="Masukkan Data Gadget"
            imgEditAhliWaris.setOnClickListener {
                val intent = Intent(this, FormGadgetData::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("data_tertanggung", dataTertanggung)
                intent.putExtra("cat_id", catId)
                startActivityForResult(intent, 21)
            }
        }else {
            imgEditAhliWaris.setOnClickListener {
                val intent = Intent(this, FormAhliWaris::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("data_tertanggung", dataTertanggung)
                intent.putExtra("cat_id", catId)
                startActivityForResult(intent, 21)
            }
        }
    }

    private fun setupEditPolisHolder() {
        imgEditPolisHolder.setOnClickListener {
            val intent = Intent(this, FormPolisHolder::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("cat_id", catId)
            intent.putExtra("data_tertanggung", dataTertanggung)
            startActivityForResult(intent, polisHolderReqCode)
        }
    }

    private fun getUser() {
        val jsonResponse = WeplusSharedPreference.getUser(this)
        val gson = Gson()
        user = gson.fromJson(jsonResponse, LoginData::class.java)

    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }

        viewback_title.text = "Beli Asuransi"
        viewback_description.text = "Isi data-data yang diperlukan"
    }

    private fun getIntentData() {
        product = intent.getParcelableExtra<Product>("product_detail")!! as Product
        catId = intent.getIntExtra("cat_id", 0)
        isAbove21 = intent.getIntExtra("isAbove21", -1)
        isAgent = intent.getBooleanExtra("is_agent",false)
        partnerWeplusId = intent.getIntExtra("partner_weplus_id",0)
        nik = intent.getStringExtra("nik").toString()
        Log.d("cat_id", "cat id $catId")
        Log.d("form", "nik $nik")
        Log.d("form", "partner weplus :  $partnerWeplusId")
        //MOTOR
        if (catId == 1) {
            motorRequestBody = intent.getParcelableExtra("request_body")!!
            tvAhliWaris.visibility = View.GONE
            AhliWarisWrapper.visibility = View.GONE
            tvDataMotor.visibility = View.VISIBLE
            dataMotorWrapper.visibility = View.VISIBLE
            imgEditDataMotor.setOnClickListener {
                val intent = Intent(this@FormBeliPolisNew, FormDataMotor::class.java)
                intent.putExtra("request_body", motorRequestBody)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                startActivityForResult(intent, formMotorReqCode)
            }
            partnerWeplusId = motorRequestBody.partner_weplus_id
            if(motorRequestBody.nik!=null){
                nik=motorRequestBody.nik;
            }
        }
        //HEALTH
        if (catId == 2) {
            healthRequestBody = intent.getParcelableExtra("request_body")!!
            dataTertanggung.isSmoking = "" + healthRequestBody.is_smoking
            dataTertanggung.groupType = "" + healthRequestBody.group_type
            dataTertanggung.adult = "" + healthRequestBody.adult
            dataTertanggung.child = "" + healthRequestBody.child
            dataTertanggung.paymentType = "" + healthRequestBody.payment_type
            dataTertanggung.dob = ""+healthRequestBody.dob
            val birthDate = dataTertanggung.dob.split("-")
            val age = getAge(birthDate[0].toInt(), birthDate[1].toInt(), birthDate[2].toInt())
                    ?: "-1"
            // SIMAS
            if (product.partner_id == "18") {
                if(age.toInt()<18) {
                    polisHolderFormWrapper.visibility = View.VISIBLE
                    imgEditPolisHolder.setOnClickListener {
                        val intent = Intent(this, FormPolisHolder::class.java)
                        intent.putExtra("product_detail", product)
                        intent.putExtra("cat_id", catId)
                        intent.putExtra("data_tertanggung", dataTertanggung)
                        startActivityForResult(intent, polisHolderReqCode)
                    }
                }else{
                    polisHolderFormWrapper.visibility = View.GONE
                }
            }
        }
        //LIFE
        if (catId == 4) {
            lifeProductListRequest = intent.getParcelableExtra("request_body")!!
            dataTertanggung.dob = lifeProductListRequest.dob
            dataTertanggung.sex = lifeProductListRequest.sex
            //sinar mas
            val birthDate = dataTertanggung.dob.split("-")
            val age = getAge(birthDate[0].toInt(), birthDate[1].toInt(), birthDate[2].toInt())
                    ?: "-1"
            if(product.partner_id =="18" || product.partner_id=="20"){
                if(age.toInt()<18){
                    polisHolderFormWrapper.visibility = View.VISIBLE
                    imgEditPolisHolder.setOnClickListener {
                        val intent = Intent(this, FormPolisHolder::class.java)
                        intent.putExtra("product_detail", product)
                        intent.putExtra("cat_id", catId)
                        intent.putExtra("data_tertanggung", dataTertanggung)
                        startActivityForResult(intent, polisHolderReqCode)
                    }
                }
            }
        }
        //MOBIL
        if (catId == 5) {
            carRequestBody = intent.getParcelableExtra("request_body")!!
            partnerWeplusId = carRequestBody.partner_weplus_id
            nik = if(carRequestBody.nik!=null)carRequestBody.nik else ""
            tvAhliWaris.visibility = View.GONE
            AhliWarisWrapper.visibility = View.GONE
            tvDataMotor.visibility = View.VISIBLE
            tvDataMotor.text = "Data Mobil"
            tvDataMotorHint.text = "Masukkan Data Mobil"
            dataMotorWrapper.visibility = View.VISIBLE
            imgEditDataMotor.setOnClickListener {
                val intent = Intent(this@FormBeliPolisNew, FormMobil::class.java)
                intent.putExtra("request_body", carRequestBody)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                startActivityForResult(intent, formCarReqCode)
            }
        }
        if (catId == 7) {
            travelRequestBody = intent.getParcelableExtra("request_body")!!
            dataTertanggung.destination = travelRequestBody.destination
            dataTertanggung.duration = travelRequestBody.duration
            dataTertanggung.departureDate = travelRequestBody.departure_date
            dataTertanggung.departureCity = travelRequestBody.departure_city
            dataTertanggung.returnDate = travelRequestBody.return_date
            dataTertanggung.packageType = "${travelRequestBody.package_type}"
            dataTertanggung.typeGroup = travelRequestBody.type_group_name.toLowerCase()
            dataTertanggung.additionProtection = travelRequestBody.addition_protection
        }
        if (catId == 9) {
            tvDataProperty.visibility = View.VISIBLE
            tvDataMobilInfo.visibility = View.GONE
            dataPropertyWrapper.visibility = View.VISIBLE
            imgEditDataProperty.setOnClickListener {
                val intent = Intent(this@FormBeliPolisNew, FormProperty::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                startActivityForResult(intent, formPropertyReqCode)
            }
            //Log.d("partner_id","partner : "+product.partner_id);
            if (product.partner_id == "9") {
                tvAhliWaris.visibility = View.VISIBLE
                AhliWarisWrapper.visibility = View.VISIBLE
            } else {
                tvAhliWaris.visibility = View.GONE
                AhliWarisWrapper.visibility = View.GONE
            }
        }
        if (catId == 11) {
            tvDataMobil.visibility = View.VISIBLE
            dataMobilWrapper.visibility = View.VISIBLE
            tvAhliWaris.visibility = View.GONE
            AhliWarisWrapper.visibility = View.GONE
            imgEditDataMobil.setOnClickListener {
                val intent = Intent(this@FormBeliPolisNew, FormDataMobil::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                startActivityForResult(intent, formVintageCarReqCode)
            }
        }
        if (catId == 12) {
            criticalRequestBody = intent.getParcelableExtra("request_body")!!
            dataTertanggung.dob = criticalRequestBody.dob
            dataTertanggung.sex = criticalRequestBody.sex
        }
        if (catId == 13) {
            tvDataMobil.visibility = View.VISIBLE
            tvDataMobil.text = "Data Tambahan"
            tvDataMobilHint.text = "Masukkan Data Tambahan"
            tvDataMobilInfo.visibility = View.GONE
            dataMobilWrapper.visibility = View.VISIBLE
            tvAhliWaris.visibility = View.GONE
            AhliWarisWrapper.visibility = View.GONE
            imgEditDataMobil.setOnClickListener {
                val intent = Intent(this@FormBeliPolisNew, FormDataTambahanSocial::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                startActivityForResult(intent, formSocialReqCode)
            }
        }
        //covid
        if (catId == 14) {
            if (isAbove21 == 1) {
                polisHolderFormWrapper.visibility = View.GONE
            } else {
                polisHolderFormWrapper.visibility = View.VISIBLE
            }
        }
        if (product.partner_id == "10" || (product.partner_id == "4" && catId == 6)) {
            tvAhliWaris.visibility = View.GONE
            AhliWarisWrapper.visibility = View.GONE
        }
        if (product.partner_id == "1" && (catId == 6 || catId == 3)) {
            tvStartDateTitle.visibility = View.VISIBLE
            startDateLayout.visibility = View.VISIBLE
            setupStartDateBehaviour()
        }
        //PA-BCA
        if (product.partner_id == "20" && catId == 6) {
            if (isAbove21 == 1) {
                polisHolderFormWrapper.visibility = View.GONE
            } else {
                polisHolderFormWrapper.visibility = View.VISIBLE
            }
        }

        if(catId==15){
            gadgetRequestBody = intent.getParcelableExtra("request_body")!!
            dataTertanggung.gadget_age = gadgetRequestBody.gadget_age
            dataTertanggung.gadget_brand = gadgetRequestBody.gadget_brand
            dataTertanggung.gadget_name = gadgetRequestBody.gadget_name
            dataTertanggung.gadget_price=gadgetRequestBody.gadget_price
            dataTertanggung.gadget_type = gadgetRequestBody.gadget_type
            partnerWeplusId = gadgetRequestBody.partner_weplus_id.toInt()
            nik = gadgetRequestBody.nik
        }

    }

    private fun setupStartDateBehaviour() {
        tvStartDateTitle.visibility = View.VISIBLE
        startDateLayout.visibility = View.VISIBLE
        tvStartDate.setOnClickListener {
            dateTimeFragment.startAtCalendarView()
            dateTimeFragment.show(supportFragmentManager, TAG_DATETIME_FRAGMENT)
        }
    }

    private fun setupDateTimePicker() {
        if (supportFragmentManager.findFragmentByTag(TAG_DATETIME_FRAGMENT) == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(R.string.ok),
                    getString(R.string.cancel)
            )
            val date = Date()

            dateTimeFragment.minimumDateTime = Date(System.currentTimeMillis() - 60 * 1000);
            dateTimeFragment.set24HoursMode(true)
            dateTimeFragment.setOnButtonClickListener(object : OnButtonWithNeutralClickListener {
                override fun onPositiveButtonClick(date: Date?) {
                    val c = Calendar.getInstance()
                    c.time = date
                    startDate = c[Calendar.YEAR].toString() + "-" + (c[Calendar.MONTH]+1) + "-" +
                            c[Calendar.DAY_OF_MONTH] + " " + c[Calendar.HOUR_OF_DAY] + ":" + c[Calendar.MINUTE] + ":" + c[Calendar.SECOND]
                    tvStartDate.text = myDateFormat.format(date)
                }

                override fun onNegativeButtonClick(date: Date?) {
                    // Do nothing
                }

                override fun onNeutralButtonClick(date: Date?) {
                    // Optional if neutral button does'nt exists
                    tvStartDate.text = ""
                }
            })
        }
    }

    private fun populateProductData() {
        tvInsuranceTitle.text = product.name
        var category = ""
        when (product.category_id) {
            "1" -> {
                category = "Motor"
            }
            "4" -> {
                category = "Life"
            }
            "5" -> {
                category = "Mobil"
            }
            "6" -> {
                category = "Personal Accident"
            }
            "7" -> {
                category = "Travel"
            }
            "8" -> {
                category = "DBD"
            }
            "9" -> {
                category = "Property"
            }
            "10" -> {
                category = "Liburan"
            }
            "11" -> {
                category = "Mobil Vintage"
            }
            "12" -> {
                category = "Penyakit Kritis"
            }
            "13" -> {
                category = "Sosial"
            }
            "15" -> {
                category = "Covid-19"
            }
        }
        tvInsuranceType.text = "Rp ${currencyFormatter(product.nominal)}"
        Glide.with(this).load(product.image).into(imgProduct)
    }

    private fun populateUserData() {
        tvOrderedByName.text = user.name
        tvEmail.text = user.email
    }

    @SuppressLint("SetTextI18n")
    private fun setupToggleButton() {
        toggleButton.setOnCheckedChangeListener { _, checked ->
            Log.d("data tertanggung", "cat id $catId")
            if (checked) {
                dataTertanggung.fullname = user.name
                dataTertanggung.idNo = user.identification_no
                dataTertanggung.phone = user.phone
                dataTertanggung.email = user.email
                dataTertanggung.address = user.address
                dataTertanggung.province = user.province
                dataTertanggung.city = user.city
                dataTertanggung.zipCode = user.codepos
                tvDataTertanggungHint.visibility = View.GONE
                tvDataTertanggungName.visibility = View.VISIBLE
                tvDataTertanggungID.visibility = View.VISIBLE
                tvDataTertanggungName.text = user.name
                tvDataTertanggungID.text = "No KTP: ${user.identification_no}"

                Log.d("data tertanggung", "sex befre id ${dataTertanggung.sex}")
                Log.d("data tertanggung", "cat id $catId")
                if (catId != 4 && catId != 12) {
                    dataTertanggung.dob = user.dob
                    dataTertanggung.sex = user.sex
                }
                Log.d("data tertanggung", "sex after id ${dataTertanggung.sex}")
                val intent = Intent(this, FormDataTertanggung::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                intent.putExtra("isAbove21",isAbove21)
                if (catId == 12) intent.putExtra("request_body", dataTertanggung)
                startActivityForResult(intent, 11)
            } else {
                dataTertanggung.fullname = ""
                dataTertanggung.idNo = ""
                dataTertanggung.phone = ""
                dataTertanggung.email = ""
                dataTertanggung.address = ""
                dataTertanggung.province = ""
                dataTertanggung.city = ""
                dataTertanggung.zipCode = user.codepos
                if (catId != 4 && catId != 12) {
                    dataTertanggung.dob = user.dob
                    dataTertanggung.sex = user.sex
                }
                tvDataTertanggungHint.visibility = View.VISIBLE
                tvDataTertanggungName.visibility = View.GONE
                tvDataTertanggungID.visibility = View.GONE
            }

        }
    }

    private fun setupToggleButtonPolis() {
        toggleButtonPolis.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                dataTertanggung.polisHolderFullName = user.name
                dataTertanggung.polisHolderIdNo = user.identification_no
                dataTertanggung.polisHolderDob = user.dob
                dataTertanggung.polisHolderSex = user.sex
                dataTertanggung.polisHolderPhone = user.phone
                dataTertanggung.polisHolderEmail = user.email
                dataTertanggung.polisHolderAddress = user.address
                dataTertanggung.polisHolderProvince = user.province
                dataTertanggung.polisHolderCity = user.city
                tvPolisHolderHint.visibility = View.GONE
                tvPolisHolderName.visibility = View.VISIBLE
                tvPolisHolderName.text = dataTertanggung.polisHolderFullName

                val intent = Intent(this, FormPolisHolder::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                startActivityForResult(intent, polisHolderReqCode)
            } else {
                dataTertanggung.polisHolderFullName = ""
                dataTertanggung.polisHolderIdNo = ""
                dataTertanggung.polisHolderDob = ""
                dataTertanggung.polisHolderSex = ""
                dataTertanggung.polisHolderPhone = ""
                dataTertanggung.polisHolderEmail = ""
                dataTertanggung.polisHolderAddress = ""
                dataTertanggung.polisHolderProvince = ""
                dataTertanggung.polisHolderCity = ""
                tvPolisHolderHint.visibility = View.VISIBLE
                tvPolisHolderName.visibility = View.GONE
            }
        }
    }

    private fun setupBuyButton() {
        btnPay.setOnClickListener {
            if (validate()) {
                showConfirmationDialog()
            }
        }
    }

    private fun showConfirmationDialog() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Sebelum ke halaman selanjutnya \n pastikan data yang anda isi sudah benar")
            builder.apply {
                setPositiveButton("Lanjut") { dialog, _ ->
                    val intent = Intent(this@FormBeliPolisNew, PembayaranActivity::class.java)
                    dataTertanggung.dateTimeStart = startDate
                    dataTertanggung.partnerWePlusId = partnerWeplusId.toString();
                    dataTertanggung.nik = nik;
                    intent.putExtra("data_tertanggung", dataTertanggung)
                    intent.putExtra("product", product)
                    intent.putExtra("is_agent",isAgent)
                    intent.putExtra("partner_weplus_id",partnerWeplusId)
                    intent.putExtra("nik",nik)
                    startActivity(intent)
                    dialog.dismiss()
                }
                setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun validate(): Boolean {
        var check = true
        if (::motorRequestBody.isInitialized) {
            if (tvDataMotorHint.visibility == View.VISIBLE) {
                check = false
                showError(this, "Lengkapi Data Motor")
            }
        } else if (::carRequestBody.isInitialized) {
            if (tvDataMotorHint.visibility == View.VISIBLE) {
                check = false
                showError(this, "Lengkapi Data Mobil")
            }
        } else if (catId == 11 || catId == 13) {
            if (tvDataMobilHint.visibility == View.VISIBLE) {
                check = false
                var errorMessage = when (catId) {
                    11 -> {
                        "Lengkapi Data Mobil"
                    }
                    else -> {
                        "Lengkapi Data Tambahan Social"
                    }
                }
                showError(this, errorMessage)
            }
        } else if(catId==9){
            if(tvDataPropertyHint.visibility==View.VISIBLE){
                showError(this,"Lengkapi data property")
            }
        }else {
            if (tvAhliWarisHint.visibility == View.VISIBLE || tvDataTertanggungHint.visibility == View.VISIBLE) {
                if ((product.partner_id == "10" && catId == 6) || (product.partner_id == "4" && catId == 6)) {
                    if (tvDataTertanggungHint.visibility == View.VISIBLE) {
                        check = false
                        showError(this, "Lengkapi Data Tertanggung")
                    }
                } else {
                    check = false
                    showError(this, "Lengkapi Data Tertanggung dan Ahli Waris")
                }
            }
        }
        if (!checkTC.isChecked) {
            check = false
            dataTertanggung.agreeTnc = 0
            showError(this, "Anda belum menyetujui syarat & ketentuan yang berlaku")
        } else {
            dataTertanggung.agreeTnc = 1
        }
        return check
    }

    override fun onDateSet(c: Calendar?, cat: String?) {
        if (c != null) {
            startDate = c[Calendar.YEAR].toString() + "-" + (c[Calendar.MONTH]+1) + "-" +
                    c[Calendar.DAY_OF_MONTH] + " " + c[Calendar.HOUR_OF_DAY] + ":" + c[Calendar.MINUTE] + ":" + c[Calendar.SECOND]
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy HH:mm")
            val newDateStr = postFormater.format(c.time)
            tvStartDate.text = newDateStr
            dataTertanggung.dateTimeStart = startDate
        }
    }

}