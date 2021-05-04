package id.weplus.model.response.emergencycontact;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EmergencyContactData {
    @SerializedName("emergency_contact")
    private ArrayList<EmergencyContact> contacts;
}
