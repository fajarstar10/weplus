package id.weplus.utility;

import android.util.Log;

import java.text.DecimalFormat;

public class TextHelper {

    static public String currencyFormatter(String num) {
        Log.d("doubleError",num);
        double m = Double.parseDouble(num);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m).replace(",",".");
    }
}
