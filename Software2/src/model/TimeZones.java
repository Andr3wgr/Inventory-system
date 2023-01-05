/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.util.Locale.US;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author LabUser
 */
public class TimeZones {
    
    public static LocalDateTime convertToLocal(LocalDateTime dateTime){
        ZonedDateTime start1 = dateTime.atZone(ZoneId.of("UTC"));
        start1 = start1.withZoneSameInstant(ZoneId.systemDefault());
        dateTime = start1.toLocalDateTime();
        return dateTime;
    }
    public static String[] estToLocal(){
        int[] time = {8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
        String[] aptTime = {"8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"};
        LocalDateTime constant = LocalDateTime.parse("2023-01-04T10:00");

        int diff = (int) ChronoUnit.HOURS.between(constant.atZone(ZoneId.of("America/New_York")),constant.atZone(ZoneId.systemDefault()));
        for (int i = 0; i < time.length; i++){
            time[i] = time[i]-diff;
            if(time[i]>23){
                time[i]=time[i]-24;
            }
        }
         for (int i = 0; i < time.length; i++){
             if(time[i]<10){
                 aptTime[i] ="0" + String.valueOf(time[i])+":00";
             }else{
                aptTime[i] = String.valueOf(time[i])+":00";
             }
         }
        return aptTime;
    }
    
    public static LocalDateTime localToUtc(LocalDateTime dateTime){
        ZonedDateTime date1 = dateTime.atZone(ZoneId.systemDefault());
        date1 = date1.withZoneSameInstant(ZoneId.of("UTC"));
        dateTime = date1.toLocalDateTime();
        return dateTime;  
    }

}
