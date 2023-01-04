/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.localDatabase;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class ChoosePageController implements Initializable {
    private static int custId = 1;
    private static int apptId = 1;
    private static int userId = 1;
     
    public static int getApptId() {
        return apptId;
    }

    public static void setApptId(int apptId) {
        ChoosePageController.apptId = apptId;
    }
    
    public static int getCustId(){
        return ChoosePageController.custId;
    }
    
    public static void setCustId(int custId){
        ChoosePageController.custId = custId;
    }
    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        ChoosePageController.userId = userId;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        try {
            custId = localDatabase.getNextId();
            apptId = localDatabase.getUserId();
        } catch (SQLException ex) {
            Logger.getLogger(ChoosePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
      @FXML
    public void toAppointmentList(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentPage.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    public void toCustomerList(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
}
