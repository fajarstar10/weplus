package id.weplus.model.response;

import java.util.List;

import id.weplus.model.Transaction;

public class TransactionListResponse {
    private String code;
    private boolean status;
    private String message;
    public TransactionListData data;

    @Override
    public String toString() {
        return "TransactionListModel{" +
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

    public TransactionListData getData() {
        return data;
    }

    public void setData(TransactionListData data) {
        this.data = data;
    }


    public class TransactionListData {
        private String transaksi;
        public List<Transaction> transaction;

        @Override
        public String toString() {
            return "TransactionListModel{" +
                    "transaction='" + transaction + '\'' +
                    '}';
        }

        public String getTransaksi() {
            return transaksi;
        }

        public void setTransaksi(String transaksi) {
            this.transaksi = transaksi;
        }

        public List<Transaction> getTransaction() {
            return transaction;
        }

        public void setTransaction(List<Transaction> transaction) {
            this.transaction = transaction;
        }

    }

}
