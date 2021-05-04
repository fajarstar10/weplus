package id.weplus.Mitra;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.Partner;

public class MitraAdapter extends RecyclerView.Adapter<MitraAdapter.MitraAdapterViewHolder> {
    private Activity activity;
    private ArrayList<Partner> partnerlist = new ArrayList<>();

    public MitraAdapter(Activity activity ){
        this.activity = activity;
    }

    public void addItems(ArrayList<Partner> partnerlist){
        this.partnerlist.addAll(partnerlist);
        notifyDataSetChanged();
    }
    @Override
    public MitraAdapter.MitraAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_mitra, viewGroup,false);
        return new MitraAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MitraAdapter.MitraAdapterViewHolder holder, int i) {
        Partner partner = partnerlist.get(i);

        Glide.with(activity)
                .load(partner.getImage())
                .placeholder(R.drawable.alfamart)
                .error(R.drawable.alfamart)
                .centerCrop()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            //if(partner.getId()==2){
                Intent intent = new Intent(activity,KaryawanAlfamartActivity.class);
                intent.putExtra("partner",partner);
                activity.startActivity(intent);
            //}
        });
    }

    @Override
    public int getItemCount() {
        return partnerlist.size();
    }

    static class MitraAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img) ImageView imageView;
        MitraAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
