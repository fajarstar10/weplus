package id.weplus.kontakklaim;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class DaftarKontakKlaimAdapter extends RecyclerView.Adapter<DaftarKontakKlaimAdapter.DaftarKontakKlaimViewHolder> {
    private Activity activity;

    public DaftarKontakKlaimAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public DaftarKontakKlaimViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_daftar_kontak_klaim, viewGroup, false);
        return new DaftarKontakKlaimViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(DaftarKontakKlaimViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class DaftarKontakKlaimViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.daftarkontakklaim_icon) ImageView icon;
        @BindView(R.id.daftarkontakklaim_name) TextView name;
        @BindView(R.id.daftarkontakklaim_email) TextView email;
        @BindView(R.id.daftarkontakklaim_address) TextView address;
        @BindView(R.id.daftarkontakklaim_phone) TextView phone;

        public DaftarKontakKlaimViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
