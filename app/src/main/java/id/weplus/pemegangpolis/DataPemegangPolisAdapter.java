package id.weplus.pemegangpolis;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.response.insureduser.InsuredUser;

public class DataPemegangPolisAdapter extends RecyclerView.Adapter<DataPemegangPolisAdapter.DataPemegangPolisViewHolder> {

    private Activity activity;
    private ArrayList<InsuredUser> listPemegangPolis=new ArrayList();
    public DataPemegangPolisAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setInsuredList(ArrayList<InsuredUser> insuredUserArrayList){
        listPemegangPolis = insuredUserArrayList;
        notifyDataSetChanged();
    }

    @Override
    public DataPemegangPolisViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(activity).inflate(R.layout.viewadapater_data_pemegang_polis, viewGroup, false);
        return new DataPemegangPolisViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(DataPemegangPolisViewHolder holder, int i) {
        InsuredUser data = listPemegangPolis.get(i);
        holder.no.setText("" + (i+1));
        holder.name.setText(data.getFullname());
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(activity, FormPemegangPolisActivity.class);
            intent.putExtra("insured_user",data);
            activity.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return listPemegangPolis.size();
    }

    public void addAll(ArrayList listFormPemegangPolis) {
        if(listFormPemegangPolis!=null) {
            this.listPemegangPolis.clear();
            listPemegangPolis.addAll(listFormPemegangPolis);
            notifyDataSetChanged();
        }
    }

    public class DataPemegangPolisViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.data_pemegang_polis_no) TextView no;
        @BindView(R.id.data_pemegang_polis_name) TextView name;
        @BindView(R.id.data_pemegang_polis_btn_edit) TextView btnEdit;

        public DataPemegangPolisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
