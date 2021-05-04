package id.weplus.model;

import com.google.gson.annotations.SerializedName;

public class BskyEnvironment {
    private int id;
    @SerializedName("bksy_paroki_id")
    private int parokiId;
    private String text;

    public int getParokiId() {
        return parokiId;
    }

    public void setParokiId(int parokiId) {
        this.parokiId = parokiId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
