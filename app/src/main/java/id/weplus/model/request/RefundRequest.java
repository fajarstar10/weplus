package id.weplus.model.request;

public class RefundRequest {
    private String order_code;
    private String payment_method;
    private String bank_name;
    private String bank_branch;
    private String account_no;
    private String account_name;

    public RefundRequest() {
    }

    public RefundRequest(String order_code, String payment_method, String bank_name, String bank_branch, String account_no, String account_name) {
        this.order_code = order_code;
        this.payment_method = payment_method;
        this.bank_name = bank_name;
        this.bank_branch = bank_branch;
        this.account_no = account_no;
        this.account_name = account_name;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
}
