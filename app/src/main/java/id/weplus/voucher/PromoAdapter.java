package id.weplus.voucher;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.PromoViewHolder> {
    private Activity activity;


    public PromoAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public PromoAdapter.PromoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_voucher_discount, viewGroup, false);
        return new PromoAdapter.PromoViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoAdapter.PromoViewHolder holder, int i) {
        holder.img.setImageResource(R.drawable.ic_promo_sample);
        holder.btnVoucherDetail.setText("Redeem");
        holder.btnVoucherDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PromoDetail.class);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class PromoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.voucher_image_icon)
        ImageView img;
        @BindView(R.id.voucher_tersedia_hingga)
        TextView tglTersediaHingga;
        @BindView(R.id.btn_voucher_detail)
        AppCompatButton btnVoucherDetail;
        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
