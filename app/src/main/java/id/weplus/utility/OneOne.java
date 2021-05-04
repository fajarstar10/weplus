package id.weplus.utility;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

public class OneOne extends AppCompatImageView {


    public OneOne(Context context) {
        super(context);
    }

    public OneOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OneOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heigth = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.makeMeasureSpec(heigth, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
