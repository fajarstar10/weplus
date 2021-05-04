package id.weplus.belipolis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.belipolis.perjalanan.FormAsuransiPerjalanan;

public class DetailProdukPolisActivity extends BaseActivity {
    @BindView(R.id.viewback_title) TextView title;
    @BindView(R.id.viewback_description) TextView description;
    @BindView(R.id.detailpolis_banner) ImageView icBanner;
    @BindView(R.id.detailpolis_title_produk) TextView productName;
    @BindView(R.id.detailpolis_price_produk) TextView price;
    @BindView(R.id.detailpolis_price_after_discount_produk) TextView priceAfterDiscount;
    @BindView(R.id.detailpolis_discount_produk) TextView discount;

    @BindView(R.id.detailpolis_title_alasan_produk) TextView productTitle;
    @BindView(R.id.deskripsi_produk) TextView productDescription;
    @BindView(R.id.btn_deskripsi_produk) ImageView descProductBtn;
    @BindView(R.id.title_informasi_umum) TextView informasiUmumTitle;
    @BindView(R.id.informasi_umum) TextView informasiUmumDesc;
    @BindView(R.id.btn_informasi_umum) ImageView informasiUmumBtn;
    @BindView(R.id.title_manfaat_produk) TextView manfaatProduckTitle;
    @BindView(R.id.manfaat_produk) TextView descManfaatProduct;
    @BindView(R.id.btn_manfaat_produk) ImageView manfaatProductbtn;

    boolean product = false;
    boolean informasi = false;
    boolean manfaat = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk_polis);
        ButterKnife.bind(this);
        title.setText(getString(R.string.detailproduk));
        description.setText(R.string.sampledesc);
    }

    @OnClick(R.id.btn_deskripsi_produk)
    public void onClickProductDescription(){

        if (product == false){
            descProductBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_expand_more_24px));
            productDescription.setVisibility(View.GONE);
            product = true;
        } else{
            descProductBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_expand_less_24px));
            productDescription.setVisibility(View.VISIBLE);
            product = false;
        }

    }

    @OnClick(R.id.btn_informasi_umum)
    public void onClickInformasiUmum(){
        if (informasi == false){
            informasiUmumBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_expand_more_24px));
            informasiUmumDesc.setVisibility(View.GONE);
            informasi = true;
        } else{
            informasiUmumBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_expand_less_24px));
            informasiUmumDesc.setVisibility(View.VISIBLE);
            informasi = false;
        }
    }

    @OnClick(R.id.btn_manfaat_produk)
    public void onClickManfaatproduct(){
        if (manfaat == false){
            manfaatProductbtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_expand_more_24px));
            descManfaatProduct.setVisibility(View.GONE);
            manfaat = true;
        } else{
            manfaatProductbtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_expand_less_24px));
            descManfaatProduct.setVisibility(View.VISIBLE);
            manfaat = false;
        }
    }

    @OnClick(R.id.viewback_buttonback)
    public void backBtn(){finish();}

    @OnClick(R.id.detailpolis_btn_ajukan)
    public void actionAjukanSekarang(){
        // label perjalanan
        Intent intent = new Intent(DetailProdukPolisActivity.this, FormAsuransiPerjalanan.class);
        startActivity(intent);
    }

    @OnClick(R.id.detailpolis_bookmark)
    public void actionBookmark(){
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
