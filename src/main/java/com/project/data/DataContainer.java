package com.project.data;

import java.util.ArrayList;
import java.util.Date;

import com.project.classes.Appointment;
import com.project.classes.Patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataContainer {
    static ArrayList<Patient> patients;
    static ArrayList<Appointment> appointments;
    static String[] doctors = {
        "Dr. Mehrnaz Zhian",
        "Dr. Syed Raza",
        "Dr. Ayush Thakur",
        "Dr. Hemang Patel",
        "Dr. Marmik Patel"
    };

    public DataContainer(){
        /*
         * @Marmik
         * Read from file
         * Change below code
         */
        patients = new ArrayList<>();
        appointments = new ArrayList<>();

        // Demo data -- Remove this if you want
        patients.add(new Patient(0, "Ayush", 21, 80, 179, 12345, "Vaughan", "Covid"));
        patients.add(new Patient(1, "Marmik", 22,  64, 182, 12345, "Brampton", "Covid"));
        patients.add(new Patient(2, "Hemang", 23,  78, 165, 12345, "Mississauga", "Covid"));
        patients.add(new Patient(3, "Bro", 19,  55, 182, 12345, "Toronto", "Covid"));
    }

    /*
    * @Marmik
    * This function will save data everytime it is called
    * Write code to save data to a binary file below
    * both saveAppointments and savePatients function
    */
    public static void saveAppointments(){
        
    }

    public static void savePatients(){
        
    }

    // For Patiends
    public static ArrayList<Patient> getPatients(){
        return patients;
    }

    public static void addPatient(Patient patient){
        patients.add(patient);
    }

    public static void addPatient(int id, String name, int age, int weight, int height, long phone, String address, String medicalHistory){
        Patient patient = new Patient(id, name, age, weight, height, phone, address, medicalHistory);
        patients.add(patient);

        System.out.println("added new appointment");
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
        patients.set(id, patient);
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
            patients.remove(i);
    }

    // For Appointments
    public static ArrayList<Appointment> getAppointments(){
        return appointments;
    }

    public static void addAppointment(Appointment appointmnet){
        appointments.add(appointmnet);
    }

    public static void addAppointment(int patientId, Date datetime, String agenda, String doctor){
        Appointment appt = new Appointment(getAppointments().size(), datetime, getPatientById(patientId), agenda, doctor);
        appointments.add(appt);
    }

    public static String getTimeTillNext(){
        if(appointments.size() == 0) return "Never";

        long timeDiff = Long.MAX_VALUE;
        Date current = new Date();
        for(Appointment p : appointments){
            if(Math.abs(current.getTime() - p.datetime.getTime()) < timeDiff){
                timeDiff = Math.abs(current.getTime() - p.datetime.getTime());
            }
        }
    
        long mins = (timeDiff / 60000);
        if(mins >= 60){
            mins /= 60;
            return (mins) + " hours";
        }
        return (mins) + " mins";
    }

    public static void editAppointment(int id, Appointment apt){
        appointments.set(id, apt);
    }

    public static void deleteAppointment(int id){
        Appointment i = null;
        for(Appointment a : appointments){
            if(a.getId() == id){
                i = a;
                break;
            }
        }

        if(i != null)
            appointments.remove(i);
    }

    // Doctors
    public static String[] getDoctors(){
        return doctors;
    }

    public static ObservableList<String> getDoctorsProperty(){
        return FXCollections.observableArrayList(doctors);
    }
}
