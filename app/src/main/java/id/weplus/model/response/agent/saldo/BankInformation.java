package id.weplus.model.response.agent.saldo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BankInformation implements Parcelable {
    @SerializedName("bank_name")
    private String bankName;
    @SerializedName("bank_account")
    private String bankAccount;
    @SerializedName("bank_account_name")
    private String bankAccountName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankName);
        dest.writeString(this.bankAccount);
        dest.writeString(this.bankAccountName);
    }

    public BankInformation() {
    }

    protected BankInformation(Parcel in) {
        this.bankName = in.readString();
        this.bankAccount = in.readString();
        this.bankAccountName = in.readString();
    }

    public static final Parcelable.Creator<BankInformation> CREATOR = new Parcelable.Creator<BankInformation>() {
        @Override
        public BankInformation createFromParcel(Parcel source) {
            return new BankInformation(source);
        }

        @Override
        public BankInformation[] newArray(int size) {
            return new BankInformation[size];
        }
    };
}
