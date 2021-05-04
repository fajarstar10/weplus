package id.weplus.model;

public class CategoryforPartnerModel {
    private String code;
    private boolean status;
    private String message;
    public CategoryforPartnerData data;

    @Override
    public String toString() {
        return "CategoryforPartner{" +
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


    public CategoryforPartnerData getData() {
        return data;
    }

    public void setData(CategoryforPartnerData data) {
        this.data = data;
    }

    public class CategoryforPartnerData {
        private int id;
        private String name;
        private String image;
        private String show_on_apps;

        @Override
        public String toString() {
            return "CategoryforPartner {" +
                    "id ='" + id + '\'' +
                    ",name ='" + name + '\'' +
                    ", image ='" + image + '\'' +
                    ", show_on_apps ='" + show_on_apps + '\'' +
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

        public String getShow_on_apps() {
            return show_on_apps;
        }

        public void setShow_on_apps(String show_on_apps) {
            this.show_on_apps = show_on_apps;
        }
    }
}

