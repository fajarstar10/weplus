package id.weplus.transaksi;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.Cicilan;

import static id.weplus.utility.TextHelper.currencyFormatter;

public class CicilanAdapter extends RecyclerView.Adapter<CicilanAdapter.CicilanViewHolder>  {
    private Activity activity;
    private ArrayList<Cicilan> cicilanList;

    public CicilanAdapter(Activity activity,ArrayList<Cicilan> cicilan){
        Log.d("size ","cicilan size : "+cicilan.size());
        this.activity = activity;
        this.cicilanList =cicilan;
    }
    @NonNull
    @Override
    public CicilanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.viewadapter_cicilan, viewGroup, false);
        return new CicilanAdapter.CicilanViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CicilanViewHolder holder, int i) {
        Cicilan cicilan = cicilanList.get(i);
        holder.tvHeader.setText(cicilan.getName());
        holder.tvAmount.setText("Rp."+currencyFormatter(cicilan.getNominal()));
        holder.tvDueDate.setText(cicilan.getJatuhTempo());
        holder.tvStatus.setText(cicilan.getStatus());
        holder.tvTransactionId.setText(cicilan.getTransactionId());
    }

    @Override
    public int getItemCount() {
        return cicilanList.size();
    }

    public class CicilanViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pembayaran_total_label) TextView tvHeader;
        @BindView(R.id.txt_nominal_id_transaksi_cicilan) TextView tvTransactionId;
        @BindView(R.id.tgl_jatuh_tempo) TextView tvDueDate;
        @BindView(R.id.txt_total_tagihan) TextView tvAmount;
        @BindView(R.id.txt_status_pembayaran) TextView tvStatus;


        public CicilanViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
