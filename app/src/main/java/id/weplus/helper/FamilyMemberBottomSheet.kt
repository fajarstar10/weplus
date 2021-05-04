package id.weplus.helper

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import id.weplus.R
import id.weplus.model.BaseFilter
import kotlinx.android.synthetic.main.bottom_sheet_family.*
import kotlinx.android.synthetic.main.bottom_sheet_option.buttonSelect
import kotlinx.android.synthetic.main.bottom_sheet_option.textTitle
import java.util.ArrayList

interface OnFamilyMemberSaved{
    fun onFamilyMemberSaved(adultCount:Int,childCount:Int)
}

@SuppressLint("ValidFragment")
class FamilyMemberBottomSheet(private val onFamilyMemberSaved: OnFamilyMemberSaved): RoundedBottomSheetDialogFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_family, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        var adultCount = arguments?.getInt("adult_count")
        var childCount = arguments?.getInt("child_count")
        adult_counter.number = adultCount.toString()
        child_counter.number = childCount.toString()
        adult_counter.setRange(1,2)
        child_counter.setRange(0,3)

        buttonSelect.setOnClickListener {
            var adultCount = adult_counter.number.toInt();
            if(adultCount>0) {
                onFamilyMemberSaved.onFamilyMemberSaved(adult_counter.number.toInt(), child_counter.number.toInt())
                dismiss()
            }else{
                Toast.makeText(context,"Minimal terdapat 1 anggota dewasa",Toast.LENGTH_LONG).show()
            }
        }
    }
}