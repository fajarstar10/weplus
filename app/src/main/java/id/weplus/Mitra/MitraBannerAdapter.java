package id.weplus.Mitra;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.PartnerBanner;

public class MitraBannerAdapter extends RecyclerView.Adapter<MitraBannerAdapter.MitraBannerAdapterViewHolder> {
    private Activity activity;
    private ArrayList<PartnerBanner> bannerList = new ArrayList<>();

    public MitraBannerAdapter(Activity activity){
        this.activity = activity;
    }

    public void addItems(ArrayList<PartnerBanner> bannerList){
        this.bannerList.addAll(bannerList);
        notifyDataSetChanged();
    }

    @Override
    public MitraBannerAdapter.MitraBannerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_partner_banner, viewGroup,false);
        return new MitraBannerAdapter.MitraBannerAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MitraBannerAdapter.MitraBannerAdapterViewHolder holder, int i) {
        PartnerBanner banner = bannerList.get(i);
        Glide.with(activity)
                .load(banner.getImage())
                .placeholder(R.drawable.alfamart)
                .error(R.drawable.alfamart)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
        });


    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public class MitraBannerAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img) ImageView imageView;
        public MitraBannerAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
