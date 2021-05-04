package id.weplus.belipolis.mudik

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.helper.DatePickerFragment
import id.weplus.helper.OnDateSetListener
import id.weplus.model.FlightData
import id.weplus.model.Product
import id.weplus.model.request.CheckFlightRequest
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.response.FlightCheckerResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.TextHelper
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.*
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.btnNext
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.errorTextTicketPrice
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.etTicketPrice
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.flightDetailWrapper
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.loader
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvAirline
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvArrivalCity
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvArrivalIata
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvArrivalTime
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvDepartureCity
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvDepartureDate
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvDepartureIata
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvDepartureTime
import kotlinx.android.synthetic.main.activity_form_aca_cancellation.tvFlightNo
import kotlinx.android.synthetic.main.activity_form_data_pesawat_aca.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FormAcaCancellation : BaseActivity(), OnDateSetListener {
    private var dataTertanggung = DataTertanggungRequest()
    private lateinit var product: Product
    private var flightData = FlightData()
    private var type:Int=0
    private var departureDate=""
    private var price=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_aca_cancellation)
        setupToolbar()
        getIntentData()
        setupDepartureDate()
        setupSelectedFlight()
        setupFlightSearchButton()
        setupFlightNumberWatcher()
        setupTicketPriceWatcher()
    }

    private fun setupTicketPriceWatcher(){
        etTicketPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                etTicketPrice.removeTextChangedListener(this)
                val temp = etTicketPrice.text.toString().replace(".", "")
                price = temp
                if (!price.isNullOrEmpty()) {
                    etTicketPrice.setText(TextHelper.currencyFormatter(price))
                    etTicketPrice.setSelection(etTicketPrice.text.toString().length)
                }
                etTicketPrice.addTextChangedListener(this)

            }

        })
    }

    private fun setupSelectedFlight() {
        flightDetailWrapper.setOnClickListener {
            if(validate()) {
                dataTertanggung.codeBooking =  etBookingCode.text.toString()
                dataTertanggung.departFlightNumber = etFlightNo.text.toString()
                dataTertanggung.destination = flightData.arrival
                dataTertanggung.arrivalDate = flightData.arrivalDate
                dataTertanggung.departure = flightData.departure
                dataTertanggung.departureDate = flightData.departureDate
                dataTertanggung.ticketPrice = price
                val intent = Intent()
                intent.putExtra("flight_data", flightData)
                intent.putExtra("data_tertanggung", dataTertanggung)
                intent.putExtra("type", type)
                setResult(16, intent)
                finish()
            }
        }
    }

    private fun validate():Boolean{
        val bookingCodeValid = etBookingCode.text.toString().isNotEmpty()
        val ticketPriceValid = etTicketPrice.text.toString().isNotEmpty()
        errorTextBookingCode.visibility=if(bookingCodeValid)View.GONE else View.VISIBLE
        errorTextTicketPrice.visibility=if(ticketPriceValid)View.GONE else View.VISIBLE
        return (bookingCodeValid && ticketPriceValid)
    }

    private fun setupFlightSearchButton(){
        btnNext.setOnClickListener{
            loader.visibility=View.VISIBLE
            checkFlight(etFlightNo.text.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text = "Periksa penerbangan"
        viewback_description.text = resources.getString(R.string.lakukanpengisiandatauntukmelanjutkan)
    }

    private fun getIntentData() {
        product = intent.getParcelableExtra("product_detail")!!
        dataTertanggung = intent.getParcelableExtra("data_tertanggung")!!
        type = intent.getIntExtra("type", -1)
        //Log.d("type","asdf $type")
        if(dataTertanggung.ticketPrice!=null){
            etTicketPrice.setText(TextHelper.currencyFormatter(dataTertanggung.ticketPrice))
        }

        if (dataTertanggung.codeBooking != null) {
            etBookingCode.setText(dataTertanggung.codeBooking)
        }
        if (dataTertanggung.departureDate != null) {
            tvDepartureDate.text = dataTertanggung.departureDate
            departureDate = dataTertanggung.departureDate
        }
        if (dataTertanggung.departFlightNumber != null) {
            etFlightNo.setText(dataTertanggung.departFlightNumber)

        }
    }

    private fun setupDepartureDate(){
        tvDepartureDate.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment.newInstance("start_date", this)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun checkFlight(flightNo: String){
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val checkFlightRequest = CheckFlightRequest()
            checkFlightRequest.flight1=flightNo
            checkFlightRequest.flight1_date=departureDate
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .checkFlight(checkFlightRequest)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), FlightCheckerResponse::class.java)
                    try {
                        loader.visibility = View.GONE
                        if (resp.code == ErrorCode.ERROR_00) {
                            flightData = resp.data.flightList[0]

                            if (flightData.airline.isNullOrEmpty()) {
                                showError(this@FormAcaCancellation, flightData.message)
                            } else {
                                flightDetailWrapper.visibility = View.VISIBLE
                                tvFlightNo.text = resp.data.flightList[0].flightNo
                                tvAirline.text = resp.data.flightList[0].airline
                                tvDepartureTime.text = resp.data.flightList[0].departureTime
                                tvArrivalTime.text = resp.data.flightList[0].arrivalTime
                                tvDepartureCity.text = resp.data.flightList[0].departure
                                tvArrivalCity.text = resp.data.flightList[0].arrival
                                tvDepartureIata.text = resp.data.flightList[0].departureAirportIata
                                tvArrivalIata.text = resp.data.flightList[0].arrivalAirportIata
                            }
                        } else if (resp.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@FormAcaCancellation, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            flightDetailWrapper.visibility = View.GONE
                            showError(this@FormAcaCancellation, resp.message)
                        }
                    } catch (e: Exception) {
                        loader.visibility = View.GONE
                        Log.i("test", "awow: " + e.message)
                        showError(this@FormAcaCancellation, e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormAcaCancellation, "Time out")
                    Log.i("test", "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    override fun onDateSet(c: Calendar?, cat: String?) {
        if (c != null) {
            departureDate = "${c.get(Calendar.YEAR)}-${c.get(Calendar.MONTH)+1}-${c.get(Calendar.DAY_OF_MONTH)}"
            @SuppressLint("SimpleDateFormat") val postFormater = SimpleDateFormat(" dd MMMM yyyy")
            val newDateStr = postFormater.format(c.time)
            tvDepartureDate.text=newDateStr
        }
    }

    private fun setupFlightNumberWatcher(){
        etFlightNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                etFlightNo.removeTextChangedListener(this)
                val temp = etFlightNo.text.toString().replace("-", "")
                val (digits, notDigits) = temp.partition { it.isDigit() }
                Log.d("temp", "temp $digits")
                if (digits.isNotBlank()) {
                    etFlightNo.setText("")
                    etFlightNo.append("${notDigits.toUpperCase()}-$digits")
                } else {
                    etFlightNo.setText("")
                    etFlightNo.append(temp.toUpperCase())
                }
                etFlightNo.addTextChangedListener(this)

            }

        })
    }

}