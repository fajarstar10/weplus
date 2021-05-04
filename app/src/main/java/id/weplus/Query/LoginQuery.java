package id.weplus.Query;

public class LoginQuery {

    private String email;
    private  String password;
//    private  String source;

    @Override
    public String toString() {
        return "LoginQuery{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setSource(String source) {
//        this.source = source;
//    }
}
