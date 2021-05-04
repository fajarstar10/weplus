package id.weplus.model.response.agent.saldo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.weplus.model.Payment;

public class AgentSaldoData implements Parcelable {
    private String username;
    private String saldo;
    @SerializedName("payment_channel")
    private ArrayList<PaymentChannel> paymentChannel;
    @SerializedName("bank_information")
    private BankInformation bankInformations;
    @SerializedName("saldo_history")
    private ArrayList<SaldoHistory> saldoHistories;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public ArrayList<PaymentChannel> getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(ArrayList<PaymentChannel> paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public BankInformation getBankInformations() {
        return bankInformations;
    }

    public void setBankInformations(BankInformation bankInformations) {
        this.bankInformations = bankInformations;
    }

    public ArrayList<SaldoHistory> getSaldoHistories() {
        return saldoHistories;
    }

    public void setSaldoHistories(ArrayList<SaldoHistory> saldoHistories) {
        this.saldoHistories = saldoHistories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.saldo);
        dest.writeList(this.paymentChannel);
        dest.writeParcelable(this.bankInformations, flags);
        dest.writeList(this.saldoHistories);
    }

    public AgentSaldoData() {
    }

    protected AgentSaldoData(Parcel in) {
        this.username = in.readString();
        this.saldo = in.readString();
        this.paymentChannel = new ArrayList<PaymentChannel>();
        in.readList(this.paymentChannel, PaymentChannel.class.getClassLoader());
        this.bankInformations = in.readParcelable(BankInformation.class.getClassLoader());
        this.saldoHistories = new ArrayList<SaldoHistory>();
        in.readList(this.saldoHistories, SaldoHistory.class.getClassLoader());
    }

    public static final Parcelable.Creator<AgentSaldoData> CREATOR = new Parcelable.Creator<AgentSaldoData>() {
        @Override
        public AgentSaldoData createFromParcel(Parcel source) {
            return new AgentSaldoData(source);
        }

        @Override
        public AgentSaldoData[] newArray(int size) {
            return new AgentSaldoData[size];
        }
    };
}
