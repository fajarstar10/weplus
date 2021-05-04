package id.weplus.agen.insured

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.detailpolis.DetailPolisMobilActivity
import id.weplus.model.Insured
import id.weplus.model.InsuredListModel
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_agen_insured.*
import kotlinx.android.synthetic.main.view_back.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgenInsuredActivity : AppCompatActivity() {

    private lateinit var adapter:AgentInsuredAdapter
    private val TAG = "AgentInsuredDetail"
    private var page=1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agen_insured)
        setupToolbar()
        getPolisAgent()
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar(){
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text="List Polis"
        viewback_description.text="Lihat semua list tertanggung"
    }

    private fun getPolisAgent() {
            loader.visibility = View.VISIBLE
            val isNetworkAvailable = Util.isNetworkAvailable(this)
            if (isNetworkAvailable) {
                val token = WeplusSharedPreference.getToken(this)
                val call = NetworkManager.getNetworkServiceWithHeader(this, token).getAgentInsuredList(page)
                call.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        val gson = Gson()
                        val insuredListModel = gson.fromJson(response.body(), InsuredListModel::class.java)
                        try {
                            loader.visibility = View.GONE
                            val job = JSONObject(response.body())
                            Log.d(TAG, job.toString())
                            val code = job.getString("code")
                            val description = job.getString("message")
                            if (code == ErrorCode.ERROR_00) {
                                val insuredList = insuredListModel.getData()
                                if (insuredList.size > 0) {
                                    setupAdapter(insuredList as ArrayList<Insured>)
                                } else {
                                    layout_no_polis.visibility = View.VISIBLE
                                }
                            } else if (code == ErrorCode.ERROR_03) {
                                val intent = Intent(this@AgenInsuredActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            } else {
                                loader.visibility = View.GONE
                                SweetAlertDialog(this@AgenInsuredActivity, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(description)
                                        .show()
                            }
                        } catch (e: Exception) {
                            loader.visibility = View.GONE
                            Log.i(TAG, e.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {
                        loader.visibility = View.GONE
                    }
                })
            } else {
                loader.visibility = View.GONE
                SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText(" ")
                        .setContentText(getString(R.string.network_error))
                        .show()
            }
        
    }

    private fun setupAdapter(insuredList: ArrayList<Insured>) {
        adapter = AgentInsuredAdapter(this, insuredList)
        val listener = AgentInsuredAdapter.PolisSayaOnItemClicked { pos: Int, tag: String? ->
            val intent = Intent(this, DetailPolisMobilActivity::class.java)
            intent.putExtra("tipe", "Aktif")
            intent.putExtra("is_agent", true)
            startActivity(intent)
        }
        adapter.setListenerPolisSaya(listener)
        val transaksiLayoutmanager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAgentInsured.layoutManager = transaksiLayoutmanager
        rvAgentInsured.adapter = adapter
        rvAgentInsured.itemAnimator = DefaultItemAnimator()
        //scrollData()?.let { rvTransaction.addOnScrollListener(it) }
    }
}