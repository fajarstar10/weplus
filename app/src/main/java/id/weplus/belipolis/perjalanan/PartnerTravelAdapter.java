package id.weplus.belipolis.perjalanan;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.PartnerTravel;

public class PartnerTravelAdapter extends RecyclerView.Adapter<PartnerTravelAdapter.PartnerViewHolder> {
    private Activity activity;
    private int[] iconImage = {R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,
                               R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,
                               R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,
                               R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance,
                               R.drawable.aca_insurance,R.drawable.aca_insurance,R.drawable.aca_insurance};

    private PartnerTravelAdapter.OnClickedKategoriSemua listener;
    private List<PartnerTravel> productList;

    public PartnerTravelAdapter(Activity activity, List<PartnerTravel> travellist) {
        this.activity = activity;
        this.iconImage = iconImage;
        this.productList= travellist;
    }

    public interface OnClickedKategoriSemua{
        void onSemua(int pos, String label);
    }

    public void setItems(int[] iconImage){
        this.iconImage = iconImage ;
    }

    @Override
    public PartnerTravelAdapter.PartnerViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_partner, viewGroup, false);
        return new PartnerTravelAdapter.PartnerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(PartnerViewHolder holder, final int i) {

        Glide.with(activity)
                .load(productList.get(i).getImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.aca_insurance)
                        .error(R.drawable.aca_insurance)
                        .circleCrop()
                ).into(holder.icon);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return iconImage.length;
    }


    public class PartnerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.partner_icon) ImageView icon;
        public PartnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
