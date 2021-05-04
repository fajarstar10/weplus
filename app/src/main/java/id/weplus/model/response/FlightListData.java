package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.FlightData;

public class FlightListData {
    @SerializedName("flight-list")
    private ArrayList<FlightData> flightList;

    public ArrayList<FlightData> getFlightList() {
        return flightList;
    }

    public void setFlightList(ArrayList<FlightData> flightList) {
        this.flightList = flightList;
    }
}
