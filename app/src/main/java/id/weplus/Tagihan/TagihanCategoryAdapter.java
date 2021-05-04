package id.weplus.Tagihan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.model.BillCategory;

public class TagihanCategoryAdapter extends  RecyclerView.Adapter<TagihanCategoryAdapter.CategoryViewHolder> {
    private Activity activity;
    private ArrayList<BillCategory> billCategoryList=new ArrayList<>();
    private OnCategoryClicked clickListener;

    public TagihanCategoryAdapter(Activity activity,OnCategoryClicked clickListener){
        this.activity = activity;
        this.clickListener=clickListener;
    }

    public void addItems(ArrayList<BillCategory> billsCategory){
        this.billCategoryList.addAll(billsCategory);
        notifyDataSetChanged();
    }

    @Override
    public TagihanCategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.viewadapter_category_tagihan, viewGroup,false);
        return new TagihanCategoryAdapter.CategoryViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TagihanCategoryAdapter.CategoryViewHolder holder, int i) {
        BillCategory bill  = billCategoryList.get(i);
        Glide.with(activity)
                .load(bill.getImage())
                .placeholder(R.drawable.alfamart)
                .error(R.drawable.alfamart)
                .centerCrop()
                .into(holder.imageView);

        holder.title.setText(bill.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCategoryClicked(bill);
            }
        });


    }

    @Override
    public int getItemCount() {
        return billCategoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
