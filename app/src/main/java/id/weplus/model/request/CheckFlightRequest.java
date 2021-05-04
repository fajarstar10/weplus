package id.weplus.model.request;

public class CheckFlightRequest {
    private String flight1;
    private String flight1_date;
    private String flight2;
    private String flight2_date;
    private String flight3;
    private String flight3_date;


    // Getter Methods

    public String getFlight1() {
        return flight1;
    }

    public String getFlight1_date() {
        return flight1_date;
    }

    public String getFlight2() {
        return flight2;
    }

    public String getFlight2_date() {
        return flight2_date;
    }

    public String getFlight3() {
        return flight3;
    }

    public String getFlight3_date() {
        return flight3_date;
    }

    // Setter Methods

    public void setFlight1(String flight1) {
        this.flight1 = flight1;
    }

    public void setFlight1_date(String flight1_date) {
        this.flight1_date = flight1_date;
    }

    public void setFlight2(String flight2) {
        this.flight2 = flight2;
    }

    public void setFlight2_date(String flight2_date) {
        this.flight2_date = flight2_date;
    }

    public void setFlight3(String flight3) {
        this.flight3 = flight3;
    }

    public void setFlight3_date(String flight3_date) {
        this.flight3_date = flight3_date;
    }
}
