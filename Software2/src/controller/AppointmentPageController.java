/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import model.localDatabase;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class AppointmentPageController implements Initializable {

    @FXML
    private TableColumn<Appointment, String> appointmentContact;
    @FXML
    private TableColumn<Appointment, String> appointmentDescription;
    @FXML
    private TableColumn<Appointment, String> appointmentId;
    @FXML
    private TableColumn<Appointment, String> appointmentLocation;
    @FXML
    private TableColumn<Appointment, String> appointmentTitle;
    @FXML
    private TableColumn<Appointment, String> appointmentType;
    @FXML
    private TableView<Appointment> appointmentsTableView;
    @FXML
    private TableColumn<Appointment, String> customerId;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endDate;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startDate;
    @FXML
    private TableColumn<Appointment, String> userId;
    @FXML
    private RadioButton allRb;
    @FXML
    private RadioButton weekRb;
    @FXML
    private RadioButton monthRb;
    @FXML
    private ToggleGroup filter;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Appointment.clear();
            localDatabase.openConnection();
            localDatabase.populateAppointmentTable("SELECT appointments.*, contacts.Contact_Name from client_schedule.appointments \n" +
                "inner join contacts on appointments.Contact_ID = contacts.Contact_ID;");
            
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
            
            appointmentId.setSortType(TableColumn.SortType.ASCENDING);
            appointmentsTableView.getSortOrder().add(appointmentId);
            appointmentsTableView.sort();
            
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
    @FXML
    void addAppointment(javafx.event.ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
    }
    
    @FXML
    void updateAppointment(javafx.event.ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
    }

    @FXML
    void delete(javafx.event.ActionEvent event) throws IOException, SQLException {
        Appointment delete = appointmentsTableView.getSelectionModel().getSelectedItem();
        if(delete == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No Appointment Selected");
                alert.showAndWait();
        }
        
        if (delete != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You sure You want to Cancel this Appointment?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get()==ButtonType.OK){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION,"Appointment Has Been Canceled\n\nAppointment ID: "+delete.getAppointmentId()+"\n"
                            + "Appointment Type: "+delete.getType());
                    alert2.show();
                    localDatabase.deleteAppointmnet(delete);    
                }
                
       Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentPage.fxml"));
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();    
       }
    }
    @FXML
    public void Filter(){
        
        if(allRb.isSelected()){
            
        }else if(weekRb.isSelected()){
            
        }else if(monthRb.isSelected()){
            
        }
    
     }
}
