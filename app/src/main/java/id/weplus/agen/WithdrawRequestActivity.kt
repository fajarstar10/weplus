package id.weplus.agen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.model.Payment
import id.weplus.model.request.PaymentMethodRequest
import id.weplus.model.request.WithdrawSaldoRequest
import id.weplus.model.response.WithdrawSaldoResponse
import id.weplus.model.response.agent.saldo.AgentSaldoData
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.pembayaran.OnPaymentClicked
import id.weplus.pembayaran.PembayaranAdapter
import id.weplus.utility.TextHelper.currencyFormatter
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_withdraw_saldo.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WithdrawRequestActivity : BaseActivity(), OnPaymentClicked {
    private lateinit var paymentAdapter: PembayaranAdapter
    private lateinit var paymentMethodRequest: PaymentMethodRequest
    private lateinit var agentSaldoData: AgentSaldoData

    private val TAG = "WithdrawRequestActivity"
    private var price=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw_saldo)
        getIntentData()
        setupToolbar()
        setupPaymentMethod()
        setupButtonTarikSemua()
        setupBtnNext()
        setupSaldoWathcer()
    }

    private fun setupSaldoWathcer(){
        etSaldo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                etSaldo.removeTextChangedListener(this)
                val temp = etSaldo.text.toString().replace(".", "")
                price = temp
                if (!price.isNullOrEmpty()) {
                    etSaldo.setText(currencyFormatter(price))
                    etSaldo.setSelection(etSaldo.text.toString().length)
                }
                etSaldo.addTextChangedListener(this)

            }

        })
    }

    private fun setupButtonTarikSemua(){
        tvTarikSemua.setOnClickListener {
            etSaldo.setText(agentSaldoData.saldo)
        }
    }

    private fun getIntentData(){
        agentSaldoData = intent.getParcelableExtra("agent_saldo")!!
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_description.text = "Lakukan Penarikan Saldo"
        viewback_title.text = "Tarik Saldo"
        viewback_buttonback.setOnClickListener { finish() }
    }

    @SuppressLint("SetTextI18n")
    override fun onPaymentClicked(payment: Payment?) {
        //TODO("Not yet implemented")
        if(etSaldo.text.toString().isNotEmpty()) {
        informationWrapper.visibility= View.VISIBLE
        tvWithdrawlInformation.text="Pencairan saldo pada tanggal ${agentSaldoData.paymentChannel[0].transferDate}"
        tvWithdralBank.text="Bank ${agentSaldoData.bankInformations.bankName} ${agentSaldoData.bankInformations.bankAccount}"
        tvWithdrawlAccountName.text="${agentSaldoData.bankInformations.bankAccountName} "
        btnNext.isEnabled=true
        btnNext.setBackgroundResource(R.drawable.border_fill_red)

            tvWithdrawalNominal.text = "Rp " + currencyFormatter(price)
            tvTotalWithdrawal.text = "Rp " + currencyFormatter(price)
        }else{
            SweetAlertDialog(this@WithdrawRequestActivity)
                    .setTitleText("")
                    .setContentText("Harap isi nominal penarikan")
                    .setConfirmText("Ok")
                    .setConfirmClickListener {
                        it.dismissWithAnimation()
                    }
                    .show()
        }
    }

    private fun setupBtnNext(){
        btnNext.setOnClickListener {
            when {
                price.toLong() < 100000 -> {
                    SweetAlertDialog(this@WithdrawRequestActivity)
                            .setTitleText("")
                            .setContentText("Nominal penarikan kurang dari batas penarikan \n(Rp. 100.000)")
                            .setConfirmText("Ok")
                            .setConfirmClickListener {
                                it.dismissWithAnimation()
                            }
                            .show()
                }
                price.toLong()>agentSaldoData.saldo.toLong() -> {
                    SweetAlertDialog(this@WithdrawRequestActivity)
                            .setTitleText("")
                            .setContentText("Nominal penarikan melebihi saldo kamu\n( Rp ${
                                currencyFormatter(agentSaldoData.saldo)
                            })")
                            .setConfirmText("Ok")
                            .setConfirmClickListener {
                                it.dismissWithAnimation()
                            }
                            .show()
                }
                else -> {
                    sendWithdrawRequest()
                }
            }
        }
    }
    
    private fun sendWithdrawRequest(){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility = View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .withdrawSaldo(WithdrawSaldoRequest(agentSaldoData.paymentChannel[0].paymentChannel, price))
            call.enqueue(object : Callback<WithdrawSaldoResponse> {
                override fun onResponse(call: Call<WithdrawSaldoResponse>, response: Response<WithdrawSaldoResponse>) {
                    try {
                        loadingProgress.visibility = View.GONE
                        if (response.body()?.code == ErrorCode.ERROR_00) {
                            SweetAlertDialog(this@WithdrawRequestActivity, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Terima Kasih")
                                    .setContentText("""
    Permintaan penarikan saldo anda akan segera diproses
    """.trimIndent())
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener {
                                        finish()
                                    }
                                    .show()
                        } else if (response.body()?.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@WithdrawRequestActivity, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            showError(this@WithdrawRequestActivity, response.message())
                        }
                    } catch (e: Exception) {
                        loadingProgress.visibility = View.GONE
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<WithdrawSaldoResponse>, t: Throwable) {
                    loadingProgress.visibility = View.GONE
                    showError(this@WithdrawRequestActivity, "Time Out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@WithdrawRequestActivity, getString(R.string.network_error))
        }
    }

    private fun setupPaymentMethod() {
        paymentAdapter = PembayaranAdapter(this, this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvWithdrawalMethod.apply {
            layoutManager = mLayoutManager
            adapter = paymentAdapter
        }
        val payments:ArrayList<Payment> = ArrayList()
        val pay = Payment()
        pay.name = agentSaldoData.paymentChannel[0].text
        payments.add(pay)
        paymentAdapter.setPaymentMethod(payments)
    }
}