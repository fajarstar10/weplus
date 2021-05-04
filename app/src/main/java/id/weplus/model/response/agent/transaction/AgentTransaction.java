package id.weplus.model.response.agent.transaction;

import android.os.Parcel;
import android.os.Parcelable;

public class AgentTransaction implements Parcelable {
    private String id;
    private String order_code;
    private String product_id;
    private String categori_id;
    private String partner_id;
    private String total;
    private String status;
    private String payment_channel;
    private String date_end;
    private String product_name;
    private String img_url;
    private String commission;
    private String insured_name;

    public String getId() {
        return id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getCategori_id() {
        return categori_id;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getPayment_channel() {
        return payment_channel;
    }

    public String getDate_end() {
        return date_end;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getCommission() {
        return commission;
    }

    public String getInsured_name() {
        return insured_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.order_code);
        dest.writeString(this.product_id);
        dest.writeString(this.categori_id);
        dest.writeString(this.partner_id);
        dest.writeString(this.total);
        dest.writeString(this.status);
        dest.writeString(this.payment_channel);
        dest.writeString(this.date_end);
        dest.writeString(this.product_name);
        dest.writeString(this.img_url);
        dest.writeString(this.commission);
        dest.writeString(this.insured_name);
    }

    public AgentTransaction() {
    }

    protected AgentTransaction(Parcel in) {
        this.id = in.readString();
        this.order_code = in.readString();
        this.product_id = in.readString();
        this.categori_id = in.readString();
        this.partner_id = in.readString();
        this.total = in.readString();
        this.status = in.readString();
        this.payment_channel = in.readString();
        this.date_end = in.readString();
        this.product_name = in.readString();
        this.img_url = in.readString();
        this.commission = in.readString();
        this.insured_name = in.readString();
    }

    public static final Parcelable.Creator<AgentTransaction> CREATOR = new Parcelable.Creator<AgentTransaction>() {
        @Override
        public AgentTransaction createFromParcel(Parcel source) {
            return new AgentTransaction(source);
        }

        @Override
        public AgentTransaction[] newArray(int size) {
            return new AgentTransaction[size];
        }
    };
}
