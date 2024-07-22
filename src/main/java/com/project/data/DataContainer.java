package com.project.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
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
        patients = new ArrayList<>();
        appointments = new ArrayList<>();

        // Load data from files
        loadPatients();
        loadAppointments();
    }
    
    public static void saveAppointments() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("appointments.dat"))) {
            oos.writeObject(appointments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePatients() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("patients.dat"))) {
            oos.writeObject(patients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadAppointments() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("appointments.dat"))) {
            appointments = (ArrayList<Appointment>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File does not exist, initialize with empty list
            appointments = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadPatients() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("patients.dat"))) {
            patients = (ArrayList<Patient>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File does not exist, initialize with empty list
            patients = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // For Patiends
    public static ArrayList<Patient> getPatients(){
        return patients;
    }

    public static void addPatient(Patient patient){
        patients.add(patient);
    }

    public static void addPatient(String name, int age, int weight, int height, long phone, String address, String medicalHistory){
        int id = 0;
        if(patients.size() > 0) id = patients.get(patients.size()-1).getId()+1;
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
        int i = 0;
        for(int j = 0; j < patients.size(); j++){
            if(patients.get(j).getId() == id){
                i = j;
                break;
            }
        }
        patients.set(i, patient);
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

    // For others
    public static String getDateTimeString(Date datetime){
        return (new SimpleDateFormat("h:mm a dd/LLL/yyyy")).format(datetime);
    }
}
