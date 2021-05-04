package id.weplus.belipolis.productlist;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import id.weplus.R;
import id.weplus.model.PartnerTravel;


public class PartnerListAdapter extends  RecyclerView.Adapter<PartnerListAdapter.PartnerViewHolder> {

    private ArrayList<PartnerTravel> partners;
    private Activity activity;
    private OnPartnerClickListener onPartnerClickListener;
    public PartnerListAdapter(
            Activity activity,
            List<PartnerTravel> partnerList,
            OnPartnerClickListener listener) {
       this.activity=activity;
       this.partners= (ArrayList<PartnerTravel>) partnerList;
       this.onPartnerClickListener = listener;
    }

    public void setPartners(ArrayList<PartnerTravel> partnerList){
        this.partners=partnerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PartnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.item_partner, viewGroup, false);
        return new PartnerViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(@NonNull PartnerViewHolder partnerViewHolder, int i) {
        PartnerTravel partnerTravel = partners.get(i);
        partnerViewHolder.wrapper.setOnClickListener(
                view -> {
                    onPartnerClickListener.onPartnerClick(partnerTravel);
                    Log.d("testing", "clicked");
                });
        Glide.with(activity)
                .load(partnerTravel.getImage())
                .placeholder(R.drawable.aca_insurance)
                .error(R.drawable.aca_insurance)
                .into(partnerViewHolder.img);
    }

    @Override
    public int getItemCount() {
        return partners.size();
    }

    static class PartnerViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        CardView wrapper;
        public PartnerViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            wrapper = itemView.findViewById(R.id.cv_mitra);
        }
    }
}
