package id.weplus.detailpolis;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class DetailProdukPolisAdapter  extends RecyclerView.Adapter<DetailProdukPolisAdapter.DetailprodukPolisViewHolder> {
    private Activity activity;
    private String polisType="Aktif";

    public DetailProdukPolisAdapter(Activity activity,String polisType) {
        this.activity = activity;
        this.polisType=polisType;
    }

    @NonNull
    @Override
    public DetailprodukPolisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_detail_polis, viewGroup, false);
        return new DetailprodukPolisViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailprodukPolisViewHolder holder, int i) {
        holder.title.setText("Axa Care Protection");
        holder.desc.setText("Kamis, 7 februari 2010 - Jumat, 7 Februari 2020");
        holder.status.setText(polisType);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class DetailprodukPolisViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detailpolis_img_icon) ImageView imgIcon;
        @BindView(R.id.detailpolis_title) TextView title;
        @BindView(R.id.detailpolis_desc) TextView desc;
        @BindView(R.id.detailpolis_status) TextView status;
        public DetailprodukPolisViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
