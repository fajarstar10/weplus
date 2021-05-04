package id.weplus.model;

import id.weplus.model.request.UpdateProfileRequest;

public class LoginData{
    private String api_token;
    private String user_id;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private String picture;
    private String dob;
    private String sex;
    private String codepos;
    private String province;
    private String city;
    private String identification_no;
    private Boolean is_confirm;

    @Override
    public String toString() {
        return "data {" +
                "api_token='" + api_token + '\'' +
                ",user_id='" + user_id + '\'' +
//                    ", name='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", picture='" + picture + '\'' +
                ", dob='" + dob + '\'' +
                ", sex='" + sex + '\'' +
                ", codepos='" + codepos + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", identification_no='" + identification_no + '\'' +
                ", is_confirm='" + is_confirm + '\'' +
                '}';
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return fullname;
    }

    public void setName(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address ;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDob() {
        return dob;
    }

    public void setDob (String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex (String sex) {
        this.sex = sex;
    }

    public String getCodepos() {
        return codepos;
    }

    public void setCodepos (String codepos) {
        this.codepos = codepos;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String image) {
        this.city = city;
    }

    public String getIdentification_no() {
        return identification_no;
    }

    public void setIdentification_no (String identification_no) {
        this.identification_no = identification_no;
    }

    public Boolean getIs_confirm() {
        return  is_confirm;
    }

    public void setIs_confirm(Boolean is_confirm) {
        this.is_confirm = is_confirm;
    }

    public UpdateProfileRequest toUpdateProfileRequest(){
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setFullname(this.fullname);
        request.setAddress(this.address);
        request.setIdentification_no(this.identification_no);
        request.setProvince(this.province);
        request.setCity(this.city);
        request.setSex(this.sex);
        request.setDob(this.dob);
        request.setZip_code(this.codepos);
        request.setProfile_picture(this.picture);

        return request;
    }
}
