package id.weplus.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import id.weplus.R
import id.weplus.model.response.insureduser.InsuredUser


class InsuredUserAdapter(
        private var InsuredUsers:ArrayList<InsuredUser>) : RecyclerView.Adapter<InsuredUserAdapter.InsuredUserHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): InsuredUserHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewadapter_insured_user,parent, false)
        return InsuredUserHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return InsuredUsers.size
    }

    public fun setInsuredUser(insuredUser:ArrayList<InsuredUser>){
        this.InsuredUsers = insuredUser
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: InsuredUserHolder, p1: Int) {
        val insuredUser = InsuredUsers[p1]
        holder.wrapper?.setOnClickListener{

        }

        holder.textOption?.text= insuredUser.fullname
    }

    class InsuredUserHolder(v: View) : RecyclerView.ViewHolder(v){
        var wrapper: ConstraintLayout? = null
        var textOption: TextView? = null

        init{
            wrapper = v.findViewById(R.id.wrapper)
            textOption = v.findViewById(R.id.tvInsuredUserName)
        }
    }
}