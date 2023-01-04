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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.localDatabase;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class AddAppointmentController implements Initializable {
    @FXML
    private Label apptIdLb;
    @FXML
    private ComboBox<String> contactCb;
    @FXML
    private ComboBox<String> custIdCb;
    @FXML
    private TextField descTxt;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<String> endTime;
    @FXML
    private TextField locTxt;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox<String> startTime;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField typeTxt;
    @FXML
    private Label userIdLb;
    private String aId;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userIdLb.setText(Integer.toString(ChoosePageController.getUserId()));
        aId = Integer.toString(ChoosePageController.getApptId());
        apptIdLb.setText(aId);
        //update time and add to comboboxes
        try {
            custIdCb.getItems().addAll(localDatabase.getCustomerIds());
            contactCb.getItems().addAll(localDatabase.getContactNames());
            startTime.getItems().addAll(localDatabase.utcToLocal());
            endTime.getItems().addAll(localDatabase.utcToLocal());
        } catch (SQLException ex) {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }  
    @FXML
    private void save(javafx.event.ActionEvent event) throws IOException, SQLException{
                
           String userId = Integer.toString(ChoosePageController.getUserId());
           String description = descTxt.getText();
           String location = locTxt.getText();
           String title = titleTxt.getText();
           String type = typeTxt.getText();
           String custId = custIdCb.getSelectionModel().getSelectedItem();
           String contName = contactCb.getSelectionModel().getSelectedItem();
           LocalDate sDate = startDate.getValue();
           LocalDate eDate = endDate.getValue();
           LocalTime sTime = LocalTime.parse(startTime.getValue());
           LocalTime eTime = LocalTime.parse(endTime.getValue());
           
           LocalDateTime startDateandTime = LocalDateTime.of(sDate, sTime);
           LocalDateTime endDateandTime = LocalDateTime.of(eDate, eTime);
          
          
           
           localDatabase.addAppointment(aId, title, description, location, contName, type,startDateandTime,endDateandTime,custId,userId);
           ChoosePageController.setApptId(Integer.parseInt(aId)+1);
  
           Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentPage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
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
