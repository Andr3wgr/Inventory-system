/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author LabUser
 */
public class Appointment {
    private static ObservableList<Appointment> Appointments = FXCollections.observableArrayList();
    private String appointmentId;
    private String title;
    private String description;
    private String Location;
    private String contact;
    private String type;
    private String startDateandTime;
    private String endDateandTime;
    private String customerId;
    private String userId;

    public Appointment(String appointmentId, String title, String description, String Location, String contact, String type, String startDateandTime, String endDateandTime, String customerId, String userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.Location = Location;
        this.contact = contact;
        this.type = type;
        this.startDateandTime = startDateandTime;
        this.endDateandTime = endDateandTime;
        this.customerId = customerId;
        this.userId = userId;
    }
   public static void clear() {
        Appointments.clear();
    }
   public static void addAppointment(Appointment appointment){
        Appointments.add(appointment);
    }
    public static ObservableList<Appointment> getAppointments() {
        return Appointments;
    }
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDateandTime() {
        return startDateandTime;
    }

    public void setStartDateandTime(String startDateandTime) {
        this.startDateandTime = startDateandTime;
    }

    public String getEndDateandTime() {
        return endDateandTime;
    }

    public void setEndDateandTime(String endDateandTime) {
        this.endDateandTime = endDateandTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
     
}
