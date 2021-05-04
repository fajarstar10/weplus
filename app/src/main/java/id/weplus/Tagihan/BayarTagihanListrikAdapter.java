package id.weplus.Tagihan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;

public class BayarTagihanListrikAdapter extends RecyclerView.Adapter<BayarTagihanListrikAdapter.BayarTagihanListrikViewHolder> {
    private Activity activity;
    int[] icon_pay = {R.drawable.ic_bca,R.drawable.ic_gopay, R.drawable.ic_alfamart,R.drawable.ic_card};
    String[] pay_name = {"BCA Virtual Account", "GO-PAY", "Alfamart", "Credit Card"};
    private int index = -1;

    public BayarTagihanListrikAdapter (Activity activity) {
        this.activity = activity;
    }

    @Override
    public BayarTagihanListrikAdapter.BayarTagihanListrikViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_bayar_tagihan_listrik,viewGroup,false);
        return new BayarTagihanListrikAdapter.BayarTagihanListrikViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(BayarTagihanListrikAdapter.BayarTagihanListrikViewHolder vh, final int i) {
        vh.iconImage.setImageResource(icon_pay[i]);
        vh.itemName.setText(pay_name[i]);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = i;
                notifyDataSetChanged();
            }
        });

        if (index == i){
            vh.itemView.setBackgroundColor(activity.getResources().getColor(R.color.red));
            vh.itemName.setTextColor(activity.getResources().getColor(R.color.white));
        } else {
            vh.itemView.setBackgroundColor(activity.getResources().getColor(R.color.white));
            vh.itemName.setTextColor(activity.getResources().getColor(R.color.grey_medium));
        }
    }

    @Override
    public int getItemCount() {
        return icon_pay.length;
    }

    public class BayarTagihanListrikViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView iconImage;
        @BindView(R.id.item_name)
        TextView itemName;

        public BayarTagihanListrikViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
