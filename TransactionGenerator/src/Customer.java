public class Customer {
    private final String customerId;
    private final String name;
    private final String address;

    public Customer(String customerId, String name, String address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return
                customerId + "," + name + "," + address;
    }
}

