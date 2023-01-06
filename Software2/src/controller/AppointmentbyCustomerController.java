/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
public class AppointmentbyCustomerController implements Initializable {
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
    private static String custId="";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Appointment.getAppointments().isEmpty()){
            try {
                localDatabase.openConnection();
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentbyCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                localDatabase.populateAppointmentTable("SELECT appointments.*, contacts.Contact_Name from client_schedule.appointments \n" +
                        "inner join contacts on appointments.Contact_ID = contacts.Contact_ID;");
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentbyCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                localDatabase.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentbyCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        
       FilteredList<Appointment>contactsfilter = new FilteredList<>(Appointment.getAppointments(), i->true);
        contactsfilter.setPredicate(appointments->{
       
        String contact =appointments.getCustomerId();
              return custId.matches(contact);
        } );
        SortedList<Appointment> sortedData = new SortedList<>(contactsfilter);
        sortedData.comparatorProperty().bind(appointmentsTableView.comparatorProperty());
        appointmentsTableView.setItems(sortedData);
          
        // TODO
    }    

    public static String getCustId() {
        return custId;
    }

    public static void setCustId(String custId) {
        AppointmentbyCustomerController.custId = custId;
    }
    @FXML
    public void back(javafx.event.ActionEvent event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
    }
}
