package id.weplus.belipolis.mudik

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.helper.OnOptionsSelect
import id.weplus.helper.RoundedBottomSheet
import id.weplus.model.BaseFilter
import id.weplus.model.FlightData
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.response.mudik.MudikSimasResponse
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_kereta.*
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_pesawat.*
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_pesawat.btnNext
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_pesawat.etbookingCode
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_pesawat.loader
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan_pesawat.tvTripReasonCode
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FormDataPesawatSimas : BaseActivity(), OnOptionsSelect {

    private lateinit var product: Product
    private var dataTertanggung = DataTertanggungRequest()
    private val departureDataReqCode = 21
    private lateinit var flightData: FlightData
    private var tripReason = ArrayList<BaseFilter>()
    private var flightList = ArrayList<FlightData>()
    private var reason = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_keberangkatan_pesawat)
        setupToolbar()
        getIntentData()
        getTripReason()
        setupCheckFlightButton()
        setFlightTypeOption()
        setupTripReasonAction()
        setupButtonNext()

    }

    private fun setupButtonNext() {
        val gson = Gson()
        btnNext.setOnClickListener {
            if (validate()) {
                dataTertanggung.tripReason = reason
                dataTertanggung.departureDate = flightList[0].departureDate
                dataTertanggung.detailFlight = gson.toJson(flightList)
                dataTertanggung.codeBooking=etbookingCode.text.toString()
                dataTertanggung.departure=flightList[0].departure
                dataTertanggung.destination=flightList[0].arrival
                val intent = Intent()
                intent.putExtra("data_tertanggung",dataTertanggung)
                setResult(82,intent)
                finish()
            }
        }
    }

    private fun validate(): Boolean {
        val validateFlightList = flightList.isNotEmpty()
        val validateBookingCode = etbookingCode.text.isNotEmpty()
        return validateFlightList && validateBookingCode
    }

    private fun setFlightTypeOption() {
        tvTransit.setOnClickListener {
            showRoundedBottomSheet()
        }
    }

    private fun setupCheckFlightButton() {
        directFlightWrapper.setOnClickListener {
            checkFlight(0)
        }

        firstTransitWrapper.setOnClickListener {
            checkFlight(1)
        }

        secondTransitWrapper.setOnClickListener {
            checkFlight(2)
        }

        thirdTransitWrapper.setOnClickListener {
            checkFlight(3)
        }
    }

    private fun checkFlight(type: Int) {
        val intent = Intent(this, FlightCheckActivity::class.java)
        intent.putExtra("product_detail", product)
        intent.putExtra("data_tertanggung", dataTertanggung)
        intent.putExtra("type", type)
        startActivityForResult(intent, departureDataReqCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == departureDataReqCode) {
            flightData = data?.getParcelableExtra("flight_data")!!
            val type = data.getIntExtra("type", -1)
            populateView(flightData, type)
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
    }

    private fun showRoundedBottomSheet() {
        val typeFlightFilter = ArrayList<BaseFilter>()
        val typeFlight = arrayOf("Penerbangan Langsung", "1x Transit", "2x Transit", "3x Transit")
        for (x in 0..3) {
            typeFlightFilter.add(BaseFilter(x, typeFlight[x], typeFlight[x]))
        }
        val roundedBottomSheet = RoundedBottomSheet(this)
        val bundle = Bundle()
        bundle.putString("title", "Pilih Tipe Penerbangan")
        bundle.putParcelableArrayList("baseFilter", typeFlightFilter)
        roundedBottomSheet.arguments = bundle
        roundedBottomSheet.show(this@FormDataPesawatSimas.supportFragmentManager, "test")
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        if (title == "Pilih Alasan Perjalanan") {
            tvTripReasonCode.text = baseFilter.filterText
            reason = baseFilter.name
        } else {
            var directVisibility = View.GONE
            var firstVisibility = View.GONE
            var secondVisibility = View.GONE
            var thirdVisibility = View.GONE
            tvTransit.text = baseFilter.filterText
            Log.d("basefilter_id ", "id : " + baseFilter.id)
            when (baseFilter.id) {
                0 -> {
                    directVisibility = View.VISIBLE
                }
                1 -> {
                    directVisibility = View.VISIBLE
                    firstVisibility = View.VISIBLE
                }
                2 -> {
                    directVisibility = View.VISIBLE
                    firstVisibility = View.VISIBLE
                    secondVisibility = View.VISIBLE
                }
                3 -> {
                    directVisibility = View.VISIBLE
                    firstVisibility = View.VISIBLE
                    secondVisibility = View.VISIBLE
                    thirdVisibility = View.VISIBLE
                }

            }
            directWrapper.visibility = directVisibility
            firstTransitWrapper.visibility = firstVisibility
            secondTransitWrapper.visibility = secondVisibility
            thirdTransitWrapper.visibility = thirdVisibility
        }
    }

    private fun populateView(flightData: FlightData, id: Int) {
        flightList.add(id, flightData)
        when (id) {
            0 -> {
                populateDirectFlight(flightData)
            }
            1 -> {
                populateFirstTransitFlight(flightData)
            }
            2 -> {
                populateSecondTransit(flightData)
            }
            3 -> {
                populateThirdTransit(flightData)
            }
        }
    }

    private fun populateDirectFlight(flightData: FlightData) {
        directFlightWrapper.visibility = View.GONE
        directFlightDetailWrapper.visibility = View.VISIBLE
        tvFlightNo.text = flightData.flightNo
        tvAirline.text = flightData.airline
        tvDepartureTime.text = flightData.departureTime
        tvArrivalTime.text = flightData.arrivalTime
        tvDepartureCity.text = flightData.departure
        tvArrivalCity.text = flightData.arrival
        tvDepartureIata.text = flightData.departureAirportIata
        tvArrivalIata.text = flightData.arrivalAirportIata
    }

    private fun populateFirstTransitFlight(flightData: FlightData) {
        transitOneFlightWrapper.visibility = View.GONE
        transitOneFlightDetailWrapper.visibility = View.VISIBLE
        tvFlightNoTransitOne.text = flightData.flightNo
        tvAirlineTransitOne.text = flightData.airline
        tvDepartureTimeTransitOne.text = flightData.departureTime
        tvArrivalTimeTransitOne.text = flightData.arrivalTime
        tvDepartureCityTransitOne.text = flightData.departure
        tvArrivalCityTransitOne.text = flightData.arrival
        tvDepartureIataTransitOne.text = flightData.departureAirportIata
        tvArrivalIataTransitOne.text = flightData.arrivalAirportIata
    }

    private fun populateSecondTransit(flightData: FlightData) {
        transitTwoFlightWrapper.visibility = View.GONE
        transitTwoFlightDetailWrapper.visibility = View.VISIBLE
        tvFlightNoTransitTwo.text = flightData.flightNo
        tvAirlineTransitTwo.text = flightData.airline
        tvDepartureTimeTransitTwo.text = flightData.departureTime
        tvArrivalTimeTransitTwo.text = flightData.arrivalTime
        tvDepartureCityTransitTwo.text = flightData.departure
        tvArrivalCityTransitTwo.text = flightData.arrival
        tvDepartureIataTransitTwo.text = flightData.departureAirportIata
        tvArrivalIataTransitTwo.text = flightData.arrivalAirportIata
    }

    private fun populateThirdTransit(flightData: FlightData) {
        transitThreeFlightWrapper.visibility = View.GONE
        transitThreeFlightDetailWrapper.visibility = View.VISIBLE
        tvFlightNoTransitThree.text = flightData.flightNo
        tvAirlineTransitThree.text = flightData.airline
        tvDepartureTimeTransitThree.text = flightData.departureTime
        tvArrivalTimeTransitThree.text = flightData.arrivalTime
        tvDepartureCityTransitThree.text = flightData.departure
        tvArrivalCityTransitThree.text = flightData.arrival
        tvDepartureIataTransitThree.text = flightData.departureAirportIata
        tvArrivalIataTransitThree.text = flightData.arrivalAirportIata
    }

    private fun setupTripReasonAction() {
        tvTripReasonCode.setOnClickListener {
            showRoundedBottomSheet("Pilih Alasan Perjalanan", tripReason)
        }
    }

    private fun showRoundedBottomSheet(title: String, baseFilter: ArrayList<BaseFilter>) {
        for ((i, filter) in baseFilter.withIndex()) {
            filter.id = i
        }
        val roundedBottomSheet = RoundedBottomSheet(this)
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putParcelableArrayList("baseFilter", baseFilter)
        roundedBottomSheet.arguments = bundle
        roundedBottomSheet.show(this@FormDataPesawatSimas.supportFragmentManager, "test")
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
                        showError(this@FormDataPesawatSimas, e.message)
                    }
                }

                override fun onFailure(call: Call<MudikSimasResponse?>, t: Throwable) {
                    showError(this@FormDataPesawatSimas, "Time out")
                    Log.i("test", "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

}