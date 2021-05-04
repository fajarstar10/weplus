package id.weplus.Tagihan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.BillTransaction;


public class BillTransactionAdapter extends RecyclerView.Adapter<BillTransactionAdapter.TransaksiViewHolder> {
    private Activity activity;
    private ArrayList<BillTransaction> transactions = new ArrayList<>();
    private OnHistoryClick onHistoryClick;

    public BillTransactionAdapter(Activity activity,OnHistoryClick onHistoryClick) {
        this.activity = activity;
        this.onHistoryClick = onHistoryClick;
    }

    @Override
    public BillTransactionAdapter.TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_transaksi_adapter, viewGroup, false);
        return new TransaksiViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull BillTransactionAdapter.TransaksiViewHolder holder, int i) {
        BillTransaction transaction = transactions.get(i);
        holder.tvIdTransaction.setText(""+transaction.getOrder_code());
        holder.tvTransactionType.setText(transaction.getProduct_name());
        holder.tvTransactionCustId.setText(transaction.getCustomer_id());
        holder.tvTransactionPrice.setText(""+transaction.getTotal());
        holder.tvPaymentMethod.setText(transaction.getPayment_channel());
        holder.tvTransactionStatus.setText("Akan batal setelah "+transaction.getDate_end());
        Glide.with(activity)
                .load(transaction.getImage())
                .apply(new RequestOptions().circleCrop())
                .into(holder.imgTransactionIcon);
        holder.wrapper.setOnClickListener(view -> onHistoryClick.setOnClickListener(transaction));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void addItems(ArrayList<BillTransaction> billCategoryArrayList) {
        transactions.addAll(billCategoryArrayList);
        notifyDataSetChanged();
    }

     static class TransaksiViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.voucher_image_icon) ImageView img;
        @BindView(R.id.polis_saya_id_transaksi) TextView tvIdTransaction;
        @BindView(R.id.transaksi_status) TextView tvTransactionStatus;
        @BindView(R.id.pembayaran_metode) TextView tvPaymentMethod;
        @BindView(R.id.txt_token_listrik) TextView tvTransactionType;
        @BindView(R.id.no_token_listrik_transaksi) TextView tvTransactionCustId;
        @BindView(R.id.nominal_harga_token_listrik) TextView tvTransactionPrice;
        @BindView(R.id.transaksi) ImageView imgTransactionIcon;
        @BindView(R.id.transaksi_layout_item) CardView wrapper;

//        @BindView(R.id.btn_voucher_detail) AppCompatButton btnVoucherDetail;
        TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
