package com.project.classes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableObjectValue;

public class Appointment{
    public int id;
    public Date datetime;
    public Patient patient;
    public String agenda;
    public String doctor;

    public int getId(){
        return id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public StringProperty getDateTimeProperty(){
        return new ReadOnlyStringWrapper((new SimpleDateFormat("h:mm a dd/LLL/yyyy")).format(datetime));
    }

    public StringProperty getTimeProperty(){
        return new ReadOnlyStringWrapper((new SimpleDateFormat("h:mm a")).format(datetime));
    }

    public ObservableObjectValue<LocalDate> getDateProperty(){
        return new ReadOnlyObjectWrapper<>(datetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
    
    public LocalDate getDate(){
        return (datetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public Patient getPatient() {
        return patient;
    }

    public String getAgenda() {
        return agenda;
    }

    public StringProperty getAgendaProperty(){
        return new ReadOnlyStringWrapper(agenda);
    }

    public String getDoctor() {
        return doctor;
    }

    public StringProperty getDoctorProperty(){
        return new ReadOnlyStringWrapper(doctor);
    }

    public Appointment(int id, Date datetime, Patient patient, String agenda, String doctor){
        this.id = id;
        this.datetime = datetime;
        this.patient = patient;
        this.agenda = agenda;
        this.doctor = doctor;
    };

    
}
