package id.weplus.detailpolis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrawalsuneet.dotsloader.loaders.AllianceLoader;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WebViewActivity;
import id.weplus.WelcomeActivity;
import id.weplus.affiliasi.AffiliationActivity;
import id.weplus.model.InsuredDetail;
import id.weplus.model.InsuredDetailData;
import id.weplus.model.response.InsuredDetailRespon;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.pembayaran.PembayaranActivity;
import id.weplus.polissaya.DetailPolisTertanggung;
import id.weplus.polissaya.contactinsurancepartner.ContactInsurancePartnerActivity;
import id.weplus.utility.RecyclerItemClickListener;
import id.weplus.utility.TextHelper;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * for detail polis mobil dan detail polis motor
 */

public class DetailPolisMobilActivity extends BaseActivity implements RecyclerItemClickListener.OnItemClickListener {
    private static final String TAG = "InsuredDetail";
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView desciption;
    @BindView(R.id.id_transaksi)
    TextView idTransaksi;
    @BindView(R.id.nomor_polis)
    TextView nomorPolis;
    @BindView(R.id.tgl_pembelian)
    TextView tglPembelian;
    @BindView(R.id.metode_pembayaran)
    TextView metodePembayaran;
    @BindView(R.id.masa_aktif)
    TextView masaAktifPolis;
    @BindView(R.id.harga)
    TextView harga;
    @BindView(R.id.biaya_produk_asuransi)
    TextView biayaProdukAsuransi;
    @BindView(R.id.biaya_admin)
    TextView biayaAdmin;
    @BindView(R.id.potongan_harga)
    TextView potonganHarga;
    @BindView(R.id.total_pembelian)
    TextView totalPembelian;
    @BindView(R.id.btn_lihat_rincian)
    TextView btnProdukRincian;
    @BindView(R.id.rec_show_rincian)
    RelativeLayout layoutRincian;
    @BindView(R.id.rec_detail_produk_klaim)
    RecyclerView recDetailProdukKlaim;
    @BindView(R.id.rec_data_mobil)
    RecyclerView recDataMobil;
    @BindView(R.id.rec_data_pemegang_polis)
    RecyclerView recDataPemegangPolis;
    @BindView(R.id.rec_menu_detail_polis)
    RecyclerView menudetailPolis;
    @BindView(R.id.transaksi_img)
    ImageView img;
    @BindView(R.id.polis_saya_tgl_transaksi)
    TextView tglTransaksi;
    @BindView(R.id.polis_saya_asuransi_name)
    TextView asuransiName;
    @BindView(R.id.transaksi_status)
    TextView transactionStatus;
    @BindView(R.id.tv_id_transaksi_val)
    TextView codeOrder;
    @BindView(R.id.textAhliWaris)
    TextView textAhliWaris;
    @BindView(R.id.detailwaris_nama)
    TextView warisNama;
    @BindView(R.id.detailwaris_email)
    TextView warisEmail;
    @BindView(R.id.detailwaris_phone)
    TextView warisPhone;
    @BindView(R.id.waris_layout)
    RelativeLayout warisLayout;
    @BindView(R.id.loader)
    AllianceLoader loader;
    @BindView(R.id.loader_bg)
    RelativeLayout loaderBackground;
    @BindView(R.id.tvDataMobil)
    TextView tvDataMobil;
    @BindView(R.id.itemDetailsBrand)
    TextView tvDetailBrand;
    @BindView(R.id.itemDetailSeries)
    TextView tvDetailSeries;
    @BindView(R.id.itemDetailsYear)
    TextView tvDetailYear;
    @BindView(R.id.transaksi_item_details)
    RelativeLayout itemDetailsWrapper;
    @BindView(R.id.contactUsWrapper)
    RelativeLayout contactUsWrapper;
    @BindView(R.id.transaksi_linear)
    RelativeLayout productDetailWrapper;
    @BindView(R.id.itemDetaislNext)
    ImageView idNext;

