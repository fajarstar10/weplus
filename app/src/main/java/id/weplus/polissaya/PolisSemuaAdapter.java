package id.weplus.polissaya;

import android.app.Activity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class PolisSemuaAdapter extends RecyclerView.Adapter<PolisSemuaAdapter.TransaksiViewHolder> {
    private Activity activity;
    private PolisSemuaOnItemClicked listener;
    private String[] status;

    public PolisSemuaAdapter(Activity activity, String[] status) {
        this.activity = activity;
        this.status = status;
    }

    public void setListenerPolisSemua(PolisSemuaAdapter.PolisSemuaOnItemClicked listener) {
        this.listener = listener;
    }

    @Override
    public PolisSemuaAdapter.TransaksiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.viewadapter_polis_saya, viewGroup, false);
        return new PolisSemuaAdapter.TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PolisSemuaAdapter.TransaksiViewHolder holder, final int i) {
        final String statusV = status[i].toLowerCase();

        if (statusV.equals("Berhasil")) {
            holder.transactionStatus.setBackgroundColor(activity.getResources().getColor(R.color.green));
        } else if (statusV.equals("Gagal")) {
            holder.transactionStatus.setBackgroundColor(activity.getResources().getColor(R.color.red));
        }
        holder.transactionStatus.setText(status[i]);
        holder.lihatDetail.setOnClickListener(v -> listener.onItem(i, statusV));
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public interface PolisSemuaOnItemClicked {
        void onItem(int pos, String tag);
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.transaksi_id_transaksi)
        TextView idTransaksi;
        @BindView(R.id.transaksi_img)
        ImageView img;
        @BindView(R.id.transaksi_name)
        TextView name;
        @BindView(R.id.transaksi_payment_type)
        TextView paymentType;
        @BindView(R.id.transaksi_amount)
        TextView amount;
        @BindView(R.id.transaksi_status)
        TextView transactionStatus;
        @BindView(R.id.transaksi_layout_item)
        CardView layoutItem;
        @BindView(R.id.adapter_polis_saya_lihat_detail)
        TextView lihatDetail;
        @BindView(R.id.label_akanbatal) TextView labelAkanBatal;

        public TransaksiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            labelAkanBatal.setText("Total Pembayaran");
        }
    }
}

