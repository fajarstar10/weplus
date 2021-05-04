package id.weplus.helper

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.weplus.R
import kotlinx.android.synthetic.main.ovo_bottom_sheet.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


interface OnSubmitNo{
    fun onSubmitNo(phone: String)
}

@SuppressLint("ValidFragment")
class OvoBottomSheet(private val onSubmitNo: OnSubmitNo): BottomSheetDialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.ovo_bottom_sheet, container)

        //dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED)
        dialog?.setOnShowListener {
            Handler().post {
                val bottomSheet = (dialog as? BottomSheetDialog)?.findViewById<View>(R.id.design_bottom_sheet) as? FrameLayout
                bottomSheet?.let {
                    BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        buttonCancel.setOnClickListener {
            dismiss()
        }
        buttonSelect.setOnClickListener {
            if(!etPhoneNumber.text.isNullOrEmpty()) {
               onSubmitNo.onSubmitNo("0${etPhoneNumber.text}")
            }else{
                Toast.makeText(context, "Pilih salah satu", Toast.LENGTH_LONG).show()
            }
            dismiss()
        }
        if(activity!=null) {
            setEventListener(
                    activity!!,
                    object : KeyboardVisibilityEventListener {
                        override fun onVisibilityChanged(isOpen: Boolean) {
                            Log.d("keyboard", "keyboard show $isOpen")
                            if(dummyView!=null) {
                                dummyView.visibility = if (isOpen) {
                                    View.VISIBLE
                                } else {
                                    View.GONE
                                }
                            }
                            //setDialogHeight(isOpen)
                        }
                    })
        }



    }

    /**
     * Changes size of dialog based on keyboard visibility state
     */
    private fun setDialogHeight(expanded: Boolean) {
        val dialog = dialog as BottomSheetDialog?
        val bottomSheet =
                dialog!!.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        val behavior = BottomSheetBehavior.from(bottomSheet!!)

        val displayMetrics = activity!!.resources.displayMetrics

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        val maxHeight = (height * 0.88).toInt()

        behavior.peekHeight = if (expanded) maxHeight else -1
    }
}