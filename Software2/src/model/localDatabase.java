/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.ChoosePageController;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
            
            
            
            LocalDateTime start = (LocalDateTime) myrs.getObject("Start");         
            LocalDateTime end = (LocalDateTime) myrs.getObject("End");
            String custid = myrs.getString("Customer_ID");
            String userid = myrs.getString("User_ID");
            start = TimeZones.convertToLocal(start);
            end = TimeZones.convertToLocal(end);
             //function to convert start and end dates from utc to local time
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
                    //save userid
                    ChoosePageController.setUserId(Integer.parseInt(myrs.getString("User_ID")));
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

    public static int getApptId() throws SQLException {
        
        //redo SQL staments!!
        
        openConnection();
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT Appointment_ID FROM client_schedule.appointments");  
        int lastId = 0;
        while(myrs.next()){
            if(lastId<Integer.parseInt(myrs.getString("Appointment_ID"))){
            lastId = Integer.parseInt(myrs.getString("Appointment_ID"));
            }
        }
        closeConnection(); 
        return lastId+1;      
    }

    public static String[] getCustomerIds() throws SQLException {
        openConnection();
        String[] custIds = {};
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT Customer_ID FROM client_schedule.customers;");  
        while(myrs.next()){
           ArrayList<String> custIdList = new ArrayList<String>(Arrays.asList(custIds));
           custIdList.add(myrs.getString("Customer_ID"));
           custIds = custIdList.toArray(custIds);
        }
        closeConnection();
        return custIds;    
    }

    public static String[] getContactNames() throws SQLException {
        openConnection();
        String[] contName = {};
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT Contact_Name FROM client_schedule.contacts");  
        while(myrs.next()){
           ArrayList<String> contNameList = new ArrayList<String>(Arrays.asList(contName));
           contNameList.add(myrs.getString("Contact_Name"));
           contName = contNameList.toArray(contName);
        }
        closeConnection();
        return contName;  
    }

    public static void addAppointment(String aId, String title, String description, String location, String contName, String type, LocalDateTime startDateandTime, LocalDateTime endDateandTime, String custId, String userId) throws SQLException {
        openConnection();
        Statement getcontactId = database.createStatement();
        ResultSet contactId = getcontactId.executeQuery("SELECT Contact_ID FROM client_schedule.contacts where Contact_Name = '"+contName+"'");
        int contactInt = 0;
        while(contactId.next()){
            contactInt = Integer.parseInt(contactId.getString("Contact_ID"));
        }
        //convert times from local time to UTC
        startDateandTime = TimeZones.localToUtc(startDateandTime);
        endDateandTime = TimeZones.localToUtc(endDateandTime);
        Statement insert = database.createStatement();
        insert.executeUpdate("insert into client_schedule.appointments(Appointment_ID,Title,Description,Location,Type,Start,End,Customer_ID,User_ID,Contact_ID) "
                + "values("+Integer.parseInt(aId)+",\""+title+"\",\""+description+"\",\""+location+"\",\""+type+"\",\""+startDateandTime+"\",\""+endDateandTime+"\","+custId+","+userId+","+contactInt+");");
        closeConnection();
        
    }

    public static void deleteAppointmnet(Appointment delete) throws SQLException {
        openConnection();
        int appointmentId = Integer.parseInt(delete.getAppointmentId());
        Statement deleteCustomer = database.createStatement();
        deleteCustomer.executeUpdate("Delete FROM client_schedule.appointments where Appointment_ID = "+appointmentId);
        closeConnection();
    }

    public static boolean checkDelete(int i) throws SQLException {
        boolean test = true;
        openConnection();
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT Customer_ID FROM client_schedule.appointments");
        while(myrs.next()){
            if(myrs.getString("Customer_ID").equals(String.valueOf(i))){
                test= false;
            }
        }
        closeConnection();
        return test;
    }

    public static boolean nextAppointmentWarning(int user) throws SQLException {
        openConnection();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime current = LocalDateTime.now(ZoneId.of("UTC"));
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT Start FROM client_schedule.appointments");
        while(myrs.next()){
            LocalDateTime appointment = LocalDateTime.parse(myrs.getString("Start"),format);
            if(current.isBefore(appointment) && current.isAfter(appointment.minusMinutes(15))){  
                return true;
            }   
        }
        closeConnection();

        return false;
    }

    public static Appointment nextAppointment() throws SQLException {
        openConnection();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime current = LocalDateTime.now(ZoneId.of("UTC"));
        Statement mystmt = database.createStatement();
        ResultSet myrs = mystmt.executeQuery("SELECT * FROM client_schedule.appointments");
        while(myrs.next()){
            LocalDateTime appointment = LocalDateTime.parse(myrs.getString("Start"),format);
            if(current.isBefore(appointment) && current.isAfter(appointment.minusMinutes(15))){  
                Appointment appt = new Appointment (myrs.getString("Appointment_ID"),null,null,null,null,null,appointment,null,null,null);
                return appt;
            }   
        }
        closeConnection();
        return null;
    }

    public static void updateAppointment(String userId, String apptId, String title, String desc, String location, String custId, String contact, LocalDateTime start, LocalDateTime end) throws SQLException {
        openConnection();
        Statement getcontactId = database.createStatement();
        ResultSet contactId = getcontactId.executeQuery("SELECT Contact_ID FROM client_schedule.contacts where Contact_Name = '"+contact+"'");
        int contactInt = 0;
        while(contactId.next()){
            contactInt = Integer.parseInt(contactId.getString("Contact_ID"));
        }
        start = TimeZones.localToUtc(start);
        end = TimeZones.localToUtc(end);
        Statement update = database.createStatement();
        update.executeUpdate("update client_schedule.appointments set User_ID ='"+userId+"',Contact_ID ='"+contactInt+"',Description = '"+desc+"',Title = '"+title+"'"+","
                + "Location ='"+location+"',Customer_ID = '"+custId+"',Start = '"+start+"',End = '"+end+"' where Appointment_ID ='"+apptId+"'");
        closeConnection();
    }

    
}
