package id.weplus.belipolis.criticalIll;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.belipolis.FormBeliPolisNew;
import id.weplus.belipolis.productdetail.ProductDetailActivity;
import id.weplus.model.Product;
import id.weplus.model.request.CriticalllnessProductListRequest;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.Util;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class CriticalProductListAdapter extends RecyclerView.Adapter<CriticalProductListAdapter.ProductViewHolder>
        implements Filterable {
    private Activity activity;
    private int catId;
    private CriticalllnessProductListRequest request;
    private int[] iconImage = {R.drawable.aca_insurance};
    private String[] nameIcon ;
    private String[] coverIcon ;
    private String[] tipeProdukIcon ;
    private String[] deskripsiIcon ;
    private String[] amountIcon ;
    private List<Product> products;
    private List<Product> originalProduct;
    private CriticalProductListAdapter.ItemFilter mItemFilter = new CriticalProductListAdapter.ItemFilter();


    public CriticalProductListAdapter(Activity activity, List<Product> productList, int catId, CriticalllnessProductListRequest requestBody) {
        this.activity = activity;
        this.iconImage = iconImage;
        this.nameIcon = nameIcon;
        this.coverIcon = coverIcon;
        this.tipeProdukIcon = tipeProdukIcon;
        this.deskripsiIcon = deskripsiIcon;
        this.amountIcon = amountIcon;
        this.products = productList;
        this.catId=catId;
        this.originalProduct=productList;
        this.request=requestBody;
    }

    @Override
    public CriticalProductListAdapter.ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_produk_terbaru, viewGroup, false);
        return new CriticalProductListAdapter.ProductViewHolder(childView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CriticalProductListAdapter.ProductViewHolder holder, int i) {
        Product prod = products.get(i);
        holder.name.setText(prod.getName());
        holder.partner.setText(prod.getPartner_name());
        holder.deskripsi.setText(HtmlCompat.fromHtml(Util.arrayListToString(prod.getDesc()),HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.price.setText("Rp."+currencyFormatter(""+prod.getPrice()));
        holder.amount.setText("Rp."+currencyFormatter(prod.getSum_insured()));

        Glide.with(activity)
                .load(prod.getImage())
                .placeholder(R.drawable.aca_insurance)
                .error(R.drawable.aca_insurance)
                .centerCrop()
                .into(holder.icon);

        holder.btnDetail.setOnClickListener(v -> {
            Intent intent  = new Intent(activity, ProductDetailActivity.class);
            intent.putExtra("product_detail",prod);
            intent.putExtra("cat_id",catId);
            activity.startActivity(intent);
        });
        Log.d("cat_id","catid = "+catId);

        holder.btnPilih.setOnClickListener(view -> {
            FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_SELECT_PRODUCT);

            Intent intent = new Intent(activity, FormBeliPolisNew.class);
                intent.putExtra("product_detail", prod);
                intent.putExtra("cat_id", catId);
                intent.putExtra("request_body",request);
                activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setItems(ArrayList<Product> prod){
        this.products=prod;
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Product> prods){
        this.products.addAll(prods);
        notifyDataSetChanged();
    }

    public void filterByPartner(String id){
        this.getFilter().filter(id);
    }

    public void sort(String id) {
        for(int i=0;i<products.size();i++){
            Product prod = products.get(i);
            if(prod.getPartner_id().equals(id)){
                products.add(0,prod);
                products.remove(i+1);
            }
        }
        notifyDataSetChanged();
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Product> list = originalProduct;

            int count = list.size();
            final ArrayList<Product> nlist = new ArrayList<Product>(count);

            Product filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getPartner_id().equals(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            products = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }

    }


    @Override
    public Filter getFilter() {
        return mItemFilter;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.produkterbaru_icon) ImageView icon;
        @BindView(R.id.produkterbaru_name) TextView name;
        @BindView(R.id.produkterbaru_partner) TextView partner;
        @BindView(R.id.produkterbaru_deskripsi) TextView deskripsi;
        @BindView(R.id.produkterbaru_amount) TextView amount;
        @BindView(R.id.hargaValue) TextView price;
        @BindView(R.id.produkterbaru_btn_detail) Button btnDetail;
        @BindView(R.id.buttonPilih) Button btnPilih;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


