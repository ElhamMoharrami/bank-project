package com.elham.bankproject.loader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerTableCreator extends TableCreator {
    private static final Logger logger = LogManager.getLogger(CustomerTableCreator.class);

    public void createTable() {
        String customerCreateQ = "CREATE TABLE IF NOT EXISTS customers" + "(customer_id bigint primary key ," +
                "customer_name varchar(200) ,post_address varchar(10))";
        super.executeCreate(customerCreateQ, "customers");
    }
}
