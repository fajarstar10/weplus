package id.weplus.detailpolis;

public class DataMobilModel {
    private String id;
    private String img;
    private String title;
    private String desc;
    private String note;
    private String name;
    private String url;

    @Override
    public String toString() {
        return "DataMobilModel{" +
                "img=" + img +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
