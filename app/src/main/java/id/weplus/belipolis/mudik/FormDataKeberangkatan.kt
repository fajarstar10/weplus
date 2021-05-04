package id.weplus.belipolis.mudik

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.helper.DatePickerFragment
import id.weplus.helper.OnDateSetListener
import id.weplus.model.City
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan.*
import kotlinx.android.synthetic.main.activity_form_data_keberangkatan.btnNext
import kotlinx.android.synthetic.main.view_back.*
import java.text.SimpleDateFormat
import java.util.*

class FormDataKeberangkatan : BaseActivity(), OnDateSetListener {
    private lateinit var product: Product
    private var dataTertanggung = DataTertanggungRequest()
    private var cities = java.util.ArrayList<City>()
    private var departureDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_keberangkatan)
        setupToolbar()
        getIntentData()
        setupDepartureDate()
        setupButtonNext()
    }

    override fun onDateSet(c: Calendar?, cat: String?) {
        //TODO("Not yet implemented")
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
        if (dataTertanggung.departure != null) {
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

    private fun validate(): Boolean {
        val validateDepartureCity = etDepartureCity.text.toString().isNotBlank()
        val validateDepartureDate = departureDate.isNotBlank()
        errorTextDeparture.visibility = if (validateDepartureDate) View.GONE else View.VISIBLE
        errorTextCity.visibility = if (validateDepartureCity) View.GONE else View.VISIBLE

        return (validateDepartureCity && validateDepartureDate)
    }

    private fun setupButtonNext() {
        btnNext.setOnClickListener {
            if (validate()) {
                val intent = Intent()
                dataTertanggung.departure = etDepartureCity.text.toString()
                dataTertanggung.departureDate = departureDate
                intent.putExtra("data_tertanggung", dataTertanggung)
                setResult(12, intent)
                finish()
            }
        }
    }
}