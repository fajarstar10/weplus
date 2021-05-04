package id.weplus.belipolis.gadget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import id.weplus.R
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import kotlinx.android.synthetic.main.activity_form_gadget_data.*
import kotlinx.android.synthetic.main.view_back.*

class FormGadgetData : AppCompatActivity() {
    private lateinit var product: Product
    private lateinit var dataTertanggung: DataTertanggungRequest
    private val TAG="FormGadgetData"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_gadget_data)
        setupToolbar()
        getIntentData()
        setupButtonNext()
    }

    private fun isNotPhoneOrTablet():Boolean{
        return dataTertanggung.gadget_type!="1" && dataTertanggung.gadget_type!="2"
    }

    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text = "Data Gadget"
        viewback_description.text = "Silahkan Lengkapi Data Gadget"
    }

    private fun getIntentData(){
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!
        if(dataTertanggung.imei!=null){
            etGadgetImei.setText(dataTertanggung.imei)
        }
        if(dataTertanggung.serial_number!=null){
            etSerialNumber.setText(dataTertanggung.serial_number)
        }
        if(isNotPhoneOrTablet()){
            gadgetBrandWrapper.visibility = View.GONE
        }
    }

    private fun validate():Boolean{
        return if(isNotPhoneOrTablet()){
            !etSerialNumber.text.isNullOrBlank()
        }else{
            (!etGadgetImei.text.isNullOrBlank() && !etSerialNumber.text.isNullOrBlank())
        }

    }

    private fun setupButtonNext(){
        btnNext.setOnClickListener {
            Log.d("validate","validate : ${validate()}")
            if(validate()) {
                val intent = Intent()
                dataTertanggung.imei = etGadgetImei.text.toString()
                dataTertanggung.serial_number = etSerialNumber.text.toString()
                intent.putExtra("data_tertanggung", dataTertanggung)
                setResult(22, intent)
                finish()
            }
        }
    }
}