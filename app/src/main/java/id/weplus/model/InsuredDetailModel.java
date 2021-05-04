package id.weplus.model;

import java.util.List;

public class InsuredDetailModel {
    private String code;
    private boolean status;
    private String message;
    public InsuredData data;

    @Override
    public String toString() {
        return "InsuredListModel{" +
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


    public InsuredData getData() {
        return data;
    }

    public void setData(InsuredData data) {
        this.data = data;
    }

    public class InsuredData {
        private int id;
        private String category_id;
        private String partner_id;
        private String category_name;
        private String order_code;
        private String no_polis;
        private String date_active;
        private boolean status;
        public ProductDetailData product_detail;
        public InsuredDetailData insured_detail;
        public ItemsDetailData items_details;
        public BeneficiaryDetailData beneficiary_detail;
        public DetailPaymentData detail_payment;

        @Override
        public String toString() {
            return "InsuredDetailData{" +
                    "id='" + id + '\'' +
                    ", category_id=" + category_id +
                    ", partner_id='" + partner_id + '\'' +
                    ", category_name=" + category_name +
                    ", order_code=" + order_code +
                    ", no_polis='" + no_polis + '\'' +
                    ", date_active=" + date_active +
                    ", status=" + status +
                    ", product_detail='" + product_detail + '\'' +
                    ", insured_detail=" + insured_detail +
                    ", items_details=" + items_details +
                    ", beneficiary_detail='" + beneficiary_detail + '\'' +
                    ", detail_payment=" + detail_payment +
                    '}';
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getPartner_id() {
            return partner_id;
        }

        public void setPartner_id(String partner_id) {
            this.partner_id = partner_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name (String category_name) {
            this.category_name = category_name;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code (String order_code) {
            this.order_code = order_code;
        }

        public String getNo_polis() {
            return no_polis;
        }

        public void setNo_polis (String no_polis) {
            this.no_polis = no_polis;
        }

        public String getDate_active() {
            return date_active;
        }

        public void setDate_active (String date_active) {
            this.date_active = date_active;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }


        public ProductDetailData getProduct_detail() {
            return product_detail;
        }

        public void setProduct_detail (ProductDetailData product_detail) {
            this.product_detail = product_detail;
        }

        public InsuredDetailData getInsured_detail() {
            return insured_detail;
        }

        public void setInsured_detail (InsuredDetailData insured_detail) {
            this.insured_detail = insured_detail;
        }

        public ItemsDetailData getItems_details() {
            return items_details;
        }

        public void setItems_details(ItemsDetailData items_details) {
            this.items_details = items_details;
        }

        public BeneficiaryDetailData getBeneficiary_detail() {
            return beneficiary_detail;
        }

        public void setBeneficiary_detail (BeneficiaryDetailData beneficiary_detail) {
            this.beneficiary_detail = beneficiary_detail;
        }

        public DetailPaymentData getDetail_payment() {
            return detail_payment;
        }

        public void setDetail_payment(DetailPaymentData detail_payment) {
            this.detail_payment = detail_payment;
        }
    }
    public class ProductDetailData{
        private String product_name;
        private String image_url;
        private String benefit;

        @Override
        public String toString() {
            return "ProductDetailData{" +
                    "product_name='" + product_name + '\'' +
                    ", image_url=" + image_url +
                    ", benefit='" + benefit + '\'' +
                    '}';
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getBenefit() {
            return benefit;
        }

        public void setBenefit (String benefit) {
            this.benefit = benefit;
        }

    }

    public class InsuredDetailData{
        private String name;
        private String nik;
        private String email;
        private String phone;
        private String insured_relation;

        @Override
        public String toString() {
            return "ProductDetailData{" +
                    " name='" + name + '\'' +
                    ", nik=" + nik +
                    ", email='" + email + '\'' +
                    ", phone=" + phone +
                    ", insured_relation='" + insured_relation + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public String getNik() {
            return nik;
        }

        public void setNik (String nik) {
            this.nik = nik;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail (String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone (String phone) {
            this.phone = phone;
        }
        public String getInsured_relation() {
            return insured_relation;
        }

        public void setInsured_relation (String insured_relation) {
            this.insured_relation = insured_relation;
        }
    }

    public class ItemsDetailData{
        List <Product> product;

        @Override
        public String toString() {
            return "ItemsDetailData{" +
                    "product ='" + product +
                    '}';
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct (List<Product> product) {
            this.product = product;
        }
    }

    public class BeneficiaryDetailData{
        private String name;
        private String relasi;
        private String email;
        private String phone;

        @Override
        public String toString() {
            return "BeneficiaryDetailData{" +
                    "name ='" + name +
                    "relasi ='" + relasi +
                    "email='" + email +
                    "phone ='" + phone +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public String getRelasi() {
            return relasi;
        }

        public void setRelasi (String relasi) {
            this.relasi = relasi;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail (String email) {
            this.email = email;
        }
        public String getPhone() {
            return phone;
        }

        public void setPhone (String phone) {
            this.phone = phone;
        }

    }
    public class DetailPaymentData{
        private String date_payment;
        private String payment_channel;
        private String product_nominal;
        private String admin_fee;
        private String discount;
        private String processing_fee;
        private String total_payment;

        @Override
        public String toString() {
            return "DetailPaymentData{" +
                    "date_payment ='" + date_payment +
                    "payment_channel ='" + payment_channel+
                    "product_nominal='" + product_nominal +
                    "admin_fee ='" + admin_fee +
                    "discount ='" + discount+
                    "processing_fee='" + processing_fee +
                    "total_payment ='" + total_payment +
                    '}';
        }

        public String getDate_payment() {
            return date_payment;
        }

        public void setDate_payment (String date_payment) {
            this.date_payment = date_payment;
        }

        public String getPayment_channel() {
            return payment_channel;
        }

        public void setPayment_channel (String payment_channel) {
            this.payment_channel = payment_channel;
        }

        public String getProduct_nominal() {
            return product_nominal;
        }

        public void setProduct_nominal (String product_nominal) {
            this.product_nominal = product_nominal;
        }
        public String getAdmin_fee () {
            return admin_fee;
        }

        public void setAdmin_fee (String admin_fee) {
            this.admin_fee = admin_fee;
        }

        public String getDiscount () {
            return discount;
        }

        public void setDiscount (String discount) {
            this.discount = discount;
        }

        public String getProcessing_fee () {
            return processing_fee;
        }

        public void setProcessing_fee (String processing_fee) {
            this.processing_fee = processing_fee;
        }

        public String getTotal_payment () {
            return total_payment;
        }

        public void setTotal_payment (String total_payment) {
            this.total_payment = total_payment;
        }

    }

    public class Product{
        private int category_id;
        private String entity;
        private String name;
        private String admin_fee;
        private int id;
        private String nominal;
        private String image;
        private String benefits;
        @Override
        public String toString() {
            return "Product{" +
                    " category_id='" + category_id + '\'' +
                    ", entity=" + entity +
                    ", name='" + name + '\'' +
                    ", admin_fee=" + admin_fee +
                    ", id='" + id + '\'' +
                    ", nominal='" + nominal + '\'' +
                    ", image=" + image +
                    ", benefits='" + benefits + '\'' +
                    '}';
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdmin_fee() {
            return admin_fee;
        }

        public void setAdmin_fee (String admin_fee) {
            this.admin_fee = admin_fee;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        public String getNominal() {
            return nominal;
        }

        public void setNominal (String nominal) {
            this.nominal = nominal;
        }

        public String getImage() {
            return image;
        }

        public void setImage (String image) {
            this.image = image;
        }

        public String getBenefits() {
            return benefits;
        }

        public void setBenefits (String benefits) {
            this.benefits = benefits;
        }
    }
}
