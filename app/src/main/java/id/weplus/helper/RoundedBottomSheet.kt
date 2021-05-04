package id.weplus.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.weplus.R
import id.weplus.model.BaseFilter
import kotlinx.android.synthetic.main.bottom_sheet_option.*
import java.util.*


interface OnOptionsSelect{
    fun onOptionSelect(baseFilter: BaseFilter, title: String)
}

@SuppressLint("ValidFragment")
class RoundedBottomSheet(private val onOptionsSelect: OnOptionsSelect): RoundedBottomSheetDialogFragment(){
    private lateinit var optionAdapter:OptionAdapter;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val dialog = dialog as BottomSheetDialog?
            val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let{
                val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.peekHeight = 0
            }
        }

        val title = arguments?.getString("title")
        val options: ArrayList<BaseFilter>? = arguments?.getParcelableArrayList<BaseFilter>("baseFilter")
        textTitle.text = title
        if(options!=null){
            optionAdapter = OptionAdapter(options)
            rvOption.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = optionAdapter
            }
        }
        buttonSelect.setOnClickListener {
            if(optionAdapter.getSelectionOption()!=null) {
                onOptionsSelect.onOptionSelect(optionAdapter.getSelectionOption()!!, textTitle.text.toString())
            }else{
                Toast.makeText(context, "Pilih salah satu", Toast.LENGTH_LONG).show()
            }
            dismiss()
        }

    }
}