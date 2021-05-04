package id.weplus.model.request;

public class Banner {
    private int id;
    private String name;
    private String image;
    private String type;
    private String status;
    private String url_redirect;

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", url_rediredt=" + url_redirect +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName() {
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

    public void setType() {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus() {
        this.status = status;
    }

    public String getUrl_redirect() {
        return url_redirect;
    }

    public void setUrl_redirect() {
        this.url_redirect = url_redirect;
    }

}
