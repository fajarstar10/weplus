package id.weplus.Query;

/**
 * value option: history or blank
 */
public class TransactionListQuery {
    private String option;

    @Override
    public String toString() {
        return "TransactionListQuery{" +
                "option='" + option + '\'' +
                '}';
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
