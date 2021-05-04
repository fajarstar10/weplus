package id.weplus.belipolis.perjalanan

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
import id.weplus.model.LoginData
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.request.TravelProductListRequest
import id.weplus.net.WeplusSharedPreference
import id.weplus.pembayaran.PembayaranActivity
import kotlinx.android.synthetic.main.activity_form_buy_travel.DataAnak1Wrapper
import kotlinx.android.synthetic.main.activity_form_buy_travel.DataAnak2Wrapper
import kotlinx.android.synthetic.main.activity_form_buy_travel.DataAnak3Wrapper
import kotlinx.android.synthetic.main.activity_form_buy_travel.DataDewasa2Wrapper
import kotlinx.android.synthetic.main.activity_form_buy_travel.btnPay
import kotlinx.android.synthetic.main.activity_form_buy_travel.checkTC
import kotlinx.android.synthetic.main.activity_form_buy_travel.imgEditAhliWaris
import kotlinx.android.synthetic.main.activity_form_buy_travel.imgEditAnak1
import kotlinx.android.synthetic.main.activity_form_buy_travel.imgEditAnak2
import kotlinx.android.synthetic.main.activity_form_buy_travel.imgEditAnak3
import kotlinx.android.synthetic.main.activity_form_buy_travel.imgEditDataTertanggung
import kotlinx.android.synthetic.main.activity_form_buy_travel.imgEditDewasa2
import kotlinx.android.synthetic.main.activity_form_buy_travel.imgEditPolisHolder
import kotlinx.android.synthetic.main.activity_form_buy_travel.imgProduct
import kotlinx.android.synthetic.main.activity_form_buy_travel.tncText
import kotlinx.android.synthetic.main.activity_form_buy_travel.toggleButton
import kotlinx.android.synthetic.main.activity_form_buy_travel.toggleButtonPolis
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvAhliWarisHint
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvAhliWarisName
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataAnak1
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataAnak1ID
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataAnak2
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataAnak2ID
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataAnak3
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataAnak3ID
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataDewasa2
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataDewasa2ID
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataTertanggungHint
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataTertanggungID
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvDataTertanggungName
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvEditAnak1hint
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvEditAnak2hint
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvEditAnak3hint
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvEditDewasa2hint
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvEmail
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvInsuranceTitle
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvInsuranceType
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvOrderedByName
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvPolisHolderHint
import kotlinx.android.synthetic.main.activity_form_buy_travel.tvPolisHolderName
import kotlinx.android.synthetic.main.view_back.*

class FormBuyTravel : BaseActivity() {
    private lateinit var product: Product
    private lateinit var user: LoginData
    private lateinit var travelRequestBody: TravelProductListRequest

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
        setContentView(R.layout.activity_form_buy_travel)
        setupToolbar()
        getIntentData()
        getUser()
        setupProductInformation()
        populateUserData()
        setupToggleButton()
        setupHealthForm()
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
                val travelIndex = data.getIntExtra("travel_index", 0)!!
                printTravelData(travelIndex)
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

    private fun setupProductInformation() {
        Glide.with(this).load(product.image).into(imgProduct)
        tvInsuranceTitle.text = product.name
        tvInsuranceType.text = product.nominal
    }

