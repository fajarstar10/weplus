package id.weplus.clinic

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.weplus.R
import id.weplus.WebViewActivity
import id.weplus.model.Clinic

class ClinicAdapter(
        private val activity: Activity,
        private val baseFilters: ArrayList<Clinic>) : RecyclerView.Adapter<ClinicAdapter.ClinicHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ClinicHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_clinic, parent, false)
        return ClinicHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return baseFilters.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClinicHolder, p1: Int) {
        val clinic = baseFilters[p1]
        holder.imgIcon?.let {
            Glide.with(activity)
                .load(clinic.image)
                .centerCrop()
                .into(it)
        }
        holder.tvTitle?.text=clinic.name
        holder.tvDetails?.text="Melakukan pengecekan kesehatan melalui website Prixa"
        holder.wrapper?.setOnClickListener {
            val intent = Intent(activity,WebViewActivity::class.java)
            intent.putExtra("url",clinic.url)
            activity.startActivity(intent)
        }
        if(clinic.url.isNullOrBlank()){
            holder.tvComingSoon?.visibility=View.VISIBLE
            holder.imgRight?.visibility=View.GONE
            holder.tvTitle?.setTextColor(ContextCompat.getColor(activity,R.color.grey_medium))
        }else{
            holder.tvComingSoon?.visibility=View.GONE
            holder.imgRight?.visibility=View.VISIBLE
            holder.tvTitle?.setTextColor(ContextCompat.getColor(activity,R.color.black_7f7f7f))
        }


    }

    class ClinicHolder(v: View) : RecyclerView.ViewHolder(v) {
        var wrapper: RelativeLayout? = null
        var tvTitle: TextView? = null
        var tvDetails: TextView? = null
        var imgIcon: ImageView? = null
        var imgRight: ImageView? = null
        var tvComingSoon: TextView? = null

        init {
            wrapper = v.findViewById(R.id.clinicWrapper)
            tvTitle = v.findViewById(R.id.clinicTitle)
            tvDetails = v.findViewById(R.id.clinicDesc)
            imgIcon = v.findViewById(R.id.imgClinic)
            imgRight = v.findViewById(R.id.imgChevronRight)
            tvComingSoon = v.findViewById(R.id.tvComingSoon)
        }
    }
}