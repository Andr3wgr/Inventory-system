/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.localDatabase;

/**
This controller is for the Update Customer Page.
 */
public class UpdateCustomerController implements Initializable {
   @FXML
   private TextField addressTxt;
   @FXML
   private ComboBox<String> countryCb;
   @FXML
   private Label customerId;
   @FXML
   private ComboBox<String> divisionCb;
   @FXML
   private TextField nameTxt;
   @FXML
   private TextField phoneTxt;
   @FXML
   private TextField postalTxt;
   private static Customer customer = null; 
   
   /**Used to pass customer object to this screen.*/
   public static void passCustomer(Customer customer){
        UpdateCustomerController.customer = customer;
    }
   
    /**
     * Initializes the controller class, and sets Customer object to UI.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] country = {"1","2","3"};
        countryCb.getItems().addAll(country);
        customerId.setText(customer.getCustomerId());
        nameTxt.setText(customer.getCustomerName());
        addressTxt.setText(customer.getCustomerAddress());
        postalTxt.setText(customer.getCustomerPostalCode());
        phoneTxt.setText(customer.getCustomerPhone());
        customerId.setText(customer.getCustomerId());
        countryCb.getSelectionModel().select(customer.getCustomerCountryId());
        divisionCb.getSelectionModel().select(customer.getCustomerDivision());
    } 
    
    /**Used to filter Divisions by Country.*/
    @FXML
    private void countryClicked() throws SQLException{
        divisionCb.getItems().clear();
        if(countryCb.getSelectionModel().getSelectedItem() == "1"){
            divisionCb.getItems().addAll(localDatabase.returnDivisions(1));
        }else if(countryCb.getSelectionModel().getSelectedItem() == "2"){
            divisionCb.getItems().addAll(localDatabase.returnDivisions(2));
        }else if(countryCb.getSelectionModel().getSelectedItem() == "3"){
            divisionCb.getItems().addAll(localDatabase.returnDivisions(3));
        }
    }
    
    /**Updates Customer object, and updates Customer in database.*/
    @FXML
    private void save(javafx.event.ActionEvent event) throws IOException, SQLException{
           String id = customer.getCustomerId();
           String name = nameTxt.getText();
           String address = addressTxt.getText();
           String postal = postalTxt.getText();
           String phone = phoneTxt.getText();
           String division = divisionCb.getSelectionModel().getSelectedItem();
           localDatabase.updateCustomer(id, name, address, postal, phone, division);
           
           Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
    }
    
    /**To Customer Page*/
    @FXML
    private void cancel(javafx.event.ActionEvent event) throws IOException{
           Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
    }
}
