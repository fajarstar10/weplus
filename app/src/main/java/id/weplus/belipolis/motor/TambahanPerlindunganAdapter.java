//package id.weplus.belipolis.motor;
//
//import android.app.Activity;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import butterknife.ButterKnife;
//import id.weplus.R;
//
//public class TambahanPerlindunganAdapter  extends RecyclerView.Adapter<TambahanPerlindunganAdapter.TambahanPerlindunganViewHolder> {
//    private Activity activity;
//
//    public TambahanPerlindunganAdapter(Activity activity) {
//        this.activity = activity;
//    }
//
//    @Override
//    public TambahanPerlindunganViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
//        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_tambahan_perlindungan, viewGroup, false);
//        return new TambahanPerlindunganViewHolder(rootView);
//    }
//
//    @Override
//    public void onBindViewHolder( TambahanPerlindunganViewHolder tambahanPerlindunganViewHolder, int i) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 10;
//    }
//
//    public class TambahanPerlindunganViewHolder extends RecyclerView.ViewHolder {
//
//        public TambahanPerlindunganViewHolder( View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//}
