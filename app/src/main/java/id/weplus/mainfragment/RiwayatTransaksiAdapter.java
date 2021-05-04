package id.weplus.mainfragment;

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

class RiwayatTransaksiAdapter extends RecyclerView.Adapter<RiwayatTransaksiAdapter.TransaksiViewHolder> {
    private Activity activity;
    private RiwayatTransaksiAdapter.RiwayatTransaksiOnItemClicked listener;
    private String[] status;

    public RiwayatTransaksiAdapter(Activity activity, String[] status) {
        this.activity = activity;
        this.status = status;
    }

    public void setRiwayatTransaksiLstener(RiwayatTransaksiAdapter.RiwayatTransaksiOnItemClicked listener) {
        this.listener = listener;
    }

    @Override
    public RiwayatTransaksiAdapter.TransaksiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.viewadapter_riwayat_transaksi, viewGroup, false);
        return new RiwayatTransaksiAdapter.TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RiwayatTransaksiAdapter.TransaksiViewHolder holder, final int i) {

        if (status[i].equals("Berhasil")) {
            holder.transactionStatus.setBackgroundColor(activity.getResources().getColor(R.color.green));
        } else if (status[i].equals("Gagal")) {
            holder.transactionStatus.setBackgroundColor(activity.getResources().getColor(R.color.red));
        }
        holder.transactionStatus.setText(status[i]);
        holder.viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItem(i, status[i]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public interface RiwayatTransaksiOnItemClicked {
        void onItem(int pos, String tag);
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.riwayattransaksi_id_transaksi) TextView idTransaksi;
        @BindView(R.id.riwayattransaksi_totalpembayaran) TextView totalPembayaran;
        @BindView(R.id.riwayattransaksi_img) ImageView img;
        @BindView(R.id.riwayattransaksi_name) TextView name;
        @BindView(R.id.riwayattransaksi_metode_pembayaran) TextView paymentType;
        @BindView(R.id.riwayattransaksi_detail) ImageView viewDetail;
        @BindView(R.id.riwayattransaksi_status) TextView transactionStatus;
        @BindView(R.id.riwayattransaksi_layout_item) CardView layoutItem;

        public TransaksiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}