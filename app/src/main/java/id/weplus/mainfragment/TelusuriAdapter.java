package id.weplus.mainfragment;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.weplus.R;
import id.weplus.utility.OneOne;

public class TelusuriAdapter  extends RecyclerView.Adapter<TelusuriAdapter.TelusuriViewHolder> {

    private Activity context;
    private int[] icon = {R.drawable.ic_menu_klinik_2,R.drawable.ic_menu_klinik_2, R.drawable.ic_menu_klinik_2,R.drawable.ic_menu_klinik_2};
    public TelusuriAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public TelusuriViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.viewadapter_telusuri,viewGroup,false);
        return new TelusuriViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder( TelusuriViewHolder telusuriViewHolder, int i) {
        telusuriViewHolder.setImage.setImageResource(icon[i]);
    }

    @Override
    public int getItemCount() {
        return icon.length;
    }

    public class TelusuriViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img)
        OneOne setImage;

        public TelusuriViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
