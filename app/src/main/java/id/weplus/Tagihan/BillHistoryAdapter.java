package id.weplus.Tagihan;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.BillTransaction;
import id.weplus.model.response.BillInquiryData;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;

public class BillHistoryAdapter extends RecyclerView.Adapter<BillHistoryAdapter.HistoryViewHolder> {
    private Activity activity;
    private ArrayList<BillTransaction> transactions = new ArrayList<>();
    private OnHistoryClick clickListener;

    public BillHistoryAdapter(Activity activity,OnHistoryClick clickListener) {
        this.activity = activity;
        this.clickListener = clickListener;
    }

    @Override
    public BillHistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_riwayat_adapter, viewGroup, false);
        return new BillHistoryAdapter.HistoryViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHistoryAdapter.HistoryViewHolder holder, int i) {
        BillTransaction transaction = transactions.get(i);
        holder.tvIdTransaction.setText("" + transaction.getOrder_code());
        holder.tvTransactionType.setText(transaction.getProduct_name());
        holder.tvTransactionCustId.setText(transaction.getCustomer_id());
        holder.tvTransactionPrice.setText("" + transaction.getTotal());
        holder.tvPaymentMethod.setText(transaction.getPayment_channel());

        String status = "";
        if(transaction.getStatus().toLowerCase().equals("cancel")){
            status = "Batal";
        }else if(transaction.getStatus().toLowerCase().equals("done")){
            status = "Berhasil";
        }else{
            status = transaction.getStatus();
        }
        holder.tvTransactionStatus.setText(status);
        Glide.with(activity)
                .load(transaction.getImage())
                .apply(new RequestOptions().circleCrop())
                .into(holder.imgTransactionIcon);
        holder.wrapper.setOnClickListener(view -> {
            FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_DETAIL_TRANSACTION_HISTORY_BILLS);

            Intent intent = new Intent(activity,DetailTagihanActivity.class);
            intent.putExtra("transaction",transaction);
            activity.startActivity(intent);
        });

        holder.buttonBuyAgain.setOnClickListener(view -> {
            clickListener.setOnClickListener(transaction);
        });

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void addItems(ArrayList<BillTransaction> billCategoryArrayList) {
        transactions.addAll(billCategoryArrayList);
        notifyDataSetChanged();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.voucher_image_icon) ImageView img;
        @BindView(R.id.tagihan_listrik_id_transaksi)
        TextView tvIdTransaction;
        @BindView(R.id.riwayattransaksi_status)
        TextView tvTransactionStatus;
        @BindView(R.id.pembayaran_metode)
        TextView tvPaymentMethod;
        @BindView(R.id.txt_token_listrik)
        TextView tvTransactionType;
        @BindView(R.id.no_token_listrik_transaksi)
        TextView tvTransactionCustId;
        @BindView(R.id.nominal_harga_token_listrik)
        TextView tvTransactionPrice;
        @BindView(R.id.transaksi)
        ImageView imgTransactionIcon;
        @BindView(R.id.transaksi_layout_item)
        LinearLayout wrapper;
        @BindView(R.id.transaksi_riwayat_btn_belilagi)
        Button buttonBuyAgain;

        //        @BindView(R.id.btn_voucher_detail) AppCompatButton btnVoucherDetail;
        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}