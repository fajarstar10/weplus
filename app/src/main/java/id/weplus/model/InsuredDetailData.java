package id.weplus.model;

public class InsuredDetailData {
    private String fullname;
    private String email;
    private String phone;
    private String insured_relation;
    private String province;
    private String job_declaration;
    private String dob;
    private String sex;
    private String identification_no;
    private String city;
    private String pob;
    private String heigth;
    private String weight;
    private String adrress;


    @Override
    public String toString() {
        return "ProductDetailData{" +
                " fullname='" + fullname + '\'' +
                ", identification_no=" + identification_no +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", insured_relation='" + insured_relation + '\'' +
                ", job_declaration='" + job_declaration + '\'' +
                ", dob='" + dob + '\'' +
                ", sex='" + sex + '\'' +
                ", city='" + city + '\'' +
                ", pob='" + pob + '\'' +
                ", heigth='" + heigth + '\'' +
                ", weight='" + weight + '\'' +
                ", adrress='" + adrress + '\'' +
                '}';
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
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

    public String getInsured_relation() {
        return insured_relation;
    }

    public void setInsured_relation(String insured_relation) {
        this.insured_relation = insured_relation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getJob_declaration() {
        return job_declaration;
    }

    public void setJob_declaration(String job_declaration) {
        this.job_declaration = job_declaration;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentification_no() {
        return identification_no;
    }

    public void setIdentification_no(String identification_no) {
        this.identification_no = identification_no;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getHeigth() {
        return heigth;
    }

    public void setHeigth(String heigth) {
        this.heigth = heigth;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAdrress() {
        return adrress;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }
}
