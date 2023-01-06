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

/**
This controller is for the filter by contacts page.
 */
public class ContactsFilterController implements Initializable {
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
    private static String compare;

    public String getCompare() {
        return compare;
    }

    public static void setCompare(String compare) {
        ContactsFilterController.compare = compare;
    }
    
    /**
     * Initializes the controller class, Contains a Lambda Expression. This lambda expression helps keep code clear and concise.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        String contact =appointments.getContact();
              return compare.matches(contact);
        } );
        
        SortedList<Appointment> sortedData = new SortedList<>(contactsfilter);
        sortedData.comparatorProperty().bind(appointmentsTableView.comparatorProperty());
        appointmentsTableView.setItems(sortedData);
    }   
    
    /**Returns to Appointments Page.*/
    @FXML
    void back(javafx.event.ActionEvent event) throws IOException, SQLException {
           Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentPage.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
    }
}
