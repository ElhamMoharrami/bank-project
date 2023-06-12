package com.elham.bankproject.loader;

public class AccountsTableCreator extends TableCreator {
    public void createTable() {
        String accountCreateQ = "CREATE TABLE IF NOT EXISTS accounts" + "(customer_id bigint,account_id bigint" +
                " primary key )";
        super.executeCreate(accountCreateQ, "accounts");
    }
}
