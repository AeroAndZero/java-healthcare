package com.project;

import java.util.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.project.classes.Appointment;
import com.project.components.TwoTextDisplay;
import com.project.data.DataContainer;
import com.project.forms.FormAppointment;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppointmentTab extends Pane{
    // Properties
    public int WIDTH = 1024;
    public int HEIGHT = 576;
    public int PADDING = 40;

    private TwoTextDisplay currentTime;
    private TwoTextDisplay nextIn;
    private TwoTextDisplay remaining;

    TableView<Appointment> tableAppointments;

    private Button btnAddAppt;
    private Button btnEditAppt;
    private Button btnDeleteAppt;
    
    public AppointmentTab(){
        super();
        
        buildGUI();

        renderAppointments();
    }

    public void buildGUI(){
        // Info bar
        HBox info = new HBox();
        nextIn = new TwoTextDisplay("Next in", "Never");
        remaining = new TwoTextDisplay("Remaining", "0");
        currentTime = new TwoTextDisplay("Current Time", LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a")).toString());
        info.setSpacing(100);
        info.setPrefWidth(WIDTH - PADDING * 2);
        info.setAlignment(Pos.CENTER_RIGHT);
        info.getChildren().addAll(nextIn, remaining, currentTime);

        // Upcoming appointments
        Label lUpcomingAppt = new Label("Upcoming Appointments");
        lUpcomingAppt.setStyle("-fx-font-size: 2em;");

        // Building a Table view
        tableAppointments = new TableView<>();
        tableAppointments.setPrefHeight(280);

        // Table Columns 
        TableColumn<Appointment, String> cTime = new TableColumn<>("Time");
        cTime.setCellValueFactory(data -> data.getValue().getDateTimeProperty());
        tableAppointments.getColumns().add(cTime);
        
        TableColumn<Appointment, Integer> cPatientID = new TableColumn<>("Patient ID");
        cPatientID.setCellValueFactory(data -> data.getValue().patient.getIdProperty());
        tableAppointments.getColumns().add(cPatientID);

        TableColumn<Appointment, String> cPatientName = new TableColumn<>("Patient Name");
        cPatientName.setCellValueFactory(data -> data.getValue().patient.getNameProperty());
        cPatientName.setPrefWidth(150);
        tableAppointments.getColumns().add(cPatientName);

        TableColumn<Appointment, String> cMedicalHistory = new TableColumn<>("Medical History");
        cMedicalHistory.setCellValueFactory(data -> data.getValue().patient.getMedicalHistoryProperty());
        cMedicalHistory.setPrefWidth(200);
        tableAppointments.getColumns().add(cMedicalHistory);

        TableColumn<Appointment, String> cAgenda = new TableColumn<>("Agenda");
        cAgenda.setCellValueFactory(data -> data.getValue().getAgendaProperty());
        cAgenda.setPrefWidth(220);
        tableAppointments.getColumns().add(cAgenda);

        TableColumn<Appointment, String> cAssignedDoctor = new TableColumn<>("Assigned to Doctor");
        cAssignedDoctor.setCellValueFactory(data -> data.getValue().getDoctorProperty());
        cAssignedDoctor.setPrefWidth(150);
        tableAppointments.getColumns().add(cAssignedDoctor);

        // Button controls
        HBox dataControls = new HBox();
        btnAddAppt = new Button("Add Appointment");
        btnEditAppt = new Button("Edit");
        btnDeleteAppt = new Button("Delete");
        dataControls.setSpacing(10);
        dataControls.getChildren().addAll(btnAddAppt, btnEditAppt, btnDeleteAppt);

        // Attaching event listeners to the buttons
        attachEvents();
        
        // Main container
        VBox root = new VBox();
        root.setSpacing(20);
        root.setPadding(new Insets(PADDING));
        root.getChildren().addAll(info, lUpcomingAppt, tableAppointments, dataControls);

        this.getChildren().addAll(root);
        this.setPrefSize(WIDTH, HEIGHT);

        // Constantly update time on mouse move
        this.setOnMouseMoved(e -> {refreshEveryMove();});
    }

    public void refreshEveryMove(){
        // The current time is being updated every time the mouse is moved
        // This is because multi-threading has not been taught yet.
        currentTime.setSubtitle(LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a")).toString());

        remaining.setSubtitle(DataContainer.getAppointments().size() + "");
        nextIn.setSubtitle(DataContainer.getTimeTillNext());
    }

    public void attachEvents(){
        btnAddAppt.setOnAction(e -> {
            FormAppointment formAppointment = new FormAppointment(this);
            formAppointment.start(new Stage());
        });

        btnEditAppt.setOnAction(e -> {
            try{
                Appointment selected = (Appointment)tableAppointments.getSelectionModel().getSelectedItem();
                
                FormAppointment formAppointment = new FormAppointment(this, selected);
                formAppointment.start(new Stage());
            }catch (Exception ex){}
        });

        btnDeleteAppt.setOnAction(e -> {
            try{
                Appointment selected = (Appointment)tableAppointments.getSelectionModel().getSelectedItem();
                
                DataContainer.deleteAppointment(selected.getId());
            }catch (Exception ex){}

            renderAppointments();
        });
    }

    public void renderAppointments(){
        // Clear all
        tableAppointments.getItems().clear();

        // Add all
        for (Appointment appointment : DataContainer.getAppointments()) {
            tableAppointments.getItems().add(appointment);
        }

        DataContainer.saveAppointments();
    }

}