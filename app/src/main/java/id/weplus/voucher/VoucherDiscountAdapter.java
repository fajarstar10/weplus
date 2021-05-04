package id.weplus.voucher;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.Voucher;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;

public class VoucherDiscountAdapter extends RecyclerView.Adapter<VoucherDiscountAdapter.VoucherDiscountViewHolder> {
    private FragmentActivity activity;
    private List<Voucher> voucherList=new ArrayList<>();


    public VoucherDiscountAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public VoucherDiscountAdapter.VoucherDiscountViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_voucher_discount, viewGroup, false);
        return new VoucherDiscountAdapter.VoucherDiscountViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherDiscountAdapter.VoucherDiscountViewHolder holder, int i) {
        Voucher voucher = voucherList.get(i);
        holder.tglTersediaHingga.setText(voucher.getExpired_date());

        holder.btnVoucherDetail.setOnClickListener(v -> {
            FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_DETAIL_VOUCHER);

            Intent voucherDetail = new Intent(activity.getApplicationContext(), VoucherDetailActivity.class);
            voucherDetail.putExtra("voucher",voucher);
            activity.startActivity(voucherDetail);
        });

        Glide.with(activity)
                .load(voucher.getImage_url())
                .into(holder.img);

    }

    public void addItems(List<Voucher> vouchers ){
        voucherList.addAll(vouchers);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public class VoucherDiscountViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.voucher_image_icon) ImageView img;
        @BindView(R.id.voucher_tersedia_hingga) TextView tglTersediaHingga;
        @BindView(R.id.btn_voucher_detail) AppCompatButton btnVoucherDetail;
        public VoucherDiscountViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