    private fun setupButtonNext() {
        btnPay.setOnClickListener {
            //Log.d("testing","validate result : ${validate()}")
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
                    val intent = Intent(this@FormBuyTravel, PembayaranActivity::class.java)
                    dataTertanggung.additionProtection=travelRequestBody.addition_protection
                    dataTertanggung.destination=travelRequestBody.destination
                    dataTertanggung.duration = travelRequestBody.duration
                    dataTertanggung.departureDate=travelRequestBody.departure_date
                    dataTertanggung.departureCity=travelRequestBody.departure_city
                    dataTertanggung.returnDate=travelRequestBody.return_date
                    dataTertanggung.typeGroup = travelRequestBody.type_group
                    dataTertanggung.packageType = ""+travelRequestBody.package_type
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

        if(travelRequestBody.type_group.toLowerCase()=="individu"){
                if(!adultValidate){
                    showError(this, "Harap lengkapi data tertanggung")
                    return false
                }
        }else{
            if (!adultValidate || !childValidate) {
                showError(this, "Harap lengkapi data tertanggung")
                return false
            }
            if (product.partner_id == "18" && !polisHolderValidate) {
                showError(this, "Harap lengkapi data pemegang polis")
                return false
            }
//        if (checkJd.isChecked) {
//            dataTertanggung.jobDeclaration = "1"
//        } else {
//            showError(this, "Anda belum menyetujui jenis pekerjaan")
//            return false
//        }
            if (tvAhliWarisHint.visibility == View.VISIBLE) {
                showError(this, "Harap isi ahli waris")
                return false
            }
            if (checkTC.isChecked) {
                dataTertanggung.agreeTnc = 1
                dataTertanggung.jobDeclaration="1"
            } else {
                showError(this, "Anda belum menyetujui syarat & ketentuan ")
                return false
            }
        }
        return true
    }

    @SuppressLint("DefaultLocale")
    private fun validateAdultForm(): Boolean {
        val adultOneValidate = tvDataTertanggungHint.visibility != View.VISIBLE
        val adultTwoValidate = tvEditDewasa2hint.visibility != View.VISIBLE
        //validate individu
        return when {
            travelRequestBody.type_group.toLowerCase()=="individu" -> {
                adultOneValidate
            }
            travelRequestBody.type_group.toLowerCase()=="family" -> {
                adultOneValidate && adultTwoValidate
            }
            else -> {
                true
            }
        }
    }

    private fun validateChildForm(): Boolean {
        val childOneValidate = tvEditAnak1hint.visibility != View.VISIBLE
        val childTwoValidate = tvEditAnak2hint.visibility != View.VISIBLE
        val childThreeValidate = tvEditAnak3hint.visibility != View.VISIBLE

        return when (travelRequestBody.package_type) {
            3 -> {
                childOneValidate
            }
            4 -> {
                (childOneValidate && childTwoValidate)
            }
            5 -> {
                (childOneValidate && childTwoValidate && childThreeValidate)
            }
            else -> {
                return true
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun printTravelData(travelIndex: Int) {
        val gson = Gson()
        if (travelIndex == 0) {
            tvDataTertanggungHint.visibility = View.GONE
            tvDataTertanggungName.visibility = View.VISIBLE
            tvDataTertanggungID.visibility = View.VISIBLE

            tvDataTertanggungName.text = dataTertanggung.fullname
            tvDataTertanggungID.text = "No ID: ${dataTertanggung.idNo}"
        } else {
            val itemList = gson.fromJson<java.util.ArrayList<DataTertanggungRequest>>(dataTertanggung.additionInsured, itemType)
            val idx = itemList.indexOfFirst { it.categoryIndex == travelIndex }
            val dataTertanggung = itemList[idx]

            when (travelIndex) {
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
            startActivityForResult(createTravelDataIntent(0), dataTertanggungReqCode)
        }

        imgEditDewasa2.setOnClickListener {
            startActivityForResult(createTravelDataIntent(1), dataTertanggungReqCode)
        }

        imgEditAnak1.setOnClickListener {
            startActivityForResult(createTravelDataIntent(2), dataTertanggungReqCode)
        }

        imgEditAnak2.setOnClickListener {
            startActivityForResult(createTravelDataIntent(3), dataTertanggungReqCode)
        }

        imgEditAnak3.setOnClickListener {
            startActivityForResult(createTravelDataIntent(4), dataTertanggungReqCode)
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

    private fun createTravelDataIntent(travelIndex: Int): Intent {
        val intent = Intent(this, FormTravelData::class.java)
        intent.putExtra("product_detail", product)
        intent.putExtra("cat_id", catId)
        intent.putExtra("data_tertanggung", dataTertanggung)
        intent.putExtra("travel_index", travelIndex)
        return intent
    }

    private fun setupHealthForm() {
        if(travelRequestBody.type_group=="family"){
            DataDewasa2Wrapper.visibility=View.VISIBLE
        }

        val listWrapperAnak = ArrayList<RelativeLayout>()
        listWrapperAnak.add(DataAnak1Wrapper)
        listWrapperAnak.add(DataAnak2Wrapper)
        listWrapperAnak.add(DataAnak3Wrapper)
        Log.d("child", "child ${travelRequestBody.package_type}")
        if (travelRequestBody.package_type > 2) {
            for (x in 0 until (travelRequestBody.package_type-2)) {
                listWrapperAnak[x].visibility = View.VISIBLE
            }
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
        product = intent.getParcelableExtra("product_detail")!!
        catId = intent.getIntExtra("cat_id", 0)
        travelRequestBody = intent.getParcelableExtra("request_body")!!
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
                dataTertanggung.dob = user.dob
                dataTertanggung.sex = user.sex
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

                val intent = Intent(this, FormTravelData::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung", dataTertanggung)
                startActivityForResult(intent, 11)
            } else {
                dataTertanggung.fullname = ""
                dataTertanggung.idNo = ""
                dataTertanggung.dob = ""
                dataTertanggung.sex = ""
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