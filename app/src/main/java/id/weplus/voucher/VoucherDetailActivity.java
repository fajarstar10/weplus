package id.weplus.voucher;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.perjalanan.BeliPolisPerjalananActivity;
import id.weplus.model.Voucher;
import id.weplus.model.VoucherDetailModel;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherDetailActivity extends BaseActivity {
    @BindView(R.id.btn_voucher_detail_btn_copy) ImageView btnCopy;
    @BindView(R.id.voucher_kode_voucher) TextView kodeVoucher;
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.voucherdetail_description) TextView voucherDetailDesc;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.voucherdetail_image_icon) ImageView img;
    @BindView(R.id.view_pager_voucher) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.progressBar) CircleProgressBar progressBar;

    private Voucher voucher;
    private VoucherDetailModel.VoucherDetailData voucherDetail;
    private String TAG="voucherDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_detail);
        ButterKnife.bind(this);
        getVoucherData();
    }

    private void getVoucherData(){
        voucher = getIntent().getParcelableExtra("voucher");
        if(voucher!=null){
            fetchVoucherDetail();
        }
    }

    private void fetchVoucherDetail(){
        boolean isNetworkAvailable = Util.isNetworkAvailable(getApplicationContext());
        if (isNetworkAvailable) {
            progressBar.setVisibility(View.VISIBLE);
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager.getNetworkServiceWithHeader(this, token).getVoucherDetail(voucher.getId());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Gson gson = new Gson();
                    VoucherDetailModel voucherListModel = gson.fromJson(response.body(), VoucherDetailModel.class);
                    try {
                        if (voucherListModel.getCode().equals(ErrorCode.ERROR_00)) {
                           voucherDetail=voucherListModel.getData();
                           populateView();
                        }else if(voucherListModel.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(VoucherDetailActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            showError(voucherListModel.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    showError("Time Out");
                    Log.i(TAG, "On FAILUR : " + t.getMessage());

                }
            });
        } else {
            showError(getString(R.string.network_error));
        }

    }

    private static final String HTML_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    private Pattern pattern = Pattern.compile(HTML_PATTERN);

    public boolean hasHTMLTags(String text){
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    @SuppressLint("SetTextI18n")
    private void populateView(){
        if(voucherDetail!=null){
            //kodeVoucher.setText(voucherDetail.getCode());
            kodeVoucher.setText(""+voucherDetail.getId());
            title.setText(voucherDetail.getTitle());
            description.setVisibility(View.GONE);
            if(hasHTMLTags(voucherDetail.getDesc())) {
                voucherDetailDesc.setText(Html.fromHtml(voucherDetail.getDesc()));
            }else{
                voucherDetailDesc.setText(voucherDetail.getDesc());
            }
            btnCopy.setOnClickListener(view -> {
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", voucherDetail.getCode());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(VoucherDetailActivity.this,"Kode telah di salin", Toast.LENGTH_LONG).show();
            });
            Glide.with(this)
                    .load(voucherDetail.getImage_url())
                    .into(img);
            VoucherDetailPagerAdapter adapter= new VoucherDetailPagerAdapter(getSupportFragmentManager(),voucherDetail.getTnc(),voucherDetail.getUsage());
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    public void btnGunakanPromo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.view_logout, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView dialogTitle = customLayout.findViewById(R.id.dialog_title);
        dialogTitle.setText(getString(R.string.kodevoucheranda) + voucherDetail.getCode() + " voucher akan hilang setelah digunakan");

        TextView btnBatal = customLayout.findViewById(R.id.dialog_btn_batal);
        TextView btnYa = customLayout.findViewById(R.id.dialog_btn_ya);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_beli_asuransi)
    public void actionVoucherBeliAsuransi(){
        btnGunakanPromo();
    }

    @OnClick(R.id.viewback_buttonback)
    public void actionBackVoucherDetail(){finish();}
}
