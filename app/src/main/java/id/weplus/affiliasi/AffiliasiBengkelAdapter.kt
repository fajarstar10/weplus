package id.weplus.affiliasi

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
import id.weplus.R
import id.weplus.model.response.afiliasibengkel.AffiliasiBengkelModel

class AffiliasiBengkelAdapter (private val activity: Activity,
private val baseFilters: ArrayList<AffiliasiBengkelModel>) : RecyclerView.Adapter<AffiliasiBengkelAdapter.BengkelHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BengkelHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_bengkel_affiliasi, parent, false)
        return BengkelHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return baseFilters.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BengkelHolder, p1: Int) {
        val bengkel = baseFilters[p1]
        holder.wrapper?.setOnClickListener {
            val intent = Intent(activity, AffiliasiBengkelDetail::class.java)
            intent.putExtra("data", bengkel)
            activity.startActivity(intent)
        }
        holder.tvBengkelName?.text=bengkel.name
        holder.tvBengkelArea?.text=bengkel.area
    }

    class BengkelHolder(v: View) : RecyclerView.ViewHolder(v) {
        var wrapper: RelativeLayout? = null
        var bengkelIcon: ImageView? = null
        var tvBengkelName: TextView? = null
        var tvBengkelArea: TextView? = null

        init {
            wrapper = v.findViewById(R.id.bengkelWrapper)
            bengkelIcon = v.findViewById(R.id.bengkelIcon)
            tvBengkelArea = v.findViewById(R.id.tvBengkelArea)
            tvBengkelName = v.findViewById(R.id.tvBengkelName)
        }
    }
}