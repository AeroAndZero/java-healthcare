package com.project.data;

import java.util.ArrayList;
import java.util.Date;

import com.project.classes.Appointment;
import com.project.classes.Patient;
import com.project.database.AppointmentDBO;
import com.project.database.PatientDBO;
import com.project.formatter.CustomDateFormatter;

public class DataContainer {
    static ArrayList<Patient> patients;
    static ArrayList<Appointment> appointments;

    public DataContainer(){
        patients = new ArrayList<>();
        appointments = new ArrayList<>();

        // Load data from files
        loadPatients();
        loadAppointments();
    }

    public static void loadAppointments() {
        appointments = AppointmentDBO.getAllAppointments();
    }

    public static void loadPatients() {
        patients = PatientDBO.getAllPatients();
    }
    
    // For Patiends
    public static ArrayList<Patient> getPatients(){
        return patients;
    }

    public static void addPatient(Patient patient){
        if(PatientDBO.addPatient(patient)){
            patients.add(patient);
        }
    }

    public static void addPatient(String name, int age, int weight, int height, long phone, String address, String medicalHistory){
        int id = 0;
        if(patients.size() > 0) id = patients.get(patients.size()-1).getId()+1;
        Patient patient = new Patient(id, name, age, weight, height, phone, address, medicalHistory);

        if(PatientDBO.addPatient(patient)){
            patients.add(patient);
        }
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

    public static void editPatient(int id, Patient patient){
        int i = 0;
        for(int j = 0; j < patients.size(); j++){
            if(patients.get(j).getId() == id){
                i = j;
                break;
            }
        }
        
        if(PatientDBO.updatePatient(i, patient)){
            patients.set(i, patient);
        }
    }

    public static void deletePatient(int id){
        Patient i = null;
        for(Patient p : patients){
            if(p.getId() == id){
                i = p;
                break;
            }
        }

        if(i != null)
        {
            if(PatientDBO.deletePatient(id)){
                patients.remove(i);
            }
        }
    }

    // For Appointments
    public static ArrayList<Appointment> getAppointments(){
        return appointments;
    }

    public static void addAppointment(Appointment appointment){
        if(AppointmentDBO.addAppointment(appointment)){
            appointments.add(appointment);
        }
    }

    public static void addAppointment(int patientId, Date datetime, String agenda, String doctor){
        int id = 0;
        if(appointments.size() > 0) id = appointments.get(appointments.size()-1).getId()+1;
        Appointment appt = new Appointment(id, datetime, getPatientById(patientId), agenda, doctor);
        
        if(AppointmentDBO.addAppointment(appt)){
            appointments.add(appt);
        }
    }

    public static void editAppointment(int id, Appointment apt){
        if(AppointmentDBO.editAppointment(id, apt)){
            appointments.set(id, apt);
        }
    }

    public static void deleteAppointment(int id){
        Appointment i = null;
        for(Appointment a : appointments){
            if(a.getId() == id){
                i = a;
                break;
            }
        }

        if(i != null){
            if(AppointmentDBO.deleteAppointment(id)){
                appointments.remove(i);
            }
        }
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
