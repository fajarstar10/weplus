package id.weplus.model;

public class CarAccessories{
    private String label;
    private boolean isChecked;
    private String imgUrl;

    public CarAccessories(String label, boolean isChecked) {
        this.label = label;
        this.isChecked = isChecked;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
