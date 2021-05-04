package id.weplus.model;

public class PartnerTravel{
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
