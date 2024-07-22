package com.project.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.classes.Appointment;

public class AppointmentFileFormat{
    int id;
    String time;
    int patientId;
    String agenda;
    String doctor;

    public static SimpleDateFormat datetimeSaveFormat = new SimpleDateFormat("h:mm a dd/LLL/yyyy");

    public int getId(){
        return id;
    }
    
    public String getTime() {
        return time;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getDoctor() {
        return doctor;
    }

    public static Date getDateObject(String time){
        try{
            return datetimeSaveFormat.parse(time);
        }catch(Exception e){
            return (new Date());
        }
    }

    public AppointmentFileFormat(Appointment appointment) {
        this.id = appointment.getId();
        this.time = datetimeSaveFormat.format(appointment.getDatetime());
        this.patientId = appointment.getPatient().getId();
        this.agenda = appointment.getAgenda();
        this.doctor = appointment.getDoctor();
    }
}
