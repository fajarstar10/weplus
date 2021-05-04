package id.weplus.agen.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WebViewActivity
import id.weplus.WelcomeActivity
import id.weplus.agen.BuyPolisActivity
import id.weplus.agen.insured.AgenInsuredActivity
import id.weplus.agen.saldo.SaldoDetailActivity
import id.weplus.agen.transaction.DataTransactionActivity
import id.weplus.model.response.agent.AgentData
import id.weplus.model.response.agent.AgentMenu
import id.weplus.model.response.agent.AgentResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.TextHelper.currencyFormatter
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_dashboard_agent.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DashboardAgentActivity : BaseActivity(), DashboardItemClickListener {
    private val TAG="DashboardAgentActivity"
    var monthName: Array<String> = arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")

    private lateinit var adapter: DashboardAgentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_agent)
        setupToolbar()
        fetchAgentData()
    }

    override fun onResume() {
        super.onResume()
        if(tvTotalKomisi!=null){
            fetchAgentData()
        }
    }

    private fun fetchAgentData(){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility = View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .agentDashboard
            call.enqueue(object : Callback<AgentResponse> {
                override fun onResponse(call: Call<AgentResponse>, response: Response<AgentResponse>) {
                    try {
                        loadingProgress.visibility = View.GONE
                        when (response.body()?.code) {
                            ErrorCode.ERROR_00 -> {
                                populateView(response.body()!!.data)
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@DashboardAgentActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                showError(this@DashboardAgentActivity, response.message())
                            }
                        }
                    } catch (e: Exception) {
                        loadingProgress.visibility = View.GONE
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<AgentResponse>, t: Throwable) {
                    loadingProgress.visibility = View.GONE
                    showError(this@DashboardAgentActivity, "Time Out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@DashboardAgentActivity, getString(R.string.network_error))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateView(agentData: AgentData){
        Log.d("error", "part1");
        tvTotalKomisi.text="Rp ${currencyFormatter(agentData.totalKomisi)}"
        tvJumlahTransaksi.text = agentData.totalTransakti
        tvPolisAktif.text=agentData.totalPolisAktif
        tvAgenSaldo.text="Saldo : Rp "+currencyFormatter(agentData.saldo)
        tvAgenName.text=agentData.fullName
        tvAchievementPeriod.text="Hingga ${generateReadableDate(agentData.dateEnd)}"
        Log.d("error", "part2")
        achievementProgress.progress=agentData.totalMonimalTriwulan.toFloat().toInt()
        Log.d("error", "part3")
        tvAgenTarget.text =
                "Rp ${currencyFormatter(agentData.totalMonimalTriwulan)} / Rp ${currencyFormatter(agentData.targetTriwulan)}"
        tvAgenTargetPercent.text="(${agentData.progressTransaksiTriwulan.toFloat().toInt()*100} %)"
        Log.d("error", "part4")
        Glide.with(this)
                .load(agentData.imageProfile)
                .apply(RequestOptions().circleCrop())
                .error(R.drawable.ic_baseline_account_circle_24)
                .into(imgAgenAvatar)
        setupAgentFeatureList(agentData.listMenu)
        buttonBuyProtection.setOnClickListener {
            val intent = Intent(this, BuyPolisActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateReadableDate(date: String):String{
        var generatedDate=""
        val formatIncoming: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val formatOutgoing = SimpleDateFormat("dd MMM yyyy, HH:mm")
        val tz: TimeZone = TimeZone.getTimeZone("Asia/Jakarta")
        println(tz.getDisplayName(false, TimeZone.SHORT, Locale.ENGLISH)) // WIB


        formatOutgoing.timeZone = tz
        generatedDate= formatOutgoing.format(formatIncoming.parse(date))

        Log.d("date", "Date in Indonesia: $generatedDate") // 2015-03-03

        var test = generatedDate.split(" ").toMutableList()


        return when {
            generatedDate.contains("Dec") -> {
                generatedDate.replace("Dec", "Desember")
            }
            generatedDate.contains("Jan") -> {
                generatedDate.toLowerCase().replace("Jan", "Januari")
            }
            generatedDate.contains("Feb") -> {
                generatedDate.toLowerCase().replace("Feb", "Februari")
            }
            generatedDate.contains("Mar") -> {
                generatedDate.toLowerCase().replace("Mar", "Maret")
            }
            generatedDate.contains("Apr") -> {
                generatedDate.toLowerCase().replace("Apr", "April")
            }
            generatedDate.contains("May") -> {
                generatedDate.toLowerCase().replace("May", "Mei")
            }
            generatedDate.contains("Jun") -> {
                generatedDate.toLowerCase().replace("Jun", "Juni")
            }
            generatedDate.contains("Jul") -> {
                generatedDate.toLowerCase().replace("Jul", "Jul")
            }
            generatedDate.contains("Aug") -> {
                generatedDate.toLowerCase().replace("Aug", "Agustus")
            }
            generatedDate.contains("Sep") -> {
                generatedDate.toLowerCase().replace("Sep", "September")
            }
            generatedDate.contains("Oct") -> {
                generatedDate.toLowerCase().replace("Oct", "Oktober")
            }
            else -> {
                generatedDate.toLowerCase().replace("Nov", "November")
            }

        }

    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar(){
        viewback_description.text="Halaman Utama WE+ Mitra"
        viewback_title.text="WE+ Mitra"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun setupAgentFeatureList(menus: ArrayList<AgentMenu>){
        adapter = DashboardAgentAdapter(this, menus, this)
        rvAgen.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAgen.adapter=adapter
    }

    override fun onDashboardItemClick(pos: Int) {
        when(pos){
            0 -> {
                startActivity(Intent(this, SaldoDetailActivity::class.java))
            }
            1 -> {
                startActivity(Intent(this, AgenInsuredActivity::class.java))
            }
            2 -> {
                startActivity(Intent(this, DataTransactionActivity::class.java))
            }
            3 -> {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", adapter.getItemAt(pos).url)
                intent.putExtra("title", "Bantuan")
                intent.putExtra("desc", "Cek informasi tentang WE+ mitra")
                startActivity(intent)
            }
            4 -> {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", adapter.getItemAt(pos).url)
                intent.putExtra("title", "Pengantar Aplikasi")
                intent.putExtra("desc", "Materi Online Belajar Aplikasi")
                startActivity(intent)
            }
        }
    }
}