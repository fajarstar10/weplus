package id.weplus.mainfragment;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import id.weplus.belipolis.criticalIll.AsuransiCriticalActivity;
import id.weplus.belipolis.kesehatan.KesehatanActivity;
import id.weplus.belipolis.life.AsuransiLifeActivity;
import id.weplus.belipolis.mobil.FormDataMobilActivity;
import id.weplus.belipolis.motor.AsuransiMotorActivity;
import id.weplus.belipolis.perjalanan.AsuransiPerjalananActivity;
import id.weplus.belipolis.productlist.ProductListActivity;
import id.weplus.model.BuyPolisModel;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;

public class ProdukBaru extends RecyclerView.Adapter<ProdukBaru.ProdukBaruViewHolder> {
    private FragmentActivity activity;
    private List<ResponseBeranda.BerandaData.NewProduct> productList;

    public ProdukBaru(FragmentActivity activity, List<ResponseBeranda.BerandaData.NewProduct> list ){
        this.activity = activity;
        this.productList = list;
    }

    @Override
    public ProdukBaru.ProdukBaruViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_produkbaru, viewGroup,false);
        return new ProdukBaru.ProdukBaruViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ProdukBaru.ProdukBaruViewHolder holder, int i) {
//        holder.imageView.setImageResource(iconImage[i]);
        ResponseBeranda.BerandaData.NewProduct np = productList.get(i);
        Glide.with(activity)
                .load(productList.get(i).getImage())
                .placeholder(R.drawable.ic_image_product_terbaru)
                .error(R.drawable.ic_image_product_terbaru)
                .into(holder.imageView);
        int pos = np.getCategory_id();
        holder.wrapper.setOnClickListener(view -> {
            FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_BANNER_NEW_PRODUCT);

            switch (pos){
                case 1:
                    Intent motor = new Intent(activity.getApplicationContext(), AsuransiMotorActivity.class);
                    motor.putExtra("cat_id",pos);
                    activity.startActivity(motor);
                    break;
                case 5:
                    Intent mobil = new Intent(activity.getApplicationContext(), FormDataMobilActivity.class);
                    mobil.putExtra("cat_id",pos);
                    activity.startActivity(mobil);
                    break;
                case 7:
                    Intent travel = new Intent(activity.getApplicationContext(), AsuransiPerjalananActivity.class);
                    travel.putExtra("cat_id",pos);
                    activity.startActivity(travel);
                    break;
                case 2:
                    Intent kesehatan = new Intent(activity.getApplicationContext(), KesehatanActivity.class);
                    kesehatan.putExtra("cat_id",pos);
                    activity.startActivity(kesehatan);
                    break;
                case 4:
                    Intent life = new Intent(activity.getApplicationContext(), AsuransiLifeActivity.class);
                    life.putExtra("cat_id",pos);
                   activity.startActivity(life);
                    break;
                case 12:
                    Intent critical = new Intent(activity.getApplicationContext(), AsuransiCriticalActivity.class);
                    critical.putExtra("cat_id",pos);
                    activity.startActivity(critical);
                    break;
                default:goToProductList(pos,true);break;
            }

        });

    }

    private void goToProductList(int id, boolean filterEnabled){
        FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_LIST_PRODUCT);

        Intent productIntent = new Intent(activity, ProductListActivity.class);
        productIntent.putExtra("cat_id",id);
        productIntent.putExtra("filterEnabled",filterEnabled);
        //activity.startActivity(productIntent);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProdukBaruViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgprodukbaru)  ImageView imageView;
        @BindView(R.id.cardWrapper)
        CardView wrapper;
        public ProdukBaruViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

