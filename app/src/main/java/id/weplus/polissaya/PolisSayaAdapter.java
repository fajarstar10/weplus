package id.weplus.polissaya;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.cardview.widget.CardView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class PolisSayaAdapter extends RecyclerView.Adapter<PolisSayaAdapter.TransaksiViewHolder> {
    private FragmentActivity activity;
    private PolisSayaOnItemClicked listener;
    private String[] status;
    private List<Insured> insuredList=new ArrayList<>();

    public PolisSayaAdapter(FragmentActivity activity, List<Insured> insuredList) {
        this.activity = activity;
        this.insuredList = insuredList;
    }

    public interface PolisSayaOnItemClicked {
        void onItem(int pos, String tag);
    }

    public void setListenerPolisSaya(PolisSayaAdapter.PolisSayaOnItemClicked listener) {
        this.listener = listener;
    }

    @Override
    public PolisSayaAdapter.TransaksiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.viewadapter_polis_saya, viewGroup, false);
        return new PolisSayaAdapter.TransaksiViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(PolisSayaAdapter.TransaksiViewHolder holder, int i) {
        Insured insured = insuredList.get(i);
        Log.e("TAG", insured.getProduct_name());
        holder.idTransaksi.setText(insured.getOrder_code());
        holder.paymentAmount.setText("Rp "+TextHelper.currencyFormatter(insured.getTotal_payment()));
        holder.asuransiName.setText(insured.getProduct_name());
        //String[] updatedAt =insured.getUpdated_at().split(" ");
        holder.tglTransaksi.setText(insured.getDate_active());
        Glide.with(activity)
                .load(insured.getImage_url())
                .into(holder.img);

        if(insured.getStatus().equalsIgnoreCase("active")){
            holder.transactionStatus.setText("Aktif");
            holder.transactionStatus.setPadding(17,0,17,0);
            holder.transactionStatus.setTextColor(activity.getResources().getColor(R.color.white));
        }else {
            holder.transactionStatus.setText("Kadaluarsa");
            holder.transactionStatus.setPadding(4,0,4,0);
            holder.transactionStatus.setTextColor(activity.getResources().getColor(R.color.white));
            holder.transactionStatus.setBackground(activity.getResources().getDrawable(R.drawable.border_grey_polis_saya));
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
        holder.lihatDetail.setOnClickListener(v -> {
            FirebaseAnalyticsHelper.logEvent(activity, Constant.ANALYTICS_DETAIL_POLIS);

            Intent intent = new Intent(activity.getApplicationContext(), DetailPolisMobilActivity.class);
            intent.putExtra("tipe","Aktif");
            intent.putExtra("id", insured.getOrder_code());
            activity.startActivity(intent);
        });
    }

    private String getDuration(String dateActive){
        try {
            String[] active = dateActive.split(" - ");
            Date dateStart = parseDate(active[0], "dd-MM-yyyy");
            Date dateEnd = parseDate(active[1], "dd-MM-yyyy");
        }catch (Exception e){
            return "";
        }
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

//    public void addItems(List<Insured> insuredList ){
//        insuredList.addAll(insuredList);
//        notifyDataSetChanged();
//    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.polis_saya_id_transaksi) TextView idTransaksi;
        @BindView(R.id.polis_saya_amount) TextView paymentAmount;
        @BindView(R.id.transaksi_img) ImageView img;
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
