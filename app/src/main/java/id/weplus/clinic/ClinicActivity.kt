package id.weplus.clinic

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.model.Clinic
import id.weplus.model.response.ClinicResponse
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_clinic.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ClinicActivity : BaseActivity() {
    private val TAG="ClinicActivity"
    private lateinit var adapter:ClinicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clinic)
        setupToolbar()
        fetchClinic()
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }
        viewback_title.text = "Klinik WE+"
        viewback_description.text = "Periksa langsung kesehatan dengan sistem berbasis AI"
    }

    private fun fetchClinic() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility= View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .clinicList
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), ClinicResponse::class.java)
                    setupClinicAdapter(resp.data.clinicArrayList)
                    loadingProgress.visibility= View.GONE
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    loadingProgress.visibility= View.GONE
                    showError(this@ClinicActivity, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this, getString(R.string.network_error))
        }
    }

    private fun setupClinicAdapter(clinicArrayList: ArrayList<Clinic>) {
        adapter = ClinicAdapter(this,clinicArrayList)
        rvClinic.isNestedScrollingEnabled = true
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvClinic.layoutManager = mLayoutManager
        rvClinic.adapter = adapter
        rvClinic.itemAnimator = DefaultItemAnimator()
    }


}