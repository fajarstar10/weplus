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
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_flight_check.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FlightCheckActivity : BaseActivity(), OnDateSetListener {
    private var dataTertanggung = DataTertanggungRequest()
    private lateinit var product: Product
    private var flightData = FlightData()
    private var type:Int=0
    private var departureDate=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_check)
        setupToolbar()
        getIntentData()
        setupDepartureDate()
        setupSelectedFlight()
        setupFlightSearchButton()
        setupFlightNumberWatcher()
    }

    private fun setupSelectedFlight() {
        flightDetailWrapper.setOnClickListener {
            dataTertanggung.codeBooking = if(etBookingCode!=null)etBookingCode.text.toString() else ""
            dataTertanggung.departFlightNumber = etFlightNo.text.toString()
            dataTertanggung.destination = flightData.arrival
            dataTertanggung.arrivalDate = flightData.arrivalDate
            dataTertanggung.departure = flightData.departure
            dataTertanggung.departureDate = flightData.departureDate
            //if(isCancellation){
                Log.d("isCancel", "set ticket price")
                //dataTertanggung.ticketPrice = price
            //}
            val intent = Intent()
            intent.putExtra("flight_data", flightData)
            intent.putExtra("data_tertanggung", dataTertanggung)
            intent.putExtra("type", type)
            setResult(15, intent)
            finish()
        }
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
                                showError(this@FlightCheckActivity, flightData.message)
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
                            val intent = Intent(this@FlightCheckActivity, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            flightDetailWrapper.visibility = View.GONE
                            showError(this@FlightCheckActivity, resp.message)
                        }
                    } catch (e: Exception) {
                        loader.visibility = View.GONE
                        Log.i("test", "awow: " + e.message)
                        showError(this@FlightCheckActivity, e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FlightCheckActivity, "Time out")
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