package id.weplus.belipolis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.weplus.R
import id.weplus.helper.OnOptionsSelect
import id.weplus.helper.RoundedBottomSheet
import id.weplus.model.BaseFilter
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.request.MotorProductListRequest
import kotlinx.android.synthetic.main.activity_form_data_motor2.*
import kotlinx.android.synthetic.main.view_back.*

class FormDataMotor : AppCompatActivity(), OnOptionsSelect {
    private lateinit var request: MotorProductListRequest
    private lateinit var dataTertanggung: DataTertanggungRequest
    private lateinit var product: Product
    private var isNew = -1

    private val motorConditionTitle = "Pilh Kondisi Motor"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_motor2)
        setupToolbar()
        getIntentData()
        setupNextButton()
        setupMotorConditionSpinner()
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        isNew = baseFilter.id
        tvConditionMotor.text = baseFilter.filterText
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_title.text = "Data Motor"
        viewback_description.text = "Silahkan Lengkapi Data Motor yang di asuransikan"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun setupMotorConditionSpinner() {
        val baseFilters = ArrayList<BaseFilter>()
        baseFilters.add(BaseFilter(1, "Baru", "Baru"))
        baseFilters.add(BaseFilter(0, "Bekas", "Bekas"))
        tvConditionMotor.setOnClickListener {
            val roundedBottomSheet = RoundedBottomSheet(this)
            val bundle = Bundle()
            bundle.putString("title", motorConditionTitle)
            bundle.putParcelableArrayList("baseFilter", baseFilters)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormDataMotor.supportFragmentManager, "test")
        }
    }

    @SuppressLint("DefaultLocale")
    private fun setupNextButton() {
        btnNext.setOnClickListener {
            if (validate()) {
                // 1 -> JAGADIRI
                // 5 -> ADIRA
                if ((product.partner_id == "5" && product.id == 87) || product.partner_id == "14" || product.partner_id == "1" ||
                        (product.partner_id == "5" && product.id == 114)) {
                    dataTertanggung.platNo = etPlateNumber.text.toString()
                    dataTertanggung.motorFrame = etRangka.text.toString()
                    dataTertanggung.engineNo = etEngine.text.toString()
                    dataTertanggung.stnkName = etStnk.text.toString()
                    dataTertanggung.condition = "" + isNew
                    dataTertanggung.motorColor = etColor.text.toString()
                }
                // ADIRA - MOTOR Mikro
                if ((product.partner_id == "5" && product.name.toLowerCase().contains("motor mikro"))) {
                    dataTertanggung.platNo = etPlateNumber.text.toString()
                    dataTertanggung.motorFrame = etRangka.text.toString()
                }
                val intent = Intent()
                Log.d("test","plat no : ${dataTertanggung.platNo}")
                intent.putExtra("data_tertanggung", dataTertanggung)
                setResult(42, intent)
                finish()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun getIntentData() {
        request = intent.getParcelableExtra("request_body")!!
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!
        if (::request.isInitialized) {
            tvMerekMotor.text = request.brand
            tvSeriMotor.text = request.series
            tvTahunProduksi.text = request.productionYear
            tvPlate.text = request.plateName
        }
        if (::dataTertanggung.isInitialized) {
            etRangka.setText(dataTertanggung.motorFrame)
            etPlateNumber.setText(dataTertanggung.platNo)
            etStnk.setText(dataTertanggung.stnkName)
            etEngine.setText(dataTertanggung.engineNo)
            etColor.setText(dataTertanggung.motorColor)
            tvConditionMotor.text = dataTertanggung.condition
        }
        if ((product.partner_id == "5" && product.name.toLowerCase().contains("motor mikro"))) {
            stnkWrapper.visibility = View.GONE
            engineWrapper.visibility = View.GONE
            colorWrapper.visibility = View.GONE
            conditionWrapper.visibility = View.GONE
        }
    }

    @SuppressLint("DefaultLocale")
    private fun validate(): Boolean {
        var result = true
        val conditionValidate = isNew != -1
        val colorValidate = etColor.text.isNotBlank()
        val plateValidate = etPlateNumber.text.isNotBlank()
        val rangkaValidate = etRangka.text.isNotBlank()
        val engineValidate = etEngine.text.isNotBlank()
        val stnkNoValidate = etStnk.text.isNotBlank()
        //ADIRA - MOTOPRO Syariah + reliance + jagadiri
        if ((product.partner_id == "5" && product.id == 87) || product.partner_id == "14" || product.partner_id == "1") {
            errorTextCondition.visibility = if (conditionValidate) View.GONE else View.VISIBLE
            errorTextColor.visibility = if (colorValidate) View.GONE else View.VISIBLE
            errorTextPlateNumber.visibility = if (plateValidate) View.GONE else View.VISIBLE
            errorTextRangka.visibility = if (rangkaValidate) View.GONE else View.VISIBLE
            errorTextEngine.visibility = if (engineValidate) View.GONE else View.VISIBLE
            errorTextStnk.visibility = if (stnkNoValidate) View.GONE else View.VISIBLE

            result = (conditionValidate && colorValidate && plateValidate && rangkaValidate && engineValidate && stnkNoValidate)
        }
        //ADIRA - MOTOR MIKRO
        if ((product.partner_id == "5" && product.name.toLowerCase().contains("motor mikro"))) {
            errorTextPlateNumber.visibility = if (plateValidate) View.GONE else View.VISIBLE
            errorTextRangka.visibility = if (rangkaValidate) View.GONE else View.VISIBLE

            result = (plateValidate && rangkaValidate)
        }
        return result
    }


}