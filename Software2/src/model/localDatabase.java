/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import javafx.collections.ObservableList;


/**
 *
 * @author LabUser
 */
public class localDatabase {
    private static String url = "jdbc:mysql://localhost:3306/client_schedule";
    private static String user = "sqlUser";
    private static String pass = "Passw0rd!";
    private static Connection database;
    public static void openConnection() throws SQLException{
        database = DriverManager.getConnection(url, user, pass);
    }
    public static void closeConnection() throws SQLException{
        database.close();
    }
    
    public static void populateCustomerTable(String string) throws SQLException{
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery(string);
       
        while (myrs.next()){
            String id = myrs.getString("Customer_Id");
            String name = myrs.getString("Customer_Name");
            String address = myrs.getString("Address");
            String postalCode = myrs.getString("Postal_Code");
            String phone = myrs.getString("Phone");
            String divisionId = myrs.getString("Division_ID");
            model.Customer.addCustomer(new Customer(id,name,address,postalCode,phone,divisionId));
        }      
    }
    public static void populateAppointmentTable(String string) throws SQLException{
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery(string);
       
        while (myrs.next()){
            String aptid = myrs.getString("Appointment_ID");
            String title = myrs.getString("Title");
            String description = myrs.getString("Description");
            String location = myrs.getString("Location");
            String contact = myrs.getString("Create_Date");
            String type = myrs.getString("Type");
            String start = myrs.getString("Start");
            String end = myrs.getString("End");
            String custid = myrs.getString("Customer_ID");
            String userid = myrs.getString("User_ID");
           
            Appointment.addAppointment(new Appointment(aptid,title,description,location
                    ,contact,type,start,end,custid,userid));
        }      
    }
}
