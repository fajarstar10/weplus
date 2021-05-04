package id.weplus.belipolis.kesehatan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.belipolis.FormAhliWaris
import id.weplus.belipolis.FormPolisHolder
import id.weplus.helper.getAge
import id.weplus.model.LoginData
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.request.HealthProductListRequest
import id.weplus.net.WeplusSharedPreference
import id.weplus.pembayaran.PembayaranActivity
import id.weplus.utility.TextHelper.currencyFormatter
import kotlinx.android.synthetic.main.activity_form_beli_polis_new.*
import kotlinx.android.synthetic.main.activity_form_beli_polis_new.tncText
import kotlinx.android.synthetic.main.activity_form_buy_health.*
import kotlinx.android.synthetic.main.activity_form_buy_health.DataAnak1Wrapper
import kotlinx.android.synthetic.main.activity_form_buy_health.DataAnak2Wrapper
import kotlinx.android.synthetic.main.activity_form_buy_health.DataAnak3Wrapper
import kotlinx.android.synthetic.main.activity_form_buy_health.DataDewasa2Wrapper
import kotlinx.android.synthetic.main.activity_form_buy_health.btnPay
import kotlinx.android.synthetic.main.activity_form_buy_health.checkTC
import kotlinx.android.synthetic.main.activity_form_buy_health.imgEditAhliWaris
import kotlinx.android.synthetic.main.activity_form_buy_health.imgEditAnak1
import kotlinx.android.synthetic.main.activity_form_buy_health.imgEditAnak2
import kotlinx.android.synthetic.main.activity_form_buy_health.imgEditAnak3
import kotlinx.android.synthetic.main.activity_form_buy_health.imgEditDataTertanggung
import kotlinx.android.synthetic.main.activity_form_buy_health.imgEditDewasa2
import kotlinx.android.synthetic.main.activity_form_buy_health.imgEditPolisHolder
import kotlinx.android.synthetic.main.activity_form_buy_health.imgProduct
import kotlinx.android.synthetic.main.activity_form_buy_health.polisHolderFormWrapper
import kotlinx.android.synthetic.main.activity_form_buy_health.toggleButton
import kotlinx.android.synthetic.main.activity_form_buy_health.toggleButtonPolis
import kotlinx.android.synthetic.main.activity_form_buy_health.tvAhliWarisHint
import kotlinx.android.synthetic.main.activity_form_buy_health.tvAhliWarisName
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataAnak1
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataAnak1ID
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataAnak2
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataAnak2ID
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataAnak3
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataAnak3ID
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataDewasa2
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataDewasa2ID
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataTertanggungHint
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataTertanggungID
import kotlinx.android.synthetic.main.activity_form_buy_health.tvDataTertanggungName
import kotlinx.android.synthetic.main.activity_form_buy_health.tvEditAnak1hint
import kotlinx.android.synthetic.main.activity_form_buy_health.tvEditAnak2hint
import kotlinx.android.synthetic.main.activity_form_buy_health.tvEditAnak3hint
import kotlinx.android.synthetic.main.activity_form_buy_health.tvEditDewasa2hint
import kotlinx.android.synthetic.main.activity_form_buy_health.tvEmail
import kotlinx.android.synthetic.main.activity_form_buy_health.tvInsuranceTitle
import kotlinx.android.synthetic.main.activity_form_buy_health.tvInsuranceType
import kotlinx.android.synthetic.main.activity_form_buy_health.tvOrderedByName
import kotlinx.android.synthetic.main.activity_form_buy_health.tvPolisHolderHint
import kotlinx.android.synthetic.main.activity_form_buy_health.tvPolisHolderName
import kotlinx.android.synthetic.main.activity_form_buy_travel.*
import kotlinx.android.synthetic.main.view_back.*

class FormBuyHealth : BaseActivity() {
    private lateinit var product: Product
    private lateinit var user: LoginData
    private lateinit var healthRequestBody: HealthProductListRequest