    private boolean showProdukRincian = false;
    private String polisType = "Aktif";
    private InsuredDetail insuredDetail;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_polis_mobil);
        ButterKnife.bind(this);

        title.setText(getString(R.string.detailpolis));
        desciption.setText(getString(R.string.melihatdetailpolisyangdibeli));

        polisType = getIntent().getStringExtra("tipe");
        id = getIntent().getStringExtra("id");

        fetchInsuredDetail();
    }

    private void setupMenuDetailList() {
        MenuDetailPolisAdapter adapterMenuDetailPolis = new MenuDetailPolisAdapter(this, insuredDetail.getListMenu(), this);
        LinearLayoutManager layoutManagerMenuDetailPolis = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        menudetailPolis.setLayoutManager(layoutManagerMenuDetailPolis);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menudetailPolis.getContext(),
                layoutManagerMenuDetailPolis.getOrientation());
        menudetailPolis.addItemDecoration(dividerItemDecoration);
        menudetailPolis.setAdapter(adapterMenuDetailPolis);
    }

    @OnClick(R.id.btn_lihat_rincian)
    public void hideShowProdukRincian() {
        if (!showProdukRincian) {
            btnProdukRincian.setText(getString(R.string.tutup));
            showProdukRincian = true;
            layoutRincian.setVisibility(View.VISIBLE);
        } else {
            btnProdukRincian.setText(getString(R.string.lihatrincian));
            showProdukRincian = false;
            layoutRincian.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.viewback_buttonback)
    public void backDetailPolis() {
        finish();
    }

    private void fetchInsuredDetail() {
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            loader.setVisibility(View.VISIBLE);
            loaderBackground.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getInsuredDetail(id);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    InsuredDetailRespon respon = gson.fromJson(response.body(), InsuredDetailRespon.class);
                    Log.e(TAG, respon.toString());
                    insuredDetail = respon.data;
                    loader.setVisibility(View.GONE);
                    loaderBackground.setVisibility(View.GONE);
                    try {
                        Log.d("error","error "+respon.getCode().equals(ErrorCode.ERROR_00));
                        if (respon.getCode().equals(ErrorCode.ERROR_00)) {
                            populateView(respon.data);
                            setupMenuDetailList();
                        } else if(respon.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(DetailPolisMobilActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            Log.d("error","should show error");
                            new SweetAlertDialog(DetailPolisMobilActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("")
                                    .setContentText(respon.getMessage())
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(sDialog -> {
                                        sDialog.dismissWithAnimation();
                                        finish();
                                    })
                                    .show();
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "asu: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loader.setVisibility(View.GONE);
                    loaderBackground.setVisibility(View.GONE);
                    try {

                        new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("")
                                .setContentText("Time Out")
                                .show();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Log.i(TAG, "ON FAILURE : " + t.getMessage());
                }
            });
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(" ")
                    .setContentText(getString(R.string.network_error))
                    .show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateView(InsuredDetail detailModel) {
        idTransaksi.setText(detailModel.getOrder_code());
        codeOrder.setText(detailModel.getOrder_code());
        nomorPolis.setText(detailModel.getNo_polis());
        tglPembelian.setText(detailModel.getDetailPayment().getDate_payment());
        metodePembayaran.setText(detailModel.getDetailPayment().getPayment_channel());
        masaAktifPolis.setText(getDuration(detailModel.getDate_active()));
        harga.setText("Rp." + TextHelper.currencyFormatter(String.valueOf(detailModel.getDetailPayment().getTotal_payment())));
        biayaProdukAsuransi.setText(TextHelper.currencyFormatter(String.valueOf(detailModel.getDetailPayment().getProduct_nominal())));
        biayaAdmin.setText(TextHelper.currencyFormatter(String.valueOf(detailModel.getDetailPayment().getAdmin_fee())));
        potonganHarga.setText(TextHelper.currencyFormatter(String.valueOf(detailModel.getDetailPayment().getProcessing_fee())));
        totalPembelian.setText(TextHelper.currencyFormatter(String.valueOf(detailModel.getDetailPayment().getTotal_payment())));

        Glide.with(this)
                .load(detailModel.getProductDetail().getImage())
                .placeholder(R.drawable.aca_insurance)
                .error(R.drawable.aca_insurance)
                .into(img);
        asuransiName.setText(detailModel.getProductDetail().getName());
        tglTransaksi.setText(detailModel.getDate_active());
        transactionStatus.setText(getStatus(detailModel.getStatus()));

        // data pemegang polis
        List<InsuredDetailData> dataPemegangPlisList = new ArrayList<InsuredDetailData>();
        dataPemegangPlisList = detailModel.getInsured_detail();
        LinearLayoutManager lk = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recDataMobil.getContext(),
                lk.getOrientation());
        recDataMobil.addItemDecoration(dividerItemDecoration);
        DetailPolisTertanggung adapter = new DetailPolisTertanggung(this, dataPemegangPlisList);
        recDataMobil.setLayoutManager(lk);
        recDataMobil.setAdapter(adapter);
        if (detailModel.getBeneficiary_detail().getName().equalsIgnoreCase("")) {
            warisLayout.setVisibility(View.GONE);
            textAhliWaris.setVisibility(View.GONE);
        }
        warisNama.setText(detailModel.getBeneficiary_detail().getName());
        warisEmail.setText(detailModel.getBeneficiary_detail().getEmail());
        warisPhone.setText(detailModel.getBeneficiary_detail().getPhone());
        setupUiByCategory(insuredDetail.getCategory_id());
        contactUsWrapper.setOnClickListener(view -> startActivity(new Intent(DetailPolisMobilActivity.this, DetailPolisContactUs.class)));
        productDetailWrapper.setOnClickListener(view -> {
            Intent intent = new Intent(DetailPolisMobilActivity.this,DetailProductPolisActivity.class);
            Log.d("detail polis product","id: "+detailModel.getId());
            Log.d("detail polis product","no polis: "+detailModel.getNo_polis());
            intent.putExtra(DetailProductPolisActivity.idTransaksiExtra,""+detailModel.getId());
            intent.putExtra(DetailProductPolisActivity.polisNumberExtra,""+detailModel.getOrder_code());
            intent.putExtra(DetailProductPolisActivity.productExtra,detailModel.getProductDetail().toProductDetail());
            startActivity(intent);
        });
    }

    private String getDuration(String duration){
        String[] durationSplit = duration.split(" - ");
        try{
            String stringStart = durationSplit[0];
            String stringEnd = durationSplit[1];
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
            Date dateStart = formatter.parse(stringStart);
            Date dateEnd = formatter.parse(stringEnd);
            long difference_In_Time
                    = dateEnd.getTime() - dateStart.getTime();

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;
            if(difference_In_Years<1){
                return difference_In_Days+" Hari";
            }

            return difference_In_Years+" Tahun";
        }catch(Exception e){
            Log.d("get duration","error "+e.getMessage());
            return duration;
        }
    }

    private void setupUiByCategory(String catId) {
        // mobil
        //Log.d("cat_id","cat id = "+catId);
        //Log.d("cat_id","cat id = "+insuredDetail.getItems_details().toString());
        if (catId.equals("5")) {
            //mobil
            tvDataMobil.setVisibility(View.VISIBLE);
            tvDataMobil.setText("Data Mobil");
            itemDetailsWrapper.setVisibility(View.VISIBLE);
            idNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            tvDetailBrand.setText(insuredDetail.getItems_details().getItem_brand());
            tvDetailSeries.setText(insuredDetail.getItems_details().getItem_series());
            tvDetailYear.setText(insuredDetail.getItems_details().getItem_year());
        } else if (catId.equals("1")) {
            //motor
            tvDataMobil.setVisibility(View.VISIBLE);
            tvDataMobil.setText("Data Motor");
            itemDetailsWrapper.setVisibility(View.VISIBLE);
            tvDetailBrand.setText(insuredDetail.getItems_details().getItem_brand());
            tvDetailSeries.setText(insuredDetail.getItems_details().getItem_series());
            tvDetailYear.setText(insuredDetail.getItems_details().getItem_year());
        } else if (catId.equals("9")){
            tvDataMobil.setVisibility(View.VISIBLE);
            tvDataMobil.setText("Data Property");
            itemDetailsWrapper.setVisibility(View.VISIBLE);
            tvDetailYear.setVisibility(View.GONE);
            tvDetailBrand.setText(insuredDetail.getItems_details().getItem_type());
            tvDetailSeries.setText(insuredDetail.getItems_details().getItem_address());
        }
    }

    private void goToAffiliation(String type){
        Intent afiliasi = new Intent(DetailPolisMobilActivity.this, AffiliationActivity.class);
        afiliasi.putExtra("type",type);
        startActivity(afiliasi);
    }

    private String getStatus(String status) {
        if (status.equals("active")) {
            return "Aktif";
        } else {
            return "Kadaluarsa";
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (insuredDetail.getListMenu().get(position).getId()) {
            case "5": {
                Intent intent = new Intent(this, ContactInsurancePartnerActivity.class);
                intent.putExtra("contact_partner", insuredDetail.getContactPartner().get(0));
                startActivity(intent);
                break;
            }
            case "1": {
                Log.d("claim link ", "url : " + insuredDetail.getListMenu().get(position).getUrl());
                Intent intent = new Intent(this, DetailClaimActivity.class);
                intent.putExtra("url", insuredDetail.getListMenu().get(position).getUrl());
                startActivity(intent);
                break;
            }
            case "3":
                Log.d("claim link ", "url : " + insuredDetail.getListMenu().get(position).getUrl());
                goToAffiliation("Rumah Sakit");
                break;
            case "4":
                Log.d("claim link ", "url : " + insuredDetail.getListMenu().get(position).getUrl());
                goToAffiliation("Bengkel");
                break;
        }
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }
}
