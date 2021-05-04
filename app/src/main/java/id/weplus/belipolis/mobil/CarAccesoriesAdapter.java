package id.weplus.belipolis.mobil;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.CarAccessories;

public class CarAccesoriesAdapter extends RecyclerView.Adapter<CarAccesoriesAdapter.CarAccesoriesViewHolder>  {

    private Activity activity;
    private ArrayList<CarAccessories> aksesorisList=new ArrayList<>();

    public CarAccesoriesAdapter(Activity activity, ArrayList<CarAccessories> aksesorisList) {
        this.activity = activity;
        this.aksesorisList = aksesorisList;
    }

    @NonNull
    @Override
    public CarAccesoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_car_accesories, viewGroup, false);
        return new CarAccesoriesViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAccesoriesViewHolder holder, int i) {
        CarAccessories acc = aksesorisList.get(i);
        holder.checkBox.setText(acc.getLabel());
        holder.checkBox.setChecked(acc.isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                acc.setChecked(b);
            }
        });
        if(acc.getLabel().contains("kaca")){
            holder.imgAcc.setImageResource(R.drawable.group_4517);
        }
        if(acc.getLabel().toLowerCase().contains("karpet")){
            holder.imgAcc.setImageResource(R.drawable.group_4518);
        }
        if(acc.getLabel().toLowerCase().contains("sarung")){
            holder.imgAcc.setImageResource(R.drawable.group_4520);
        }
        if(acc.getLabel().toLowerCase().contains("roda")){
            holder.imgAcc.setImageResource(R.drawable.group_4513);
        }
        if(acc.getLabel().toLowerCase().contains("body")){
            holder.imgAcc.setImageResource(R.drawable.group_4521);
        }
        if(acc.getLabel().toLowerCase().contains("lain")){
            holder.imgAcc.setImageResource(R.drawable.group_4522);
        }

    }

    public void checkAll(){
        for(CarAccessories acc:aksesorisList){
            acc.setChecked(!acc.isChecked());
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedLabels(){
        ArrayList<String> ids = new ArrayList<>();
        for(CarAccessories acc:aksesorisList){
            Log.d("test adapter","testing acc "+acc.isChecked()+" "+acc.getLabel());
            if(acc.isChecked()){
               ids.add(acc.getLabel());
            }
        }
        return ids;
    }

    public int getCheckedList(){
        int i=0;
        for(CarAccessories acc:aksesorisList){
            Log.d("test adapter","testing acc "+acc.isChecked()+" "+acc.getLabel());
            if(acc.isChecked()){
                i++;
            }
        }
        return i;
    }

    @Override
    public int getItemCount() {
        return aksesorisList.size();
    }

    public class CarAccesoriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkboxAcc) CheckBox checkBox;
        @BindView(R.id.imgAccesorries) ImageView imgAcc;
        public CarAccesoriesViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
