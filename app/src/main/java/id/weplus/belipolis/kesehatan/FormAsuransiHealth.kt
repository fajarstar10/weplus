package id.weplus.belipolis.kesehatan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.helper.*
import id.weplus.model.BaseFilter
import id.weplus.model.request.HealthProductListRequest
import id.weplus.model.response.HealthFilterResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_asuransi_health.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class FormAsuransiHealth : BaseActivity(), OnDateSetListener, OnFamilyMemberSaved, OnOptionsSelect {
    private var isFamily = 0
    private var isSmoker = 0
    private var dob = ""
    private var groupType = 0
    private var paymentType = 0
    private var adultCount = 1
    private var childCount = 0
    private var partnerId=0;
    private var tag = "FormHealth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_asuransi_health)
        getData()
        setupToolbar()
        fetchHealthFilter()
        setupNextButton()
        setupFamilyMember()
    }

    private fun setupFamilyMember() {
        if (isFamily == 1) {
            layout_jumlah_anggota_keluarga.visibility = View.VISIBLE
            layout_jenis_kelamin.visibility = View.GONE
        } else {
            layout_jumlah_anggota_keluarga.visibility = View.GONE
            layout_jenis_kelamin.visibility = View.VISIBLE
        }

        anggota_keluarga_value.setOnClickListener {
            val roundedBottomSheet = FamilyMemberBottomSheet(this)
            val bundle = Bundle()
            bundle.putInt("adult_count", adultCount)
            bundle.putInt("child_count", childCount)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormAsuransiHealth.supportFragmentManager, "test")
        }
    }

    private fun fetchHealthFilter() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .healthFilter
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), HealthFilterResponse::class.java)
                    try {
                        if (resp.code == ErrorCode.ERROR_00) {
                            Log.d(tag, resp.data.paymentTypes.toString())
                            Log.d(tag, "${resp.data.paymentTypes.size} 3")
                            setupClaimMethod(resp.data.paymentTypes)
                            setupDatePicker(resp.data.minAge, resp.data.maxAge)
                        } else if (resp.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@FormAsuransiHealth, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            showError(this@FormAsuransiHealth, resp.message)
                        }
                    } catch (e: Exception) {
                        Log.i(tag, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormAsuransiHealth, "Time out")
                    Log.i(tag, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun setupClaimMethod(filter: ArrayList<BaseFilter>) {
        metode_klaim_value.setOnClickListener {
            val roundedBottomSheet = RoundedBottomSheet(this)
            val bundle = Bundle()
            bundle.putString("title", "Pilih Metode Klaim")
            bundle.putParcelableArrayList("baseFilter", filter)
            roundedBottomSheet.arguments = bundle
            roundedBottomSheet.show(this@FormAsuransiHealth.supportFragmentManager, "test")
        }

    }

    private fun setupNextButton() {
        btnNext.setOnClickListener {
            if (validate()) {
                val productIntent = Intent(this, HealthProductListActivity::class.java)
                val sex = if (healthFormGender.selectedItemPosition == 1) {
                    "m"
                } else {
                    "f"
                }

                val healthProductListRequest = HealthProductListRequest(
                        0, "", 2, partnerId, isSmoker, dob, sex, paymentType, groupType
                )
                healthProductListRequest.adult = adultCount
                healthProductListRequest.child = childCount
                productIntent.putExtra("requestBody", healthProductListRequest)
                startActivity(productIntent)
            }
        }
    }

    private fun validate(): Boolean {
        val dobValidate = dob.isNotBlank()
        val genderValidate = healthFormGender.selectedItemPosition != 0
        val paymentTypeValidate = paymentType != 0

        errorTextKlaim.visibility = if (paymentTypeValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        errorTextDob.visibility = if (dobValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        errorTextGender.visibility = if (genderValidate) {
            View.GONE
        } else {
            View.VISIBLE
        }

        return if (groupType == 26) {
            (dobValidate && paymentTypeValidate)
        } else {
            (dobValidate && genderValidate && paymentTypeValidate)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupDatePicker(min: Int, max: Int) {
        if (isFamily == 1) tgllahir_label.text = "Tanggal Lahir Anggota Tertua"
        healthFormDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val minAge = calendar.get(Calendar.YEAR) - min
            val maxAge = calendar.get(Calendar.YEAR) - max
            Log.d("min max age", " test $minAge - $maxAge")
            val newFragment: DialogFragment = DatePickerFragment.newInstance("dob", maxAge, minAge, this)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }

        viewback_title.text = "Asuransi Kesehatan"
        viewback_description.text = ""
    }

    private fun getData() {
        isFamily = intent.getIntExtra("isFamily", 0)
        isSmoker = intent.getIntExtra("isSmoker", 0)
        partnerId = intent.getIntExtra("partner_id", 0);

        healthFormInsuranceType.text = if (isFamily == 0) {
            groupType = 25
            adultCount = 1
            "Individu"
        } else {
            groupType = 26
            "Keluarga"
        }

        healthFormIsSmoker.text = if (isSmoker == 0) {
            "Tidak"
        } else {
            "Ya"
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(c: Calendar?, cat: String?) {
        val postFormater = SimpleDateFormat(" dd MMMM yyyy")
        val newDateStr = postFormater.format(c?.time)

        if (c != null) {
            dob = ("${c[Calendar.YEAR]}-${c[Calendar.MONTH + 1]}-${c[Calendar.DAY_OF_MONTH]}")
            healthFormDob.text = newDateStr
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onFamilyMemberSaved(adultCount: Int, childCount: Int) {
        this.adultCount = adultCount
        this.childCount = childCount
        anggota_keluarga_value.text = "$adultCount Dewasa, $childCount Anak"
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        metode_klaim_value.text = baseFilter.filterText
        paymentType = baseFilter.id
    }


}