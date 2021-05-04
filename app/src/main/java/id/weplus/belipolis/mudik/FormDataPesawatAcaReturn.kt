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
import kotlinx.android.synthetic.main.activity_form_data_pesawat_aca.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FormDataPesawatAcaReturn : BaseActivity(), OnDateSetListener {
    private var dataTertanggung = DataTertanggungRequest()
    private lateinit var product: Product
    private var flightData = FlightData()
    private var type:Int=0
    private var price="";
    private var departureDate=""
    private var isCancellation=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_pesawat_aca_return)
        setupToolbar()
        getIntentData()
        setupDepartureDate()
        setupFlightSearchButton()
        onSelectFlight()
        setupTicketPriceWatcher()
        setupFlightNumberWatcher()
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

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text = "Cek Penerbangan"
        viewback_description.text = resources.getString(R.string.lakukanpengisiandatauntukmelanjutkan)
    }

    private fun getIntentData() {
        if(intent.getParcelableExtra<Product>("product_detail")!=null){
            product = intent.getParcelableExtra("product_detail")!!
        }
        if(intent.getParcelableExtra<DataTertanggungRequest>("data_tertanggung")!=null) {
            dataTertanggung = intent.getParcelableExtra<DataTertanggungRequest>("data_tertanggung")!!
        }
        type = intent.getIntExtra("type", -1)

        if(product.name.toLowerCase().contains("cancellation")){
            isCancellation=true
        }
        if(isCancellation){
            ticketPriceWrapper.visibility= View.VISIBLE
            if(dataTertanggung.returnTicketPrice!=null){
                etTicketPrice.setText(dataTertanggung.returnTicketPrice)
            }
        }

        if (dataTertanggung.returnCodeBooking != null) {
            etbookingCode.setText(dataTertanggung.returnCodeBooking)
        }
        if (dataTertanggung.returnDepartureDate != null) {
            tvDepartureDate.text = dataTertanggung.returnDepartureDate
            departureDate = dataTertanggung.returnDepartureDate
        }
        if (dataTertanggung.returnFlightNumber != null) {
            tvFlightNumber.setText(dataTertanggung.returnFlightNumber)
        }

    }

    private fun setupDepartureDate(){
        tvDepartureDate.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment.newInstance("start_date", this)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }


    private fun setupFlightSearchButton(){
        btnNext.setOnClickListener{
            loader.visibility= View.VISIBLE
            checkFlight(tvFlightNumber.text.toString())
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
                                showError(this@FormDataPesawatAcaReturn, flightData.message)
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
                            val intent = Intent(this@FormDataPesawatAcaReturn, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            flightDetailWrapper.visibility = View.GONE
                            showError(this@FormDataPesawatAcaReturn, resp.message)
                        }
                    } catch (e: Exception) {
                        loader.visibility = View.GONE
                        Log.i("test", "awow: " + e.message)
                        showError(this@FormDataPesawatAcaReturn, e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataPesawatAcaReturn, "Time out")
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

    private fun onSelectFlight(){
        flightDetailWrapper.setOnClickListener {
            dataTertanggung.returnCodeBooking = etbookingCode.text.toString()
            dataTertanggung.returnFlightNumber = tvFlightNumber.text.toString()
            dataTertanggung.returnDestination = flightData.arrival
            dataTertanggung.returnDeparture = flightData.departure
            dataTertanggung.returnDepartureDate = departureDate
            dataTertanggung.returnArrivalDate=flightData.arrivalDate
            if(isCancellation){
                dataTertanggung.returnTicketPrice = price
            }

            val intent = Intent();
            intent.putExtra("data_tertanggung", dataTertanggung)
            setResult(14, intent)
            finish()
        }
    }

    private fun setupFlightNumberWatcher(){
        tvFlightNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                tvFlightNumber.removeTextChangedListener(this)
                val temp = tvFlightNumber.text.toString().replace("-", "")
                val (digits, notDigits) = temp.partition { it.isDigit() }
                Log.d("temp", "temp $digits")
                if (digits.isNotBlank()) {
                    tvFlightNumber.setText("")
                    tvFlightNumber.append("${notDigits.toUpperCase()}-$digits")
                } else {
                    tvFlightNumber.setText("")
                    tvFlightNumber.append(temp.toUpperCase())
                }
                tvFlightNumber.addTextChangedListener(this)

            }

        })
    }
}