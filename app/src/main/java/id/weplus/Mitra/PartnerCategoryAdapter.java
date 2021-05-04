package id.weplus.Mitra;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.BuyPolisModel;


public class PartnerCategoryAdapter extends RecyclerView.Adapter<PartnerCategoryAdapter.PartnerCategoryViewHolder>   {
    private Activity activity;
    private OnPartnerCategoryClickListener clickListener;
    private ArrayList<BuyPolisModel.Categori> partnerlist = new ArrayList<>();

    public PartnerCategoryAdapter(Activity activity, OnPartnerCategoryClickListener clickListener ){
        this.activity = activity;
        this.clickListener = clickListener;
    }

    public void addItems(ArrayList<BuyPolisModel.Categori> partnerlist){
        this.partnerlist.addAll(partnerlist);
        notifyDataSetChanged();
    }
    @Override
    public PartnerCategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_kategori, viewGroup,false);
        return new PartnerCategoryViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(PartnerCategoryViewHolder holder, int i) {
        BuyPolisModel.Categori cat = partnerlist.get(i);
        holder.wrapper.setOnClickListener(view -> clickListener.onClickListener(cat));
        holder.title.setText(cat.getName());
        Glide.with(activity)
                .load(cat.getImage())
                .placeholder(R.drawable.alfamart)
                .error(R.drawable.alfamart)
                .apply(new RequestOptions().circleCrop())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return partnerlist.size();
    }

    static class PartnerCategoryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.adapterkategori_icon) ImageView imageView;
        @BindView(R.id.adapterkategori_name) TextView title;
        @BindView(R.id.adapterkategori_layout) RelativeLayout wrapper;
        PartnerCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
