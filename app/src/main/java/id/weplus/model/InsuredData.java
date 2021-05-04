package id.weplus.model;

import java.util.List;

public class InsuredData {
    public List<Insured> insured;

    public InsuredData(List<Insured> insured) {
        this.insured = insured;
    }

    public List<Insured> getInsured() {
        return insured;
    }

    public void setInsured (List<Insured> insured) {
        this.insured = insured;
    }
}
