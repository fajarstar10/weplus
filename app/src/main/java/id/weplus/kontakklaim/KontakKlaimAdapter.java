package id.weplus.kontakklaim;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class KontakKlaimAdapter extends RecyclerView.Adapter<KontakKlaimAdapter.KontakKlaimViewHolder> {
    private Activity activity;
    private onKontakKlaimClicked listener;

    public KontakKlaimAdapter(Activity activity) {
        this.activity = activity;
    }

    public interface onKontakKlaimClicked{
        void onItemClicked(int pos);
    }

    public void setKontakKlaimListener(onKontakKlaimClicked listener){
        this.listener = listener;
    }

    @Override
    public KontakKlaimViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_kontak_klaim,viewGroup, false);
        return new KontakKlaimAdapter.KontakKlaimViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(KontakKlaimViewHolder holder, final int i) {
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class KontakKlaimViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.kontakklaim_icon) ImageView icon;
        @BindView(R.id.kontakklaim_name) TextView name;
        @BindView(R.id.kontakklaim_layout) RelativeLayout relativeLayout;
        public KontakKlaimViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
