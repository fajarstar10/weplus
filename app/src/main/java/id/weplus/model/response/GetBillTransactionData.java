package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.BillTransaction;

public class GetBillTransactionData {
    @SerializedName("bills_transaction")
    private ArrayList<BillTransaction> billTransactionArrayList;

    public ArrayList<BillTransaction> getBillTransactionArrayList() {
        return billTransactionArrayList;
    }

    public void setBillTransactionArrayList(ArrayList<BillTransaction> billTransactionArrayList) {
        this.billTransactionArrayList = billTransactionArrayList;
    }
}
