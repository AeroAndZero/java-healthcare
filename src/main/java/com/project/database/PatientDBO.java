package com.project.database;

import java.sql.Statement;
import java.util.ArrayList;

import com.project.classes.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDBO {

    public static ArrayList<Patient> getAllPatients(){
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

        return patients;
    }

    public static boolean addPatient(Patient patient){
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
            return true;
        }catch(SQLException e){
            System.out.println("failed to add patients to database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] PatientDBO:addPatient() > Encountered unknown error");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatient(int id, Patient patient){
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
            return true;
        }catch(SQLException e){
            System.out.println("failed to update patients to database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] PatientDBO:updatePatient() > Encountered unknown error");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deletePatient(int id){
        try{
            Connection conn = DatabaseManager.getConnection();

            PreparedStatement statement = conn.prepareStatement("delete from patients " 
                +" where patient_id = ?"
            );

            statement.setInt(1, id);

            int result = statement.executeUpdate();

            System.out.println("Patient deleted from database: " + result);
            
            return true;
        }catch(SQLException e){
            System.out.println("failed to remove patient from database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] PatientDBO:deletePatient() > Encountered unknown error");
            e.printStackTrace();
        }

        return false;
    }
}
