package id.weplus.Tagihan;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.Partner;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.TagihanAdapterViewHolder> {
    private Activity activity;
    private int[] iconImage = {R.drawable.alfamart,R.drawable.alfamart};
//    private KategoriAdapter.OnClickedKategoriSemua listener;
    private List<Partner> partnerlist;

    // ini kan adapternya minta diisi list partnerList
    public TagihanAdapter(Activity activity ){
        this.activity = activity;
//        this.partnerlist = mitralist;
    }

    public interface OnClickedKategoriSemua{
        void onSemua(int pos, String label);
    }

    @Override
    public TagihanAdapter.TagihanAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_tagihan, viewGroup,false);
        return new TagihanAdapter.TagihanAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TagihanAdapter.TagihanAdapterViewHolder holder, int i) {

        Glide.with(activity)
                .load(partnerlist.get(i).getImage())
                .placeholder(R.drawable.alfamart)
                .error(R.drawable.alfamart)
                .centerCrop()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onSemua(i, partnerlist.get(i).getName());
            }
        });


    }

    @Override
    public int getItemCount() {
        return iconImage.length;
    }

    public class TagihanAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img) ImageView imageView;
        public TagihanAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
