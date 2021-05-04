package id.weplus.model;

import java.util.List;

public class BillPaymentProductModel {
    private String code;
    private boolean status;
    private String message;
    public BillPaymentProductData data;

    @Override
    public String toString() {
        return "BillPaymentProduct{" +
                "code='" + code + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

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

    public BillPaymentProductData getData() {
        return data;
    }

    public void setData(BillPaymentProductData data) {
        this.data = data;
    }



    public class BillPaymentProductData {
        private int id;
        private String name;
        private String status;
        private String image;
        private String desc;
        public List<PaymentProduct> product;

        @Override
        public String toString() {
            return "BillPaymentProductData{" +
                    "id='" + id + '\'' +
                    ", name=" + name +
                    ", status='" + status + '\'' +
                    ", image=" + image +
                    ", desc='" + desc + '\'' +
                    ", product" + product +
                    '}';
        }

        public int Id() {
            return id;
        }

        public void setId (int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage (String image) {
            this.image = image;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List <PaymentProduct> getProduct() {
            return product;
        }

        public void setProduct(List<PaymentProduct> product) {
            this.product = product;
        }

    }

    public class PaymentProduct{
        private int id;
        private String name;
        private String nominal;
        private String image;
        private String alias;
        private String code;

        @Override
        public String toString() {
            return "PaymentProduct{" +
                    "id='" + id + '\'' +
                    ", name=" + name +
                    ", nominal='" + nominal + '\'' +
                    ", image=" + image +
                    ", alias='" + alias + '\'' +
                    ", code" + code +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNominal() {
            return nominal;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getCode() {
            return code;
        }

        public void setCode (String code) {
            this.code = code;
        }
    }
}
