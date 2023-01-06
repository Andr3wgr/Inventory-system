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
import java.time.LocalTime;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.TimeZones;
import model.localDatabase;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class UpdateAppointmentController implements Initializable {
    @FXML
    private DatePicker Date;
    @FXML
    private Label apptIdLb;
    @FXML
    private ComboBox<String> contactCb;
    @FXML
    private ComboBox<String> custIdCb;
    @FXML
    private TextField descTxt;
    @FXML
    private ComboBox<String> endTime;
    @FXML
    private TextField locTxt;
    @FXML
    private ComboBox<String> startTime;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField typeTxt;
    @FXML
    private Label userIdLb;
    private static Appointment appointment = null; 
    
    public static void passAppointment(Appointment appointment){
        UpdateAppointmentController.appointment = appointment;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userIdLb.setText(String.valueOf(ChoosePageController.getUserId()));
        apptIdLb.setText(appointment.getAppointmentId());
        titleTxt.setText(appointment.getTitle());
        typeTxt.setText(appointment.getType());
        descTxt.setText(appointment.getDescription());
        locTxt.setText(appointment.getLocation());
        custIdCb.getSelectionModel().select(appointment.getCustomerId());
        contactCb.getSelectionModel().select(appointment.getContact());
        Date.setValue(appointment.getStartDateandTime().toLocalDate());
        startTime.getSelectionModel().select(appointment.getStartDateandTime().toLocalTime().toString());
        endTime.getSelectionModel().select(appointment.getEndDateandTime().toLocalTime().toString());
        
        
         try {
            custIdCb.getItems().addAll(localDatabase.getCustomerIds());
            contactCb.getItems().addAll(localDatabase.getContactNames());
            startTime.getItems().addAll(TimeZones.estToLocal());
            endTime.getItems().addAll(TimeZones.estToLocal());
        } catch (SQLException ex) {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
    }    
        @FXML
    private void save(javafx.event.ActionEvent event) throws IOException, SQLException{
        boolean checkOverlap = false;
        String userId =userIdLb.getText();
        String apptId =apptIdLb.getText();
        String title =titleTxt.getText();
        String desc =descTxt.getText();
        String location =locTxt.getText();
        String custId =custIdCb.getSelectionModel().getSelectedItem();
        String contact =contactCb.getSelectionModel().getSelectedItem();
        LocalDate date =Date.getValue();
        LocalTime start = LocalTime.parse(startTime.getValue());
        LocalTime end = LocalTime.parse(endTime.getValue());
        LocalDateTime startDateTime = LocalDateTime.of(date,start);
        LocalDateTime endDateTime = LocalDateTime.of(date,end);
        if(startDateTime.isAfter(endDateTime)||startDateTime.isEqual(endDateTime)){
             Alert alert1 = new Alert(Alert.AlertType.ERROR);
             alert1.setTitle("ERROR");
             alert1.setContentText("Start Time Must be Before End Time");
             alert1.showAndWait(); 
             return;
            }
        
        if(checkOverlap = Appointment.checkOverlap(startDateTime,endDateTime,apptId)){
        
           localDatabase.updateAppointment(userId,apptId,title,desc,location,custId,contact,startDateTime,endDateTime);
           Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentPage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
        } else if(checkOverlap == false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Cannot Schedule Overlapping Appointments");
                alert.showAndWait();
           }
    }
    @FXML
    private void cancel(javafx.event.ActionEvent event) throws IOException{
           Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentPage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
    }
}
