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

//        connectionTest.executeQuerySelect(connection);
//
//        ResultSet resultSet = connectionTest.executeQueryDOB(1998);
//        connectionTest.processResultSet(resultSet);

        connectionTest.executeQueryInsert(25,"44012252G","Isaac","Diez","Peris","Barcelona","C/Briz","628759641","1977-04-11","H","Alumno");

        connectionTest.executeQuerySelect(connection);


    }





}