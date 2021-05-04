package id.weplus.mainfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.Transaction;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class TransaksiSayaAdapter extends RecyclerView.Adapter<TransaksiSayaAdapter.TransaksiViewHolder> {
    private Activity activity;
    private TransaksiOnItemClicked listener;
    private ArrayList<Transaction> transactions = new ArrayList();

    public TransaksiSayaAdapter(Activity activity,TransaksiOnItemClicked onItemClicked) {
        this.activity = activity;
        listener=onItemClicked;

    }

    public void addItems(ArrayList<Transaction> data) {
        transactions.addAll(data);
        notifyDataSetChanged();
    }

    public interface TransaksiOnItemClicked{
        void onItem(Transaction transaction);
    }

    @Override
    public TransaksiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.viewadapter_transaksi_saya, viewGroup, false);
        return new TransaksiViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TransaksiViewHolder holder, final int i) {
        Transaction transaction = transactions.get(i);
        holder.idTransaksi.setText(""+transaction.getOrder_code());
        holder.expired.setText(transaction.getDate_end());
        holder.name.setText(transaction.getProduct_name());
        holder.amount.setText("Rp "+currencyFormatter(transaction.getTotal()));
        holder.paymentType.setText(transaction.getPayment_channel());
        holder.transactionStatus.setText(getStatus(transaction.getStatus()));

        Glide.with(activity)
                .load(transaction.getImg_url())
                .placeholder(R.drawable.aca_insurance)
                .error(R.drawable.aca_insurance)
                .into(holder.img);
        holder.layoutItem.setOnClickListener(view->{

            listener.onItem(transaction);
        });
    }

    private String getStatus(String s){
        if(s.toLowerCase().equals("waiting document from user")){
            return "Menunggu upload dokumen";
        }else if(s.toLowerCase().equals("waiting payment")){
            return "Menunggu pembayaran";
        }else if(s.toLowerCase().equals("done")){
            return "Sukses";
        }else if(s.toLowerCase().equals("cancel transaction")){
            return "Batal";
        }else{
            return s;
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.transaksi_id_transaksi) TextView idTransaksi;
        @BindView(R.id.transaksi_expired) TextView expired;
        @BindView(R.id.transaksi_img) ImageView img;
        @BindView(R.id.transaksi_name) TextView name;
        @BindView(R.id.transaksi_payment_type) TextView paymentType;
        @BindView(R.id.transaksisaya_amount) TextView amount;
        @BindView(R.id.transaksi_status) TextView transactionStatus;
        @BindView(R.id.transaksisaya_detail) ImageView viewDetailTransaksiSaya;
        @BindView(R.id.transaksi_layout_item) CardView layoutItem;
        public TransaksiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
