package id.weplus.utility;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ThreeTwo extends AppCompatImageView {


    public ThreeTwo(Context context) {
        super(context);
    }

    public ThreeTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThreeTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heigth = MeasureSpec.getSize(widthMeasureSpec) * 1/3;
//        int heigth = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.makeMeasureSpec(heigth, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
