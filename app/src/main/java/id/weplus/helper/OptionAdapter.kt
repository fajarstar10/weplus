package id.weplus.helper

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import id.weplus.R
import id.weplus.model.BaseFilter

class OptionAdapter(
        private val baseFilters:ArrayList<BaseFilter>) : RecyclerView.Adapter<OptionAdapter.OptionHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): OptionHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewadapter_option,parent, false)
        return OptionHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return baseFilters.size
    }
    
    public fun getSelectionOption():BaseFilter?{
        val idx = baseFilters.indexOfFirst { it.isSelected }
        return if(idx!=-1)
            baseFilters[idx]
        else null
    }

    override fun onBindViewHolder(holder: OptionHolder, p1: Int) {
        val baseFilter = baseFilters[p1]
        holder.wrapper?.setOnClickListener{
            for(filter in baseFilters){
                filter.isSelected = filter.id==baseFilter.id
            }
            notifyDataSetChanged()
        }

        holder.textOption?.text="${baseFilter.filterText}"
        if(baseFilter.isSelected){
            holder.imgOptions?.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
        }else{
            holder.imgOptions?.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
        }
    }

    class OptionHolder(v: View) : RecyclerView.ViewHolder(v){
         var wrapper: ConstraintLayout? = null
         var imgOptions: ImageView? = null
         var textOption: TextView? = null

        init{
            wrapper = v.findViewById(R.id.wrapper)
            imgOptions = v.findViewById(R.id.imgOption)
            textOption = v.findViewById(R.id.textOption)
        }
    }
}