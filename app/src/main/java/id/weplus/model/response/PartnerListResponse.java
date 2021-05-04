package id.weplus.model.response;

import java.util.List;

import id.weplus.model.Partner;
import id.weplus.model.PartnerBanner;

public class PartnerListResponse {
    private String code;
    private boolean status;
    private String message;
    public PartnerListResponse.PartnerListData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PartnerListData getData() {
        return data;
    }

    public void setData(PartnerListData data) {
        this.data = data;
    }

    public class PartnerListData{
        public List<Partner> partner;
        public List<PartnerBanner> banner;

        @Override
        public String toString() {
            return "PartnerListData {" +
                    "partner ='" + partner + '\'' +
                    ",barner ='" + banner + '\'' +
                    '}';
        }

        public List<Partner> getPartner() {
            return partner;
        }

        public void setPartner(List<Partner> partner) {
            this.partner = partner;
        }

        public List<PartnerBanner> getBanner() {
            return banner;
        }

        public void setBanner(List<PartnerBanner> banner) {
            this.banner = banner;
        }
    }
}
