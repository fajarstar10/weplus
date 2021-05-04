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
import id.weplus.ResponseBeranda;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;

public class ProdukFavorit extends RecyclerView.Adapter<ProdukFavorit.ProdukFavoritViewHolder> {
    private FragmentActivity activity;
    private List<ResponseBeranda.BerandaData.ProductFavorite> favoriteList;


    // todo ini namanya class constructor
    // biar dapat parsing data dari respon
    public ProdukFavorit(FragmentActivity activity, List<ResponseBeranda.BerandaData.ProductFavorite> list){
        this.activity = activity;
        this.favoriteList = list;
    }

    @Override
    public ProdukFavorit.ProdukFavoritViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_produkfavorit, viewGroup,false);
        return new ProdukFavorit.ProdukFavoritViewHolder(rootView);
    }



    @Override
    public void onBindViewHolder(ProdukFavorit.ProdukFavoritViewHolder holder, int i) {
        Glide.with(activity)
                .load(favoriteList.get(i).getImage())
                .into(holder.imageView);
        holder.imageView.setOnClickListener(view -> FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_BANNER_FAVORITE_PRODUCT));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class ProdukFavoritViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img) ImageView imageView;
        public ProdukFavoritViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
