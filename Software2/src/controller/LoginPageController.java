/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LabUser
 */
public class LoginPageController implements Initializable {
    
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label titleLb;
    @FXML
    private Label locationLb;
    @FXML
    private Label usernameLb;
    @FXML
    private Label passwordLb;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 

    @FXML
    private void login(javafx.event.ActionEvent event) throws IOException {
        
        
           Parent root = FXMLLoader.load(getClass().getResource("/view/ChoosePage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
           
           
           
           
           
    }
    
    /**This method is for logging in and changing screen. */
  

}
