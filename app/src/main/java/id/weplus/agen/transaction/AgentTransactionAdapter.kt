package id.weplus.agen.transaction

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.weplus.R
import id.weplus.model.Transaction
import id.weplus.model.response.agent.transaction.AgentTransaction
import id.weplus.utility.TextHelper.currencyFormatter
import java.util.*

class AgentTransactionAdapter(private val activity: Activity, private val listener: TransaksiOnItemClicked) : RecyclerView.Adapter<AgentTransactionAdapter.TransaksiViewHolder>() {
    private val transactions: ArrayList<AgentTransaction> = ArrayList()
    fun addItems(data: ArrayList<AgentTransaction>) {
        transactions.addAll(data)
        notifyDataSetChanged()
    }

    interface TransaksiOnItemClicked {
        fun onItem(agentTransaction: AgentTransaction)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TransaksiViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.viewadapter_agent_transaction, viewGroup, false)
        return TransaksiViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransaksiViewHolder, i: Int) {
        val transaction = transactions[i]
        holder.tvTransactionId?.text = transaction.order_code
        holder.tvTransactionExpDate?.text= transaction.date_end
        holder.tvTransactionProductName?.text = transaction.product_name
        holder.tvTransactionCustName?.text = transaction.insured_name
        holder.tvTransactionProductPrice?.text="Rp "+currencyFormatter(transaction.total)
        holder.tvTransactionStatus?.text=getStatus(transaction.status)
        holder.imgProduct?.let { Glide.with(activity).load(transaction.img_url).into(it) }
        holder.tvComission?.text="+ Komisi ${currencyFormatter(transaction.commission)}"
        holder.imgDetail?.setOnClickListener {
            listener.onItem(transaction)
        }
    }

    private fun getStatus( status:String):String{
        var s = status
        when (status) {
            "done" -> {
                s= "Berhasil"
            }
            "cancel transaction" -> {
                s="Transaksi dibatalkan"
            }
            "waiting document from user" -> {
                s = "Menunggu document"
            }
            "waiting payment" -> {
                s= "Menunggu pembayaran"
            }
        }
        return s
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class TransaksiViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            var wrapper: RelativeLayout? = null
            var tvTransactionId: TextView? = null
            var tvTransactionExpDate: TextView? = null
            var tvTransactionCustName: TextView? = null
            var tvTransactionProductPrice: TextView? = null
            var tvTransactionProductName: TextView? = null
            var tvTransactionStatus: TextView? = null
            var tvComission:TextView?=null
            var imgProduct:ImageView?=null
            var imgDetail:ImageView?=null

            init {
                wrapper = v.findViewById(R.id.bengkelWrapper)
                tvTransactionId = v.findViewById(R.id.tvTransactionId)
                tvTransactionExpDate = v.findViewById(R.id.tvTransactionExpDate)
                tvTransactionProductPrice = v.findViewById(R.id.tvTransactionProductPrice)
                tvTransactionProductName = v.findViewById(R.id.tvTransactionProductName)
                tvTransactionStatus = v.findViewById(R.id.tvTransactionStatus)
                tvComission = v.findViewById(R.id.tvComission)
                tvTransactionCustName = v.findViewById(R.id.tvTransactionCustName)
                imgProduct = v.findViewById(R.id.imgProduct)
                imgDetail = v.findViewById(R.id.transaksisaya_detail)
            }

    }
}