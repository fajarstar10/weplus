package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.Clinic;

public class ClinicData {
    @SerializedName("clinic_category")
    private ArrayList<Clinic> clinicArrayList;

    public ArrayList<Clinic> getClinicArrayList() {
        return clinicArrayList;
    }

    public void setClinicArrayList(ArrayList<Clinic> clinicArrayList) {
        this.clinicArrayList = clinicArrayList;
    }
}
