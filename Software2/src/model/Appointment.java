/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
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
    private LocalDateTime startDateandTime;
    private LocalDateTime endDateandTime;
    private String customerId;
    private String userId;
    

    public Appointment(String appointmentId, String title, String description, String Location, String contact, String type, LocalDateTime startDateandTime, LocalDateTime endDateandTime, String customerId, String userId) {
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

    public LocalDateTime getStartDateandTime() {
        return startDateandTime;
    }

    public void setStartDateandTime(LocalDateTime startDateandTime) {
        this.startDateandTime = startDateandTime;
    }

    public LocalDateTime getEndDateandTime() {
        return endDateandTime;
    }

    public void setEndDateandTime(LocalDateTime endDateandTime) {
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
    
    public static boolean checkOverlap(LocalDateTime startDateandTime, LocalDateTime endDateandTime,String ApptId) {
        for(Appointment appointment: Appointment.getAppointments()){
            if(ApptId==appointment.getAppointmentId()){
                continue;
            }
            LocalDateTime a=appointment.getStartDateandTime();
            LocalDateTime b=appointment.getEndDateandTime();
            if((a.isBefore(startDateandTime)||a.isEqual(startDateandTime))&&(b.isAfter(startDateandTime))){
                return false;
            }else if(startDateandTime.isBefore(a)&&endDateandTime.isAfter(a)){
                return false;    
            }
        }
        return true;
    }
}
