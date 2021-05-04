package id.weplus.agen.dashboard

import android.app.Activity
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
import id.weplus.model.response.agent.AgentMenu

class DashboardAgentAdapter(
        private var activity: Activity,
        private var menus:ArrayList<AgentMenu>,
        private var dashboardItemClick: DashboardItemClickListener
) : RecyclerView.Adapter<DashboardAgentAdapter.AgentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val rootView: View = LayoutInflater.from(activity).inflate(R.layout.viewadapter_agen_list, parent, false)
        return AgentViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, pos: Int) {
        val menu = menus[pos]
        Log.d("image","image url ${menu.img}")
        Glide.with(activity).load(menu.img).into(holder.imageView)

        holder.tvFeatureDesc.text=menu.desc
        holder.tvFeature.text=menu.name

        holder.wrapper.setOnClickListener {
            dashboardItemClick.onDashboardItemClick(pos)
        }
    }

    public fun getItemAt(pos:Int):AgentMenu{
        return menus[pos]
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    class AgentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        @BindView(R.id.imgIcon)
        lateinit var imageView: ImageView
        @BindView(R.id.tvFeature)
        lateinit var tvFeature: TextView
        @BindView(R.id.tvFeatureDesc)
        lateinit var tvFeatureDesc: TextView
        @BindView(R.id.wrapper)
        lateinit var wrapper:RelativeLayout

        init {
            ButterKnife.bind(this, itemView!!)
            Log.d("init","cuy init")
        }

        fun bind(feature:String){
            Log.d("testing bind","error")
            tvFeature.text=feature
        }
    }
}