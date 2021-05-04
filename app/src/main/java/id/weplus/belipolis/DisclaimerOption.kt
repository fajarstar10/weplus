package id.weplus.belipolis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.weplus.R
import id.weplus.model.LoginData
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import kotlinx.android.synthetic.main.activity_disclaimer_option.*
import kotlinx.android.synthetic.main.view_back.*

class DisclaimerOption : AppCompatActivity() {
    private lateinit var product: Product
    private lateinit var user: LoginData
    private var dataTertanggung = DataTertanggungRequest()
    private var catId:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disclaimer_option)
        setToolbar()
        getIntentData()
        setupButtonAction()
    }

    private fun setToolbar(){
        viewback_title.text = "Informasi"
        viewback_description.visibility= View.INVISIBLE
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }

    private fun getIntentData(){
        product = intent.getParcelableExtra<Product>("product_detail")!! as Product
        catId = intent.getIntExtra("cat_id",0)
        Log.d("cat_id","cat id $catId")
    }

    private fun setupButtonAction(){
        btnUnder21thn.setOnClickListener {
            val intent = Intent(this, FormBeliPolisNew::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("cat_id", catId)
            intent.putExtra("isAbove21",0)
            startActivity(intent)
        }

        btnAbove21thn.setOnClickListener {
            val intent = Intent(this, FormBeliPolisNew::class.java)
            intent.putExtra("product_detail", product)
            intent.putExtra("cat_id", catId)
            intent.putExtra("isAbove21",1)
            startActivity(intent)
        }
    }
}