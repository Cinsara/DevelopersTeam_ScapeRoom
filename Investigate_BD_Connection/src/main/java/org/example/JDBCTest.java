package org.example;

import java.sql.*;

public class JDBCTest {
    private static final String URL = "jdbc:mysql://localhost:3306/universidad";
    private static final String USER = "root";
    private static final String PASSWORD = "Java2024";

    public static Connection getConnection(){

        try  {
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);

            if (connection !=null){
                System.out.println("Connected to the database!");
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void getConnectionWithResources(){

        try (Connection connection = DriverManager.getConnection(URL,USER,PASSWORD)) {
            if (connection !=null){
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    public void createStatementExample (Connection connection) {
//        try (Statement statement = connection.createStatement()){
//            //queries
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }
//    }

    public ResultSet executeQueryDOB(int year) {

        ResultSet resultSet = null;

        String query = "SELECT * FROM persona WHERE fecha_nacimiento LIKE \""+ year +"%\";";

        try  {
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);

            if (connection !=null){
                System.out.println("Connected to the database!");
            }

            Statement statement = connection.createStatement();
            return resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void executeQuerySelect(Connection connection) {

        ResultSet resultSet = null;

        String query = "SELECT * FROM persona";

        try  {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                System.out.println("id: " + resultSet.getString(1) + ", " +
                        "DNI: " + resultSet.getString(2) + ", " +
                        "nombre: " + resultSet.getString(3) + ", " +
                        "apellido1: " + resultSet.getString(4) + ", " +
                        "apellido2: " + resultSet.getString(5) + ", " +
                        "ciudad: " + resultSet.getString(6) + ", " +
                        "direccion: " + resultSet.getString(7) + ", " +
                        "telefono: " + resultSet.getString(8) + ", " +
                        "dob: " + resultSet.getString(9) + ", " +
                        "sexo: " + resultSet.getString(10) + ", " +
                        "tipo: " + resultSet.getString(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }


    public void processResultSet(ResultSet resultSet){
        try {
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido1 = resultSet.getString("apellido1");
                String dob = resultSet.getString("fecha_nacimiento");
                System.out.println("Nombre: " + nombre + ", apellido: " + apellido1 + ", fecha_nacimiento: " + dob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
