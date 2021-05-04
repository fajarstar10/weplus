package id.weplus.model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    private String code;
    private boolean status;
    private String message;
    private LoginData data;

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

    @Override
    public String toString() {
        return "RegisterModel{" +
                "code='" + code + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public class RegisterData {
        private String key;
        private int user_id;
        @SerializedName("fullname")
        private String name;
        private String email;
        private String phone;
        private String image;
        private boolean is_confirm;
        private String otp;

        private String api_token;

        @Override
        public String toString() {
            return "RegisterData{" +
                    "key='" + key + '\'' +
                    ", user=" + getUser() +
                    ", otp='" + otp + '\'' +
                    '}';
        }

        public LoginData toLoginData(){
           return new LoginData();
        }

        public String getName() {
            return name;
        }

        public void setName (String fullname) {
            this.name = fullname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail (String emailprofile) {
            this.email = emailprofile;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone (String phone) {
            this.phone = phone;
        }

        public String getApi_token() {
            return api_token;
        }

        public void setApi_token(String api_token) {
            this.api_token = api_token;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public User getUser() {
            return new User(user_id,name,email,phone,image,is_confirm);
        }

//        public void setUser(User user) {
//            this.user = user;
//        }

    }

    public class User {
        private int user_id;
        private String name;
        private String email;
        private String phone;
        private String image;
        private boolean is_confirm;

        public User(int user_id,String name,String email,String phone,String image,boolean isConfirm){
            this.user_id=user_id;
            this.name=name;
            this.email=email;
            this.phone=phone;
            this.image=image;
            this.is_confirm=isConfirm;
        }

        @Override
        public String toString() {
            return "User{" +
                    "user_id=" + user_id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", image='" + image + '\'' +
                    ", is_confirm=" + is_confirm +
                    '}';
        }

        public boolean isIs_confirm() {
            return is_confirm;
        }

        public void setIs_confirm(boolean is_confirm) {
            this.is_confirm = is_confirm;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}


