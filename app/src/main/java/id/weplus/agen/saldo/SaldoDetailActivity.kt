package id.weplus.agen.saldo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.agen.WithdrawRequestActivity
import id.weplus.agen.dashboard.DashboardItemClickListener
import id.weplus.model.response.agent.saldo.AgentSaldoData
import id.weplus.model.response.agent.saldo.AgentSaldoResponse
import id.weplus.model.response.agent.saldo.SaldoHistory
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.TextHelper.currencyFormatter
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_saldo_detail.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaldoDetailActivity : BaseActivity(), DashboardItemClickListener {
    private var page=1
    private val TAG="SaldoDetailActivity"

    private lateinit var adapter:SaldoAdapter
    private lateinit var agentSaldoData: AgentSaldoData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saldo_detail)
        setupToolbar()
        fetchAgentSaldo()
        setupButton()
    }

    private fun setupToolbar(){
        viewback_description.text="Lihat dan Tarik Saldo yang dimiliki"
        viewback_title.text="Detail Saldo"
        viewback_buttonback.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        if(::adapter.isInitialized){
            fetchAgentSaldo()
        }
    }

    private fun setupButton(){
        btnNext.setOnClickListener {
            val intent = Intent(this, WithdrawRequestActivity::class.java)
            intent.putExtra("agent_saldo", agentSaldoData)
            startActivity(intent)
        }
    }

    private fun fetchAgentSaldo(){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility = View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getAgentSaldo(page)
            call.enqueue(object : Callback<AgentSaldoResponse> {
                override fun onResponse(call: Call<AgentSaldoResponse>, response: Response<AgentSaldoResponse>) {
                    try {
                        loadingProgress.visibility = View.GONE
                        when (response.body()?.code) {
                            ErrorCode.ERROR_00 -> {
                                response.body()?.data?.let {
                                    Log.d("saldo", "saldo got")
                                    agentSaldoData = it
                                    populateView(it)
                                }
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@SaldoDetailActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                showError(this@SaldoDetailActivity, response.message())
                            }
                        }
                    } catch (e: Exception) {
                        loadingProgress.visibility = View.GONE
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<AgentSaldoResponse>, t: Throwable) {
                    loadingProgress.visibility = View.GONE
                    showError(this@SaldoDetailActivity, "Time Out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@SaldoDetailActivity, getString(R.string.network_error))
        }
    }

    private fun populateView(data: AgentSaldoData) {
        tvTotalSaldo.text="Rp ${currencyFormatter(data.saldo)}"
        setupSaldoHistory(data.saldoHistories)
        Log.d("test", "saldo history ${data.saldoHistories.size}")
    }

    private fun setupSaldoHistory(saldoHistory: ArrayList<SaldoHistory>){
        adapter = SaldoAdapter(this, saldoHistory, this)
        //adapter = DashboardAgentAdapter(this, menus, this)
        rvSaldoHistory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvSaldoHistory.adapter=adapter
    }

    override fun onDashboardItemClick(pos: Int) {
        //TODO("Not yet implemented")
    }
}