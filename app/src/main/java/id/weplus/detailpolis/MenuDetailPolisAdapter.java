package id.weplus.detailpolis;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.utility.RecyclerItemClickListener;

public class MenuDetailPolisAdapter extends RecyclerView.Adapter<MenuDetailPolisAdapter.DataMobilDetailPolisViewHolder> {

    private Activity activity;
    private List<DataMobilModel> list;
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener;
    public MenuDetailPolisAdapter(Activity activity, List<DataMobilModel> list, RecyclerItemClickListener.OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MenuDetailPolisAdapter.DataMobilDetailPolisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_detail_produk_polis, viewGroup, false);
        return new MenuDetailPolisAdapter.DataMobilDetailPolisViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuDetailPolisAdapter.DataMobilDetailPolisViewHolder holder, int i) {
        //holder.imgIcon.setImageResource(list.get(i).getImg());
        Glide.with(activity).load(list.get(i).getImg()).into(holder.imgIcon);
        holder.title.setText(list.get(i).getName());
        holder.description.setText(list.get(i).getDesc());
        if(list.get(i).getNote()!=null) {
            holder.description.setText(list.get(i).getDesc() + "\n" + list.get(i).getNote());
        }else{
            holder.description.setText(list.get(i).getDesc());
        }
        holder.wrapper.setOnClickListener(view -> onItemClickListener.onItemClick(view,i));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataMobilDetailPolisViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapterWrapper) RelativeLayout wrapper;
        @BindView(R.id.detailpolis_img_icon) ImageView imgIcon;
        @BindView(R.id.detailpolis_title) TextView title;
        @BindView(R.id.detailpolis_desc) TextView description;
        public DataMobilDetailPolisViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
