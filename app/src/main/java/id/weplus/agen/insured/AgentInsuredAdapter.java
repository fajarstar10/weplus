package id.weplus.agen.insured;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.detailpolis.DetailPolisMobilActivity;
import id.weplus.model.Insured;
import id.weplus.utility.Constant;
import id.weplus.utility.FirebaseAnalyticsHelper;
import id.weplus.utility.TextHelper;

public class AgentInsuredAdapter extends RecyclerView.Adapter<AgentInsuredAdapter.TransaksiViewHolder> {
    private FragmentActivity activity;
    private AgentInsuredAdapter.PolisSayaOnItemClicked listener;
    private String[] status;
    private List<Insured> insuredList=new ArrayList<>();

    public AgentInsuredAdapter(FragmentActivity activity, List<Insured> insuredList) {
        this.activity = activity;
        this.insuredList = insuredList;
    }

    public interface PolisSayaOnItemClicked {
        void onItem(int pos, String tag);
    }

    public void setListenerPolisSaya(AgentInsuredAdapter.PolisSayaOnItemClicked listener) {
        this.listener = listener;
    }

    @Override
    public AgentInsuredAdapter.TransaksiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.viewadapter_agent_insured, viewGroup, false);
        return new AgentInsuredAdapter.TransaksiViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AgentInsuredAdapter.TransaksiViewHolder holder, int i) {
        Insured insured = insuredList.get(i);
        Log.e("TAG", insured.getProduct_name());
        holder.idTransaksi.setText(insured.getOrder_code());

        holder.asuransiName.setText(insured.getInsured_name());
        String[] updatedAt =insured.getDate_active().split(" - ");
        holder.tvProtectionUntil.setText(updatedAt[1]);
        holder.tglTransaksi.setText(insured.getProduct_name());
        Glide.with(activity)
                .load(insured.getImage_url())
                .into(holder.img);

        if(insured.getStatus().equalsIgnoreCase("active")){
            holder.transactionStatus.setText("Aktif");
            holder.transactionStatus.setTextColor(activity.getResources().getColor(R.color.white));
        }else {
            holder.transactionStatus.setText("Kadaluarsa");
            holder.transactionStatus.setTextColor(activity.getResources().getColor(R.color.white));
            holder.transactionStatus.setBackgroundColor(activity.getResources().getColor(R.color.grey_medium));
        }
        getDuration(insured.getDate_active());
//        if (statusV.equals("sukses") || statusV.equals("aktif")) {
//            holder.transactionStatus.setTextColor(activity.getResources().getColor(R.color.white));
//        } else if (statusV.equals("gagal") || statusV.equals("ditolak")) {
//            holder.transactionStatus.setTextColor(activity.getResources().getColor(R.color.red));
//        } else if (statusV.equals("kadaluarsa")) {
//            holder.transactionStatus.setTextColor(activity.getResources().getColor(R.color.white));
//            holder.transactionStatus.setBackgroundColor(activity.getResources().getColor(R.color.grey_medium));
//        }
//        holder.transactionStatus.setText(status[i]);
        holder.lihatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_DETAIL_POLIS);

                Intent intent = new Intent(activity.getApplicationContext(), DetailPolisMobilActivity.class);
                intent.putExtra("tipe","Aktif");
                intent.putExtra("id", insured.getOrder_code());
                activity.startActivity(intent);
            }
        });
    }

    private String getDuration(String dateActive){
        String[] active = dateActive.split(" - ");
        Date dateStart = parseDate(active[0], "dd-MM-yyyy");
        Date dateEnd = parseDate(active[1], "dd-MM-yyyy");
        //dateStart
        return "";
    }

    private Date parseDate(String date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return Calendar.getInstance().getTime();
        }
    }

    @Override
    public int getItemCount() {
        return insuredList.size();
    }
    
    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.polis_saya_id_transaksi)
        TextView idTransaksi;
        @BindView(R.id.polis_saya_amount) TextView tvProtectionUntil;
        @BindView(R.id.transaksi_img)
        ImageView img;
        @BindView(R.id.polis_saya_tgl_transaksi) TextView tglTransaksi;
        @BindView(R.id.polis_saya_asuransi_name) TextView asuransiName;
        @BindView(R.id.transaksi_status) TextView transactionStatus;
        @BindView(R.id.transaksi_layout_item)
        CardView layoutItem;
        @BindView(R.id.adapter_polis_saya_lihat_detail) TextView lihatDetail;

        public TransaksiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

