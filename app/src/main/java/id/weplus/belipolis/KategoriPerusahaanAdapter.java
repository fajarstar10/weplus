package id.weplus.belipolis;


import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.BuyPolisModel;

public class KategoriPerusahaanAdapter extends RecyclerView.Adapter<KategoriPerusahaanAdapter.KategoriPerusahaanViewHolder>{
    private FragmentActivity activity;
    private int[] imgIcon =
            {R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded,
            R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded,
            R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable. border_fill_red_rounded,
            R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded,
            R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded,
            R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded};

    private String[] labelIcon ;
    private KategoriPerusahaanAdapter.OnClickedKategoriSemua listener;
    private List <BuyPolisModel.Partner> partnerList;

    public KategoriPerusahaanAdapter(FragmentActivity activity, List<BuyPolisModel.Partner> perusahaanlist){
        this.activity = activity;
        this.imgIcon = imgIcon;
        this.labelIcon = labelIcon;
        this.partnerList = perusahaanlist;
    }

    public interface OnClickedKategoriSemua{
        void onSemua(int pos, String label);
    }

    public void setItems(int[] imgIcon, String[] labelIcon){
        this.imgIcon = imgIcon;
        this.labelIcon = labelIcon;
    }

    public BuyPolisModel.Partner getItem(int pos){
        return partnerList.get(pos);
    }
    public void setListenerSemua(KategoriPerusahaanAdapter.OnClickedKategoriSemua listener){
        this.listener = listener;
    }


    @Override
    public KategoriPerusahaanAdapter.KategoriPerusahaanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_kategori_perusahaan, viewGroup, false);
        return new KategoriPerusahaanAdapter.KategoriPerusahaanViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(KategoriPerusahaanAdapter.KategoriPerusahaanViewHolder holder, final int i) {
        holder.label.setText(partnerList.get(i).getName());

        Glide.with(activity)
                .load(partnerList.get(i).getImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.border_fill_red_rounded)
                        .error(R.drawable.border_fill_red_rounded)
                        .circleCrop()
                ).into(holder.icon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSemua(i, partnerList.get(i).getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return imgIcon.length;
    }

    public class KategoriPerusahaanViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapterkategori_icon) ImageView icon;
        @BindView(R.id.adapterkategori_name) TextView label;
        @BindView(R.id.adapterkategori_layout) LinearLayout layout;
        public KategoriPerusahaanViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
