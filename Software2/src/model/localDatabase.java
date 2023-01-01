/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
            String division = myrs.getString("Division");
            String countryId = myrs.getString("Country_ID");
            model.Customer.addCustomer(new Customer(id,name,address,postalCode,phone,division,countryId));
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
            String contact = myrs.getString("Contact_Name");
            String type = myrs.getString("Type");
            
            //function to convert start and end dates from utc to local time
            String start = myrs.getString("Start");
            String end = myrs.getString("End");
            String custid = myrs.getString("Customer_ID");
            String userid = myrs.getString("User_ID");
           
            Appointment.addAppointment(new Appointment(aptid,title,description,location
                    ,contact,type,start,end,custid,userid));
        }      
    }
    public static boolean login(String user, String pass) throws SQLException{
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT * FROM client_schedule.users");     
        while(myrs.next()){
            if(myrs.getString("User_Name").equals(user)){
                if(myrs.getString("Password").equals(pass)){
                    //add to login history
                    return true;
                }
            }
        }     
        return false;
    }
    public static String[] returnDivisions(int countryNum) throws SQLException{
        openConnection();
        String[] divisions = {};
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT Division FROM client_schedule.first_level_divisions where Country_ID = " + countryNum);  
        while(myrs.next()){
           ArrayList<String> divisionsList = new ArrayList<String>(Arrays.asList(divisions));
           divisionsList.add(myrs.getString("Division"));
           divisions = divisionsList.toArray(divisions);
        }
        closeConnection();
        return divisions;
    }
    public static void addCustomer(String id,String name,String address,String postal,String phone,String division) throws SQLException{
        openConnection();
        Statement getdivisionId = database.createStatement();
        ResultSet divisionId = getdivisionId.executeQuery("SELECT Division_ID FROM client_schedule.first_level_divisions where Division = '"+division+"'");
        int divisionInt = 0;
        while(divisionId.next()){
            divisionInt = Integer.parseInt(divisionId.getString("Division_ID"));
        }
        Statement insert = database.createStatement();
        insert.executeUpdate("insert into client_schedule.customers(Customer_ID,Customer_Name,Address,Postal_Code,Phone,Division_ID)"
                + " values('"+id+"','"+name+"','"+address+"','"+postal+"','"+phone+"',"+divisionInt+")"); 
        closeConnection();
    }
    
    public static void updateCustomer(String id, String name, String address, String postal, String phone, String division) throws SQLException {
        openConnection();
        Statement getdivisionId = database.createStatement();
        ResultSet divisionId = getdivisionId.executeQuery("SELECT Division_ID FROM client_schedule.first_level_divisions where Division = '"+division+"'");
        int divisionInt = 0;
        while(divisionId.next()){
            divisionInt = Integer.parseInt(divisionId.getString("Division_ID"));
        }
        Statement update = database.createStatement();
        update.executeUpdate("update client_schedule.customers set Customer_ID ='"+id+"',Customer_Name ='"+name+"',Address = '"+address+"',Postal_Code = '"+postal+"'"+","
                + "Phone ='"+phone+"',Division_ID = "+divisionInt+" where Customer_ID ='"+id+"'");
        closeConnection();
    }
    
    public static void deleteCustomer(Customer delete) throws SQLException {
        openConnection();
        int customerId = Integer.parseInt(delete.getCustomerId());
        Statement deleteCustomer = database.createStatement();
        deleteCustomer.executeUpdate("Delete FROM client_schedule.customers where Customer_ID = "+customerId);
        closeConnection();
    }

    public static int getNextId() throws SQLException {
        openConnection();
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT Customer_ID FROM client_schedule.customers");  
        int lastId = 0;
        while(myrs.next()){
            if(lastId<Integer.parseInt(myrs.getString("Customer_ID"))){
            lastId = Integer.parseInt(myrs.getString("Customer_ID"));
            }
        }
        closeConnection(); 
        return lastId+1;       
    }


}
