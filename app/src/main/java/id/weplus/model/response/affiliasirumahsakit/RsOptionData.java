package id.weplus.model.response.affiliasirumahsakit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RsOptionData {
    @SerializedName("option")
    private ArrayList<RSOptionModel> options;

    public ArrayList<RSOptionModel> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<RSOptionModel> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "RsOptionData{" +
                "options=" + options +
                '}';
    }
}
