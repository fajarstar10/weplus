package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.BaseFilter;

public class BskyData {
    @SerializedName("keuskupan")
    private ArrayList<BaseFilter> keuskupan;
    @SerializedName("family_role")
    private ArrayList<BaseFilter> familyRole;
    @SerializedName("job")
    private ArrayList<BaseFilter> job;
    @SerializedName("type")
    private ArrayList<BaseFilter> protectionType;

    public ArrayList<BaseFilter> getFamilyRole() {
        return familyRole;
    }

    public void setFamilyRole(ArrayList<BaseFilter> familyRole) {
        this.familyRole = familyRole;
    }

    public ArrayList<BaseFilter> getJob() {
        return job;
    }

    public void setJob(ArrayList<BaseFilter> job) {
        this.job = job;
    }

    public ArrayList<BaseFilter> getProtectionType() {
        return protectionType;
    }

    public void setProtectionType(ArrayList<BaseFilter> protectionType) {
        this.protectionType = protectionType;
    }

    public ArrayList<BaseFilter> getKeuskupan() {
        return keuskupan;
    }

    public void setKeuskupan(ArrayList<BaseFilter> keuskupan) {
        this.keuskupan = keuskupan;
    }
}
