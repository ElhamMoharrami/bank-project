public class Transaction {
    private final String[] transactionData;

    public Transaction(String[] transactionData) {
        this.transactionData = transactionData;
    }
    public String getAmount(){
        return transactionData[1];
    }

    public String getSrcAcc(){
        return transactionData[2];
    }
    public String getDestAcc(){
        return transactionData[3];
    }
}
