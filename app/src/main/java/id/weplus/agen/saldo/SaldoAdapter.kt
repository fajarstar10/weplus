package id.weplus.agen.saldo

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import id.weplus.R
import id.weplus.agen.dashboard.DashboardItemClickListener
import id.weplus.model.response.agent.AgentMenu
import id.weplus.model.response.agent.saldo.SaldoHistory
import id.weplus.utility.TextHelper.currencyFormatter
import id.weplus.utility.Util


public class SaldoAdapter(
        private var activity: Activity,
        private var menus:ArrayList<SaldoHistory>,
        private var dashboardItemClick: DashboardItemClickListener
) : RecyclerView.Adapter<SaldoAdapter.AgentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val rootView: View = LayoutInflater.from(activity).inflate(R.layout.viewadapter_agen_saldo_history, parent, false)
        return AgentViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, pos: Int) {
        val menu = menus[pos]
        if(menu.type=="1" || menu.type=="4") {
            Glide.with(activity).load(R.drawable.uang_hijau).into(holder.imageView)
        }else{
            Glide.with(activity).load(R.drawable.uang_merah).into(holder.imageView)
        }
        holder.tvDateRequest.text= Util.formatDate(menu.date)
        holder.tvWithdrawId.text=menu.requestWithdrawId
        holder.tvHistoryType.text=menu.title
        holder.tvWithdrawalAmout.text="Rp ${currencyFormatter(menu.total)}"
        
        if(menu.type!="3") {
            holder.tvWithdrawalMethod.text="Bank Transfer"
            holder.tvWithdrawalStatus.text = menu.status
            holder.tvWithdrawalDest.text=menu.desc

            if (menu.status.toLowerCase() == "berhasil") {
                holder.tvWithdrawalStatus.setBackgroundResource(R.drawable.border_fill_green)
            } else {
                holder.tvWithdrawalStatus.setBackgroundResource(R.drawable.border_fill_yellow_rounded)
            }
            if(menu.type=="1"){
                holder.tvWithdrawLabel.visibility=View.INVISIBLE
                holder.tvWithdrawId.visibility=View.INVISIBLE
            }
        }else{
            
            Log.d("test","testing suu ${menu.status}")
            holder.tvWithdrawalMethod.text=""
            holder.tvWithdrawalDest.text=""
            holder.tvWithdrawalDest.text=menu.desc
            holder.tvWithdrawalStatus.visibility=View.INVISIBLE
        }

        holder.wrapper.setOnClickListener {
            dashboardItemClick.onDashboardItemClick(pos)
        }

    }

    override fun getItemCount(): Int {
        return menus.size
    }

    class AgentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        @BindView(R.id.imgWithdrawal)
        lateinit var imageView: ImageView
        @BindView(R.id.tvHistoryType)
        lateinit var tvHistoryType: TextView
        @BindView(R.id.tvWithdrawalMethod)
        lateinit var tvWithdrawalMethod: TextView
        @BindView(R.id.tvWithdrawalDest)
        lateinit var tvWithdrawalDest: TextView
        @BindView(R.id.tvWithdrawalAmout)
        lateinit var tvWithdrawalAmout: TextView
        @BindView(R.id.tvWithdrawalStatue)
        lateinit var tvWithdrawalStatus: TextView
        @BindView(R.id.tvDateRequest)
        lateinit var tvDateRequest: TextView
        @BindView(R.id.tvWithdrawId)
        lateinit var tvWithdrawId: TextView
        @BindView(R.id.withdrawIdLabel)
        lateinit var tvWithdrawLabel: TextView
        @BindView(R.id.wrapper)
        lateinit var wrapper: RelativeLayout

        init {
            ButterKnife.bind(this, itemView!!)
            Log.d("init","cuy init")
        }

        fun bind(feature:String){
            Log.d("testing bind","error")
            //tvFeature?.text=feature
        }
    }
}