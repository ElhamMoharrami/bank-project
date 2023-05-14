package com.elham.bankproject.loader;

import com.elham.bankproject.common.ConnectDB;
import com.elham.bankproject.searcher.FileSearcher;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
    private final ConnectDB createConnection ;

    public CreateTable() {
        this.createConnection= new ConnectDB();
    }

    public void createTable() {
        BasicConfigurator.configure();
        Logger log = Logger.getLogger(FileSearcher.class.getName());
        try {
            Connection connection = createConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS customers" + "(customer_id varchar(10) primary key ,customer_name varchar(200) ,post_address varchar(10))");
            statement.execute("CREATE TABLE IF NOT EXISTS accounts" + "(customer_id varchar(10),account_id varchar(10))");
            statement.execute("CREATE TABLE IF NOT EXISTS transactions" + "(transaction_time varchar(600),amount numeric(5,2) ,src_acc varchar(10),dest_acc varchar(10),transaction_type varchar(7) )");
        } catch (Exception e) {
            log.error("issue at create table");
        }
    }
}
