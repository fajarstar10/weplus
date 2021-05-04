package id.weplus.model.response.mudik;

import java.util.ArrayList;

import id.weplus.model.BaseFilter;

public class MudikSimasData {
    private ArrayList<BaseFilter> trip_reason;
    private ArrayList<BaseFilter> beneficiary_relation;

    public ArrayList<BaseFilter> getTrip_reason() {
        return trip_reason;
    }

    public ArrayList<BaseFilter> getBeneficiary_relation() {
        return beneficiary_relation;
    }
}
