package id.weplus.polissaya;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.InsuredDetailData;

public class DetailPolisTertanggung  extends RecyclerView.Adapter<DetailPolisTertanggung.VH> {
    private Activity activity;
    private List<InsuredDetailData> insuredDetailDataList;

    public DetailPolisTertanggung(Activity activity, List<InsuredDetailData> insuredDetailData){
        this.activity = activity;
        this.insuredDetailDataList = insuredDetailData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_detail_tertanggung, viewGroup, false);
        return new VH(childView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int i) {
        InsuredDetailData detailData = insuredDetailDataList.get(i);
        holder.title.setText(detailData.getFullname().toString());
        holder.desc.setText(detailData.getEmail().toString());
        holder.email.setVisibility(View.VISIBLE);
        holder.email.setText(detailData.getPhone().toString());
        holder.status.setVisibility(View.GONE);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), DetailTertanggungActivity.class);
                intent.putExtra("nama", detailData.getFullname());
                intent.putExtra("phone", detailData.getPhone());
                intent.putExtra("nik", detailData.getIdentification_no());
                intent.putExtra("email", detailData.getEmail());
                intent.putExtra("dob", detailData.getDob());
                intent.putExtra("city", detailData.getCity());
                intent.putExtra("province", detailData.getProvince());
                intent.putExtra("sex", detailData.getSex());
                intent.putExtra("height", detailData.getHeigth());
                intent.putExtra("weight", detailData.getWeight());
                intent.putExtra("address", detailData.getAdrress());
                intent.putExtra("pob", detailData.getPob());
                intent.putExtra("job", detailData.getJob_declaration());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return insuredDetailDataList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.detailpolis_img_icon)
        ImageView imgIcon;
        @BindView(R.id.detailpolis_title)
        TextView title;
        @BindView(R.id.detailpolis_desc) TextView desc;
        @BindView(R.id.detailpolis_email) TextView email;
        @BindView(R.id.detailpolis_status) TextView status;
        @BindView(R.id.card)
        RelativeLayout card;
        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
