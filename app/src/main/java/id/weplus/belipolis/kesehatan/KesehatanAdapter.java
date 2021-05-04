package id.weplus.belipolis.kesehatan;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class KesehatanAdapter extends RecyclerView.Adapter<KesehatanAdapter.KesehatanAdapterViewHolder> {
    private Activity activity;
    private int[] iconImage = {R.drawable.kesehatan};

    public KesehatanAdapter (Activity activity){
        this.activity = activity;
    }

    @Override
    public KesehatanAdapter.KesehatanAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_mitrabanner, viewGroup,false);
        return new KesehatanAdapter.KesehatanAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(KesehatanAdapter.KesehatanAdapterViewHolder holder, int i) {
        holder.imageView.setImageResource(iconImage[i]);
    }

    @Override
    public int getItemCount() {
        return iconImage.length;
    }

    public class KesehatanAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img)
        ImageView imageView;
        public KesehatanAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

