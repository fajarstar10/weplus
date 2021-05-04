package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Partner implements Parcelable {
    private int id;
    private String name;
    private String image;
    private String show_on_apps;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShow_on_apps() {
        return show_on_apps;
    }

    public void setShow_on_apps(String show_on_apps) {
        this.show_on_apps = show_on_apps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.show_on_apps);
    }

    public Partner() {
    }

    protected Partner(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.show_on_apps = in.readString();
    }

    public static final Parcelable.Creator<Partner> CREATOR = new Parcelable.Creator<Partner>() {
        @Override
        public Partner createFromParcel(Parcel source) {
            return new Partner(source);
        }

        @Override
        public Partner[] newArray(int size) {
            return new Partner[size];
        }
    };
}
