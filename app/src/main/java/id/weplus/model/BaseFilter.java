package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseFilter implements Parcelable {
    private int id;
    @SerializedName("text")
    private String filterText;
    @SerializedName("name")
    private String name;
    @SerializedName("unit_type")
    private ArrayList<BaseFilter> unitType;
    private boolean isSelected;

    public ArrayList<BaseFilter> getUnitType() {
        return unitType;
    }

    public void setUnitType(ArrayList<BaseFilter> unitType) {
        this.unitType = unitType;
    }

    public BaseFilter(int id, String filterText, String name) {
        this.id = id;
        this.filterText = filterText;
        this.name = name;
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.filterText);
        dest.writeString(this.name);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public BaseFilter() {
    }

    protected BaseFilter(Parcel in) {
        this.id = in.readInt();
        this.filterText = in.readString();
        this.name = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<BaseFilter> CREATOR = new Parcelable.Creator<BaseFilter>() {
        @Override
        public BaseFilter createFromParcel(Parcel source) {
            return new BaseFilter(source);
        }

        @Override
        public BaseFilter[] newArray(int size) {
            return new BaseFilter[size];
        }
    };
}
