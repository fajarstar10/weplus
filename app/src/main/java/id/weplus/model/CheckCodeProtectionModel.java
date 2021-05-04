package id.weplus.model;

import java.util.List;

public class CheckCodeProtectionModel {
    private String code;
    private boolean status;
    private String message;
    public CheckCodeProtectionData data;

    @Override
    public String toString() {
        return "CheckCodeProtection{" +
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

    public CheckCodeProtectionData getData() {
        return data;
    }

    public void setData(CheckCodeProtectionData data) {
        this.data = data;
    }

    public class CheckCodeProtectionData{
        private String code;
        private String type_protection;
        private String category_id;
        private String product_id;
        private String partner_id;
        private String max_age;
        private String min_age;
        private String duration;
        private String type_duration;
        private String sex;
        public List<Country> country;
        public List<City> city;
        private String tnc;
        private String relation;

        @Override
        public String toString() {
            return "CheckCodeProtectionData{" +
                    "code='" + code + '\'' +
                    ", type_protection=" + type_protection +
                    ", category_id='" + category_id + '\'' +
                    ", product_id=" + product_id +
                    ", partner_id=" + partner_id +
                    ", max_age='" + max_age + '\'' +
                    ", min_age=" + min_age +
                    ", duration=" + duration +
                    ", type_duration='" + type_duration + '\'' +
                    ", sex=" + sex +
                    ", country=" + country +
                    ", city='" + city + '\'' +
                    ", tnc=" + tnc +
                    ", relation=" + relation +
                    '}';
        }

        public String getCode() {
            return code;
        }

        public void setCode (String code) {
            this.code = code;
        }

        public String getType_protection() {
            return type_protection;
        }

        public void setType_protection (String type_protection) {
            this.type_protection = type_protection;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id (String category_id) {
            this.category_id = category_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id (String product_id) {
            this.product_id = product_id;
        }

        public String getPartner_id() {
            return partner_id;
        }

        public void setPartner_id (String partner_id) {
            this.partner_id = partner_id;
        }

        public String getMax_age() {
            return max_age;
        }

        public void setMax_age (String max_age) {
            this.max_age = max_age;
        }

        public String getMin_age() {
            return min_age;
        }

        public void setMin_age (String min_age) {
            this.min_age = min_age;
        }

        public String getDuration() {
            return duration ;
        }

        public void setDuration (String duration) {
            this.duration = duration;
        }

        public String getType_duration() {
            return type_duration ;
        }

        public void setType_duration (String type_duration) {
            this.type_duration = type_duration;
        }

        public String getSex() {
            return sex ;
        }

        public void setSex (String sex) {
            this.sex = sex;
        }

        public List<Country> getCountry() {
            return country;
        }

        public void setCountry (List<Country> country) {
            this.country = country;
        }

        public List<City> getCity() {
            return city;
        }

        public void setCity (List<City> city) {
            this.city = city;
        }

        public String getTnc() {
            return tnc ;
        }

        public void setTnc (String tnc) {
            this.tnc = tnc;
        }

        public String getRelation() {
            return relation ;
        }

        public void setRelation (String relation) {
            this.relation = relation;
        }
    }

    public class Country{

    }

    public class City{
        private String province;
        private String city;

        @Override
        public String toString() {
            return "City{" +
                    "province='" + province + '\'' +
                    ", city=" + city +
                    '}';
        }

        public String getProvince() {
            return province;
        }

        public void setProvince (String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity (String city) {
            this.city = city;
        }
    }

}
