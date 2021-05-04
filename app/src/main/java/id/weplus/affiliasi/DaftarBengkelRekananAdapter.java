package id.weplus.affiliasi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class DaftarBengkelRekananAdapter extends RecyclerView.Adapter<DaftarBengkelRekananAdapter.DaftarbengkelRekananViewHolder> {
    private Activity activity;

    public DaftarBengkelRekananAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public DaftarbengkelRekananViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_bengkel_rekanan, viewGroup, false);
        return new DaftarbengkelRekananViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(DaftarbengkelRekananViewHolder holder, int i) {

        holder.btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+Uri.encode("021".trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(callIntent);

        });


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class DaftarbengkelRekananViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapterbengkelrekanan_icon) ImageView icon;
        @BindView(R.id.adapterbengkelrekanan_title) TextView title;
        @BindView(R.id.adapterbengkelrekanan_address) TextView address;
        @BindView(R.id.adapterbengkelrekanan_telp) TextView telp;
        @BindView(R.id.adapterbengkelrekanan_btn_call) AppCompatButton btnCall;


        public DaftarbengkelRekananViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
