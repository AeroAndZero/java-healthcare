package com.project;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Homescreen extends Application {
    public static Scene Homescreen;

    @Override
    public void start(Stage stage) throws IOException{
        AppointmentTab appointmentTab = new AppointmentTab();
        PatientTab patientTab = new PatientTab();
        
        // Creating Tabular view for two screens
        Tab tabAppointments = new Tab("Appointments", appointmentTab);
        tabAppointments.setClosable(false);
        
        Tab tabPatients = new Tab("Patients", patientTab);
        tabPatients.setClosable(false);
        
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(tabAppointments);
        tabPane.getTabs().add(tabPatients);
        
        Homescreen = new Scene(tabPane);
        stage.setScene(Homescreen);
        stage.setTitle("Home - Java Healthcare");
        stage.show();
    }
}
