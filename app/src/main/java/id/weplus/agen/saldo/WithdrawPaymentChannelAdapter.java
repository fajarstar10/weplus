package id.weplus.agen.saldo;


import android.app.Activity;
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
import id.weplus.model.response.agent.saldo.PaymentChannel;
import id.weplus.pembayaran.OnPaymentClicked;

public class WithdrawPaymentChannelAdapter extends RecyclerView.Adapter<WithdrawPaymentChannelAdapter.PembayaranViewHolder> {
    private Activity activity;
    private int index = -1;
    private ArrayList<PaymentChannel> payments = new ArrayList<>();
    private OnPaymentClicked onPaymentClicked;

    public WithdrawPaymentChannelAdapter(Activity activity,OnPaymentClicked onPaymentClicked) {
        this.activity = activity;
        this.onPaymentClicked=onPaymentClicked;
    }

    @Override
    public WithdrawPaymentChannelAdapter.PembayaranViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_pembayaran,viewGroup,false);
        return new WithdrawPaymentChannelAdapter.PembayaranViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(PembayaranViewHolder vh, int i) {
        PaymentChannel payment = payments.get(i);
//        Glide.with(this.activity).load(payment.getImage()).into(vh.iconImage);
//        vh.itemName.setText(payment.getName());
//
//        vh.itemView.setOnClickListener(v -> {
//            index = i;
//            onPaymentClicked.onPaymentClicked(payment);
//            notifyDataSetChanged();
//        });
//
//        if (index == i){
//            vh.itemView.setBackgroundColor(activity.getResources().getColor(R.color.red));
//            vh.itemName.setTextColor(activity.getResources().getColor(R.color.white));
//        } else {
//            vh.itemView.setBackgroundColor(activity.getResources().getColor(R.color.white));
//            vh.itemName.setTextColor(activity.getResources().getColor(R.color.grey_medium));
//        }
    }

    public int getSelectedIndex(){
        return index;
    }

    public String getSelectedParamValue(){
        String s="";
        if(index==-1){
            //s=payments.get(index).getParamValue();
        }
        return s;
    }

    public void setPaymentMethod(ArrayList<Payment> paymentList){
        //this.payments=paymentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class PembayaranViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView iconImage;
        @BindView(R.id.item_name)
        TextView itemName;

        public PembayaranViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

