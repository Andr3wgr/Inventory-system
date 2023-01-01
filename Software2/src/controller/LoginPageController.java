/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
 * FXML Controller class
 *
 * @author LabUser
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale currentLocale = Locale.getDefault();
        ZoneId zone = ZoneId.systemDefault();
        locationLb.setText(zone.toString());
        if(String.valueOf(currentLocale.getDisplayLanguage())=="French"){
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
    /**This method is for logging in and changing screen. */
    @FXML
    private void login(javafx.event.ActionEvent event) throws IOException, SQLException {
        localDatabase.openConnection();
        if(localDatabase.login(usernameTxt.getText(), passwordTxt.getText())){
            Parent root = FXMLLoader.load(getClass().getResource("/view/ChoosePage.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle(error);
           alert.setContentText(wrongLogin);
           alert.showAndWait(); 
        }
        localDatabase.closeConnection();       
    }
}
