/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.localDatabase;

/**
This controller is for the Login Page.
 */
public class LoginPageController implements Initializable {   
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label loginBt;
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
    private String wrongLogin = "";
    private String error = "";
    
    /**
     * Initializes the controller class, and translates to French if System is set to French.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale currentLocale = Locale.getDefault();
        ZoneId zone = ZoneId.systemDefault();
        locationLb.setText(zone.toString());
        if(String.valueOf(currentLocale.getLanguage())=="fr"){
            titleLb.setText("Connexion");
            usernameLb.setText("Nom d'utilisateur");
            passwordLb.setText("Mot de passe");
            loginBt.setText("Connexion");
            wrongLogin = "mauvaise identification";
            error = "Erreur";
        }else{
            titleLb.setText("Log-In");
            usernameLb.setText("Username");
            passwordLb.setText("Password");
            loginBt.setText("Log-In");
            wrongLogin = "Wrong Log-In"; 
            error = "ERROR";
        }    
    } 
    
    /**For logging in and changing screen. Login attempts are save to file "login_activity.txt" in root directory.*/
    @FXML
    private void login(javafx.event.ActionEvent event) throws IOException, SQLException {
        LocalDateTime now = LocalDateTime.now();
        String fileName = "login_activity.txt";
        FileWriter output = new FileWriter(fileName,true);
        localDatabase.openConnection();
        if(localDatabase.login(usernameTxt.getText(), passwordTxt.getText())){
            output.append(now.toString()+" Successful Login\n");
            output.close();
            Parent root = FXMLLoader.load(getClass().getResource("/view/ChoosePage.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            output.append(now.toString()+" Unsuccessful Login\n");
            output.close();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(error);
            alert.setContentText(wrongLogin);
            alert.showAndWait(); 
        }
        localDatabase.closeConnection();       
    }
}
