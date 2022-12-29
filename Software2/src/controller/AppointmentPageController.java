/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import model.localDatabase;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class AppointmentPageController implements Initializable {

    @FXML
    private TableColumn<Appointment, ?> appointmentContact;
    @FXML
    private TableColumn<Appointment, ?> appointmentDescription;
    @FXML
    private TableColumn<Appointment, ?> appointmentId;
    @FXML
    private TableColumn<Appointment, ?> appointmentLocation;
    @FXML
    private TableColumn<Appointment, ?> appointmentTitle;
    @FXML
    private TableColumn<Appointment, ?> appointmentType;
    @FXML
    private TableView<Appointment> appointmentsTableView;
    @FXML
    private TableColumn<Appointment, ?> customerId;
    @FXML
    private TableColumn<Appointment, ?> endDate;
    @FXML
    private TableColumn<Appointment, ?> startDate;
    @FXML
    private TableColumn<Appointment, ?> userId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Appointment.clear();
            localDatabase.openConnection();
            localDatabase.populateAppointmentTable("SELECT * FROM client_schedule.appointments");
            
            appointmentsTableView.setItems(Appointment.getAppointments());
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDate.setCellValueFactory(new PropertyValueFactory<>("startDateandTime"));
            endDate.setCellValueFactory(new PropertyValueFactory<>("endDateandTime"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            localDatabase.closeConnection();
            
        }catch (SQLException ex) {
             Logger.getLogger(CustomerPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }    
    public void toCustomerPage(javafx.event.ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();

    }
}
