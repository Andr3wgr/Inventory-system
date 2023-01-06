/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.localDatabase;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class CustomerPageController implements Initializable {

    @FXML
    private TableColumn<Customer, String> customerAddress;
    @FXML
    private TableColumn<Customer, String> customerCountryId;
    @FXML
    private TableColumn<Customer, Integer> customerDivision;
    @FXML
    private TableColumn<Customer, Integer> customerId;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> customerPhone;
    @FXML
    private TableColumn<Customer, String> customerPostalCode;
    @FXML
    private TableView<Customer> customerTableView;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Customer.clear();
            localDatabase.openConnection();
            localDatabase.populateCustomerTable("SELECT customers.*, first_level_divisions.Country_ID, first_level_divisions.Division from client_schedule.customers \n" +
"inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID;");
            
            customerTableView.setItems(Customer.getCustomers());
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            customerDivision.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
            customerCountryId.setCellValueFactory(new PropertyValueFactory<>("customerCountryId"));
            
            localDatabase.closeConnection();
            
        }catch (SQLException ex) {
             Logger.getLogger(CustomerPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }    
    
    public void toAppointmentsPage(javafx.event.ActionEvent event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentPage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
    }
    public void toAddCustomer(javafx.event.ActionEvent event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
    }
    public void toUpdateCustomerPage(javafx.event.ActionEvent event) throws IOException {
           Customer update = customerTableView.getSelectionModel().getSelectedItem();
           if(update == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No Customer Selected");
                alert.showAndWait();
           }
           UpdateCustomerController.passCustomer(update); 
           Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
    
           
    }
    public void deleteCustomer(javafx.event.ActionEvent event) throws SQLException, IOException{
        Customer delete = customerTableView.getSelectionModel().getSelectedItem();
        if(delete == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No Customer Selected");
                alert.showAndWait();
                
        }
        boolean del= localDatabase.checkDelete(Integer.parseInt(delete.getCustomerId()));
        if (delete != null && del==true){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You sure You want to Delete this Customer?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get()==ButtonType.OK){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION,"Customer Has been Deleted");
                    localDatabase.deleteCustomer(delete);  
                    alert2.show();
                }
            }if(delete != null && del==false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Cannot Delete Customer While Customer Has Appointments Scheduled");
                alert.showAndWait();
            }
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void viewAppointments(javafx.event.ActionEvent event) throws SQLException, IOException{
        Customer view = customerTableView.getSelectionModel().getSelectedItem();
        if(view == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No Customer Selected");
                alert.showAndWait();
                return;
        }
        AppointmentbyCustomerController.setCustId(view.getCustomerId());
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentbyCustomer.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
