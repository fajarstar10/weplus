package id.weplus.agen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.belipolis.criticalIll.AsuransiCriticalActivity
import id.weplus.belipolis.gadget.GadgetFilterFormActivity
import id.weplus.belipolis.kesehatan.KesehatanActivity
import id.weplus.belipolis.life.AsuransiLifeActivity
import id.weplus.belipolis.mobil.FormDataMobilActivity
import id.weplus.belipolis.motor.AsuransiMotorActivity
import id.weplus.belipolis.perjalanan.AsuransiPerjalananActivity
import id.weplus.belipolis.productlist.ProductListActivity
import id.weplus.model.BuyPolisModel
import id.weplus.model.BuyPolisModel.Categori
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.GridSpacingItemDecoration
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_buy_polis.*
import kotlinx.android.synthetic.main.view_back.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyPolisActivity : BaseActivity() {
    private val TAG = "BuyPolisAgentActivity"
    private lateinit var kategoriAdapter: BuyPolisAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_polis)
        setupToolbar()
        fetchPolisCategory()
    }

    private fun setupToolbar(){
        viewback_buttonback.setOnClickListener {
            finish()
        }

        viewback_description.text="Pilih berbagai jenis asuransi yang tersedia"
        viewback_title.text="Beli Polis"
    }

    private fun fetchPolisCategory() {
        val isNetworkAvailable = Util.isNetworkAvailable(application)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val header = HashMap<String, String>()
            header["Content-Type"] = "application/json"
            header["agent"] = "1"
            val call = NetworkManager.getNetworkServiceWithHeader(this, token)
                    .getAgentBuyPolis(header, 1)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i(TAG, "Beli Polis : " + response.body())
                    val gson = Gson()
                    val buyPolisModel = gson.fromJson(response.body(), BuyPolisModel::class.java)
                    val buypolisData = buyPolisModel.getData()
                    try {
                        val belipolis = JSONObject(response.body())
                        val code = belipolis.getString("code")
                        val description = belipolis.getString("message")
                        when (code) {
                            ErrorCode.ERROR_00 -> {
                                WeplusSharedPreference.setBuyPolis(this@BuyPolisActivity, buypolisData.polis)
                                Log.d(TAG, "category : " + buypolisData.category.toString())
                                kategoriAdapter = BuyPolisAdapter(this@BuyPolisActivity, buypolisData.category)
                                rvPolisKategori.isNestedScrollingEnabled = true
                                val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this@BuyPolisActivity, 3)
                                rvPolisKategori.layoutManager = mLayoutManager
                                rvPolisKategori.adapter = kategoriAdapter
                                rvPolisKategori.addItemDecoration(GridSpacingItemDecoration(3, Util.dpToPx(10, this@BuyPolisActivity), false))
                                rvPolisKategori.itemAnimator = DefaultItemAnimator()
                                kategoriAdapter.setListenerSemua(listenerSemua)
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@BuyPolisActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                SweetAlertDialog(this@BuyPolisActivity, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(description)
                                        .show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, e.message.toString())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    SweetAlertDialog(this@BuyPolisActivity, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show()
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            SweetAlertDialog(this@BuyPolisActivity, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show()
        }
    }

    val listenerSemua = BuyPolisAdapter.OnClickedKategoriSemua { pos: Int, tag: String? ->
        when (kategoriAdapter.getItem(pos).id) {
            1 -> {
                val motor = Intent(this, AsuransiMotorActivity::class.java)
                motor.putExtra("cat_id", kategoriAdapter.getItem(pos).id)
                startActivity(motor)
            }
            5 -> {
                val mobil = Intent(this, FormDataMobilActivity::class.java)
                mobil.putExtra("is_agent", true)
                mobil.putExtra("cat_id", kategoriAdapter.getItem(pos).id)
                startActivity(mobil)
            }
            7 -> {
                val travel = Intent(this, AsuransiPerjalananActivity::class.java)
                travel.putExtra("cat_id", kategoriAdapter.getItem(pos).id)
                startActivity(travel)
            }
            2 -> {
                val kesehatan = Intent(this, KesehatanActivity::class.java)
                kesehatan.putExtra("cat_id", kategoriAdapter.getItem(pos).id)
                startActivity(kesehatan)
            }
            4 -> {
                val life = Intent(this, AsuransiLifeActivity::class.java)
                life.putExtra("is_agent", true)
                life.putExtra("cat_id", kategoriAdapter.getItem(pos).id)
                startActivity(life)
            }
            12 -> {
                val critical = Intent(this, AsuransiCriticalActivity::class.java)
                critical.putExtra("cat_id", kategoriAdapter.getItem(pos).id)
                startActivity(critical)
            }
            15 -> {
                val gadgetIntent = Intent(this, GadgetFilterFormActivity::class.java)
                gadgetIntent.putExtra("cat_id", kategoriAdapter.getItem(pos).id)
                startActivity(gadgetIntent)
            }
            else -> goToProductList(kategoriAdapter.getItem(pos), true)
        }
    }

    private fun goToProductList(cat: Categori, filterEnabled: Boolean) {
        val productIntent = Intent(this, ProductListActivity::class.java)
        productIntent.putExtra("cat_id", cat.id)
        productIntent.putExtra("is_agent", true)
        productIntent.putExtra("filterEnabled", filterEnabled)
        startActivity(productIntent)
    }
}