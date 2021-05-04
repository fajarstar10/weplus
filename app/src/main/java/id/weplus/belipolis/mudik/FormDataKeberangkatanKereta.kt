package id.weplus.belipolis.mudik

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.helper.DatePickerFragment
import id.weplus.helper.OnDateSetListener
import id.weplus.helper.OnOptionsSelect
import id.weplus.helper.RoundedBottomSheet
import id.weplus.model.BaseFilter
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.response.mudik.MudikSimasResponse
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_kereta.*
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_kereta.btnNext
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_kereta.errorTextTripReason
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_kereta.errorTextbookingCode
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_kereta.etbookingCode
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_kereta.loader
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_kereta.tvTripReasonCode
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_pesawat.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FormDataKeberangkatanKereta : BaseActivity(), OnDateSetListener, OnOptionsSelect {
    private lateinit var product: Product
    private var dataTertanggung = DataTertanggungRequest()
    private var departureDate = ""
    private var reason = ""
    private var tripReason = ArrayList<BaseFilter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_keberangkatan_kereta)
        setupToolbar()
        getIntentData()
        setupDepartureDate()
        getTripReason()
        setupTripReasonAction()
        setupButtonNext()
    }

    private fun setupButtonNext() {
        btnNext.setOnClickListener {
            if(validate()){
                dataTertanggung.tripReason = reason;
                dataTertanggung.codeBooking = etbookingCode.text.toString()
                dataTertanggung.departureDate = departureDate
                dataTertanggung.departure=etDepartureCity.text.toString()
                dataTertanggung.destination=etDestinationCity.text.toString()

                val intent = Intent()
                intent.putExtra("data_tertanggung", dataTertanggung)
                setResult(82, intent)
                finish()
            }
        }
    }

    private fun validate():Boolean {
        val reasonValidate = reason.isNotEmpty()
        val dateValidate = departureDate.isNotEmpty()
        val bookingCodeValidate = etbookingCode.text.isNotEmpty()
        val departureCityValidate = etDepartureCity.text.isNotEmpty()
        val destinationCityValidate = etDestinationCity.text.isNotEmpty()
        errorTextTripReason.visibility = if(reasonValidate)View.GONE else View.VISIBLE
        errorTextbookingCode.visibility = if(bookingCodeValidate)View.GONE else View.VISIBLE
        errorTextDeparture.visibility = if(dateValidate)View.GONE else View.VISIBLE
        errorTextCity.visibility = if(departureCityValidate)View.GONE else View.VISIBLE
        errorDestinationTextCity.visibility = if(destinationCityValidate)View.GONE else View.VISIBLE

        return (reasonValidate && dateValidate && bookingCodeValidate && departureCityValidate && destinationCityValidate)
    }

    override fun onDateSet(c: Calendar?, cat: String?) {
        if (c != null) {
            departureDate = "${c.get(Calendar.YEAR)}-${c.get(Calendar.MONTH)+1}-${c.get(Calendar.DAY_OF_MONTH)}"
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(c.time)
            tvDepartureDate.text = newDateStr
        }
    }

    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text = "Data Keberangkatan"
        viewback_description.text = resources.getString(R.string.lakukanpengisiandatauntukmelanjutkan)
    }

    private fun getIntentData() {
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!

        if(dataTertanggung.tripReason!=null){
            tvTripReasonCode.text=dataTertanggung.tripReason
        }
        if(dataTertanggung.codeBooking!=null){
            etbookingCode.setText(dataTertanggung.codeBooking)
        }
        if(dataTertanggung.destination!=null){
            etDestinationCity.setText(dataTertanggung.destination)
        }
        if(dataTertanggung.departure!=null){
            etDepartureCity.setText(dataTertanggung.departure)
        }

        if (dataTertanggung.departureDate != null) {
            departureDate = dataTertanggung.departureDate
            val date = departureDate.split("-")
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, date[0].toInt())
            cal.set(Calendar.MONTH, date[1].toInt())
            cal.set(Calendar.DAY_OF_MONTH, date[2].toInt())
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(cal.time)
            tvDepartureDate.text = newDateStr
        }
    }

    private fun setupDepartureDate() {
        tvDepartureDate.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment.newInstance("start_date", this)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun getTripReason() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .simasMudikFilter
            call.enqueue(object : Callback<MudikSimasResponse?> {
                override fun onResponse(call: Call<MudikSimasResponse?>, response: Response<MudikSimasResponse?>) {
                    try {
                        loader.visibility = View.GONE
                        response.body()?.data?.trip_reason?.let {
                            tripReason = it
                        }
                    } catch (e: Exception) {
                        loader.visibility = View.GONE
                        showError(this@FormDataKeberangkatanKereta, e.message)
                    }
                }

                override fun onFailure(call: Call<MudikSimasResponse?>, t: Throwable) {
                    showError(this@FormDataKeberangkatanKereta, "Time out")
                    Log.i("test", "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun showRoundedBottomSheet(title: String, baseFilter: ArrayList<BaseFilter>) {
        for((i, filter) in baseFilter.withIndex()){
            filter.id=i
        }
        val roundedBottomSheet = RoundedBottomSheet(this)
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putParcelableArrayList("baseFilter", baseFilter)
        roundedBottomSheet.arguments = bundle
        roundedBottomSheet.show(this@FormDataKeberangkatanKereta.supportFragmentManager, "test")
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        tvTripReasonCode.text = baseFilter.filterText
        reason = baseFilter.name
    }

    private fun setupTripReasonAction() {
        tvTripReasonCode.setOnClickListener {
            showRoundedBottomSheet("Pilih Alasan Perjalanan", tripReason)
        }
    }

}