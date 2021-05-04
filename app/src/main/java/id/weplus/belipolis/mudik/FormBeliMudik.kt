package id.weplus.belipolis.mudik

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.belipolis.FormAhliWaris
import id.weplus.belipolis.FormDataTertanggung
import id.weplus.model.LoginData
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.net.WeplusSharedPreference
import id.weplus.pembayaran.PembayaranActivity
import id.weplus.utility.Util.getRupiahFormat
import kotlinx.android.synthetic.main.activity_form_beli_mudik.*
import kotlinx.android.synthetic.main.activity_prod_detail.*
import kotlinx.android.synthetic.main.view_back.*

class FormBeliMudik : BaseActivity() {
    private lateinit var product: Product
    private lateinit var user: LoginData
    private var dataTertanggung = DataTertanggungRequest()
    private var catId:Int=0
    private var dataTertanggungReqCode=11
    private var ahliWarisReqCode=21
    private var departureDataReqCode=31
    private var isAhliWarisHidden = false
    private var isRoundTrip=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_beli_mudik)
        getIntentData()
        getUser()
        setupToolbar()
        populateProductData()
        populateUserData()
        setupToggleButton()
        setupBuyButton()
        setupEditDataTertanggung()
        setupEditAhliWaris()
        setupEditDataKeberangkatan()
        setupButtonNext()
        setupEditDataPulang()
    }

    private fun setupButtonNext() {

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
                    tvDataTertanggungID.text="No KTP: "+dataTertanggung.idNo
                    Log.d("age","ages $catId - ${product.partner_id}")
                }
            }
        }
        if(requestCode==ahliWarisReqCode){
            if(resultCode==22){
                if(data!=null){
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvAhliWarisHint.visibility= View.GONE
                    tvAhliWarisName.visibility=View.VISIBLE
                    tvAhliWarisName.text= dataTertanggung.beneficiaryName
                    tvAhliWarisInfo.visibility=View.VISIBLE
                    tvAhliWarisInfo.text="Hubungan Keluarga : ${dataTertanggung.beneficiaryRelation}"
                    Log.d("partner_id","partner_id ${product.partner_id}")
                }
            }
        }

        if(requestCode==departureDataReqCode){
            if(resultCode==12){
                if(data!=null){
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataKeberangkatanHint.visibility= View.GONE
                    tvDataKeberangkatanName.visibility=View.VISIBLE
                    tvDataKeberangkatanName.text="Tanggal Keberangkatan : ${dataTertanggung.departureDate}"
                    tvDataKeberangkatanInfo.visibility=View.VISIBLE
                    tvDataKeberangkatanInfo.text="Kota Keberangkatan : ${dataTertanggung.departure}"
                }
            }

            if(resultCode==82){
                if(data!=null){
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataKeberangkatanHint.visibility= View.GONE
                    tvDataKeberangkatanName.visibility=View.VISIBLE
                    tvDataKeberangkatanName.text="Kode Booking : ${dataTertanggung.codeBooking}"
                    tvDataKeberangkatanInfo.visibility=View.VISIBLE
                    tvDataKeberangkatanInfo.text="Alasan Perjalanan : ${dataTertanggung.tripReason}"
                    Log.d("partner_id","partner_id ${product.partner_id}")
                }
            }

            if(resultCode==13){
                if(data!=null){
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataKeberangkatanHint.visibility= View.GONE
                    tvDataKeberangkatanName.visibility=View.VISIBLE
                    tvDataKeberangkatanName.text="Kode Booking : ${dataTertanggung.codeBooking}"
                    tvDataKeberangkatanInfo.visibility=View.VISIBLE
                    tvDataKeberangkatanInfo.text="No Penerbangan : ${dataTertanggung.departFlightNumber}"
                }
            }

            if(resultCode==14){
                if(data!=null){
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataPulangHint.visibility= View.GONE
                    tvDataPulangName.visibility=View.VISIBLE
                    tvDataPulangName.text="Kode Booking : ${dataTertanggung.returnCodeBooking}"
                    tvDataPulangInfo.visibility=View.VISIBLE
                    tvDataPulangInfo.text="No Penerbangan : ${dataTertanggung.returnFlightNumber}"
                }
            }
            if(resultCode==15){
                Log.d("testing","from flight check activty")
                if(data!=null){
                    Log.d("testing","data is not null")
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataPulangHint.visibility= View.GONE
                    tvDataPulangInfo.visibility=View.VISIBLE
                    tvDataPulangInfo.text="No Penerbangan : ${dataTertanggung.returnFlightNumber}"
                }else{
                    Log.d("testing","data is null")
                }
            }
            if(resultCode==16){
                Log.d("testing","from flight check activty")
                if(data!=null){
                    Log.d("testing","data is not null")
                    dataTertanggung = data.getParcelableExtra("data_tertanggung")!!
                    tvDataKeberangkatanHint.visibility= View.GONE
                    tvDataKeberangkatanName.visibility=View.VISIBLE
                    tvDataKeberangkatanName.text="Kode Booking : ${dataTertanggung.codeBooking}"
                    tvDataKeberangkatanInfo.visibility=View.VISIBLE
                    tvDataKeberangkatanInfo.text="No Penerbangan : ${dataTertanggung.departFlightNumber}"
                }else{
                    Log.d("testing","data is null")
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

    private fun setupEditDataPulang(){
        imgEditDataPulang.setOnClickListener {
            val intent = Intent(this, FormDataPesawatAcaReturn::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("data_tertanggung",dataTertanggung)
            intent.putExtra("cat_id", catId)
            startActivityForResult(intent,31)
        }
    }

    private fun setupEditDataKeberangkatan(){
        imgEditDataKeberangkatan.setOnClickListener {
            val intent = if(product.partner_id=="11" && product.name.toLowerCase().contains("train") ){
                Intent(this, FormDataKeberangkatanKereta::class.java)
            }else if(product.partner_id=="11" && product.name.toLowerCase().contains("flight")){
                Intent(this, FormDataPesawatSimas::class.java)
            }else if(product.partner_id=="11" && product.name.toLowerCase().contains("cancellation")){
                Intent(this, FormDataPesawatSimas::class.java)
            }else if(product.partner_id=="9" && product.name.toLowerCase().contains("safe domestic")){
                Intent(this, FormDataKeberangkatan::class.java)
            }else if(product.partner_id=="9" && product.name.toLowerCase().contains("flight")){
                Intent(this,FormDataPesawatAca::class.java)
            }else if(product.partner_id=="9" && product.name.toLowerCase().contains("cancellation")){
                Intent(this, FormAcaCancellation::class.java)
            }else{
                Intent(this, FlightCheckActivity::class.java)
            }
            intent.putExtra("product_detail", product)
            intent.putExtra("data_tertanggung",dataTertanggung)
            intent.putExtra("cat_id", catId)
            startActivityForResult(intent,departureDataReqCode)
        }
        Log.d("test","partner-id : "+product.partner_id+" && "+product.name.toLowerCase().contains("round trip"))
        if(product.partner_id=="9" && product.name.toLowerCase().contains("round trip")){
            isRoundTrip=true
            tvAhliWaris.visibility=View.GONE
            AhliWarisWrapper.visibility=View.GONE
            tvDataKepulangan.visibility=View.VISIBLE
            returnWrapper.visibility=View.VISIBLE
        }

        if(product.partner_id=="9" && product.name.toLowerCase().contains("flight")){
            isAhliWarisHidden=true
            tvAhliWaris.visibility=View.GONE
            AhliWarisWrapper.visibility=View.GONE
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
        tvInsuranceType.text= getRupiahFormat(product.nominal)
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
                dataTertanggung.idNo=user.identification_no
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
                dataTertanggung = DataTertanggungRequest()
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
                    val intent = Intent(this@FormBeliMudik, PembayaranActivity::class.java)
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
        if(isAhliWarisHidden){
            check = tvDataTertanggungHint.visibility != View.VISIBLE
            if(!check){
                showError(this, "Lengkapi Data Tertanggung")
            }
        }else if(isRoundTrip){

            val dataTertanggungValidate = tvDataTertanggungHint.visibility !=View.VISIBLE
            val dataKeberangkatanValidate = tvDataKeberangkatanHint.visibility!=View.VISIBLE
            val dataPulangValidate = tvDataPulangHint.visibility!=View.VISIBLE

            check = dataTertanggungValidate && dataKeberangkatanValidate && dataPulangValidate
            showError(this, "Lengkapi Data Tertanggung,data keberangkatan dan Data pulang")
        }else if (tvAhliWarisHint.visibility == View.VISIBLE || tvDataTertanggungHint.visibility == View.VISIBLE) {
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

}