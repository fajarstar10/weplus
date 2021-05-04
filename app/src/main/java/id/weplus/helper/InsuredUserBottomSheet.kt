package id.weplus.helper

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.weplus.R
import id.weplus.model.BaseFilter
import id.weplus.model.response.insureduser.InsuredUser
import java.util.ArrayList


interface OnUserSelect{
    fun selectUser(user: InsuredUser)
}

@SuppressLint("ValidFragment")
class InsuredUserBottomSheet(private val onOptionsSelect: OnUserSelect): RoundedBottomSheetDialogFragment(){
    private lateinit var optionAdapter:OptionAdapter;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_insured_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options: ArrayList<InsuredUser>? = arguments?.getParcelableArrayList<InsuredUser>("insured_user")
        Log.d("options","insured user size : ${options?.size}")
        //TODO link with adapter
    }
}