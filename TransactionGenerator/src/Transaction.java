public class Transaction {
    private final long time;
    private final double amount;
    private final int accAId;
    private final int accBId;
    private final String type;

    public Transaction(long time, double amount, int accAId, int accBId, String type) {
        this.time = time;
        this.amount = amount;
        this.accAId = accAId;
        this.accBId = accBId;
        this.type = type;
    }

    @Override
    public String toString() {
        return time + "," + amount + "," + accAId + "," + accBId + "," + type ;
    }
}
