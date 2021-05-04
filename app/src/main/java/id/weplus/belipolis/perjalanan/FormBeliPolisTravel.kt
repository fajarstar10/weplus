package id.weplus.belipolis.perjalanan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.belipolis.*
import id.weplus.helper.DatePickerFragment
import id.weplus.helper.OnDateSetListener
import id.weplus.model.LoginData
import id.weplus.model.Product
import id.weplus.model.request.*
import id.weplus.net.WeplusSharedPreference
import id.weplus.pembayaran.PembayaranActivity
import kotlinx.android.synthetic.main.activity_form_beli_polis_new.*
import kotlinx.android.synthetic.main.view_back.*
import java.text.SimpleDateFormat
import java.util.*


class FormBeliPolisNew : BaseActivity(), OnDateSetListener {
    private lateinit var product: Product
    private lateinit var user: LoginData
    private var dataTertanggung = TravelBuyRequest()
    private var catId:Int=0
    private var dataTertanggungReqCode=11
    private var ahliWarisReqCode=21
    private var startDate="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_beli_polis_new)
        getIntentData()
        getUser()
        setupToolbar()
        populateProductData()
        populateUserData()
        setupToggleButton()
        setupBuyButton()
        setupEditDataTertanggung()
        setupEditAhliWaris()
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==dataTertanggungReqCode){
            if(resultCode==21){
                if (data != null) {
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataTertanggungHint.visibility= View.GONE
                    tvDataTertanggungName.visibility= View.VISIBLE
                    tvDataTertanggungID.visibility= View.VISIBLE
                    tvDataTertanggungName.text=dataTertanggung.fullname
                    Log.d("age","ages $catId - ${product.partner_id}")
                }
            }
        }
        if(requestCode==ahliWarisReqCode){
            if(resultCode==22){
                if(data!=null){
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvAhliWarisHint.visibility= View.GONE
                    Log.d("partner_id","partner_id ${product.partner_id}")
                }
            }
        }

    }

    private fun setupEditDataTertanggung() {
        imgEditDataTertanggung.setOnClickListener {
            val intent = Intent(this, FormDataTertanggung::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("cat_id", catId)
            intent.putExtra("data_tertanggung",dataTertanggung)
            if(catId==12)intent.putExtra("request_body",dataTertanggung)
            startActivityForResult(intent,11)
        }
    }

    private fun setupEditAhliWaris() {
        imgEditAhliWaris.setOnClickListener {
            val intent = Intent(this, FormAhliWaris::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("data_tertanggung",dataTertanggung)
            intent.putExtra("cat_id", catId)
            startActivityForResult(intent,21)
        }
    }

    private fun getUser(){
        val jsonResponse = WeplusSharedPreference.getUser(this)
        val gson = Gson()
        user = gson.fromJson(jsonResponse, LoginData::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar(){
        viewback_buttonback.setOnClickListener {
            finish()
        }

        viewback_title.text="Beli Asuransi"
        viewback_description.text="Isi data-data yang diperlukan"
    }

    private fun getIntentData(){
        product = intent.getParcelableExtra("product_detail")!!
        catId = intent.getIntExtra("cat_id",0)
    }

    private fun populateProductData(){
        tvInsuranceTitle.text=product.name
        tvInsuranceType.text=if(catId==6){
            "Personal Accident"
        }else{
            "Demam Berdarah"
        }
        Glide.with(this).load(product.image).into(imgProduct)
    }

    private fun populateUserData(){
        tvOrderedByName.text=user.name
        tvEmail.text=user.email
    }

    @SuppressLint("SetTextI18n")
    private fun setupToggleButton(){
        toggleButton.setOnCheckedChangeListener { _, checked ->
            if(checked){
                dataTertanggung.fullname=user.name
                dataTertanggung.dob=user.dob
                dataTertanggung.sex=user.sex
                dataTertanggung.phone=user.phone
                dataTertanggung.email=user.email
                dataTertanggung.address=user.address
                dataTertanggung.province=user.province
                dataTertanggung.city=user.city
                tvDataTertanggungHint.visibility= View.GONE
                tvDataTertanggungName.visibility= View.VISIBLE
                tvDataTertanggungID.visibility= View.VISIBLE
                tvDataTertanggungName.text=user.name
                tvDataTertanggungID.text="No KTP: ${user.identification_no}"

                val intent = Intent(this, FormDataTertanggung::class.java)
                intent.putExtra("product_detail", product)
                intent.putExtra("cat_id", catId)
                intent.putExtra("data_tertanggung",dataTertanggung)
                if(catId==12)intent.putExtra("request_body",dataTertanggung)
                startActivityForResult(intent,11)
            }else{
                dataTertanggung = TravelBuyRequest()
                tvDataTertanggungHint.visibility= View.VISIBLE
                tvDataTertanggungName.visibility= View.GONE
                tvDataTertanggungID.visibility= View.GONE
            }
        }
    }

    private fun setupBuyButton() {
        btnPay.setOnClickListener {
            if(validate()){
                showConfirmationDialog()
            }
        }
    }

    private fun showConfirmationDialog(){
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Sebelum ke halaman selanjutnya \n pastikan data yang anda isi sudah benar")
            builder.apply {
                setPositiveButton("Lanjut") { dialog, _ ->
                    val intent = Intent(this@FormBeliPolisNew, PembayaranActivity::class.java)
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

    private fun validate():Boolean{
        var check=true

        if (tvAhliWarisHint.visibility == View.VISIBLE || tvDataTertanggungHint.visibility == View.VISIBLE) {
            if ((product.partner_id == "10" && catId == 6) || (product.partner_id=="4" && catId==6)) {
                if (tvDataTertanggungHint.visibility == View.VISIBLE) {
                    check = false
                    showError(this, "Lengkapi Data Tertanggung")
                }
            } else {
                check = false
                showError(this, "Lengkapi Data Tertanggung dan Ahli Waris")
            }
        }

        if(!checkTC.isChecked){
            check=false
            showError(this,"Anda belum menyetujui syarat & ketentuan yang berlaku")
        }
        return check
    }

    override fun onDateSet(c: Calendar?, cat: String?) {
        if (c != null) {
            startDate = c[Calendar.YEAR].toString() + "-" + c[Calendar.MONTH] + "-" +
                    c[Calendar.DAY_OF_MONTH]+" "+c[Calendar.HOUR]+":"+c[Calendar.MINUTE]
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy HH:mm")
            val newDateStr = postFormater.format(c.time)
            tvStartDate.text=newDateStr
        }
    }

}