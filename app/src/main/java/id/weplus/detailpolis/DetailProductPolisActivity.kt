package id.weplus.detailpolis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.view.View
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import id.weplus.R
import id.weplus.model.ProductDetail
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_detail_product_polis.*
import kotlinx.android.synthetic.main.view_back.*

class DetailProductPolisActivity : AppCompatActivity() {
    private lateinit var idTransaksi:String
    private lateinit var polisNumber:String
    private lateinit var productDetail: ProductDetail

    private var isDescShow=false
    private var isInfoShow=false
    private var isBenefitShow=false


    companion object{
        const val idTransaksiExtra="id_transaksi"
        const val polisNumberExtra="polis_number"
        const val productExtra="product_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product_polis)
        setupToolbar()
        getIntentData()
        populateView()
        handleToggleBehaviour()
    }

    private fun setupToolbar(){
        viewback_title.text="Detail Product"
        viewback_description.text="Berikut detail produk asuransi yang anda pilih"
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }

    private fun getIntentData(){
        idTransaksi = intent.getStringExtra(idTransaksiExtra)!!
        polisNumber = intent.getStringExtra(polisNumberExtra)!!
        productDetail = intent.getSerializableExtra(productExtra) as ProductDetail
    }

    private fun populateView(){
        Glide.with(this).load(productDetail.image).into(imgAvatar)
        tvName.text=productDetail.partnerName
        tvProductName.text=productDetail.name
        tvProductPrice.text=idTransaksi
        tvBiayaPremiPrice.text=polisNumber

        tvBenefitValue.text= HtmlCompat.fromHtml(Util.arrayListToString(productDetail.benefits),HtmlCompat.FROM_HTML_MODE_LEGACY)
        tvDescValue.text= HtmlCompat.fromHtml(Util.arrayListToString(productDetail.general),HtmlCompat.FROM_HTML_MODE_LEGACY)
        tvInfoValue.text=HtmlCompat.fromHtml(Util.arrayListToString(productDetail.resume),HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun handleToggleBehaviour(){
        imgDropDesc.setOnClickListener {
            if(!isDescShow){
                imgDropDesc.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                tvDescValue.visibility= View.VISIBLE
            }else{
                imgDropDesc.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                tvDescValue.visibility=View.GONE
            }
            isDescShow=!isDescShow
        }

        imgDropBenefit.setOnClickListener {
            if(!isBenefitShow){
                imgDropBenefit.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                tvBenefitValue.visibility=View.VISIBLE
            }else{
                imgDropBenefit.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                tvBenefitValue.visibility=View.GONE
            }
            isBenefitShow=!isBenefitShow
        }

        imgDropInfo.setOnClickListener {
            if(!isInfoShow){
                imgDropInfo.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                tvInfoValue.visibility=View.VISIBLE
            }else{
                imgDropBenefit.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                tvInfoValue.visibility=View.GONE
            }
            isInfoShow=!isInfoShow
        }
    }
}