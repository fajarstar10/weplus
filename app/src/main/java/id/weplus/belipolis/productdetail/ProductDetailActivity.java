package id.weplus.belipolis.productdetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.BaseActivity;
import id.weplus.R;
import id.weplus.Tagihan.BayarTagihanListrikActivity;
import id.weplus.WelcomeActivity;
import id.weplus.belipolis.FormBeliPolisNew;
import id.weplus.belipolis.kesehatan.FormBuyHealth;
import id.weplus.belipolis.mudik.FormBeliMudik;
import id.weplus.belipolis.perjalanan.FormBuyTravel;
import id.weplus.belipolis.productlist.ProductListActivity;
import id.weplus.model.Product;
import id.weplus.model.ProductDetail;
import id.weplus.model.request.CarProductListRequest;
import id.weplus.model.request.GadgetProductRequest;
import id.weplus.model.request.HealthProductListRequest;
import id.weplus.model.request.LifeProductListRequest;
import id.weplus.model.request.MotorProductListRequest;
import id.weplus.model.request.TravelProductListRequest;
import id.weplus.model.response.ProductDetailResponse;
import id.weplus.net.ErrorCode;
import id.weplus.net.NetworkManager;
import id.weplus.net.WeplusSharedPreference;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class ProductDetailActivity extends BaseActivity {

    private ProductDetail productDetail;
    private Product product;
    private MotorProductListRequest motorRequestBody;
    private CarProductListRequest carRequestBody;
    private TravelProductListRequest travelRequestBody;
    private HealthProductListRequest healthRequestBody;
    private GadgetProductRequest gadgetRequestBody;
    private LifeProductListRequest lifeRequestBody;
    //private Mobil
    private String TAG = "productDetail";
    private int catId = 0;
    @BindView(R.id.expandableRv)
    RecyclerView rvExpandable;
    @BindView(R.id.viewback_title)
    TextView title;
    @BindView(R.id.viewback_description)
    TextView description;
    @BindView(R.id.viewback_buttonback)
    ImageView backButton;

    @BindView((R.id.imgAvatar))
    ImageView mProductImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvProtectionLabel)
    TextView tvAddress;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tvProductPrice)
    TextView tvProductPrice;
    @BindView(R.id.tvBiayaPremi)
    TextView tvBiayaPremi;
    @BindView(R.id.tvBiayaPremiPrice)
    TextView tvBiayaPremiPrice;
    @BindView(R.id.tvBiayaPremiPriceNormal)
    TextView tvBiayaPremiPriceNormal;
    @BindView(R.id.tvBenefitValue)
    TextView tvBenefit;
    @BindView(R.id.tvHargaValue)
    TextView tvHargaValue;
    @BindView(R.id.btn_beli)
    TextView btnBeli;
    @BindView(R.id.loader_bg)
    RelativeLayout loaderWrapper;

    private boolean isAgent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAnalyticsHelper.logEvent(this, Constant.ANALYTICS_DETAIL_PRODUCT);

        setContentView(R.layout.activity_prod_detail);
        ButterKnife.bind(this);
        initToolbar();
        getIntentValue();
        fetchProductDetail();
        setupButtonBeli();
    }

    @SuppressLint("SetTextI18n")
    private void initToolbar() {
        title.setText("Detail Produk");
        description.setText("Berikut detail produk asuransi yang anda pilih");
        backButton.setOnClickListener(view -> finish());
    }

    private void setupButtonBeli() {
        //todo redirect health to formBuyHealth instead of formBeliPolis
        btnBeli.setOnClickListener(view -> {
            if (catId == 6 || catId == 8 || catId == 3 || catId == 12 || catId == 4 || catId == 5 || catId == 1 || catId == 14 || catId == 15 || catId == 11 || catId == 13 || catId == 9) {
                if (product.getPartner_id().equals("15") && catId == 6) {
                    Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(this, FormBeliPolisNew.class);
                    intent.putExtra("product_detail", product);
                    intent.putExtra("cat_id", catId);
                    intent.putExtra("is_agent", isAgent);
                    if (catId == 1) {
                        intent.putExtra("request_body", motorRequestBody);
                    } else if (catId == 15) {
                        intent.putExtra("request_body", gadgetRequestBody);
                    } else if (catId == 4) {
                        intent.putExtra("request_body", lifeRequestBody);
                    } else {
                        intent.putExtra("request_body", carRequestBody);
                    }
                    startActivity(intent);
                }
            } else if (catId == 7) {
                Intent intent = new Intent(this, FormBuyTravel.class);
                intent.putExtra("product_detail", product);
                intent.putExtra("cat_id", catId);
                intent.putExtra("is_agent", isAgent);
                intent.putExtra("request_body", travelRequestBody);
                startActivity(intent);
            } else if (catId == 2) {
                Intent intent = new Intent(this, FormBuyHealth.class);
                intent.putExtra("product_detail", product);
                intent.putExtra("cat_id", catId);
                intent.putExtra("is_agent", isAgent);
                intent.putExtra("request_body", healthRequestBody);
                startActivity(intent);
            } else if (catId == 10) {
                Intent intent = new Intent(this, FormBeliMudik.class);
                intent.putExtra("product_detail", product);
                intent.putExtra("cat_id", catId);
                intent.putExtra("is_agent", isAgent);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void populateView() {
        Glide.with(this)
                .load(product.getImage())
                .placeholder(R.drawable.aca_insurance)
                .error(R.drawable.aca_insurance)
                .centerCrop()
                .into(mProductImage);

        tvProductName.setText(product.getName());
        tvProductPrice.setText("Rp." + currencyFormatter(product.getSum_insured()));
        tvHargaValue.setText("Rp." + currencyFormatter(product.getNominal()));

        if (Integer.parseInt(product.getDiscount()) > 0) {
            tvBiayaPremiPriceNormal.setVisibility(View.VISIBLE);
            tvBiayaPremiPriceNormal.setText("Rp" + currencyFormatter(product.getPrice()));
            tvBiayaPremiPrice.setText("Rp" + currencyFormatter(product.getNominal()));
        } else {
            tvBiayaPremiPrice.setText("Rp." + currencyFormatter("" + product.getNominal()));
        }

        tvName.setText(product.getPartner_name());
        tvBenefit.setText(Html.fromHtml(Util.arrayListToString(productDetail.getDesc())));
    }

    private void fetchProductDetail() {
        loaderWrapper.setVisibility(View.VISIBLE);
        boolean isNetworkAvailable = Util.isNetworkAvailable(this);
        if (isNetworkAvailable) {
            String token = WeplusSharedPreference.getToken(this);
            Call<String> call = NetworkManager
                    .getNetworkServiceWithHeader(this, token)
                    .getProductDetail(product.getId());

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loaderWrapper.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    ProductDetailResponse resp = gson.fromJson(response.body(), ProductDetailResponse.class);
                    Log.d(TAG, resp.getMessage());
                    try {
                        if (resp.getCode().equals(ErrorCode.ERROR_00)) {
                            productDetail = resp.getProductDetail();
                            setupExpandableRecyclerView();
                            populateView();
                        } else if(resp.getCode().equals(ErrorCode.ERROR_03)){
                            Intent intent = new Intent(ProductDetailActivity.this, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("")
                                    .setContentText(resp.getMessage())
                                    .show();
                        }
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loaderWrapper.setVisibility(View.GONE);
                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Time Out")
                            .show();

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

    private void getIntentValue() {
        product = (Product) getIntent().getParcelableExtra("product_detail");
        catId = getIntent().getIntExtra("cat_id", 0);
        isAgent = getIntent().getBooleanExtra("is_agent", false);
        if (getIntent().getParcelableExtra("request_body") != null) {
            if (catId == 1) {
                motorRequestBody = getIntent().getParcelableExtra("request_body");
            } else if (catId == 5) {
                carRequestBody = getIntent().getParcelableExtra("request_body");
            } else if (catId == 7) {
                travelRequestBody = getIntent().getParcelableExtra("request_body");
            } else if (catId == 2) {
                healthRequestBody = getIntent().getParcelableExtra("request_body");
            } else if (catId == 15) {
                gadgetRequestBody = getIntent().getParcelableExtra("request_body");
            } else if (catId == 4) {
                lifeRequestBody = getIntent().getParcelableExtra("request_body");
            }
        }
        Log.d("product detail", "id : " + product.getId());
    }

    private void setupExpandableRecyclerView() {
        rvExpandable.setLayoutManager(new LinearLayoutManager(this));
        rvExpandable.setAdapter(new SimpleAdapter(rvExpandable, productDetail));
    }

    private static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
        private static final int UNSELECTED = -1;

        private RecyclerView recyclerView;
        private ProductDetail product;
        private int selectedItem = UNSELECTED;
        private String[] title = {"Informasi Produk", "Syarat Ketentuan", "Manfaat"};

        public SimpleAdapter(RecyclerView recyclerView, ProductDetail productDetail) {
            this.recyclerView = recyclerView;
            this.product = productDetail;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.expandable_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind();
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
            private ExpandableLayout expandableLayout;
            private TextView expandButton;
            private TextView expandContent;

            public ViewHolder(View itemView) {
                super(itemView);

                expandableLayout = itemView.findViewById(R.id.expandable_layout);
                expandableLayout.setInterpolator(new OvershootInterpolator());
                expandableLayout.setOnExpansionUpdateListener(this);
                expandButton = itemView.findViewById(R.id.expand_button);
                expandContent = itemView.findViewById(R.id.tvContent);
                expandButton.setOnClickListener(this);
            }

            public void bind() {
                int position = getAdapterPosition();
                boolean isSelected = position == selectedItem;

                expandButton.setText(title[position]);
                expandButton.setSelected(isSelected);
                String content = getContent(position);
                expandContent.setText(Html.fromHtml(content));
                expandableLayout.setExpanded(isSelected, false);
            }

            private String getContent(int pos) {
                switch (pos) {
                    case 0:
                        return Util.arrayListToString(product.getGeneral());
                    case 1:
                        return Util.arrayListToString(product.getResume());
                    case 2:
                        return Util.arrayListToString(product.getBenefits());
                    default:
                        return product.getClaim();
                }
            }

            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
                if (holder != null) {
                    holder.expandButton.setSelected(false);
                    holder.expandableLayout.collapse();
                }

                int position = getAdapterPosition();
                if (position == selectedItem) {
                    selectedItem = UNSELECTED;
                } else {
                    expandButton.setSelected(true);
                    expandableLayout.expand();
                    selectedItem = position;
                }
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout", "State: " + state);
                if (state == ExpandableLayout.State.EXPANDING) {
                    recyclerView.smoothScrollToPosition(getAdapterPosition());
                }
            }
        }
    }
}
