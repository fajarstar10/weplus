package id.weplus.model;

import com.google.gson.annotations.SerializedName;

public class Paroki {
    private String id;
    @SerializedName("bksy_keuskupan_id")
    private String bskyKeuskupanId;
    private String location;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBskyKeuskupanId() {
        return bskyKeuskupanId;
    }

    public void setBskyKeuskupanId(String bskyKeuskupanId) {
        this.bskyKeuskupanId = bskyKeuskupanId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
