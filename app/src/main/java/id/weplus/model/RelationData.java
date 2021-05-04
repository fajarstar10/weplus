package id.weplus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RelationData {
    @SerializedName("relation")
    private ArrayList<BaseFilter> relation;

    public ArrayList<BaseFilter> getRelation() {
        return relation;
    }

    public void setRelation(ArrayList<BaseFilter> relation) {
        this.relation = relation;
    }
}
