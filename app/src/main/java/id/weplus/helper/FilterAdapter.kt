package id.weplus.helper


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import id.weplus.R

interface OnFilterClicked{
    fun onFilterClick(text:String)
}
class FilterAdapter(
        private val filters:ArrayList<String>,
        private val onFilterClicked: OnFilterClicked)
    : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>(),Filterable{

    private var defaultList:ArrayList<String> = ArrayList<String>()
    init{
        defaultList=filters
    }
    class FilterViewHolder(v: View)  : RecyclerView.ViewHolder(v){
        var wrapper: ConstraintLayout? = null
        var textFilter: TextView? = null

        init{
            wrapper = v.findViewById(R.id.wrapper)
            textFilter = v.findViewById(R.id.tvFilterText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FilterViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewadapter_filter,parent, false)
        return FilterViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return defaultList.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, i: Int) {
        Log.d("test","testing ${defaultList[i]}")
        holder.textFilter?.text = defaultList[i]
        holder.wrapper?.setOnClickListener{
            onFilterClicked.onFilterClick(defaultList[i])
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                defaultList = filterResults.values as ArrayList<String>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    filters
                else
                    filters.filter {
                        it.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }

}