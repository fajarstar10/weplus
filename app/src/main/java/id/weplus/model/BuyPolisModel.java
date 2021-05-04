package id.weplus.model;

import java.util.List;

public class BuyPolisModel {
    private String code;
    private boolean status;
    private String message;
    public BuyPolisData data;

    @Override
    public String toString() {
        return "BuyPolisModel{" +
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

    public BuyPolisData getData() {
        return data;
    }

    public void setData(BuyPolisData data) {
        this.data = data;
    }

    public class BuyPolisData {
        private String polis;
        private String perusahaan;
        public List <Categori> category;
        public List <Partner> partner;

        @Override
        public String toString() {
            return "BuyPolis{" +
                    "category ='" + category +
                    "partner ='" + partner +
                    '}';
        }

        public String getPerusahaan(){
            return perusahaan;
        }
        public void setPerusahaan (String perusahaanpartner){
            this.perusahaan = perusahaanpartner;
        }

        public String getPolis() {
            return polis;
        }

        public void setPolis (String polis) {
            this.polis = polis ;
        }

        public List<Categori> getCategory() {
            return category;
        }

        public void setCategory(List<Categori> categori) {
            this.category = categori;
        }

        public List<Partner> getPartner() {
            return partner;
        }

        public void setPartner (List<Partner> partner) {
            this.partner = partner;
        }
    }

    public class Categori {
        private int id;
        private String name;
        private String image;
        private String show_on_apps;


        @Override
        public String toString() {
            return "category {" +
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

    public class Partner {
        private int id;
        private String name;
        private String image;
        private String show_on_apps;

        @Override
        public String toString() {
            return "partner {" +
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
