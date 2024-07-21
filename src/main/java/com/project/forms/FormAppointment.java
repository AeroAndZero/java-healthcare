package com.project.forms;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.project.AppointmentTab;
import com.project.classes.Appointment;
import com.project.components.FormHFields;
import com.project.components.FormVFields;
import com.project.data.DataContainer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormAppointment extends Application {
    Appointment appointment;
    
    Scene AppointmentRecord;
    Stage stage;
    AppointmentTab appointmentTab;

    FormHFields hboxPatientID;
    FormVFields vboxTime;
    FormVFields vboxDate;
    FormVFields vboxAgenda;
    FormVFields vboxDoctor;
    Button btnCancel;
    Button btnSave;

    // Properties
    public int WIDTH = 315;
    public int HEIGHT = 380;
    public int PADDING = 20;

    public FormAppointment(AppointmentTab appointmentTab){
        super();
        this.appointmentTab = appointmentTab;
        buildGUI();
    }

    public FormAppointment(AppointmentTab appointmentTab, Appointment appointment){
        super();
        this.appointmentTab = appointmentTab;
        this.appointment = appointment;
        buildGUI();
    }

    public void buildGUI(){
        hboxPatientID = new FormHFields(
            new Label("Patient ID:"),
            new TextField()
        );

        vboxTime = new FormVFields(
            new Label("Appointment Time"),
            new TextField("8:00 AM")
        );

        vboxDate = new FormVFields(
            new Label("Appointment Date"),
            new DatePicker()
        );
        ((DatePicker)vboxDate.control2).setValue(LocalDate.now());

        vboxAgenda = new FormVFields(
            new Label("Appointment Agenda"),
            new TextArea()
        );

        ComboBox<String> cboxDoctor = new ComboBox<String>(DataContainer.getDoctorsProperty());
        cboxDoctor.getSelectionModel().select(0);
        vboxDoctor = new FormVFields(
            new Label("Assign doctor"),
            cboxDoctor
        );

        btnCancel = new Button("Cancel");
        btnSave = new Button("Save");
        FormHFields formControls = new FormHFields(
            btnCancel,
            btnSave
        );
        formControls.setAlignment(Pos.CENTER_RIGHT);

        attachEvents();

        // Main container
        VBox root = new VBox();
        root.getChildren().addAll(
            hboxPatientID,
            vboxTime,
            vboxDate,
            vboxAgenda,
            vboxDoctor,
            formControls
        );
        root.setSpacing(10);
        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(PADDING));

        AppointmentRecord = new Scene(root);

        // Fill forms
        if(appointment != null){
            ((TextField)hboxPatientID.control2).setText(appointment.patient.getIdProperty().getValue().toString());
            ((TextField)vboxTime.control2).setText(appointment.getTimeProperty().get());
            ((DatePicker)vboxDate.control2).setValue(appointment.getDate());
            ((TextArea)vboxAgenda.control2).setText(appointment.getAgenda());
            ((ComboBox<String>)vboxDoctor.control2).setValue(appointment.getDoctor());
        }
    }

    public void attachEvents(){
        btnSave.setOnAction(e -> {
            /*
            * @Hemang
            * Handle exception on below form fields
            */
            // Patient ID
            int patientId = Integer.parseInt(((TextField)hboxPatientID.control2).getText());

            // Time and date field
            String timeStr = ((TextField)vboxTime.control2).getText();
            LocalDate localDate = ((DatePicker)vboxDate.control2).getValue();
            timeStr += " " + localDate.getDayOfMonth() + "-" + localDate.getMonth() + "-" + localDate.getYear();

            SimpleDateFormat format = new SimpleDateFormat("hh:mm a dd-LLLL-yyyy");
            Date datetime = new Date();
            try{
                datetime= format.parse(timeStr);
            }catch(Exception ex){
                System.out.println("Time format should be hh:mm a: " + ex);
            }
            
            // Agenda Field
            String agenda = ((TextArea)vboxAgenda.control2).getText();

            // Doctor Field
            String dr = ((ComboBox<String>)vboxDoctor.control2).getValue();

            // Saving if new
            if(appointment == null){
                    
                if(!DataContainer.doesPatientExist(patientId)){
                    System.out.println("Patient does not exist");
                }
                
                DataContainer.addAppointment(patientId, datetime, agenda, dr);
            }
            
            // Editing if exists
            else{
                Appointment newAppt = new Appointment(appointment.getId(), datetime, DataContainer.getPatientById(patientId), agenda, dr);
                DataContainer.editAppointment(appointment.getId(), newAppt);
            }

            // Exiting and refreshing
            appointmentTab.renderAppointments();
            stage.close();
        });

        btnCancel.setOnAction(e -> {stage.close();});
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setAlwaysOnTop(true);
        this.stage.setScene(AppointmentRecord);
        this.stage.initStyle(StageStyle.UTILITY);
        this.stage.setTitle("Appointment Record");
        this.stage.setResizable(false);
        this.stage.show();
    }
    
}
