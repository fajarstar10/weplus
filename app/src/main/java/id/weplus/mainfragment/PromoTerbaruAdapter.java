//package id.weplus.mainfragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.support.annotation.BinderThread;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import id.weplus.R;
//import id.weplus.ResponseBeranda;
//import id.weplus.utility.ThreeTwo;
//
//public class PromoTerbaruAdapter extends RecyclerView.Adapter<PromoTerbaruAdapter.PromoTerbaruViewHolder> {
//    private Activity activity;
//    private List<ResponseBeranda.BerandaData.Promo> promoList;
//    private int[] iconImage = {R.drawable.ic_as_axa,R.drawable.ic_as_axa,R.drawable.ic_as_axa,R.drawable.ic_as_axa};
//
//    public PromoTerbaruAdapter(Activity activity, List<ResponseBeranda.BerandaData.Promo> promoList) {
//        this.activity = activity;
//        this.promoList = promoList;
//    }
//
//    @Override
//    public PromoTerbaruAdapter.PromoTerbaruViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_promoterbaru,viewGroup,false);
//        return new PromoTerbaruAdapter.PromoTerbaruViewHolder(rootView);
//    }
//
//    @Override
//    public void onBindViewHolder(PromoTerbaruAdapter.PromoTerbaruViewHolder holder, int i) {
////            holder.img.setImageResource(iconImage[i]);
//        holder.promoTitle.setText(promoList.get(i).getName());
//
//
//        Glide.with(activity)
//                .load(promoList.get(i).getImg_url())
//                .placeholder(R.drawable.ic_product_fav)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(holder.img);
//    }
//
//    @Override
//    public int getItemCount() {
//        return promoList.size();
//    }
//
//    public class PromoTerbaruViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.img) imageView img;
//        @BindView(R.id.promo_title) TextView promoTitle;
//        public PromoTerbaruViewHolder( View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
//}
