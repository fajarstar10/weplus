package id.weplus.model;

public class PartnerBanner {
    private int id;
    private String name;
    private String image;
    private String type;
    private boolean status;
    private String url_redirect;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrl_redirect() {
        return url_redirect;
    }

    public void setUrl_redirect(String url_redirect) {
        this.url_redirect = url_redirect;
    }
}
