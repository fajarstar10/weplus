package id.weplus.belipolis.gadget

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.weplus.BaseActivity
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.helper.FullScreenFilterActivity
import id.weplus.helper.OnOptionsSelect
import id.weplus.helper.RoundedBottomSheet
import id.weplus.model.BaseFilter
import id.weplus.model.request.GadgetProductRequest
import id.weplus.model.response.gadget.*
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.TextHelper.currencyFormatter
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_gadget_filter_form.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GadgetFilterFormActivity : BaseActivity(), OnOptionsSelect {
    private val TAG = "GadgetFilterActivity"
    private var option = ""
    private var brand = ""
    private var type = ""
    private var age = ""
    private var price = ""
    private var partnerWeplusId=0
    private var nik=""


    private val optionTitle = "Pilih Jenis Gadget"
    private val priceTitle = "Pilih Harga Gadget"
    private val ageTitle = "Pilih Tahun Produksi"
    private var data: GadgetData? = null
    private var gadgetList = ArrayList<GadgetListBrandData>()
    private val reqBrand = 12
    private val resBrand = 22
    private val reqType = 13
    private val resType = 23
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gadget_filter_form)
        getData()
        setupToolbar()
        fetchGadgetCategory()
        setupButtonSubmit()
    }

    private fun getData(){
        partnerWeplusId = intent.getIntExtra("partner_weplus_id",0)
        nik = intent.getStringExtra("nik").toString()
    }

    private fun setupButtonSubmit() {

        btnNext.setOnClickListener {
            var gadgetProductRequest = GadgetProductRequest()
            Log.d("price", "price $option - $price -  ${option.toLowerCase() == "tablet"}")
            if (option.toLowerCase() == "1" || option.toLowerCase() == "2") {
                gadgetProductRequest.gadget_type = option
                gadgetProductRequest.gadget_name = type
                gadgetProductRequest.gadget_brand = brand

            } else {
                gadgetProductRequest.gadget_type = option
                gadgetProductRequest.gadget_brand = etGadgetBrand.text.toString()
                gadgetProductRequest.gadget_name = etGadgetType.text.toString()
            }
            gadgetProductRequest.gadget_age = age
            gadgetProductRequest.gadget_price = price
            gadgetProductRequest.category_id = "15"
            gadgetProductRequest.partner_weplus_id=""+partnerWeplusId
            gadgetProductRequest.nik=nik
            val intent = Intent(this@GadgetFilterFormActivity, GadgetProductListActivity::class.java)
            intent.putExtra("requestBody", gadgetProductRequest)
            intent.putExtra("cat_id", 15)
            startActivity(intent)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onOptionSelect(baseFilter: BaseFilter, title: String) {
        if (title == optionTitle) {
            if (!baseFilter.filterText.isNullOrBlank()) {
                tvGadgetOption.text = baseFilter.filterText
                option = baseFilter.name
                fetchGadgetBrand(option)
                brand = ""
                tvGadgetBrand.text = "Pilih Merek Gadget"
                etGadgetBrand.setText("")
                type = ""
                tvGadgetType.text = "Pilih Tipe Gadget"
                etGadgetType.setText("")
                age = ""
                tvGadgetYear.text = "Contoh  : 2020"
                etGadgetYear.setText("")
                price = ""
                tvGadgetPrice.text = "Contoh : Rp 555.000,00"
                etGadgetPrice.setText("")
                setupFormBasedOnOption(baseFilter.name)
            }
        }
        if (title == priceTitle) {
            price = baseFilter.name;
            if(option=="1" || option=="2")
                tvGadgetPrice.text = "Rp ${currencyFormatter(baseFilter.filterText)}"
            else
                tvGadgetPrice.text = baseFilter.filterText
        }

        if (title == ageTitle) {
            age = baseFilter.name
            tvGadgetYear.text = baseFilter.filterText
        }
    }

    private fun setupFormBasedOnOption(option: String) {
        if (option != "1" && option != "2") {
            tvGadgetBrand.visibility = View.GONE
            tvGadgetType.visibility = View.GONE
            tvGadgetYear.visibility = View.VISIBLE
            tvGadgetPrice.visibility = View.VISIBLE

            etGadgetBrand.visibility = View.VISIBLE
            etGadgetType.visibility = View.VISIBLE
            etGadgetYear.visibility = View.GONE
            etGadgetPrice.visibility = View.GONE
            tvGadgetYear.isEnabled = true
            tvGadgetPrice.isEnabled = true
            iconPriceDropdown.visibility=View.VISIBLE
            iconYearDropdown.visibility=View.VISIBLE
            tvGadgetYear.text = "Pilih Tahun Produksi"
            tvGadgetPrice.text = "Pilih Harga Gadget"
            tvGadgetYear.setOnClickListener {
                data?.listAge?.let { it1 -> openBottomSheet(ageTitle, it1) }
            }

            tvGadgetPrice.setOnClickListener {
                data?.listPrice?.let { it2 -> openBottomSheet(priceTitle, it2) }
            }
        } else {
            tvGadgetYear.isEnabled = false
            tvGadgetPrice.isEnabled = false
            etGadgetBrand.visibility = View.GONE
            etGadgetType.visibility = View.GONE
            etGadgetYear.visibility = View.GONE
            etGadgetPrice.visibility = View.GONE

            tvGadgetBrand.visibility = View.VISIBLE
            tvGadgetType.visibility = View.VISIBLE
            tvGadgetYear.visibility = View.VISIBLE
            tvGadgetPrice.visibility = View.VISIBLE

            iconPriceDropdown.visibility=View.GONE
            iconYearDropdown.visibility=View.GONE
            tvGadgetYear.hint = "Contoh:2020"
            tvGadgetPrice.hint = "Contoh : Rp 1.000.000"

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            reqBrand -> {
                brand = data?.getStringExtra("result").toString()
                tvGadgetBrand.text = brand
                fetchGadgetList()
                type = ""
                tvGadgetType.text = "Pilih Tipe Gadget"
                age = ""
                tvGadgetYear.text = "Contoh  : 2020"
                price = ""
                tvGadgetPrice.text = "Contoh : Rp 555.000,00"
            }
            reqType -> {
                type = data?.getStringExtra("result").toString()
                tvGadgetType.text = type
                if (!type.isNullOrBlank()) {
                    setYearAndPrice(type)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setYearAndPrice(s: String) {
        val index = gadgetList.indexOfFirst { it -> it.text_name == s }
        if (index != -1) {
            val data = gadgetList[index]
            age = data.key_age
            price = data.key_price
            tvGadgetPrice.text = "Rp. "+ currencyFormatter(price)
            tvGadgetYear.text = data.text_age
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        viewback_title.text = "Asuransi Gadget"
        viewback_description.text = "Isi sesuai data gadget anda"
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun fetchGadgetCategory() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility = View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .gadgetFilter
            call.enqueue(object : Callback<GadgetCategoryResponse> {
                override fun onResponse(call: Call<GadgetCategoryResponse>, response: Response<GadgetCategoryResponse>) {
                    try {
                        loadingProgress.visibility = View.GONE
                        if (response.body()?.code == ErrorCode.ERROR_00) {
                            data = response.body()?.data
                            setupForm()
                        } else if (response.body()?.code == ErrorCode.ERROR_03) {
                            val intent = Intent(this@GadgetFilterFormActivity, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            showError(this@GadgetFilterFormActivity, response.message())
                        }
                    } catch (e: Exception) {
                        loadingProgress.visibility = View.GONE
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<GadgetCategoryResponse>, t: Throwable) {
                    loadingProgress.visibility = View.GONE
                    showError(this@GadgetFilterFormActivity, "Time Out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@GadgetFilterFormActivity, getString(R.string.network_error))
        }
    }

    private fun fetchGadgetBrand(brand: String) {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility = View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getGadgetBrand(brand)
            call.enqueue(object : Callback<GadgetBrandResponse> {
                override fun onResponse(call: Call<GadgetBrandResponse>, response: Response<GadgetBrandResponse>) {
                    try {
                        loadingProgress.visibility = View.GONE
                        if (response.body()?.code == ErrorCode.ERROR_00) {
                            response.body()?.data?.let {
                                tvGadgetBrand.isEnabled = true
                                setupBrandForm(it)
                            }
                        } else {
                            showError(this@GadgetFilterFormActivity, response.message())
                        }
                    } catch (e: Exception) {
                        loadingProgress.visibility = View.GONE
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<GadgetBrandResponse>, t: Throwable) {
                    loadingProgress.visibility = View.GONE
                    showError(this@GadgetFilterFormActivity, "Time Out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@GadgetFilterFormActivity, getString(R.string.network_error))
        }
    }

    private fun fetchGadgetList() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            loadingProgress.visibility = View.VISIBLE
            val token = WeplusSharedPreference.getToken(this)
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getGadgetList(option, brand)
            call.enqueue(object : Callback<GadgetListResponse> {
                override fun onResponse(call: Call<GadgetListResponse>, response: Response<GadgetListResponse>) {
                    try {
                        loadingProgress.visibility = View.GONE
                        if (response.body()?.code == ErrorCode.ERROR_00) {

                            response.body()?.data?.brand?.let {
                                tvGadgetType.isEnabled = true
                                gadgetList.clear()
                                gadgetList.addAll(it)
                                setupGagdgetTypeForm()
                            }
                        } else {
                            showError(this@GadgetFilterFormActivity, response.message())
                        }
                    } catch (e: Exception) {
                        loadingProgress.visibility = View.GONE
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<GadgetListResponse>, t: Throwable) {
                    loadingProgress.visibility = View.GONE
                    showError(this@GadgetFilterFormActivity, "Time Out")
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            showError(this@GadgetFilterFormActivity, getString(R.string.network_error))
        }
    }

    private fun setupGagdgetTypeForm() {
        val gadgetTypes = ArrayList<String>()
        for (g in gadgetList) {
            gadgetTypes.add(g.text_name)
        }

        tvGadgetType.setOnClickListener {
            openFullscreenSelection(reqType, resType, gadgetTypes)
        }
    }

    private fun setupForm() {
        tvGadgetOption.setOnClickListener {
            data?.gadgetType?.let { it1 -> openBottomSheet(optionTitle, it1) }
        }
    }

    private fun setupBrandForm(data: GadgetBrand) {
        val brands = ArrayList<String>()
        for (d in data.brands) {
            brands.add(d.brand)
        }
        tvGadgetBrand.setOnClickListener {
            openFullscreenSelection(reqBrand, resBrand, brands)
        }

    }

    private fun openBottomSheet(title: String, baseFilters: ArrayList<BaseFilter>) {
        val roundedBottomSheet = RoundedBottomSheet(this)
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putParcelableArrayList("baseFilter", baseFilters)
        roundedBottomSheet.arguments = bundle
        roundedBottomSheet.show(this@GadgetFilterFormActivity.supportFragmentManager, "test")
    }

    private fun openFullscreenSelection(requestCode: Int, responseCode: Int, spinnerArray: ArrayList<String>) {
        val intentBrand = Intent(this@GadgetFilterFormActivity, FullScreenFilterActivity::class.java)
        intentBrand.putStringArrayListExtra("searchList", spinnerArray)
        intentBrand.putExtra("resultCode", responseCode)
        startActivityForResult(intentBrand, requestCode)
    }

}