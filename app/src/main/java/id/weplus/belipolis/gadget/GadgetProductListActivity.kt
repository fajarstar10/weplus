package id.weplus.belipolis.gadget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import id.weplus.R
import id.weplus.WelcomeActivity
import id.weplus.belipolis.productlist.OnPartnerClickListener
import id.weplus.belipolis.productlist.PartnerListAdapter
import id.weplus.helper.EndlessOnScrollListener
import id.weplus.model.PartnerTravel
import id.weplus.model.Product
import id.weplus.model.request.GadgetProductRequest
import id.weplus.model.response.ProductListResponse
import id.weplus.net.ErrorCode
import id.weplus.net.NetworkManager
import id.weplus.net.WeplusSharedPreference
import id.weplus.utility.Constant
import id.weplus.utility.FirebaseAnalyticsHelper.Factory.logEvent
import id.weplus.utility.Util
import kotlinx.android.synthetic.main.activity_gadget_product_list.*
import kotlinx.android.synthetic.main.view_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class GadgetProductListActivity : AppCompatActivity(), OnPartnerClickListener {
    private var page = 1
    private var catId = 15
    private var partnerId = 0
    private var isFilterEnabled = true
    private var isFromCompany=false
    private val TAG="GadgetProductList"

    private var productsAdapter: GadgetProductListAdapter? = null
    private var partnersAdapter: PartnerListAdapter? = null
    private var requestBody: GadgetProductRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logEvent(this, Constant.ANALYTICS_LIST_PRODUCT)

        setContentView(R.layout.activity_gadget_product_list)
        loadingWrapper.visibility=View.VISIBLE
        getIntentData()
        initToolbar()
        setupProductAdapter()
        setupPartnerAdapter()
        fetchProductList()
    }

    private fun initToolbar() {
        viewback_title.text = resources.getString(R.string.product_asuransi)
        viewback_description.text = resources.getString(R.string.product_asuransi_desc)
        viewback_buttonback.setOnClickListener { finish() }
    }

    private fun getIntentData(){
        requestBody = intent.getParcelableExtra("requestBody")
        if(requestBody?.partner_id!="0") isFromCompany=true
    }

    private fun setupProductAdapter() {
        productsAdapter = GadgetProductListAdapter(this@GadgetProductListActivity, ArrayList(), catId, requestBody)
        rvProducts.isNestedScrollingEnabled = true
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@GadgetProductListActivity)
        rvProducts.layoutManager = mLayoutManager
        rvProducts.adapter = productsAdapter
        rvProducts.itemAnimator = DefaultItemAnimator()
        scrollData()?.let { rvProducts.addOnScrollListener(it) }
    }

    private fun setupPartnerAdapter() {
        partnersAdapter = PartnerListAdapter(this, ArrayList(), this)
        if (isFilterEnabled) {
            rvPartners.visibility = View.VISIBLE
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvPartners.layoutManager = layoutManager
            rvPartners.adapter = partnersAdapter
            rvProducts.itemAnimator = DefaultItemAnimator()
        } else {
            tvSubtitle.visibility = View.GONE
            rvPartners.visibility = View.GONE
        }
    }

    private fun fetchProductList() {
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            Log.d("partner", "request : $catId - $partnerId")
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getGadgetProductList(page, requestBody)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    loadingWrapper.visibility = View.GONE
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), ProductListResponse::class.java)
                    try {
                        when (resp.code) {
                            ErrorCode.ERROR_00 -> {
                                productsAdapter!!.addItems(resp.data.getProduct() as ArrayList<Product?>)
                                partnersAdapter!!.setPartners(resp.data.partner as ArrayList<PartnerTravel?>)
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@GadgetProductListActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                SweetAlertDialog(this@GadgetProductListActivity, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(resp.message)
                                        .show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    loadingWrapper.visibility = View.GONE
                    SweetAlertDialog(this@GadgetProductListActivity, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show()
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show()
        }
    }

    private fun fetchProductByPartner(partnerId: Int) {
        loadingWrapper.visibility = View.VISIBLE
        requestBody!!.partner_id = ""+partnerId
        val isNetworkAvailable = Util.isNetworkAvailable(this)
        if (isNetworkAvailable) {
            val token = WeplusSharedPreference.getToken(this)
            Log.d("partner", "request : $catId - $partnerId")
            val call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getGadgetProductList(page, requestBody)
            call.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val gson = Gson()
                    val resp = gson.fromJson(response.body(), ProductListResponse::class.java)
                    try {
                        when (resp.code) {
                            ErrorCode.ERROR_00 -> {
                                if(page==1 && productsAdapter!=null) productsAdapter!!.clearItems()
                                productsAdapter!!.setItems(resp.data.getProduct() as ArrayList<Product?>)
                                partnersAdapter!!.setPartners(resp.data.partner as ArrayList<PartnerTravel?>)
                            }
                            ErrorCode.ERROR_03 -> {
                                val intent = Intent(this@GadgetProductListActivity, WelcomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                SweetAlertDialog(applicationContext, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("")
                                        .setContentText(resp.message)
                                        .show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, "asu: " + e.message)
                    } finally {
                        loadingWrapper.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    SweetAlertDialog(applicationContext, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show()
                    Log.i(TAG, "ON FAILURE : " + t.message)
                }
            })
        } else {
            loadingWrapper.visibility = View.GONE
            SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show()
        }
    }


    private fun scrollData(): EndlessOnScrollListener? {
        return object : EndlessOnScrollListener() {
            override fun onLoadMore() {
                page++
                fetchProductList()
            }
        }
    }

    override fun onPartnerClick(partnerTravel: PartnerTravel) {
        page = 1
        fetchProductByPartner(partnerTravel.id)
    }
}