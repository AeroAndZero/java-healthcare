package com.project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.classes.Appointment;
import com.project.classes.Patient;
import com.project.data.DataContainer;
import com.project.formatter.CustomDateFormatter;

public class AppointmentDBO {
    public static ArrayList<Appointment> getAllAppointments(){
        ArrayList<Appointment> appointments = new ArrayList<>();
        try{
            Connection conn = DatabaseManager.getConnection();

            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from appointments");

            while(rs.next()){
                Patient p = DataContainer.getPatientById(rs.getInt(2));
                
                appointments.add(new Appointment(
                    rs.getInt(1), // appointment_id
                    CustomDateFormatter.getDate(rs.getString(3), CustomDateFormatter.FULL_TIME_DATE), // date
                    p, // patient
                    rs.getString(4), // agenda
                    rs.getString(5) // doctor
                ));
            }

        }catch(SQLException e){
            System.out.println("failed to get appointments from database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] AppointmentDBO:getAllAppointments() > Encountered unknown error");
            e.printStackTrace();
        }

        return appointments;
    }


    public static boolean addAppointment(Appointment appointment){
        try{
            Connection conn = DatabaseManager.getConnection();

            PreparedStatement statement = conn.prepareStatement("insert into appointments values(?, ?, ?, ?, ?)");
            statement.setInt(1, appointment.getId());
            statement.setInt(2, appointment.getPatient().getId());
            statement.setString(3, CustomDateFormatter.getDateTimeStr(appointment.getDatetime()));
            statement.setString(4, appointment.getAgenda());
            statement.setString(5, appointment.getDoctor());
            int result = statement.executeUpdate();

            System.out.println("Appointment added to database: " + result);
            return true;
        }catch(SQLException e){
            System.out.println("failed to add appointment to database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] AppointmentDBO:addAppointment() > Encountered unknown error");
            e.printStackTrace();
        }
        
        return false;
    }

    public static boolean editAppointment(int appointmentId, Appointment appointment){
        try{
            Connection conn = DatabaseManager.getConnection();

            PreparedStatement statement = conn.prepareStatement("update appointments set "
            + "patient_id = ?, "
            + "appointment_date = ?, "
            + "agenda = ?, "
            + "doctor_name = ? "
            + "where appointment_id = ?"
            );
            statement.setInt(1, appointment.getPatient().getId());
            statement.setString(2, CustomDateFormatter.getDateTimeStr(appointment.getDatetime()));
            statement.setString(3, appointment.getAgenda());
            statement.setString(4, appointment.getDoctor());
            statement.setInt(5, appointment.getId());
            int result = statement.executeUpdate();

            System.out.println("Appointment updated to database: " + result);
            return true;
        }catch(SQLException e){
            System.out.println("failed to update appointment to database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] AppointmentDBO:editAppointment() > Encountered unknown error");
            e.printStackTrace();
        }
        
        return false;
    }

    public static boolean deleteAppointment(int id){
        try{
            Connection conn = DatabaseManager.getConnection();

            PreparedStatement statement = conn.prepareStatement("delete from appointments "
            + "where appointment_id = ?"
            );
            statement.setInt(1, id);
            int result = statement.executeUpdate();

            System.out.println("Appointment removed from database: " + result);
            return true;
        }catch(SQLException e){
            System.out.println("failed to remove appointment to database: " + e.getMessage());
        }catch(Exception e){
            System.out.println("[!] AppointmentDBO:deleteAppointment() > Encountered unknown error");
            e.printStackTrace();
        }

        return false;
    }
}
