package id.weplus.polissaya.contactinsurancepartner

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import id.weplus.R
import id.weplus.model.ContactPartnerDetail


class ContactInsurancePartnerAdapter(
        private val activity: Activity,
        private val partnerName: String,
        private val baseFilters: ArrayList<ContactPartnerDetail>) : RecyclerView.Adapter<ContactInsurancePartnerAdapter.ContactPartner>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ContactPartner {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_contact_partner, parent, false)
        return ContactPartner(inflatedView)
    }

    override fun getItemCount(): Int {
        return baseFilters.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactPartner, p1: Int) {
        val contactPartner = baseFilters[p1]
        holder.tvName?.text=partnerName
        holder.tvTitle?.text=contactPartner.type
        holder.tvLocation?.text=contactPartner.address
        holder.tvService?.text=contactPartner.detail
        holder.tvContactNumber?.text=contactPartner.phone
        holder.tvContactCall?.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.fromParts(
                    "tel", contactPartner.phone, null))
            activity.startActivity(phoneIntent)
        }
    }

    class ContactPartner(v: View) : RecyclerView.ViewHolder(v) {
        var tvTitle: TextView? = null
        var tvName: TextView? = null
        var tvLocation: TextView? = null
        var tvService: TextView? = null
        var tvContactNumber: TextView? = null
        var tvContactCall: TextView? = null

        init {
            tvTitle = v.findViewById(R.id.tvContactType)
            tvName = v.findViewById(R.id.tvContactName)
            tvLocation = v.findViewById(R.id.tvContactAddress)
            tvService = v.findViewById(R.id.tvContactService)
            tvContactNumber = v.findViewById(R.id.tvContactNumber)
            tvContactCall = v.findViewById(R.id.tvPartnerCall)
        }
    }
}