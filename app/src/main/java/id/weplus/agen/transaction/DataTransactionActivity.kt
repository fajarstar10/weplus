package id.weplus.agen.transaction

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.helper.EndlessOnScrollListener
import id.weplus.model.response.agent.transaction.AgentTransaction
import id.weplus.model.response.agent.transaction.AgentTransactionListResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_data_transaction.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataTransactionActivity : AppCompatActivity(),AgentTransactionAdapter.TransaksiOnItemClicked {
    
    private val TAG="AgenDataTransaction"
    private var adapter: AgentTransactionAdapter? = null
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_transaction)
        setupToolbar()
        setupAdapter()
        fetchTransactionList()
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar(){
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text="List Transaksi"
        viewback_description.text="Lihat semua transaksi mitra"
    }

    private fun setupAdapter() {
        adapter = AgentTransactionAdapter(this, this)
        val transaksiLayoutmanager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTransaction.layoutManager = transaksiLayoutmanager
        rvTransaction.adapter = adapter
        rvTransaction.itemAnimator = DefaultItemAnimator()
        scrollData()?.let { rvTransaction.addOnScrollListener(it) }
    }
    private fun fetchTransactionList() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingWrapper.visibility= View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call: Call<AgentTransactionListResponse> = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getAgentTransactions(page)
            call.enqueue(object : Callback<AgentTransactionListResponse?> {
                override fun onResponse(call: Call<AgentTransactionListResponse?>, response: Response<AgentTransactionListResponse?>) {
                    loadingWrapper.visibility = View.GONE
                    val resp = response.body()
                    try {
                        when (resp?.code) {
                            ErrorCode.ERROR_00 -> {
                                resp?.data?.transaction?.let { adapter?.addItems(it) }
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@DataTransactionActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                SweetAlertDialog(this@DataTransactionActivity, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(resp?.message)
                                        .show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<AgentTransactionListResponse?>, t: Throwable) {
                    loadingWrapper.visibility = View.GONE
                    try {
                        SweetAlertDialog(this@DataTransactionActivity, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("")
                                .setContentText("Time Out")
                                .show()
                    } catch (e: Exception) {
                        e.message
                    }
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show()
        }
    }

    private fun scrollData(): EndlessOnScrollListener? {
        return object : EndlessOnScrollListener() {
            override fun onLoadMore() {
                page++
                fetchTransactionList()
            }
        }
    }

    override fun onItem(agentTransaction: AgentTransaction) {
        val intent = Intent(this, AgentTransactionDetailActivity::class.java)
        intent.putExtra("history", false)
        intent.putExtra("transaction", agentTransaction)
        startActivity(intent)
    }


}