package id.weplus.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.Payment;

public class PaymentMethodData {
    @SerializedName("payment_discount")
    private String statusDiscount;
    @SerializedName("message_discount")
    private String messageDiscount;
    @SerializedName("payment_list")
    private ArrayList<Payment> paymentList;

    public String getStatusDiscount() {
        return statusDiscount;
    }

    public void setStatusDiscount(String statusDiscount) {
        this.statusDiscount = statusDiscount;
    }

    public String getMessageDiscount() {
        return messageDiscount;
    }

    public void setMessageDiscount(String messageDiscount) {
        this.messageDiscount = messageDiscount;
    }

    public ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(ArrayList<Payment> paymentList) {
        this.paymentList = paymentList;
    }
}
