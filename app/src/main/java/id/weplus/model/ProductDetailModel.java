package id.weplus.model;

public class ProductDetailModel {
    private String code;
    private boolean status;
    private String message;
    public ProductDetailData data;

    @Override
    public String toString() {
        return "ProductDetailModel{" +
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


    public ProductDetailData getData() {
        return data;
    }

    public void setData(ProductDetailData data) {
        this.data = data;
    }

    public class ProductDetailData{
        private int id;
        private String name;
        private String image;
        private String desc;
        private String general;
        private String resume;
        private String benefits;
        private String tnc;
        private String claim;
        private String duration;
        private String additional_text;
        private String admin_fee;
        private String discount;
        private String price;
        private String min_insured_age;
        private String max_insured_age;
        private String min_holder_age;
        private String max_holder_age;
        private String message_insured_age;
        private String category_id;
        private String category_name;
        private String category_image;
        private String partner_id;
        private String partner_name;
        private String partner_image;
        private String partner_address;

        @Override
        public String toString() {
            return " ProductDetailModel {" +
                    " id ='" + id + '\'' +
                    ", name ='" + name + '\'' +
                    ", image ='" + image + '\'' +
                    ", desc ='" + desc + '\'' +
                    ", general ='" + general + '\'' +
                    ", resume ='" + resume + '\'' +
                    ", benefits ='" + benefits + '\'' +
                    ", tnc ='" + tnc + '\'' +
                    ", claim ='" + claim + '\'' +
                    ", duration ='" + duration + '\'' +
                    ", additional_text ='" + additional_text + '\'' +
                    ", admin_fee ='" + admin_fee + '\'' +
                    ", discount ='" + discount + '\'' +
                    ", price ='" + price + '\'' +
                    ", min_insured_age ='" + min_insured_age + '\'' +
                    ", max_insured_age ='" + max_insured_age + '\'' +
                    ", min_holder_age ='" + min_holder_age + '\'' +
                    ", max_holder_age ='" + max_holder_age + '\'' +
                    ", message_insured_age ='" + message_insured_age + '\'' +
                    ", category_id ='" + category_id + '\'' +
                    ", category_name ='" + category_name + '\'' +
                    ", category_image ='" + category_image + '\'' +
                    ", partner_id ='" + partner_id + '\'' +
                    ", partner_name ='" + partner_name + '\'' +
                    ", partner_image ='" + partner_image + '\'' +
                    ", partner_address ='" + partner_address + '\'' +
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc (String desc) {
            this.desc = desc;
        }
        public String getGeneral() {
            return general;
        }

        public void setGeneral (String general) {
            this.general = general;
        }

        public String getResume() {
            return resume;
        }

        public void setResume (String resume) {
            this.resume = resume;
        }
        public String getBenefits() {
            return benefits;
        }

        public void setBenefits (String benefits) {
            this.benefits = benefits;
        }

        public String getTnc() {
            return tnc;
        }

        public void setTnc (String tnc) {
            this.tnc = tnc;
        }

        public String getClaim() {
            return claim;
        }

        public void setClaim (String claim) {
            this.claim = claim;
        }
        public String getDuration() {
            return duration;
        }

        public void setDuration (String duration) {
            this.duration = duration;
        }

        public String getAdditional_text() {
            return additional_text;
        }

        public void setAdditional_text (String additional_text) {
            this.additional_text = additional_text;
         }

        public String getAdmin_fee() {
            return admin_fee;
        }

        public void setAdmin_fee (String admin_fee) {
            this.admin_fee = admin_fee;
        }
        public String getDiscount() {
            return discount;
        }

        public void setDiscount (String discount) {
            this.discount = discount;
        }
        public String getPrice () {
            return price;
        }

        public void setPrice (String price) {
            this.price = price;
        }

        public String getMin_insured_age() {
            return min_insured_age;
        }

        public void setMin_insured_age (String min_insured_age) {
            this.max_insured_age = max_insured_age;
        }
        public String getMax_insured_age() {
            return max_insured_age;
        }

        public void setMax_insured_age (String max_insured_age) {
            this.max_insured_age = max_insured_age;
        }
        public String getMin_holder_age () {
            return min_holder_age;
        }

        public void setMin_holder_age (String min_holder_age) {
            this.min_holder_age = min_holder_age;
        }

        public String getMax_holder_age() {
            return max_holder_age;
        }

        public void setMax_holder_age (String max_holder_age) {
            this.max_holder_age = max_holder_age;
        }
        public String getMessage_insured_age() {
            return message_insured_age;
        }

        public void setMessage_insured_age (String message_insured_age) {
            this.message_insured_age = message_insured_age;
        }
        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id (String category_id) {
            this.category_id = category_id;
        }
        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name (String category_name) {
            this.category_name = category_name;
        }
        public String getCategory_image() {
            return category_image;
        }

        public void setCategory_image (String category_image) {
            this.category_image = category_image;
        }
        public String getPartner_id() {
            return partner_id;
        }

        public void setPartner_id (String partner_id) {
            this.partner_id = partner_id;
        }
        public String getPartner_name() {
            return partner_name;
        }

        public void setPartner_name (String partner_name) {
            this.partner_name = partner_name;
        }
        public String getPartner_image() {
            return partner_image;
        }

        public void setPartner_image (String partner_image) {
            this.partner_image = partner_image;
        }
        public String getPartner_address() {
            return partner_address;
        }

        public void setPartner_address (String partner_address) {
            this.partner_address = partner_address;
        }

    }
}
