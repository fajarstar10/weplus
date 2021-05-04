package id.weplus.belipolis

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import id.weplus.R
import id.weplus.helper.DatePickerFragment
import id.weplus.helper.OnDateSetListener
import id.weplus.helper.OnOptionsSelect
import id.weplus.helper.RoundedBottomSheet
import id.weplus.model.BaseFilter
import id.weplus.model.Product
import id.weplus.model.request.CarProductListRequest
import id.weplus.model.request.DataTertanggungRequest
import kotlinx.android.synthetic.main.activity_form_mobil.*
import kotlinx.android.synthetic.main.activity_form_mobil.btnNext
import kotlinx.android.synthetic.main.activity_form_mobil.etColor
import kotlinx.android.synthetic.main.activity_form_mobil.etEngine
import kotlinx.android.synthetic.main.activity_form_mobil.etPlateNumber
import kotlinx.android.synthetic.main.activity_form_mobil.etRangka
import kotlinx.android.synthetic.main.activity_form_mobil.etStnk
import kotlinx.android.synthetic.main.activity_form_mobil.tvConditionMotor
import kotlinx.android.synthetic.main.activity_form_mobil.tvPlate
import kotlinx.android.synthetic.main.activity_form_mobil.tvStartDate
import kotlinx.android.synthetic.main.activity_form_mobil.tvTahunProduksi
import kotlinx.android.synthetic.main.view_back.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FormMobil : AppCompatActivity(),OnOptionsSelect,OnDateSetListener {
    private lateinit var  request: CarProductListRequest
    private lateinit var dataTertanggung: DataTertanggungRequest
    private lateinit var product: Product
    private var isNew=-1
    private var isAutomatic=-1
    private val motorConditionTitle="Pilh Kondisi Mobil"
    private val motorTransmissionTitle="Pilh Transmisi Mobil"
    private var startDate=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_mobil)
        setupToolbar()
        getIntentData()
        setupNextButton()
        setupCarSpinner()
        setupDatePicker()
    }

    private fun setupDatePicker() {
        tvStartDate.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment.newInstance("start_date", this)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        if(title==motorConditionTitle){
            isNew=baseFilter.id
            tvConditionMotor.text = baseFilter.filterText
        }

        if(title==motorTransmissionTitle){
            isAutomatic=baseFilter.id
            tvTransmisi.text=baseFilter.filterText
        }
    }

    override fun onDateSet(c: Calendar?, cat: String?) {
        if (c != null) {
            startDate = c[Calendar.YEAR].toString() + "-" + c[Calendar.MONTH+1] + "-" +
                    c[Calendar.DAY_OF_MONTH]
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(c.time)
            tvStartDate.text=newDateStr
            dataTertanggung.startDate=startDate
        }
    }

    private fun setupCarSpinner() {
        val conditionFilter = ArrayList<BaseFilter>()
        conditionFilter.add(BaseFilter(1,"Baru","Baru"))
        conditionFilter.add(BaseFilter(0,"Bekas","Bekas"))

        val transmissionFilter  = ArrayList<BaseFilter>()
        transmissionFilter.add(BaseFilter(1,"Otomatis","otomatis"))
        transmissionFilter.add(BaseFilter(0,"Manual","manual"))

        tvConditionMotor.setOnClickListener {
            val roundedBottomSheet = RoundedBottomSheet(this)
            val bundle = Bundle()
            bundle.putString("title", motorConditionTitle)
            bundle.putParcelableArrayList("baseFilter", conditionFilter)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormMobil.supportFragmentManager, "test")
        }

        tvTransmisi.setOnClickListener {
            val roundedBottomSheet = RoundedBottomSheet(this)
            val bundle = Bundle()
            bundle.putString("title", motorTransmissionTitle)
            bundle.putParcelableArrayList("baseFilter", transmissionFilter)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormMobil.supportFragmentManager, "test")
        }
    }

    private fun setupNextButton() {
        btnNext.setOnClickListener {
            if(validate()){
                dataTertanggung.platNo = etPlateNumber.text.toString()
                dataTertanggung.carFrame = etRangka.text.toString()
                dataTertanggung.engineNo = etEngine.text.toString()
                dataTertanggung.stnkName=etStnk.text.toString()
                dataTertanggung.condition=""+isNew
                dataTertanggung.motorColor=etColor.text.toString()
                dataTertanggung.isAutomatic=""+isAutomatic
                dataTertanggung.startDate=startDate
                dataTertanggung.platId=request.plat_id
                dataTertanggung.typeInsurance = request.type_insurance
                dataTertanggung.additionDriverIncident=request.addition_driver_incident
                dataTertanggung.additionPassegerIncident=request.addition_passenger_incident
                dataTertanggung.additionProtection=request.addition_protection
                dataTertanggung.additionTpl=""+request.addition_tpl
                dataTertanggung.carPassenger = request.number_of_passenger
                val intent = Intent();
                intent.putExtra("data_tertanggung",dataTertanggung)
                setResult(52,intent)
                finish()
            }
        }
    }

    private fun validate(): Boolean {
        var result = true
        val conditionValidate=isNew!=-1
        val transimissionValiate=isAutomatic!=-1
        val colorValidate = etColor.text.isNotBlank()
        val plateValidate = etPlateNumber.text.isNotBlank()
        val rangkaValidate = etRangka.text.isNotBlank()
        val engineValidate = etEngine.text.isNotBlank()
        val stnkNoValidate = etStnk.text.isNotBlank()
        val startDateValidate = startDate.isNotBlank()

        if(conditionValidate){errorTextCondition.visibility=View.GONE}else{errorTextCondition.visibility=View.VISIBLE}
        if(transimissionValiate){errorTextTransmission.visibility=View.GONE}else{errorTextTransmission.visibility=View.VISIBLE}
        if(colorValidate){errorTextCondition.visibility=View.GONE}else{errorTextColor.visibility=View.VISIBLE}
        if(plateValidate){errorTextPlateNumber.visibility=View.GONE}else{errorTextPlateNumber.visibility=View.VISIBLE}
        if(rangkaValidate){errorTextRangka.visibility=View.GONE}else{errorTextRangka.visibility=View.VISIBLE}
        if(engineValidate){errorTextEngine.visibility=View.GONE}else{errorTextEngine.visibility=View.VISIBLE}
        if(stnkNoValidate){errorTextStnk.visibility=View.GONE}else{errorTextStnk.visibility=View.VISIBLE}
        if(startDateValidate){errorTextStartDate.visibility=View.GONE}else{errorTextStartDate.visibility=View.VISIBLE}

        return (conditionValidate && transimissionValiate && colorValidate && plateValidate && rangkaValidate
                && engineValidate && stnkNoValidate && startDateValidate)
    }

    private fun setupToolbar() {
        viewback_title.text = "Data Mobil"
        viewback_description.text="Silahkan Lengkapi Data Mobil yang di asuransikan"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun getIntentData() {
        request = intent.getParcelableExtra("request_body")!!
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!
        if(::request.isInitialized){
            tvMerekMobil.text=request.brand
            tvSeriMobil.text = request.series
            tvTahunProduksi.text=request.productionYear
            tvPlate.text=request.plateName
        }
        if(::dataTertanggung.isInitialized){
            if(dataTertanggung.condition!=null)
            isNew=dataTertanggung.condition.toInt()
            if(dataTertanggung.isAutomatic!=null)
            isAutomatic=dataTertanggung.isAutomatic.toInt()
            etRangka.setText(dataTertanggung.carFrame)
            etPlateNumber.setText(dataTertanggung.platNo)
            etStnk.setText(dataTertanggung.stnkName)
            etEngine.setText(dataTertanggung.engineNo)
            etColor.setText(dataTertanggung.motorColor)
            tvConditionMotor.text =if(isNew==1) "Baru" else if(isNew==-1) "Pilih Kondisi Mobil" else "Bekas"
            tvTransmisi.text =if(isAutomatic==1) "Otomatis" else if(isAutomatic==-1) "Pilih Transmisi Mobil" else  "Manual"
            if(dataTertanggung.startDate!=null) {
                startDate=dataTertanggung.startDate
               val startDates = dataTertanggung.startDate.split("-")
               val calendar = Calendar.getInstance()
               calendar.set(Calendar.YEAR, startDates[0].toInt())
               calendar.set(Calendar.MONTH, startDates[1].toInt())
               calendar.set(Calendar.DAY_OF_MONTH, startDates[2].toInt())
               @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
               val newDateStr = postFormater.format(calendar.time)
               tvStartDate.text = newDateStr
           }
        }
    }




}