    private val itemType = object : TypeToken<java.util.ArrayList<DataTertanggungRequest>>() {}.type
    private var dataTertanggungReqCode = 11
    private var ahliWarisReqCode = 21
    private var dataTertanggungRespCode = 12
    private var ahliWarisRespCode = 22
    private var polisHolderReqCode = 31
    private var polisHolderRespCode = 32
    private var dataTertanggung = DataTertanggungRequest()
    private var catId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_buy_health)
        setupToolbar()
        getIntentData()
        getUser()
        setupProductInformation()
        setupToggleButton()
        setupHealthForm()
        populateUserData()
        setupButtonEdit()
        setupEditAhliWaris()
        setupEditPolisHolder()
        setupButtonNext()
        tncText.text = Html.fromHtml("Dengan ini saya sudah menyetujui <strong>syarat dan ketentuan</strong> yang berlaku");

    }

    private fun populateUserData() {
        tvOrderedByName.text = user.name
        tvEmail.text = user.email
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == dataTertanggungRespCode) {
            if (data != null) {
                dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                val healthIndex = data.getIntExtra("health_index", 0)!!
                printHealthData(healthIndex)
                if(product.partner_id=="18") copyDataTertanggungDataToPolisHolder()
            }
        }
        if (resultCode == ahliWarisRespCode) {
            if (data != null) {
                dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                tvAhliWarisHint.visibility = View.GONE
                tvAhliWarisName.visibility = View.VISIBLE
                tvAhliWarisName.text = dataTertanggung.beneficiaryName
            }
        }
        if (requestCode == polisHolderReqCode) {
            if (resultCode == polisHolderRespCode) {
                if (data != null) {
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvPolisHolderHint.visibility = View.GONE
                    tvPolisHolderName.visibility = View.VISIBLE
                    tvPolisHolderName.text = dataTertanggung.polisHolderFullName
                }
            }
        }
    }

    private fun copyDataTertanggungDataToPolisHolder(){
        val birthDate = dataTertanggung.dob.split("-")
        val age = getAge(birthDate[0].toInt(), birthDate[1].toInt(), birthDate[2].toInt())
                ?: "-1"
        if(age.toInt()>18) {
            dataTertanggung.polisHolderFullName = dataTertanggung.fullname
            dataTertanggung.polisHolderIdNo = dataTertanggung.idNo
            dataTertanggung.polisHolderPob = dataTertanggung.pob
            dataTertanggung.polisHolderDob = dataTertanggung.dob
            dataTertanggung.polisHolderRelation = "none"
            dataTertanggung.polisHolderSex = dataTertanggung.sex
            dataTertanggung.polisHolderPhone = dataTertanggung.phone
            dataTertanggung.polisHolderEmail = dataTertanggung.email
            dataTertanggung.polisHolderAddress = dataTertanggung.address
            dataTertanggung.polisHolderProvince = dataTertanggung.province
            dataTertanggung.polisHolderCity = dataTertanggung.city
            dataTertanggung.polisHolderZipCode = dataTertanggung.zipCode
        }
    }

    private fun setupProductInformation() {
        Glide.with(this).load(product.image).into(imgProduct)
        tvInsuranceTitle.text = product.name
        tvInsuranceType.text = "Rp "+currencyFormatter(""+product.nominal)
    }

    private fun setupButtonNext() {
        btnPay.setOnClickListener {
            if (validate()) {
                dataTertanggung.isSmoking = healthRequestBody.is_smoking.toString()
                dataTertanggung.paymentType = healthRequestBody.payment_type.toString()
                dataTertanggung.groupType = healthRequestBody.group_type.toString()
                dataTertanggung.adult = healthRequestBody.adult.toString()
                dataTertanggung.child = healthRequestBody.child.toString()
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
                    val intent = Intent(this@FormBuyHealth, PembayaranActivity::class.java)
                    intent.putExtra("data_tertanggung", dataTertanggung)
                    intent.putExtra("product", product)
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
        //validate form
        val adultValidate = validateAdultForm()
        val childValidate = validateChildForm()
        val polisHolderValidate = tvPolisHolderHint.visibility != View.VISIBLE

        if (!adultValidate || !childValidate) {
            showError(this, "Harap lengkapi data tertanggung")
            return false
        }
        if (product.partner_id == "18" && !polisHolderValidate) {
            val birthDate = dataTertanggung.dob.split("-")
            val age = getAge(birthDate[0].toInt(), birthDate[1].toInt(), birthDate[2].toInt())
                    ?: "-1"
            if(age.toInt()<18) {
                showError(this, "Harap lengkapi data pemegang polis")
                return false
            }
        }
        if (tvAhliWarisHint.visibility == View.VISIBLE) {
            showError(this, "Harap isi ahli waris")
            return false
        }
        if (checkTC.isChecked) {
            dataTertanggung.agreeTnc = 1
            dataTertanggung.jobDeclaration = "1"
        } else {
            showError(this, "Anda belum menyetujui syarat & ketentuan ")
            return false
        }
        return true
    }

    private fun validateAdultForm(): Boolean {
        val adultOneValidate = tvDataTertanggungHint.visibility != View.VISIBLE
        val adultTwoValidate = tvEditDewasa2hint.visibility != View.VISIBLE

        return if (healthRequestBody.adult == 1) {
            adultOneValidate
        } else {
            adultOneValidate && adultTwoValidate
        }
    }

    private fun validateChildForm(): Boolean {
        val childOneValidate = tvEditAnak1hint.visibility != View.VISIBLE
        val childTwoValidate = tvEditAnak2hint.visibility != View.VISIBLE
        val childThreeValidate = tvEditAnak3hint.visibility != View.VISIBLE


        return when (healthRequestBody.child) {
            1 -> {
                childOneValidate
            }
            2 -> {
                (childOneValidate && childTwoValidate)
            }
            3 -> {
                (childOneValidate && childTwoValidate && childThreeValidate)
            }
            else -> {
                return true
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun printHealthData(healthIndex: Int) {
        val gson = Gson()
        if (healthIndex == 0) {
            tvDataTertanggungHint.visibility = View.GONE
            tvDataTertanggungName.visibility = View.VISIBLE
            tvDataTertanggungID.visibility = View.VISIBLE

            tvDataTertanggungName.text = dataTertanggung.fullname
            tvDataTertanggungID.text = "No ID: ${dataTertanggung.idNo}"
        } else {
            val itemList = gson.fromJson<java.util.ArrayList<DataTertanggungRequest>>(dataTertanggung.additionData, itemType)
            val idx = itemList.indexOfFirst { it.categoryIndex == healthIndex }
            val dataTertanggung = itemList[idx]

            when (healthIndex) {
                1 -> {
                    tvEditDewasa2hint.visibility = View.GONE
                    tvDataDewasa2.visibility = View.VISIBLE
                    tvDataDewasa2ID.visibility = View.VISIBLE

                    tvDataDewasa2.text = dataTertanggung.fullname
                    tvDataDewasa2ID.text = "No ID: ${dataTertanggung.idNo}"
                }
                2 -> {
                    tvEditAnak1hint.visibility = View.GONE
                    tvDataAnak1.visibility = View.VISIBLE
                    tvDataAnak1ID.visibility = View.VISIBLE

                    tvDataAnak1.text = dataTertanggung.fullname
                    tvDataAnak1ID.text = "No ID: ${dataTertanggung.idNo}"
                }
                3 -> {
                    tvEditAnak2hint.visibility = View.GONE
                    tvDataAnak2.visibility = View.VISIBLE
                    tvDataAnak2ID.visibility = View.VISIBLE

                    tvDataAnak2.text = dataTertanggung.fullname
                    tvDataAnak2ID.text = "No ID: ${dataTertanggung.idNo}"
                }
                4 -> {
                    tvEditAnak3hint.visibility = View.GONE
                    tvDataAnak3.visibility = View.VISIBLE
                    tvDataAnak3ID.visibility = View.VISIBLE

                    tvDataAnak3.text = dataTertanggung.fullname
                    tvDataAnak3ID.text = "No ID: ${dataTertanggung.idNo}"
                }
            }
        }
    }

    private fun setupButtonEdit() {
        imgEditDataTertanggung.setOnClickListener {
            startActivityForResult(createHealthDataIntent(0), dataTertanggungReqCode)
        }

        imgEditDewasa2.setOnClickListener {
            startActivityForResult(createHealthDataIntent(1), dataTertanggungReqCode)
        }

        imgEditAnak1.setOnClickListener {
            startActivityForResult(createHealthDataIntent(2), dataTertanggungReqCode)
        }

        imgEditAnak2.setOnClickListener {
            startActivityForResult(createHealthDataIntent(3), dataTertanggungReqCode)
        }

        imgEditAnak3.setOnClickListener {
            startActivityForResult(createHealthDataIntent(4), dataTertanggungReqCode)
        }
    }

    private fun setupEditAhliWaris() {
        imgEditAhliWaris.setOnClickListener {
            val intent = Intent(this, FormAhliWaris::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("data_tertanggung", dataTertanggung)
            intent.putExtra("cat_id", catId)
            startActivityForResult(intent, 21)
        }
    }

    private fun createHealthDataIntent(healthIndex: Int): Intent {
        dataTertanggung.dob = healthRequestBody.dob
        dataTertanggung.sex = healthRequestBody.sex
        val intent = Intent(this, FormHealthData::class.java)
        intent.putExtra("product_detail", product)
        intent.putExtra("cat_id", catId)
        intent.putExtra("data_tertanggung", dataTertanggung)
        intent.putExtra("health_index", healthIndex)
        return intent
    }

    private fun setupHealthForm() {
        if (healthRequestBody.adult > 1) {
            DataDewasa2Wrapper.visibility = View.VISIBLE
        }
        val listWrapperAnak = ArrayList<RelativeLayout>()
        listWrapperAnak.add(DataAnak1Wrapper)
        listWrapperAnak.add(DataAnak2Wrapper)
        listWrapperAnak.add(DataAnak3Wrapper)
        Log.d("child", "child ${healthRequestBody.child}")
        if (healthRequestBody.child > 0) {
            for (x in 0 until (healthRequestBody.child)) {
                listWrapperAnak[x].visibility = View.VISIBLE
            }
        }
        // show data pemegang polis jika merupakan product simas
        dataTertanggung.dob = healthRequestBody.dob
        val birthDate = dataTertanggung.dob.split("-")
        val age = getAge(birthDate[0].toInt(), birthDate[1].toInt(), birthDate[2].toInt())
                ?: "-1"
        if (product.partner_id == "18" && age.toInt() < 18) {
            polisHolderFormWrapper.visibility = View.VISIBLE
        } else {
            polisHolderFormWrapper.visibility = View.GONE
        }
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
        healthRequestBody = intent.getParcelableExtra("request_body")!!
    }

    private fun getUser() {
        val jsonResponse = WeplusSharedPreference.getUser(this)
        val gson = Gson()
        user = gson.fromJson(jsonResponse, LoginData::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun setupToggleButton() {
        toggleButton.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                dataTertanggung.fullname = user.name
                dataTertanggung.idNo = user.identification_no
                dataTertanggung.dob = healthRequestBody.dob
                dataTertanggung.sex = healthRequestBody.sex
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

                val intent = Intent(this, FormHealthData::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                startActivityForResult(intent, 11)
            } else {
                dataTertanggung.fullname = ""
                dataTertanggung.idNo = ""
                dataTertanggung.dob = healthRequestBody.dob
                dataTertanggung.sex = healthRequestBody.sex
                dataTertanggung.phone = ""
                dataTertanggung.email = ""
                dataTertanggung.address = ""
                dataTertanggung.province = ""
                dataTertanggung.city = ""
                dataTertanggung.zipCode = user.codepos
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

    private fun setupEditPolisHolder() {
        imgEditPolisHolder.setOnClickListener {
            val intent = Intent(this, FormPolisHolder::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("cat_id", catId)
            intent.putExtra("data_tertanggung", dataTertanggung)
            startActivityForResult(intent, polisHolderReqCode)
        }
    }
}