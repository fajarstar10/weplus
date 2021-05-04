package id.weplus.model;

import java.util.List;

public class BillPaymentTransactionListModel {
    private String code;
    private boolean status;
    private String message;
    public List<BillPaymentTransactionList> data;

    @Override
    public String toString() {
        return "BillPaymentTransactionListModel {" +
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

    public List<BillPaymentTransactionList> getData() {
        return data;
    }

    public void setData(List<BillPaymentTransactionList> data) {
        this.data = data;
    }

    public class BillPaymentTransactionList {
        private int id;
        private String order_code;
        private String bill_number;
        private String invoice_code;
        private String nominal;
        private String discount;
        private String admin_fee;
        private String processing_fee;
        private String total;
        private String payment_channel;
        private String status_payment;
        private String status_vendor;
        public InquiryData inquiry;
        private String product_id;
        private String product_name;
        private String image;
        private String date_start;
        private String date_end;
        private String result;
        private String category_id;
        private String category_name;
        private String code;

        @Override
        public String toString() {
            return "BillPaymentTransactionListData {" +
                    "id='" + id + '\'' +
                    ", order_code=" + order_code +
                    ", bill_number='" + bill_number + '\'' +
                    ", invoice_code=" + invoice_code +
                    ", nominal=" + nominal +
                    ", discount='" + discount + '\'' +
                    ", admin_fee=" + admin_fee +
                    ", processing_fee='" + processing_fee + '\'' +
                    ", total=" + total +
                    ", payment_channel=" + payment_channel +
                    ", status_payment='" + status_payment + '\'' +
                    ", status_vendor=" + status_vendor +
                    ", inquiry=" + inquiry +
                    ", product_id=" + product_id +
                    ", product_name='" + product_name + '\'' +
                    ", image=" + image +
                    ", date_start='" + date_start + '\'' +
                    ", date_end=" + date_end +
                    ", result=" + result +
                    ", category_id='" + category_id + '\'' +
                    ", category_name=" + category_name +
                    ", code=" + code +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getBill_number() {
            return bill_number;
        }

        public void setBill_number(String bill_number) {
            this.bill_number = bill_number;
        }

        public String getInvoice_code() {
            return invoice_code;
        }

        public void setInvoice_code(String invoice_code) {
            this.invoice_code = invoice_code;
        }

        public String getNominal() {
            return nominal;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getAdmin_fee() {
            return admin_fee;
        }

        public void setAdmin_fee(String admin_fee) {
            this.admin_fee = admin_fee;
        }

        public String getProcessing_fee() {
            return processing_fee;
        }

        public void setProcessing_fee(String processing_fee) {
            this.processing_fee = processing_fee;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPayment_channel() {
            return payment_channel;
        }

        public void setPayment_channel(String payment_channel) {
            this.payment_channel = payment_channel;
        }

        public String getStatus_payment() {
            return status_payment;
        }

        public void setStatus_payment(String status_payment) {
            this.status_payment = status_payment;
        }

        public String getStatus_vendor() {
            return status_vendor;
        }

        public void setStatus_vendor(String status_vendor) {
            this.status_vendor = status_vendor;
        }

        public InquiryData getInquiry() {
            return inquiry;
        }

        public void setData(InquiryData inquiry) {
            this.inquiry = inquiry;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id (String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name (String product_name) {
            this.product_name = product_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage (String image) {
            this.image = image;
        }

        public String getDate_start() {
            return date_start;
        }

        public void setDate_start (String date_start) {
            this.date_start = date_start;
        }

        public String getDate_end() {
            return date_end;
        }

        public void setDate_end (String date_end) {
            this.date_end = date_end;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result= result;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id= category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name= category_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code= code;
        }

        public class InquiryData {
            private String rq_uuid;
            private String rs_datetime;
            private String error_code;
            private String error_desc;
            private String order_id;
            private String amount;
            private String bill_amount;
            private String admin_fee;
            public CustomerData data;
            public PaymentData data_payment;



            @Override
            public String toString() {
                return "InquiryData {" +
                        "rq_uuid='" + rq_uuid + '\'' +
                        ", rs_datetime=" + rs_datetime +
                        ", error_code='" + error_code + '\'' +
                        ", error_desc=" + error_desc +
                        ", order_id='" + order_id + '\'' +
                        ", amount=" + amount +
                        ", bill_amount='" + bill_amount + '\'' +
                        ", admin_fee=" + admin_fee +
                        ", data=" + data +
                        ", data_payment=" + data_payment +
                        '}';
            }

            public String getRq_uuid() {
                return rq_uuid;
            }

            public void setRq_uuid(String rq_uuid) {
                this.rq_uuid = rq_uuid;
            }

            public String getRs_datetime() {
                return rs_datetime;
            }

            public void setRs_datetime(String rs_datetime) {
                this.rs_datetime = rs_datetime;
            }

            public String getError_code() {
                return error_code;
            }

            public void setError_code(String error_code) {
                this.error_code = error_code;
            }

            public String getError_desc() {
                return error_desc;
            }

            public void setError_desc(String error_desc) {
                this.error_desc = error_desc;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getBill_amount() {
                return bill_amount;
            }

            public void setBill_amount(String bill_amount) {
                this.bill_amount = bill_amount;
            }

            public String getAdmin_fee() {
                return admin_fee;
            }

            public void setAdmin_fee(String admin_fee) {
                this.admin_fee = admin_fee;
            }

            public CustomerData getData() {
                return data;
            }

            public void setData(CustomerData data) {
                this.data = data;
            }

            public PaymentData getData_payment() {
                return data_payment;
            }

            public void setData(PaymentData data_payment) {
                this.data_payment = data_payment;
            }

        }

        public class CustomerData {
            private String customer_id;
            private String customer_name;
            private String total_bill;
            private String bill_period;
            private String bill_amount;
            private String admin_fee;

            @Override
            public String toString() {
                return "CustomerData {" +
                        "customer_id='" + customer_id + '\'' +
                        ", customer_name=" + customer_name +
                        ", total_bill='" + total_bill + '\'' +
                        ", bill_period=" + bill_period +
                        ", bill_amount='" + bill_amount + '\'' +
                        ", admin_fee=" + admin_fee +
                        '}';
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getCustomer_name() {
                return customer_name;
            }

            public void setCustomer_name(String customer_name) {
                this.customer_name = customer_name;
            }

            public String getTotal_bill() {
                return total_bill;
            }

            public void setTotal_bill(String total_bill) {
                this.total_bill = total_bill;
            }

            public String getBill_period() {
                return bill_period;
            }

            public void setBill_period(String bill_period) {
                this.bill_period = bill_period;
            }

            public String getBill_amount() {
                return bill_amount;
            }

            public void setBill_amount(String bill_amount) {
                this.bill_amount = bill_amount;
            }

            public String getAdmin_fee() {
                return admin_fee;
            }

            public void setAdmin_fee(String admin_fee) {
                this.admin_fee = admin_fee;
            }
        }

        public class PaymentData {
            private String va_number;
            private String customer_id;
            private String customer_name;
            private String family_number;
            private String bill_period;
            private String bill_amount;
            private String admin_fee;
            private String amount;
            private String remain_payment;
            private String reff_code;
            private String branch_code;
            private String branch_name;
            private String remark;

            @Override
            public String toString() {
                return "PaymentData {" +
                        "va_number='" + va_number + '\'' +
                        ", customer_id=" + customer_id +
                        ", customer_name='" + customer_name + '\'' +
                        ", family_number=" + family_number +
                        ", bill_period='" + bill_period + '\'' +
                        ", bill_amount=" + bill_amount +
                        ", admin_fee=" + admin_fee +
                        ", amount='" + amount + '\'' +
                        ", remain_payment=" + remain_payment +
                        ", reff_code='" + reff_code + '\'' +
                        ", branch_code=" + branch_code +
                        ", branch_name='" + branch_name + '\'' +
                        ", remark=" + remark +
                        '}';
            }

            public String getVa_number() {
                return va_number;
            }

            public void setVa_number (String va_number) {
                this.va_number = va_number;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id (String customer_id) {
                this.customer_id = customer_id;
            }

            public String getCustomer_name() {
                return customer_name;
            }

            public void setCustomer_name (String customer_name) {
                this.customer_name = customer_name;
            }

            public String getFamily_number() {
                return family_number;
            }

            public void setFamily_number (String family_number) {
                this.family_number = family_number;
            }

            public String getBill_period() {
                return bill_period;
            }

            public void setBill_period (String bill_period) {
                this.bill_period = bill_period;
            }

            public String getBill_amount() {
                return bill_amount;
            }

            public void setBill_amount (String bill_amount) {
                this.bill_amount = bill_amount;
            }

            public String getAdmin_fee() {
                return admin_fee;
            }

            public void setAdmin_fee (String admin_fee) {
                this.admin_fee = admin_fee;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount (String amount) {
                this.amount = amount;
            }
            public String getRemain_payment() {
                return remain_payment;
            }

            public void setRemain_payment (String remain_payment) {
                this.remain_payment = remain_payment;
            }
            public String getReff_code() {
                return reff_code;
            }

            public void setReff_code (String reff_code) {
                this.reff_code = reff_code;
            }
            public String getBranch_code() {
                return branch_code;
            }

            public void setBranch_code (String branch_code) {
                this.branch_code= branch_code;
            }
            public String getBranch_name() {
                return branch_name;
            }

            public void setBranch_name (String branch_name) {
                this.branch_name= branch_name;
            }
            public String getRemark() {
                return remark;
            }

            public void setRemark (String remark) {
                this.remark= remark;
            }
        }
    }
}
