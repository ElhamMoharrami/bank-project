package com.elham.bankproject.loader;

public class TransactionTableCreator extends TableCreator {
    public void createTable() {
        String TransactionCreateQ = "CREATE TABLE IF NOT EXISTS transactions" + "(transaction_id bigint primary key ," +
                "transaction_time bigint,amount numeric(5,2) ,src_acc bigint,dest_acc bigint," +
                "transaction_type varchar(7) )";
        super.executeCreate(TransactionCreateQ, "transactions");
    }

}
