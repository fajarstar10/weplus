package id.weplus.belipolis.social

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.helper.FullScreenFilterActivity
import id.weplus.helper.OnOptionsSelect
import id.weplus.helper.RoundedBottomSheet
import id.weplus.model.BaseFilter
import id.weplus.model.BskyEnvironment
import id.weplus.model.Paroki
import id.weplus.model.Product
import id.weplus.model.request.DataTertanggungRequest
import id.weplus.model.response.BskyEnvironmentResponse
import id.weplus.model.response.BskyResponse
import id.weplus.model.response.ParokiResponse
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_form_data_tambahan_social.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormDataTambahanSocial : BaseActivity(), OnOptionsSelect {
    private lateinit var dataTertanggungRequest: DataTertanggungRequest
    private lateinit var product: Product
    private var catId: Int = 0

    private val TAG = "datatambahansocial"
    private val protectionTypeTitle = "Pilih Tipe Perlindungan"
    private val keuskupanTitle = "Pilih Keuskupan"
    private val parokiTitle = "Pilih Paroki"
    private val environmentTitle = "Pilih Lingkungan"
    private val jobTitle = "Pilih Pekerjaan"
    private val familyRoleTitle = "Pilih Hubungan Keluarga"

    private var type: String = ""
    private var keuskupan: String = ""
    private var paroki: String = ""
    private var environment: String = ""
    private var job: String = ""
    private var familyRole: String = ""
    private var parokiList: ArrayList<Paroki> = ArrayList()
    private var envList: ArrayList<BskyEnvironment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_data_tambahan_social)
        setupToolbar()
        getIntentData()
        fetchFormParam()
        setupButtonSave()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            if (resultCode == 20) {
                paroki = data?.getStringExtra("result").toString()
                tvParokiName.text = paroki
                tvEnvironment.isEnabled = true
                fetchDataEnvironment(getParokiId(paroki))

            }
        } else if (requestCode == 11) {
            if (resultCode == 21) {
                environment = data?.getStringExtra("result").toString()
                tvEnvironment.text = environment
            }
        }
    }

    private fun setupButtonSave() {
        btnNext.setOnClickListener {
            if (validate()) {
                Log.d("social", "Type : $type")
                Log.d("social", "address : ${etAddress.text.toString()}")
                dataTertanggungRequest.protectionType = type
                dataTertanggungRequest.keuskupan = keuskupan
                dataTertanggungRequest.paroki = if (parokiNameWrapper.visibility == View.VISIBLE) {
                    paroki
                } else {
                    etCommunityName.text.toString()
                }
                dataTertanggungRequest.komunitas = etCommunityName.text.toString()
                dataTertanggungRequest.lingkungan = if (parokiNameWrapper.visibility == View.VISIBLE) {
                    environment
                } else {
                    if (etEnvironment.text.toString().isBlank()) {
                        "-"
                    } else {
                        etEnvironment.text.toString()
                    }

                }
                dataTertanggungRequest.familyRole = familyRole
                dataTertanggungRequest.family_role_other = etOtherFamily.text.toString()
                dataTertanggungRequest.job = job
                dataTertanggungRequest.jobOther = etOtherJob.text.toString()
                dataTertanggungRequest.addressKtp = etAddress.text.toString()
                Log.d("social", "Type dt : ${dataTertanggungRequest.protectionType}")
                Log.d("social", "address : ${dataTertanggungRequest.addressKtp}")
                val intent = Intent()
                intent.putExtra("data_tertanggung", dataTertanggungRequest)
                setResult(82, intent)
                finish()
            }
        }
    }

    private fun setupToolbar() {
        viewback_buttonback.setOnClickListener {
            finish()
        }

        viewback_title.text = "Data Tambahan"
        viewback_description.text = "Silahkan Lengkapi Data Tambahan"
    }

    private fun getIntentData() {
        dataTertanggungRequest = intent.getParcelableExtra("data_tertanggung")!!
        product = intent.getParcelableExtra("product_detail")!!
        catId = intent.getIntExtra("cat_id", 0)
        prePopulateField()
    }

    private fun prePopulateField() {
        if (dataTertanggungRequest.protectionType != null) {
            type = dataTertanggungRequest.protectionType
            tvProtectionType.text = type
            if (type.toLowerCase() == "umum") {
                parokiNameWrapper.visibility = View.VISIBLE
                environmentWrapper.visibility = View.VISIBLE
                environmentTextWrapper.visibility = View.GONE
                communityNameWrapper.visibility = View.GONE
            } else {
                parokiNameWrapper.visibility = View.GONE
                environmentWrapper.visibility = View.GONE
                environmentTextWrapper.visibility = View.VISIBLE
                communityNameWrapper.visibility = View.VISIBLE
            }
            familyRelationWrapper.visibility = View.VISIBLE
            jobWrapper.visibility = View.VISIBLE
            addressWrapper.visibility = View.VISIBLE
        }else{
            environmentTextWrapper.visibility = View.GONE
            communityNameWrapper.visibility = View.GONE
        }
        if (dataTertanggungRequest.keuskupan != null) {
            keuskupan = dataTertanggungRequest.keuskupan
            tvKeuskupanName.text = dataTertanggungRequest.keuskupan
        }
        if (dataTertanggungRequest.paroki != null) {
            paroki = dataTertanggungRequest.paroki
            tvParokiName.text = dataTertanggungRequest.paroki
        }

        if (dataTertanggungRequest.komunitas != null && dataTertanggungRequest.komunitas.isNotBlank()) {
            paroki = "empty"
            parokiNameWrapper.visibility = View.GONE
            environmentWrapper.visibility = View.VISIBLE
            environmentTextWrapper.visibility = View.GONE
            etCommunityName.setText(dataTertanggungRequest.komunitas)
        }


        if (dataTertanggungRequest.lingkungan != null) {
            if (parokiNameWrapper.visibility == View.VISIBLE) {
                environmentTextWrapper.visibility = View.GONE
                environmentWrapper.visibility = View.VISIBLE
                environment = dataTertanggungRequest.lingkungan
                tvEnvironment.text = dataTertanggungRequest.lingkungan
            } else {
                etEnvironment.setText(dataTertanggungRequest.lingkungan)
                environmentTextWrapper.visibility = View.VISIBLE
                environmentWrapper.visibility = View.GONE
            }
        }

        if (dataTertanggungRequest.familyRole != null) {
            familyRole = dataTertanggungRequest.familyRole
            tvFamilyRelation.text = dataTertanggungRequest.familyRole
        }
        if (dataTertanggungRequest.family_role_other != null) {
            etOtherFamily.setText(dataTertanggungRequest.family_role_other)
        }
        if (dataTertanggungRequest.job != null) {
            job = dataTertanggungRequest.job
            tvJob.text = dataTertanggungRequest.job
        }
        if (dataTertanggungRequest.jobOther != null) {
            etOtherJob.setText(dataTertanggungRequest.jobOther)
        }
        if (dataTertanggungRequest.addressKtp != null) {
            etAddress.setText(dataTertanggungRequest.addressKtp)
        }
    }

    private fun validate(): Boolean {
        val typeValidate = type.isNotBlank()
        val keuskupanValidate = keuskupan.isNotBlank()
        val parokiValidate = paroki.isNotBlank()
//        val environmentValidate = if (parokiNameWrapper.visibility == View.VISIBLE) {
//            environment.isNotBlank()
//        } else {
//            true
//            //etEnvironment.text.isNotBlank()
//        }
        val jobValidate = job.isNotBlank()
        val familyRoleValidate = familyRole.isNotBlank()
        val communityNameValidate = etCommunityName.text.isNotBlank()
        val otherRoleValidate = if (familyRole.toLowerCase() == "lainnya") {
            !etOtherFamily.text.isNullOrBlank()
        } else {
            true
        }
        val otherJobValidate = if (job.toLowerCase() == "lainnya") {
            !etOtherJob.text.toString().isNullOrBlank()
        } else {
            true
        }
        val addressValidate = !etAddress.text.toString().isNullOrBlank()

        if (typeValidate) {
            errorTextProtectionType.visibility = View.GONE
        } else {
            errorTextProtectionType.visibility = View.VISIBLE
        }
        if (keuskupanValidate) {
            errorTextKeuskupan.visibility = View.GONE
        } else {
            errorTextKeuskupan.visibility = View.VISIBLE
        }
        if (parokiNameWrapper.visibility == View.VISIBLE) {
            if (parokiValidate) {
                errorTextParoki.visibility = View.GONE
            } else {
                errorTextParoki.visibility = View.VISIBLE
            }
        }
        if (communityNameWrapper.visibility == View.VISIBLE) {
            if (parokiValidate) {
                errorTextCommunity.visibility = View.GONE
            } else {
                errorTextCommunity.visibility = View.VISIBLE
            }
        }
//        if (environmentValidate) {
//            errorTextEnvironment.visibility = View.GONE
//        } else {
//            errorTextEnvironment.visibility = View.VISIBLE
//        }
        if (jobValidate) {
            errorTextJob.visibility = View.GONE
        } else {
            errorTextJob.visibility = View.VISIBLE
        }
        if (familyRoleValidate) {
            errorTextFamilyRelation.visibility = View.GONE
        } else {
            errorTextFamilyRelation.visibility = View.VISIBLE
        }
        if (otherRoleValidate) {
            errorTextOtherFamily.visibility = View.GONE
        } else {
            errorTextOtherFamily.visibility = View.VISIBLE
        }
        if (otherJobValidate) {
            errorTextOtherJob.visibility = View.GONE
        } else {
            errorTextOtherJob.visibility = View.VISIBLE
        }
        if (addressValidate) {
            errorTextAddress.visibility = View.GONE
        } else {
            errorTextAddress.visibility = View.VISIBLE
        }

        val protectionDataValidate = if (parokiNameWrapper.visibility == View.VISIBLE) {
            parokiValidate
        } else {
            communityNameValidate
        }

        return (typeValidate &&
                keuskupanValidate &&
                protectionDataValidate &&
//                environmentValidate &&
                jobValidate &&
                familyRoleValidate &&
                otherJobValidate &&
                otherRoleValidate &&
                addressValidate)
    }

    private fun fetchFormParam() {
        Log.d("buy", "fetch form param")
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getBSKYFormParam(13, product.partner_id.toInt())
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {

                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), BskyResponse::class.java)
                    try {
                        setupDataKeuskupan(resp.data.keuskupan)
                        setupDataProtectionType(resp.data.protectionType)
                        setupDataFamilyRelation(resp.data.familyRole)
                        setupDataJob(resp.data.job)
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTambahanSocial, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormDataTambahanSocial, getString(R.string.network_error))
        }
    }

    private fun showRoundedBottomSheet(title: String, baseFilter: ArrayList<BaseFilter>) {
        val roundedBottomSheet = RoundedBottomSheet(this)
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putParcelableArrayList("baseFilter", baseFilter)
        roundedBottomSheet.arguments = bundle
        roundedBottomSheet.show(this@FormDataTambahanSocial.supportFragmentManager, "test")
    }

    private fun showFullScreenOption(optionsArray: ArrayList<String>, resultCode: Int, requestCode: Int) {
        val intentBrand = Intent(this, FullScreenFilterActivity::class.java)
        intentBrand.putStringArrayListExtra("searchList", optionsArray)
        intentBrand.putExtra("resultCode", resultCode)
        startActivityForResult(intentBrand, requestCode)
    }

    private fun setupDataProtectionType(listProtectionType: ArrayList<BaseFilter>) {
        tvProtectionType.setOnClickListener {
            showRoundedBottomSheet(protectionTypeTitle, listProtectionType)
        }
    }

    private fun setupDataKeuskupan(listKeuskupan: ArrayList<BaseFilter>) {
        tvKeuskupanName.setOnClickListener {
            showRoundedBottomSheet(keuskupanTitle, listKeuskupan)
        }
    }

    private fun setupDataFamilyRelation(listRelation: ArrayList<BaseFilter>) {
        for ((i, base) in listRelation.withIndex()) {
            base.id = i
        }
        tvFamilyRelation.setOnClickListener {
            showRoundedBottomSheet(familyRoleTitle, listRelation)
        }
    }

    private fun setupDataJob(listJob: ArrayList<BaseFilter>) {
        for ((i, base) in listJob.withIndex()) {
            base.id = i
        }
        tvJob.setOnClickListener {
            showRoundedBottomSheet(jobTitle, listJob)
        }
    }

    private fun clearSelection() {
        keuskupan = ""
        tvKeuskupanName.text = "Pilih keuskupan"
        paroki = ""
        tvParokiName.text = "Pilih Paroki"
        environment = ""
        tvEnvironment.text = "Pilih Lingkungan"
        etEnvironment.hint="Masukkan Nama Komunitas"
        job = ""
        etOtherJob.setText(job)
        etOtherJob.hint="Contoh: Karyawan Swasta"
        tvJob.text = "Pilih Pekerjaan"
        tvFamilyRelation.text = "Pilih Hubungan Keluarga"
        familyRole = ""
        etOtherFamily.setText(familyRole)
        etOtherFamily.hint="Contoh : sepupu"
        etAddress.setText("");
        etAddress.hint="Masukkan Alamat KTP"
    }

    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        when (title) {
            protectionTypeTitle -> {
                clearSelection()
                val protection: String = baseFilter.filterText
                if (baseFilter.filterText.toLowerCase() == "umum") {
                    parokiNameWrapper.visibility = View.VISIBLE
                    environmentWrapper.visibility = View.VISIBLE
                    communityNameWrapper.visibility = View.GONE
                    environmentTextWrapper.visibility = View.GONE
                } else {
                    parokiNameWrapper.visibility = View.GONE
                    environmentWrapper.visibility = View.GONE
                    communityNameWrapper.visibility = View.VISIBLE
                    environmentTextWrapper.visibility = View.VISIBLE
                }
                keuskupanNameWrapper.visibility = View.VISIBLE
                familyRelationWrapper.visibility = View.VISIBLE
                jobWrapper.visibility = View.VISIBLE
                addressWrapper.visibility = View.VISIBLE

                tvProtectionType.text = protection
                type = protection
            }
            keuskupanTitle -> {
                keuskupan = baseFilter.filterText
                tvKeuskupanName.text = baseFilter.filterText
                tvParokiName.isEnabled = true
                fetchDataParoki(baseFilter.id)
            }
            parokiTitle -> {

            }
            jobTitle -> {
                job = baseFilter.filterText
                tvJob.text = baseFilter.filterText
                if (job.toLowerCase() == "lainnya") {
                    otherJobWrapper.visibility = View.VISIBLE
                }
            }
            familyRoleTitle -> {
                tvFamilyRelation.text = baseFilter.filterText
                familyRole = baseFilter.filterText
                if (familyRole.toLowerCase() == "lainnya") {
                    otherFamilyWrapper.visibility = View.VISIBLE
                }
            }
            environmentTitle -> {

            }
        }
    }

    private fun fetchDataParoki(keuskupanId: Int) {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getParokiList(keuskupanId)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), ParokiResponse::class.java)
                    try {
                        parokiList = resp.data.listParoki
                        setupDataParoki(resp.data.listParoki)
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTambahanSocial, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormDataTambahanSocial, getString(R.string.network_error))
        }
    }

    private fun setupDataParoki(parokiList: ArrayList<Paroki>) {
        tvParokiName.setOnClickListener {
            val baseFilters = ArrayList<String>()
            for (paroki in parokiList) {
                baseFilters.add(paroki.text)
            }
            showFullScreenOption(baseFilters, 20, 10)
        }
    }

    private fun fetchDataEnvironment(keuskupanId: Int) {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getBskyEnvironmentList(keuskupanId)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), BskyEnvironmentResponse::class.java)
                    try {
                        envList = resp.data.bskyEnvironmentArrayList
                        setupDataEnvironment(resp.data.bskyEnvironmentArrayList)
                    } catch (e: java.lang.Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    showError(this@FormDataTambahanSocial, "Time out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@FormDataTambahanSocial, getString(R.string.network_error))
        }
    }

    private fun setupDataEnvironment(environmentList: ArrayList<BskyEnvironment>) {
        tvEnvironment.setOnClickListener {
            val baseFilters = ArrayList<String>()
            for (paroki in environmentList) {
                baseFilters.add(paroki.text)
            }
            showFullScreenOption(baseFilters, 21, 11)
        }
    }

    private fun getParokiId(name: String): Int {
        val idx = parokiList.indexOfFirst { it.text == name }
        if (idx == -1) {
            return 0
        }
        return idx
    }


}