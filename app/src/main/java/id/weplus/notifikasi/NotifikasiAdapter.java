package id.weplus.notifikasi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.NotifikasiViewHolder> {
    private Activity activity;

    public   NotifikasiAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public NotifikasiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_notifikasi, viewGroup, false);
        return new NotifikasiViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(NotifikasiViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class NotifikasiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notifikasi_title) TextView title;
        @BindView(R.id.notifikasi_description) TextView description;
        public NotifikasiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
