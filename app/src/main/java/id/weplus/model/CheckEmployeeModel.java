package id.weplus.model;

public class CheckEmployeeModel {
    private String code;
    private boolean status;
    private String message;
    public CheckEmployeeData data;

    @Override
    public String toString() {
        return "CheckEmployeeModel {" +
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


    public CheckEmployeeData getData() {
        return data;
    }

    public void setData(CheckEmployeeData data) {
        this.data = data;
    }

    public class CheckEmployeeData {
        private int id;
        private String nik;
        private String partner_id;
        private String status;

        @Override
        public String toString() {
            return "CheckEmployeeModel {" +
                    "id ='" + id + '\'' +
                    ",name ='" + nik + '\'' +
                    ", image ='" + partner_id + '\'' +
                    ", show_on_apps ='" + status + '\'' +
                    '}';
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNik() {
            return nik;
        }

        public void setNik (String nik) {
            this.nik = nik;
        }

        public String getPartner_id() {
            return partner_id;
        }

        public void setPartner_id (String partner_id) {
            this.partner_id = partner_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus (String status) {
            this.status = status;
        }
    }
}
