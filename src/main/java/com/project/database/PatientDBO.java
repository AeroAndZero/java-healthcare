package com.project.database;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import com.project.classes.DatabaseObject;
import com.project.classes.Patient;
import com.project.data.DataContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDBO extends DatabaseObject {
   OPERATION CURRENT_OPERATION;
   Patient patient = null;
   int id = 0;

   ExecutorService executorService;

    public PatientDBO(){
        executorService = Executors.newCachedThreadPool();
    }

    public synchronized void setOperation(OPERATION operation){
        CURRENT_OPERATION = operation;
    }

    public synchronized void setOperation(OPERATION operation, Patient patient){
        CURRENT_OPERATION = operation;
        this.patient = patient;
    }

    public synchronized void setOperation(OPERATION operation, int id){
        CURRENT_OPERATION = operation;
        this.id = id;
    }

    public synchronized void setOperation(OPERATION operation, int id, Patient patient){
        CURRENT_OPERATION = operation;
        this.id = id;
        this.patient = patient;
    }

    
    public synchronized void getAllPatients(){
        ArrayList<Patient> patients = new ArrayList<>();
        
        try{
            Connection conn = DatabaseManager.getConnection();
            String query = "select * from patients";
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);

            while(rs.next()){
                patients.add(new Patient(
                    rs.getInt(1), //id
                    rs.getString(2), //name
                    rs.getInt(3), //age
                    rs.getInt(4), //weight
                    rs.getInt(5), //height
                    rs.getLong(6), //phone
                    rs.getString(7), //address
                    rs.getString(8) //medical history
                ));
            }
        }catch(SQLException e){
            System.out.println("failed to get patients from database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] PatientDBO:getAllPatients() > Encountered unknown error");
            e.printStackTrace();
        }

        if(patients.size() > 0){
        	synchronized (DataContainer.patients) {
                DataContainer.patients = patients;
        	}
            System.out.println("Patients have been loaded");
        }
    }

    public synchronized void addPatient(Patient patient){
        try{
            Connection conn = DatabaseManager.getConnection();
            
            System.out.println("Adding patient: " + patient.getId());

            PreparedStatement statement = conn.prepareStatement("insert into patients values(?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, patient.getId());
            statement.setString(2, patient.getName());
            statement.setInt(3, patient.getAge());
            statement.setInt(4, patient.getWeight());
            statement.setInt(5, patient.getHeight());
            statement.setLong(6, patient.getPhone());
            statement.setString(7, patient.getAddress());
            statement.setString(8, patient.getMedicalHistory());
            int result = statement.executeUpdate();

            System.out.println("Patient added to database: " + result);
            
           synchronized (DataContainer.patients) {
               DataContainer.patients.add(patient);
           }
        }catch(SQLException e){
            System.out.println("failed to add patients to database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] PatientDBO:addPatient() > Encountered unknown error");
            e.printStackTrace();
        }
    }

    public synchronized void editPatient(int id, Patient patient){
        try{
            Connection conn = DatabaseManager.getConnection();

            PreparedStatement statement = conn.prepareStatement("update patients set " 
                + "name = ?, "
                + "age = ?, "
                + "weight = ?, "
                + "height = ?, "
                + "phone = ?, "
                + "address = ?, "
                + "medical_history = ? "
                +" where patient_id = ?"
            );

            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setInt(3, patient.getWeight());
            statement.setInt(4, patient.getHeight());
            statement.setLong(5, patient.getPhone());
            statement.setString(6, patient.getAddress());
            statement.setString(7, patient.getMedicalHistory());
            statement.setInt(8, patient.getId());

            int result = statement.executeUpdate();

            System.out.println("Patient update to database: " + result);
            
         synchronized (DataContainer.patients) {
        	 int i = -1;
            for(int j = 0; j < DataContainer.patients.size(); j++){
                if(DataContainer.patients.get(j).getId() == id){
                    i = j;
                    break;
                }
            }

            if(i != -1) 
            {
                DataContainer.patients.set(i, patient);
            }
         }
        }catch(SQLException e){
            System.out.println("failed to update patients to database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] PatientDBO:updatePatient() > Encountered unknown error");
            e.printStackTrace();
        }
    }

    //hemang
    public synchronized void deletePatient(int id){
        try{
            Connection conn = DatabaseManager.getConnection();

            PreparedStatement statement = conn.prepareStatement("delete from patients " 
                +" where patient_id = ?"
            );

            statement.setInt(1, id);

            int result = statement.executeUpdate();

            System.out.println("Patient deleted from database: " + result);
       synchronized (DataContainer.patients) {
            Patient i = null;
            for(Patient p : DataContainer.patients){
                if(p.getId() == id){
                    i = p;
                    break;
                }
            }

            if(i != null) {
                DataContainer.patients.remove(i);
        }
       }
      }
        catch(SQLException e){
            System.out.println("failed to remove patient from database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] PatientDBO:deletePatient() > Encountered unknown error");
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        try {
            executorService.execute(this);
        } catch (RejectedExecutionException ex) {
            System.out.println("\nYou cannot execute any new Task"); 
        }
    }

    @Override
    public void run() {
        switch (CURRENT_OPERATION) {
            case GET:
                getAllPatients();
                DataContainer.loadAppointments();
                break;

            case ADD:
                addPatient(patient);
                break;
            
            case EDIT:
                editPatient(id, patient);
                break;
            
            case DELETE:
                deletePatient(id);
                break;
        
            default:
                break;
        }
    }
    
    
    public void shutdown() {
    	executorService.shutdown();
    	System.out.println("Service shutdown");
    }
    
}
