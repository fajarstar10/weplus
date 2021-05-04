package id.weplus.belipolis;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.Product;

public class ProdukTerbaruAdapter extends RecyclerView.Adapter<ProdukTerbaruAdapter.ProdukTerbaruViewHolder> {
    private Activity activity;
    private int[] iconImage = {R.drawable.aca_insurance};
    private String[] nameIcon ;
    private String[] coverIcon ;
    private String[] tipeProdukIcon ;
    private String[] deskripsiIcon ;
    private String[] amountIcon ;
    private List<Product> produktravelList;


    public ProdukTerbaruAdapter(Activity activity, List<Product> produkterbaruList) {
        this.activity = activity;
        this.iconImage = iconImage;
        this.nameIcon = nameIcon;
        this.coverIcon = coverIcon;
        this.tipeProdukIcon = tipeProdukIcon;
        this.deskripsiIcon = deskripsiIcon;
        this.amountIcon = amountIcon;
        this.produktravelList= produkterbaruList;
    }

    @Override
    public ProdukTerbaruViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_produk_terbaru, viewGroup, false);
        return new ProdukTerbaruViewHolder(childView);
    }

    @Override
    public void onBindViewHolder( ProdukTerbaruViewHolder holder, int i) {
        holder.name.setText(produktravelList.get(i).getName());

        holder.amount.setText(produktravelList.get(i).getPrice());

        Glide.with(activity)
                .load(produktravelList.get(i).getImage())
                .placeholder(R.drawable.aca_insurance)
                .error(R.drawable.aca_insurance)
                .centerCrop()
                .into(holder.icon);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(activity, DetailProdukPolisActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ProdukTerbaruViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.produkterbaru_icon) ImageView icon;
        @BindView(R.id.produkterbaru_name) TextView name;;
        @BindView(R.id.produkterbaru_deskripsi) TextView deskripsi;
        @BindView(R.id.produkterbaru_amount) TextView amount;
        @BindView(R.id.produkterbaru_btn_detail) TextView btnDetail;
//        @BindView(R.id.produkterbaru) TextView btnDetail;

        public ProdukTerbaruViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
