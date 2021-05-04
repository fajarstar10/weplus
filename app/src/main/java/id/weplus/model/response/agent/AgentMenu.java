package id.weplus.model.response.agent;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AgentMenu implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("img")
    private String img;
    @SerializedName("desc")
    private String desc;
    @SerializedName("url")
    private String url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.img);
        dest.writeString(this.desc);
        dest.writeString(this.url);
    }

    public AgentMenu() {
    }

    protected AgentMenu(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.img = in.readString();
        this.desc = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<AgentMenu> CREATOR = new Parcelable.Creator<AgentMenu>() {
        @Override
        public AgentMenu createFromParcel(Parcel source) {
            return new AgentMenu(source);
        }

        @Override
        public AgentMenu[] newArray(int size) {
            return new AgentMenu[size];
        }
    };
}
