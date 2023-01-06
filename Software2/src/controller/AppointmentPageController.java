/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.localDatabase;

/**
This controller is for the Appointment Page.
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
    @FXML
    private ComboBox<String> contactsCb;
    
    /**Initializes page and calls update table.*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        try {
            Appointment.clear();
            localDatabase.openConnection();
            localDatabase.populateAppointmentTable("SELECT appointments.*, contacts.Contact_Name from client_schedule.appointments \n" +
                "inner join contacts on appointments.Contact_ID = contacts.Contact_ID;");
            localDatabase.closeConnection();
            updateAppointmentTable();
            contactsCb.getItems().addAll(localDatabase.getContactNames());            
        }catch (SQLException ex) {
             Logger.getLogger(CustomerPageController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }  
    
    /**Updates Appointment table.*/
    public void updateAppointmentTable(){
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
    }
    
    /**Displays customer page.*/
    public void toCustomerPage(javafx.event.ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
    }
    
    /**Displays add appointment page.*/
    @FXML
    void addAppointment(javafx.event.ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
    }
    
    /**Displays update appointment page.*/
    @FXML
    void updateAppointment(javafx.event.ActionEvent event) throws IOException {
        Appointment update = appointmentsTableView.getSelectionModel().getSelectedItem();
        if(update == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No Appointment Selected");
                alert.showAndWait();
        }if(update!=null){
            UpdateAppointmentController.passAppointment(update);
            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**Deletes appointment and calls delete from database.*/
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
    
    /**This Method is to filter the Appointments and display Appointments in the next week and month and is a Lambda Expression. 
     * Filtering lists is much more concise when using a lambda function.*/
    @FXML
    public void Filter(){
        FilteredList<Appointment>appointmentsfilter = new FilteredList<>(Appointment.getAppointments(), i->true);
        appointmentsfilter.setPredicate(appointment->{
            if(allRb.isSelected()){
                return true;
            }else if(weekRb.isSelected()){
                if(appointment.getStartDateandTime().isBefore(LocalDateTime.now().plusWeeks(1))
                        &&appointment.getStartDateandTime().isAfter(LocalDateTime.now())){
                    return true;
                }
            }else if(monthRb.isSelected()){
                if(appointment.getStartDateandTime().isBefore(LocalDateTime.now().plusMonths(1))
                        &&appointment.getStartDateandTime().isAfter(LocalDateTime.now())){
                    return true;
                }
            }
            return false;
        } );
        SortedList<Appointment> sortedData = new SortedList<>(appointmentsfilter);
        sortedData.comparatorProperty().bind(appointmentsTableView.comparatorProperty());
        appointmentsTableView.setItems(sortedData);
    }
    
    /**Displays filtered list by contacts.*/
    @FXML
    public void filterByContacts(javafx.event.ActionEvent event) throws SQLException, IOException{
       ContactsFilterController.setCompare(contactsCb.getValue());
  
       Parent root = FXMLLoader.load(getClass().getResource("/view/ContactsFilter.fxml"));
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();   
    }
    
    /**Displays total appointments by month in an information alert.*/
    @FXML
    void totalMonth(javafx.event.ActionEvent event) throws IOException, SQLException {
        List<String> month = new ArrayList<String>();
        for(Appointment appointment: Appointment.getAppointments()){
            month.add(String.valueOf(appointment.getStartDateandTime().getMonth()));
        }
        Map<String, Integer> map= new HashMap<String, Integer>();
        for(String s: month){
            map.put(s,Collections.frequency(month,s));
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total by month");
        alert.setContentText(map.toString());
        alert.showAndWait();     
    }
    
    /**Displays total appointments by type in an information alert.*/
    @FXML
    void totalType(javafx.event.ActionEvent event) throws IOException, SQLException {
        List<String> type = new ArrayList<String>();
        for(Appointment appointment: Appointment.getAppointments()){
            type.add(appointment.getType());
        }      
        Map<String, Integer> map= new HashMap<String, Integer>();
        for(String s: type){
            map.put(s,Collections.frequency(type,s));
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total by Type");
        alert.setContentText(map.toString());
        alert.showAndWait();     
    }
}
