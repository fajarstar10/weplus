package id.weplus.pembayaran;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.Payment;

public class PembayaranAdapter extends RecyclerView.Adapter<PembayaranAdapter.PembayaranViewHolder> {
    private Activity activity;
    private int index;
    private ArrayList<Payment> payments = new ArrayList<>();
    private OnPaymentClicked onPaymentClicked;

    public PembayaranAdapter(Activity activity,OnPaymentClicked onPaymentClicked) {
        this.activity = activity;
        this.onPaymentClicked=onPaymentClicked;
        index=-1;
    }

    @Override
    public PembayaranAdapter.PembayaranViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_pembayaran,viewGroup,false);
        return new PembayaranAdapter.PembayaranViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(PembayaranAdapter.PembayaranViewHolder vh, int i) {
        Payment payment = payments.get(i);
        Glide.with(this.activity).load(payment.getImage()).error(R.drawable.ic_bca).into(vh.iconImage);
        vh.itemName.setText(payment.getName());

        vh.itemView.setOnClickListener(v -> {
            index = i;
            onPaymentClicked.onPaymentClicked(payment);
            notifyDataSetChanged();
        });

        if (index == i){
            vh.itemView.setBackgroundResource(R.drawable.rounded_red_primary);
            vh.itemName.setTextColor(activity.getResources().getColor(R.color.white));
        } else {
            vh.itemView.setBackgroundResource(R.drawable.border_rounded_grey_medium);
            vh.itemName.setTextColor(activity.getResources().getColor(R.color.grey_medium));
        }
    }

    public int getSelectedIndex(){
        return index;
    }

    public String getSelectedParamValue(){
        if(index!=-1){
            return "";
        }
        return payments.get(index).getParamValue();
    }

    public void setPaymentMethod(ArrayList<Payment> paymentList){
        this.payments=paymentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class PembayaranViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image) ImageView iconImage;
        @BindView(R.id.item_name) TextView itemName;

        public PembayaranViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
