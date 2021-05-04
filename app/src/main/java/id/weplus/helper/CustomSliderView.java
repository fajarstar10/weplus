package id.weplus.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.daimajia.slider.library.R;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Picasso;

public class CustomSliderView extends BaseSliderView {

    public CustomSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_default,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        ProgressBar progressBar = v.findViewById(R.id.loading_bar);
        bindEventAndShow(v, target);
        v.setBackgroundColor(Color.TRANSPARENT);
        Picasso picasso = Picasso.get();
        picasso.load(getUrl())
                .transform(new RoundedCornersTransformation(40,1))
                .into(target, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }

                });
        return v;
    }
}
