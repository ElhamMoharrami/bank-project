import java.util.*;

public class CustomerGenerator {
    private static final List<String> fName = Arrays.asList("Julian", "Dante", "Jacks", "Scarlet", "Tella", "Nicolas");
    private static final List<String> lName = Arrays.asList("Santos", "Dragna", "Duarte", "Green", "Blake", "Roans");
    private static final List<Customer> customers = new ArrayList<>();

    public static void generateCustomers(int accountCount) {
        for (int i = 1; i <= accountCount; i++) {
            String customerId = String.valueOf(i);
            Random random = new Random();
            String name = fName.get(random.nextInt(fName.size())) + " " + lName.get(random.nextInt(lName.size()));
            long address = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
            customers.add(new Customer(customerId, name, String.valueOf(address)));
        }
    }

    public static List<Customer> getCustomers() {
        return customers;
    }
}
