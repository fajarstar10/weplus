package id.weplus.belipolis.mobil;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import id.weplus.R;
import id.weplus.model.BaseFilter;

public class TambahanPerlindunganMobilAdapter extends RecyclerView.Adapter<TambahanPerlindunganMobilAdapter.TambahanPerlindunganMobilViewHolder> {
    private Activity activity;
    private ArrayList<BaseFilter> addProtection= new ArrayList<>();

    public TambahanPerlindunganMobilAdapter(Activity activity,ArrayList<BaseFilter> addProtection) {
        this.activity = activity;
        this.addProtection=addProtection;
    }

    @Override
    public TambahanPerlindunganMobilAdapter.TambahanPerlindunganMobilViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_add_protection, viewGroup, false);
        return new TambahanPerlindunganMobilAdapter.TambahanPerlindunganMobilViewHolder(rootView);
    }

    public ArrayList<BaseFilter> getLists(){
        return addProtection;
    }

    @Override
    public void onBindViewHolder(TambahanPerlindunganMobilAdapter.TambahanPerlindunganMobilViewHolder holder, int i) {
        BaseFilter filter = addProtection.get(i);
        if(filter.isSelected()){
            holder.imgCheck.setImageResource(R.drawable.ic_check_box_black_24dp);
        }else{
            holder.imgCheck.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
        }

        holder.tvContent.setText(filter.getFilterText());

        holder.wrapper.setOnClickListener(view -> {
            filter.setSelected(!filter.isSelected());
            notifyDataSetChanged();
        });

        String detail="";
        switch (filter.getId()){
            case 10:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Kerusuhan";break;
            case 11:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Terorisme & Sabotase";break;
            case 12:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Gempa Bumi";break;
            case 13:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Banjir";break;
            case 91:detail = "Kompensasi atau biaya perbaikan untuk kerusakan yang disebabkan oleh Bengkel";break;
        }
        holder.tvContentDetail.setText(detail);

    }

    @Override
    public int getItemCount() {
        return addProtection.size();
    }

    public class TambahanPerlindunganMobilViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCheck;
        TextView tvContent;
        RelativeLayout wrapper;
        TextView tvContentDetail;

        public TambahanPerlindunganMobilViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            tvContent = itemView.findViewById(R.id.tvContent);
            wrapper = itemView.findViewById(R.id.wrapper);
            tvContentDetail = itemView.findViewById(R.id.tvContentDetail);
        }
    }
}
