package id.weplus.belipolis.motor;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class AsuransiMotorAdapter extends RecyclerView.Adapter<AsuransiMotorAdapter.AsuransiMotorAdapterViewHolder> {
    private Activity activity;
    private int[] iconImage = {R.drawable.kesehatan};

    public AsuransiMotorAdapter (Activity activity){
        this.activity = activity;
    }

    @Override
    public AsuransiMotorAdapter.AsuransiMotorAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_asuransimotor, viewGroup,false);
        return new AsuransiMotorAdapter.AsuransiMotorAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(AsuransiMotorAdapter.AsuransiMotorAdapterViewHolder holder, int i) {
        holder.imageView.setImageResource(iconImage[i]);
    }

    @Override
    public int getItemCount() {
        return iconImage.length;
    }

    public class AsuransiMotorAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img)
        ImageView imageView;
        public AsuransiMotorAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}


