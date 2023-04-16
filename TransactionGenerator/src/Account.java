public class Account {
    private final String accountId;
    private final String customerId;

    public Account(String accountId, String customerId) {
        this.accountId = accountId;
        this.customerId = customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return
                customerId+ ","+accountId;
    }
}
