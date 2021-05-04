package id.weplus.belipolis.perjalanan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.View
import id.weplus.BaseActivity
import id.weplus.R
import kotlinx.android.synthetic.main.activity_asuransi_perjalanan.*
import kotlinx.android.synthetic.main.view_back.*

class AsuransiPerjalananActivity : BaseActivity() {
    private var tipePerjalanan=""
    private var tujuanPerjalanan=""
    private var partnerId=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asuransi_perjalanan)
        partnerId= intent.getIntExtra("partner_id",0);
        handleScreenBehaviour()
        setupToolbar()
        setupButton()
    }

    private fun setupToolbar() {
        viewback_title.text = "Perjalanan"
        viewback_description.text = "Pilih Tipe Asuransi"
    }

    @SuppressLint("SetTextI18n")
    private fun handleScreenBehaviour(){
        menuOnetime.setOnClickListener {
            if(tipePerjalanan == ""){
                menuOnetime.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
                menuOnetime.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
            }else if(tipePerjalanan == "annual"){
                menuOnetime.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
                menuOnetime.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
                menuTahunan.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_bg_border))
                menuTahunan.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.black_7f7f7f))
            }
            tipePerjalanan="onetime"
            layout_destinasi.visibility= View.VISIBLE
        }

        menuTahunan.setOnClickListener {
            if(tipePerjalanan == ""){
                menuTahunan.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
                menuTahunan.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
            }else if(tipePerjalanan == "onetime"){
                menuTahunan.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
                menuTahunan.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
                menuOnetime.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_bg_border))
                menuOnetime.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.black_7f7f7f))
            }
            tipePerjalanan="annual"
            layout_destinasi.visibility=View.VISIBLE
        }

        menuLuarNegeri.setOnClickListener {
            if(tujuanPerjalanan == ""){
                menuLuarNegeri.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
                menuLuarNegeri.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
            }else if(tujuanPerjalanan == "domestik"){
                menuLuarNegeri.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
                menuLuarNegeri.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
                menuDomestik.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_bg_border))
                menuDomestik.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.black_7f7f7f))
            }
            tujuanPerjalanan="abroad";
            layoutInformation.visibility=View.VISIBLE
            textInformation1.text="Perjalanan mencakup negara diluar dari Indonesia"
            enableButton()
        }

        menuDomestik.setOnClickListener {
            if(tujuanPerjalanan == ""){
                menuDomestik.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
                menuDomestik.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
            }else if(tujuanPerjalanan == "abroad"){
                menuDomestik.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
                menuDomestik.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
                menuLuarNegeri.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_bg_border))
                menuLuarNegeri.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.black_7f7f7f))
            }
            tujuanPerjalanan="domestik";
            layoutInformation.visibility=View.VISIBLE
            textInformation1.text="Perjalanan mencakup kota-kota di Indonesia"
            enableButton()
        }
    }

    private fun enableButton(){
        if (!btnTravelNext.isEnabled) {
            btnTravelNext.background = ContextCompat.getDrawable(this, R.drawable.border_fill_red)
            btnTravelNext.setTextColor(ContextCompat.getColor(this@AsuransiPerjalananActivity, R.color.white))
            btnTravelNext.isEnabled = true
        }
    }

    private fun setupButton(){
        btnTravelNext.setOnClickListener {
            val travelIntent = Intent(this, BeliPolisPerjalananActivity::class.java)
            travelIntent.putExtra("tipePerjalanan", tipePerjalanan)
            travelIntent.putExtra("tujuanPerjalanan", tujuanPerjalanan)
            travelIntent.putExtra("partner_id",partnerId);
            startActivity(travelIntent)
        }
    }
}