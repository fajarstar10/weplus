package id.weplus.model;

import java.util.List;

public class ProductListModel {
    private String code;
    private boolean status;
    private String message;
    public ProductListData data;

    @Override
    public String toString() {
        return "ProductListModel{" +
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

    public ProductListData getData() {
        return data;
    }

    public void setData(ProductListData data) {
        this.data = data;
    }

    public class ProductTravel{
        private int id;
        private String name;
        private String image;
        private String desc;
        private String additional_text;
        private String price;
        private String category_id;
        private String partner_id;

        @Override
        public String toString() {
            return "partner {" +
                    "id ='" + id + '\'' +
                    ",name ='" + name + '\'' +
                    ", image ='" + image + '\'' +
                    ", desc ='" + desc + '\'' +
                    ", additional_text ='" + additional_text + '\'' +
                    ", price ='" + price + '\'' +
                    ", category_id ='" + category_id + '\'' +
                    ", partner_id ='" + partner_id + '\'' +
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

        public String getDesc () {
            return desc;
        }

        public void setDesc (String desc) {
            this.desc = desc;
        }

        public String getAdditional_text () {
            return additional_text;
        }

        public void setAdditional_text (String additional_text) {
            this.additional_text = additional_text;
        }

        public String getPrice () {
            return price;
        }

        public void setPrice (String price) {
            this.price = price;
        }

        public String getCategory_id () {
            return category_id;
        }

        public void setCategory_id (String category_id) {
            this.category_id = category_id;
        }

        public String getPartner_id () {
            return partner_id;
        }

        public void setPartner_id(String partner_id) {
            this.partner_id = partner_id ;
        }
    }
}
