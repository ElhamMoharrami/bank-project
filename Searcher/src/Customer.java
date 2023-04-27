public class Customer {
    private final String[] userData;

    public Customer(String[] userData) {
        this.userData = userData;
    }

    public String getCustomerId() {
        return userData[0];
    }

    public String getCustomerName() {
        return userData[1];
    }
}