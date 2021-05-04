package id.weplus.model;

public class VoucherDetailModel {
    private String code;
    private boolean status;
    private String message;
    public VoucherDetailData data;

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

    public VoucherDetailData getData() {
        return data;
    }

    public void setData(VoucherDetailData data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "VoucherDetail{" +
                "code='" + code + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public class VoucherDetailData {
        private int id;
        private int type;
        private String title;
        private String code;
        private String expired_date;
        private String desc;
        private String tnc;
        private String usage;
        private String image_url;
        private String voucher_redeem;
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getExpired_date() {
            return expired_date;
        }

        public void setExpired_date(String expired_date) {
            this.expired_date = expired_date;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTnc() {
            return tnc;
        }

        public void setTnc(String tnc) {
            this.tnc = tnc;
        }

        public String getUsage() {
            return usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getVoucher_redeem() {
            return voucher_redeem;
        }

        public void setVoucher_redeem(String voucher_redeem) {
            this.voucher_redeem = voucher_redeem;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
