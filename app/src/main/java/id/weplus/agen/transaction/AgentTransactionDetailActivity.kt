package id.weplus.agen.transaction

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.model.Cicilan
import id.weplus.model.TransactionDetail
import id.weplus.model.response.TransactionDetailResponse
import id.weplus.model.response.agent.transaction.AgentTransaction
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.transaksi.RefundActivity
import id.weplus.transaksi.UploadTransferFileActivity
import id.weplus.utility.TextHelper
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_agent_transaction_detail.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class AgentTransactionDetailActivity : BaseActivity() {
    private var transaction: AgentTransaction? = null
    private var transactionDetail: TransactionDetail? = null
    private var detailCicilan = ArrayList<Cicilan>()

    companion object {
        var TAG = "AgentTransactionDetail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_transaction_detail)
        setupToolbar()
        getArguments()
        setupSalinButton()
        fetchTransactionDetail()
    }

    private fun setupToolbar() {
        viewback_title.text = getString(R.string.transaksisaya)
        viewback_description.text = getString(R.string.melihatdaftartransaksiygdilakukan)
        viewback_buttonback.setOnClickListener {
            finish()
        }
    }

    private fun setupSalinButton() {
        tvSalin.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("simple text", "0353250229")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@AgentTransactionDetailActivity, "No rek telah di salin", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchTransactionDetail() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            transaction?.id?.toInt()?.let {
                val call = NetworkManager
                        .getNetworkServiceWithHeader(this, token)
                        .getAgentTransactionDetail(it)
                call.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        val gson = Gson()
                        val resp = gson.fromJson(response.body(), TransactionDetailResponse::class.java)
                        try {
                            if (resp.code == ErrorCode.ERROR_00) {
                                transactionDetail = resp.getData()
                                populateView(resp.data)
                                detailCicilan = resp.data.detailCicilan
                            } else if (resp.code == ErrorCode.ERROR_03) {
                                val intent = Intent(this@AgentTransactionDetailActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            } else {
                                showError(this@AgentTransactionDetailActivity, resp.message)
                            }
                        } catch (e: Exception) {
                            Log.i(TAG, "asu: " + e.message)
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {
                        try {
                            showError(this@AgentTransactionDetailActivity, "Time Out")
                        } catch (e: Exception) {
                            e.message
                        }
                        Log.i(TAG, "ON FAILURE : " + t.message)
                    }
                })
            }

        } else {
            showError(this@AgentTransactionDetailActivity, getString(R.string.network_error))
        }
    }

    private fun setCountdown(countdownMillis: Long) {
        countdown.start(countdownMillis)
    }

    private fun getArguments() {
        val intent = intent
        transaction = intent.getParcelableExtra("transaction")
        //isHistory = intent.getBooleanExtra("history", false)
    }

    @SuppressLint("SetTextI18n")
    private fun populateView(transaction: TransactionDetail) {
        Log.d("imageurl", "image " + transaction.productDetail.imageNew)
        Glide.with(this)
                .load(transaction.productDetail.imageNew)
                .error(R.drawable.aca_insurance)
                .into(transaksi_img)
        Log.d("imageurl", "name : " + transaction.productDetail.nama)
        insuranceName.text = transaction.productDetail.nama
        insuranceDuration.text = transaction.date_end
        insurancePrice.text = "Rp." + TextHelper.currencyFormatter(transaction.productDetail.price)
        insuranceStatus.text = transaction.status
        insuranceId.text = "" + transaction.order_code
        val paymentChannel = transaction.detailPayment.payment_channel
        transaksionclik_metode_pembayaran.text = paymentChannel
        when {
            paymentChannel.toLowerCase() == "gopay" -> {
                trfTitle.visibility = View.GONE
                trfRek.visibility = View.GONE
                tvSalin.visibility = View.GONE
                imgTrfMethod.setImageResource(R.drawable.ic_logo_gopay)
            }
            paymentChannel.toLowerCase() == "ovo" -> {
                trfTitle.visibility = View.GONE
                imgTrfMethod.setImageResource(R.drawable.ic_logo_ovo_purple)
                trfRek.visibility = View.GONE
                tvSalin.visibility = View.GONE
            }
            paymentChannel.toLowerCase().contains("cicilan") -> {
                Log.d("test", "cicilan")
                layout_petunjuk_trf.visibility = View.GONE
                layout_petunjuk_cicilan.visibility = View.VISIBLE
                tvBiayaCicilan.text = "Rp." + transaction.productDetail.price
            }
            paymentChannel.toLowerCase().contains("alfa") -> {
                trfTitle.text = "Kode Pembayaran di Alfa Group"
                imgTrfMethod.setImageResource(R.drawable.ic_alfamart)
                trfRek.text = transaction.order_code
                tvSalin.visibility = View.GONE
            }
            paymentChannel.toLowerCase().contains("indomaret") -> {
                trfTitle.setText("Kode Pembayaran di Indomaret")
                imgTrfMethod.setImageResource(R.drawable.logo_indomaret)
                trfRek.text = transaction.order_code
                tvSalin.visibility = View.GONE
            }
        }
        tv_biaya_admin.text = TextHelper.currencyFormatter("" + transaction.detailPayment.processing_fee)
        tv_biaya_asuransi.text = TextHelper.currencyFormatter("" + transaction.detailPayment.admin_fee)
        tv_biaya_product.text = TextHelper.currencyFormatter("" + transaction.detailPayment.product_nominal)
        if (transaction.detailPayment.discount > 0) {
            tv_pot_voucher.text = TextHelper.currencyFormatter("-" + transaction.detailPayment.discount)
        } else {
            tv_pot_voucher.text = TextHelper.currencyFormatter("" + transaction.detailPayment.discount)
        }
        tv_biaya_total.text = "" + TextHelper.currencyFormatter("" + transaction.detailPayment.total_payment)

        countdown.visibility = View.VISIBLE
        setCountdown(transaction.count_down_timer.toLong())
        val format = SimpleDateFormat("yy-MM-dd HH:mm:ss")
        try {
            val date = format.parse(transaction.date_end)
            val date1 = date.time
            val now = System.currentTimeMillis()
            val diff = abs(date1 - now)
            setCountdown(diff)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        setupViewBasedOnStatus(transaction.status, paymentChannel)
    }


    @SuppressLint("SetTextI18n")
    private fun setupViewBasedOnStatus(status: String, paymentChannel: String) {
        if (status.toLowerCase() == "Refund") {
            transaksionclik_status_time.visibility = View.INVISIBLE
            layout_petunjuk_trf.visibility = View.GONE
            transaksionclik_btn_selesai.visibility = View.GONE
            transaksionclik_btn_selesai.text = "Refund"
            transaksionclik_btn_selesai.setOnClickListener { _ ->
                val intent = Intent(this@AgentTransactionDetailActivity, RefundActivity::class.java)
                intent.putExtra("order_code", transaction!!.order_code)
                startActivity(intent)
            }
            if (transactionDetail!!.refundData != null) {
                val refundData = transactionDetail!!.refundData.data
                if (refundData.id != 0) {
                    layout_detail_refund.visibility = View.VISIBLE
                    transaksionclik_btn_selesai.visibility = View.GONE
                    tvRefundBank.text = refundData.bank_name
                    tvRefundBranch.text = refundData.bank_branch
                    tvRefundAccountName.text = refundData.account_name
                    tvRefundAccountNumber.text = refundData.account_no
                } else {
                    layout_detail_refund.visibility = View.GONE
                    transaksionclik_btn_selesai.visibility = View.VISIBLE
                }
            }
            detail_refund_arrow.setOnClickListener {
                if (detail_refund_label.visibility == View.VISIBLE) {
                    detail_refund_label.visibility = View.GONE
                } else {
                    detail_refund_label.visibility = View.VISIBLE
                }
            }
        } else if (status.toLowerCase() == "waiting document from user" || status.toLowerCase() == "waiting payment") {
            var s = ""
            s = if (status.toLowerCase() == "waiting document from user") {
                "Menunggu upload dokumen"
            } else {
                "Menunggu pembayaran"

            }
            insuranceStatus.text = s
            layout_detail_refund.visibility = View.GONE
            layout_petunjuk_trf.visibility = View.VISIBLE
            transaksionclik_btn_selesai.visibility = View.GONE
            if (paymentChannel.toLowerCase().contains("bca transfer")) {
                transaksionclik_btn_selesai.text = "Upload Bukti Pembayaran"
                transaksionclik_btn_selesai.background = ContextCompat.getDrawable(this, R.drawable.border_yellow_2)
                transaksionclik_btn_selesai.setOnClickListener {
                    val intent = Intent(this@AgentTransactionDetailActivity, UploadTransferFileActivity::class.java)
                    intent.putExtra("order_code", transaction!!.order_code)
                    startActivity(intent)
                }
            }
        }
    }


}