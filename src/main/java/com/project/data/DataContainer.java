package com.project.data;

import java.util.ArrayList;
import java.util.Date;

import com.project.classes.Appointment;
import com.project.classes.Patient;
import com.project.classes.DatabaseObject;
import com.project.database.AppointmentDBO;
import com.project.database.PatientDBO;
import com.project.formatter.CustomDateFormatter;

public class DataContainer {
    public static ArrayList<Patient> patients;
    public static ArrayList<Appointment> appointments;

    public DataContainer(){
        patients = new ArrayList<>();
        appointments = new ArrayList<>();

        // Load data from database
        loadPatients(); // first patients are loaded. Then loadAppointments is called
    }
    
    // --------------- For Patiends
    public static ArrayList<Patient> getPatients(){
        return patients;
    }

    public static boolean doesPatientExist(int patientId){
        for(Patient p : patients){
            if(p.getId() == patientId){
                return true;

            }
        }
        return false;
    }

    public static Patient getPatientById(int id){
        for(Patient p : patients){
            if(p.getId() == id){
                return p;
            }
        }

        return null;
    }

    public static void loadPatients() {
        PatientDBO patientDBO = new PatientDBO();
        patientDBO.setOperation(DatabaseObject.OPERATION.GET);
        patientDBO.execute();
    }

    public static void addPatient(String name, int age, int weight, int height, long phone, String address, String medicalHistory){
        int id = 0;
        if(patients.size() > 0) id = patients.get(patients.size()-1).getId()+1;
        Patient patient = new Patient(id, name, age, weight, height, phone, address, medicalHistory);

        PatientDBO patientDBO = new PatientDBO();
        patientDBO.setOperation(DatabaseObject.OPERATION.ADD, patient);
        patientDBO.execute();
    }

    public static void editPatient(int id, Patient patient){
        PatientDBO patientDBO = new PatientDBO();
        patientDBO.setOperation(DatabaseObject.OPERATION.EDIT, id, patient);
        patientDBO.execute();
    }

    public static void deletePatient(int id){
        PatientDBO patientDBO = new PatientDBO();
        patientDBO.setOperation(DatabaseObject.OPERATION.DELETE, id);
        patientDBO.execute();
    }

    // --------------- For Appointments
    public static ArrayList<Appointment> getAppointments(){
        return appointments;
    }

    public static void loadAppointments() {
        AppointmentDBO appointmentDBO = new AppointmentDBO();
        appointmentDBO.setOperation(DatabaseObject.OPERATION.GET);
        appointmentDBO.execute();
    }

    public static void addAppointment(int patientId, Date datetime, String agenda, String doctor){
        int id = 0;
        if(appointments.size() > 0) id = appointments.get(appointments.size()-1).getId()+1;
        Appointment appt = new Appointment(id, datetime, getPatientById(patientId), agenda, doctor);

        AppointmentDBO appointmentDBO = new AppointmentDBO();
        appointmentDBO.setOperation(DatabaseObject.OPERATION.ADD, appt);
        appointmentDBO.execute();
    }

    public static void editAppointment(int id, Appointment apt){
        AppointmentDBO appointmentDBO = new AppointmentDBO();
        appointmentDBO.setOperation(DatabaseObject.OPERATION.EDIT, id, apt);
        appointmentDBO.execute();
    }

    public static void deleteAppointment(int id){
        AppointmentDBO appointmentDBO = new AppointmentDBO();
        appointmentDBO.setOperation(DatabaseObject.OPERATION.DELETE, id);
        appointmentDBO.execute();
    }

    // For others
    public static String getDateTimeString(Date datetime){
        return CustomDateFormatter.getDateTimeStr(datetime);
    }

    public static String getTimeTillNext(){
        if(appointments.size() == 0) return "Never";

        long timeDiff = Long.MAX_VALUE;
        Date current = new Date();
        for(Appointment p : appointments){
            long diff = p.datetime.getTime() - current.getTime();
            if(diff > 0 && diff < timeDiff){
                timeDiff = diff;
            }
        }
    
        if(timeDiff == Long.MAX_VALUE) return "Never";

        long mins = (timeDiff / 60000);
        if(mins >= 60){
            mins /= 60;
            return (mins) + " hours";
        }
        return (mins) + " mins";
    }
}
