package id.weplus.belipolis.motor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.weplus.R;
import id.weplus.model.BaseFilter;

public class AdditionalProtectionAdapter extends RecyclerView.Adapter<AdditionalProtectionAdapter.ProtectionViewHolder> {

    private ArrayList<BaseFilter> protections = new ArrayList<>();
    private Activity activity;

    public AdditionalProtectionAdapter(Activity activity,ArrayList<BaseFilter> baseFilters){
        this.activity=activity;
        protections=baseFilters;
    }

    public ArrayList<BaseFilter> getList(){
        return protections;
    }
    @NonNull
    @Override
    public ProtectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_add_protection, viewGroup,false);
        return new ProtectionViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProtectionViewHolder protectionViewHolder, int i) {
        BaseFilter filter = protections.get(i);
        if(filter.isSelected()){
            protectionViewHolder.imgCheck.setImageResource(R.drawable.ic_check_box_black_24dp);
        }else{
            protectionViewHolder.imgCheck.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
        }
        protectionViewHolder.tvContent.setText(filter.getFilterText());

        protectionViewHolder.wrapper.setOnClickListener(view -> {
         filter.setSelected(!filter.isSelected());
         notifyDataSetChanged();
        });
        String detail="";
        switch (filter.getId()){
            case 4:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Huru Hara, Kerusuhan, Terorisme, dan Sabotase";break;
            case 5:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh gempa Bumi, Tsunami, dan Letusan Gunung Berapi";break;
            case 6:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Banjir, Angin Topan, Hujan Es, dan Tanah Longsor";break;
            case 50:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Jaminan Kapal Pesiar";break;
            case 51:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Olahraga Musim Dingin";break;
            case 52:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Aktivitas Petualangan";break;
        }
        protectionViewHolder.tvContentDetail.setText(detail);


    }

    @Override
    public int getItemCount() {
        return protections.size();
    }

    static class ProtectionViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCheck;
        TextView tvContent;
        RelativeLayout wrapper;
        TextView tvContentDetail;

        public ProtectionViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            tvContent = itemView.findViewById(R.id.tvContent);
            wrapper = itemView.findViewById(R.id.wrapper);
            tvContentDetail = itemView.findViewById(R.id.tvContentDetail);
        }
    }
}
