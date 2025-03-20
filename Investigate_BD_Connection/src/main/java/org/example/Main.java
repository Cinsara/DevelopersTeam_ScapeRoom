package org.example;

import java.sql.Connection;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JDBCTest connectionTest = new JDBCTest();


        Connection connection = connectionTest.getConnection();

        connectionTest.executeQuerySelect(connection);

        ResultSet resultSet = connectionTest.executeQueryDOB(1998);
        connectionTest.processResultSet(resultSet);



    }





}