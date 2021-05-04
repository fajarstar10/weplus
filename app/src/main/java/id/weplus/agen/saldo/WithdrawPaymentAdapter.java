package id.weplus.agen.saldo;


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
import id.weplus.pembayaran.OnPaymentClicked;

public class WithdrawPaymentAdapter extends RecyclerView.Adapter<WithdrawPaymentAdapter.WithdrawViewHolder> {
    private Activity activity;
    private int index = -1;
    private ArrayList<Payment> payments = new ArrayList<>();
    private OnPaymentClicked onPaymentClicked;

    public WithdrawPaymentAdapter(Activity activity,OnPaymentClicked onPaymentClicked) {
        this.activity = activity;
        this.onPaymentClicked=onPaymentClicked;
    }

    @Override
    public WithdrawPaymentAdapter.WithdrawViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_pembayaran,viewGroup,false);
        return new WithdrawPaymentAdapter.WithdrawViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(WithdrawPaymentAdapter.WithdrawViewHolder vh, int i) {
        Payment payment = payments.get(i);
        //Log.d("image","iamge : "+payment.getImage());
        Glide.with(this.activity).load(payment.getImage()).into(vh.iconImage);
        vh.itemName.setText(payment.getName());

        vh.itemView.setOnClickListener(v -> {
            index = i;
            onPaymentClicked.onPaymentClicked(payment);
            notifyDataSetChanged();
        });

        if (index == i){
            vh.itemView.setBackgroundResource(R.drawable.border_fill_red);
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

    public class WithdrawViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image) ImageView iconImage;
        @BindView(R.id.item_name) TextView itemName;

        public WithdrawViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

