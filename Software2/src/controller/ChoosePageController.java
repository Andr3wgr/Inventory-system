/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //get table view and cycle through, add 1 to last id
        try {
            custId = localDatabase.getNextId();
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
