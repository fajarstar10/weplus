package id.weplus.profil;

import android.app.Activity;
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

public class ProfilAdapter extends RecyclerView.Adapter<ProfilAdapter.ProfilViewHolder> {
    private Activity activity;
    private int[] iconImage = { R.drawable.ic_baseline_notifications_24px,
            R.drawable.ic_tentang_kami, R.drawable.ic_syarat_ketentuan,
            R.drawable.ic_baseline_star_24px};
    private String[] title = {
            "Notifikasi",
            "Tentang Kami",
            "Syarat dan Ketentuan Layanan",
            "Rate Aplikasi WE+"};
    private String[] descriptions = {
            "Periksa notifikasi yang ada",
            "Informasi WE+",
            "Syarat dan ketentuan layanan",
            "Bantu rekomendasikan kami di AppStore"};
    private OnClickItemProfile listener;

    public ProfilAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListener(OnClickItemProfile listener) {
        this.listener = listener;
    }

    @Override
    public ProfilAdapter.ProfilViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_profil_list, viewGroup, false);
        return new ProfilAdapter.ProfilViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ProfilViewHolder holder, final int i) {
        holder.img.setImageResource(iconImage[i]);
        holder.title.setText(title[i]);
        holder.desc.setText(descriptions[i]);
        holder.relativeLayout.setOnClickListener(v -> listener.onItemClicked(i));

    }

    @Override
    public int getItemCount() {
        return iconImage.length;
    }

    public interface OnClickItemProfile {
        void onItemClicked(int pos);
    }

    public class ProfilViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewadapterprofillist_img)
        ImageView img;
        @BindView(R.id.viewadapterprofillist_title)
        TextView title;
        @BindView(R.id.viewadapterprofillist_desc)
        TextView desc;
        @BindView(R.id.viewadapterprofillist_relative)
        RelativeLayout relativeLayout;

        public ProfilViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
