import java.time.LocalDate;

public class Transaction {
    private final long time;
    private final double amount;
    private final String accAId;
    private final String accBId;
    private final String type;

    public Transaction(long time, double amount, String accAId, String accBId, String type) {
        this.time = time;
        this.amount = amount;
        this.accAId = accAId;
        this.accBId = accBId;
        this.type = type;
    }

    @Override
    public String toString() {
        return time + "," + amount + "," + accAId + "," + accBId + "," + type + "\n";
    }
}
