package id.weplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FlightData implements Parcelable {
    @SerializedName("flight_no")
    private String flightNo;
    @SerializedName("airline")
    private String airline;
    @SerializedName("departure")
    private String departure;
    @SerializedName("departure_date")
    private String departureDate;
    @SerializedName("departure_time")
    private String departureTime;
    @SerializedName("departure_airport")
    private String departureAirport;
    @SerializedName("departure_airport_iata")
    private String departureAirportIata;
    @SerializedName("arrival")
    private String arrival;
    @SerializedName("arrival_date")
    private String arrivalDate;
    @SerializedName("arrival_time")
    private String arrivalTime;
    @SerializedName("arrival_airport")
    private String arrivalAirport;
    @SerializedName("arrival_airport_iata")
    private String arrivalAirportIata;
    @SerializedName("message")
    private String message;


    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDepartureAirportIata() {
        return departureAirportIata;
    }

    public void setDepartureAirportIata(String departureAirportIata) {
        this.departureAirportIata = departureAirportIata;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getArrivalAirportIata() {
        return arrivalAirportIata;
    }

    public void setArrivalAirportIata(String arrivalAirportIata) {
        this.arrivalAirportIata = arrivalAirportIata;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Creator<FlightData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.flightNo);
        dest.writeString(this.airline);
        dest.writeString(this.departure);
        dest.writeString(this.departureDate);
        dest.writeString(this.departureTime);
        dest.writeString(this.departureAirport);
        dest.writeString(this.departureAirportIata);
        dest.writeString(this.arrival);
        dest.writeString(this.arrivalDate);
        dest.writeString(this.arrivalTime);
        dest.writeString(this.arrivalAirport);
        dest.writeString(this.arrivalAirportIata);
        dest.writeString(this.message);
    }

    public FlightData() {
    }

    protected FlightData(Parcel in) {
        this.flightNo = in.readString();
        this.airline = in.readString();
        this.departure = in.readString();
        this.departureDate = in.readString();
        this.departureTime = in.readString();
        this.departureAirport = in.readString();
        this.departureAirportIata = in.readString();
        this.arrival = in.readString();
        this.arrivalDate = in.readString();
        this.arrivalTime = in.readString();
        this.arrivalAirport = in.readString();
        this.arrivalAirportIata = in.readString();
        this.message = in.readString();
    }

    public static final Creator<FlightData> CREATOR = new Creator<FlightData>() {
        @Override
        public FlightData createFromParcel(Parcel source) {
            return new FlightData(source);
        }

        @Override
        public FlightData[] newArray(int size) {
            return new FlightData[size];
        }
    };
}
