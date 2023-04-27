public class Account {
    private final String[] customerAccounts;

    public Account(String[] customerAccounts) {
        this.customerAccounts = customerAccounts;
    }

    public String getCustomerId() {
        return customerAccounts[0];
    }

    public String getAccountId() {
        return customerAccounts[1];
    }

}
