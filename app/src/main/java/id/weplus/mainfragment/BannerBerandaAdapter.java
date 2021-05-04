package id.weplus.mainfragment;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.request.Banner;
import id.weplus.utility.RecyclerItemClickListener;

public class BannerBerandaAdapter extends RecyclerView.Adapter<BannerBerandaAdapter.BannerViewHolder>{
    private FragmentActivity activity;
    private List<Banner> bannerList;
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener;

    public BannerBerandaAdapter(FragmentActivity activity, List<Banner> list, RecyclerItemClickListener.OnItemClickListener onItemClickListener){
        this.activity = activity;
        this.bannerList = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_beranda_banner, viewGroup,false);
        return new BannerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(BannerBerandaAdapter.BannerViewHolder holder, int i) {
        Banner banner = bannerList.get(i);
        Glide.with(activity)
                .load(banner.getImage())
                .placeholder(R.drawable.ic_image_product_terbaru)
                .error(R.drawable.ic_image_product_terbaru)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(view -> onItemClickListener.onItemClick(holder.itemView,i));

    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgprodukbaru)
        ImageView imageView;
        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
