/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author LabUser
 */
public class Customer {
    private static ObservableList<Customer> Customers = FXCollections.observableArrayList();


    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private String customerDivisionId;
    
    public Customer (String customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, String customerDivisionId){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivisionId = customerDivisionId;
    }

    public static ObservableList<Customer> getCustomers() {
        return Customers;
    }
    public static void clear() {
        Customers.clear();
    }   
    public static void addCustomer(Customer customer){
        Customers.add(customer);
    }
    public void setCustomers(ObservableList<Customer> Customers) {
        this.Customers = Customers;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerDivisionId() {
        return customerDivisionId;
    }

    public void setCustomerDivisionId(String customerDivisionId) {
        this.customerDivisionId = customerDivisionId;
    }
    
}
