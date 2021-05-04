package id.weplus.belipolis;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.BuyPolisModel;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>{
    private FragmentActivity activity;
    private int[] imgIcon = {R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded,
            R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded,
            R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded, R.drawable.border_fill_red_rounded  };
    private String[] labelIcon ;
    private OnClickedKategoriSemua listener;
    private List <BuyPolisModel.Categori> kategoriList;

    public KategoriAdapter(FragmentActivity activity, List<BuyPolisModel.Categori> list){
        this.activity = activity;
        this.imgIcon = imgIcon;
        this.labelIcon = labelIcon;
        this.kategoriList = list;
    }

    public interface OnClickedKategoriSemua{
        void onSemua(int pos, String label);
    }

    public void setItems(int[] imgIcon, String[] labelIcon){
        this.imgIcon = imgIcon;
        this.labelIcon = labelIcon;
    }

    public BuyPolisModel.Categori getItem(int i){
        return kategoriList.get(i);
    }

    public void setListenerSemua(OnClickedKategoriSemua listener){
        this.listener = listener;
    }


    @Override
    public KategoriAdapter.KategoriViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_kategori, viewGroup, false);
        return new KategoriAdapter.KategoriViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(KategoriViewHolder holder, final int i) {
        holder.label.setText(kategoriList.get(i).getName());
        Glide.with(activity)
                .load(kategoriList.get(i).getImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.border_fill_red_rounded)
                        .error(R.drawable.border_fill_red_rounded)
                        .centerInside()
                        .circleCrop()
                ).into(holder.icon);

        holder.itemView.setOnClickListener(v -> listener.onSemua(i, kategoriList.get(i).getName()));
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapterkategori_icon) ImageView icon;
        @BindView(R.id.adapterkategori_name) TextView label;
        @BindView(R.id.adapterkategori_layout) RelativeLayout layout;
        public KategoriViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
