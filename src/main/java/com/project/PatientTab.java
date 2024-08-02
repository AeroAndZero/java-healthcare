package com.project;

import com.project.classes.Patient;
import com.project.data.DataContainer;
import com.project.forms.FormPatient;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientTab extends Pane{
    // Properties
    public int WIDTH = 1024;
    public int HEIGHT = 576;
    public int PADDING = 40;

    private Button btnAddPatient;
    private Button btnEditPatient;
    private Button btnDeletePatient;

    TableView<Patient> tablePatients;

    public PatientTab(){
        super();

        buildGUI();

        renderPatients();
    }

    public void buildGUI(){
        // Patient Records
        Label lPatientRecords = new Label("Patient Records");
        lPatientRecords.setStyle("-fx-font-size: 2em;");

        // Table view
        tablePatients = new TableView<Patient>();
        tablePatients.setPrefHeight(380);
        tablePatients.setPrefWidth(WIDTH - PADDING * 2);

        TableColumn<Patient, Integer> cPatientId = new TableColumn<Patient, Integer>("Patient ID");
        cPatientId.setCellValueFactory(data -> data.getValue().getIdProperty());
        tablePatients.getColumns().add(cPatientId);

        TableColumn<Patient, String> cPatientName = new TableColumn<Patient, String>("Patient Name");
        cPatientName.setCellValueFactory(data -> data.getValue().getNameProperty());
        cPatientName.setPrefWidth(120);
        tablePatients.getColumns().add(cPatientName);

        TableColumn<Patient, Integer> cAge = new TableColumn<Patient, Integer>("Age");
        cAge.setCellValueFactory(data -> data.getValue().getAgeProperty());
        tablePatients.getColumns().add(cAge);
        
        TableColumn<Patient, Integer> cWeight = new TableColumn<Patient, Integer>("Weight");
        cWeight.setCellValueFactory(data -> data.getValue().getWeightProperty());
        tablePatients.getColumns().add(cWeight);

        TableColumn<Patient, Integer> cHeight = new TableColumn<Patient, Integer>("Height");
        cHeight.setCellValueFactory(data -> data.getValue().getHeightProperty());
        tablePatients.getColumns().add(cHeight);

        TableColumn<Patient, Long> cPhone = new TableColumn<Patient, Long>("Phone");
        cPhone.setCellValueFactory(data -> data.getValue().getPhoneProperty());
        tablePatients.getColumns().add(cPhone);

        TableColumn<Patient, String> cAddress = new TableColumn<Patient, String>("Address");
        cAddress.setCellValueFactory(data -> data.getValue().getAddressProperty());
        cAddress.setPrefWidth(150);
        tablePatients.getColumns().add(cAddress);

        TableColumn<Patient, String> cMedicalHistory = new TableColumn<Patient, String>("Medical History");
        cMedicalHistory.setCellValueFactory(data -> data.getValue().getMedicalHistoryProperty());
        cMedicalHistory.setPrefWidth(240);
        tablePatients.getColumns().add(cMedicalHistory);

        // Data controls
        HBox dataControls = new HBox();
        btnAddPatient = new Button("Add Patient");
        btnEditPatient = new Button("Edit");
        btnDeletePatient = new Button("Delete");
        dataControls.setSpacing(10);
        dataControls.getChildren().addAll(btnAddPatient, btnEditPatient, btnDeletePatient);

        // Attaching events to data controls
        attachEvents();

        // Main container
        VBox root = new VBox();
        root.setSpacing(20);
        root.setPadding(new Insets(PADDING));
        root.getChildren().addAll(lPatientRecords, tablePatients, dataControls);

        this.getChildren().addAll(root);
        this.setPrefSize(WIDTH, HEIGHT);
    }

    public void attachEvents(){
        btnAddPatient.setOnAction(e -> {
            FormPatient formPatient = new FormPatient(this);
            formPatient.start(new Stage());
        });

        btnEditPatient.setOnAction(e -> {
            Patient selected = (Patient)tablePatients.getSelectionModel().getSelectedItem();
            FormPatient formPatient = new FormPatient(this, selected);
            formPatient.start(new Stage());
        });

        btnDeletePatient.setOnAction(e -> {
            Patient selected = (Patient)tablePatients.getSelectionModel().getSelectedItem();
            DataContainer.deletePatient(selected.getId());
            renderPatients();
        });
    }

    public void renderPatients(){
        // Clear all
        tablePatients.getItems().clear();

        // Add all
        for (Patient patient : DataContainer.getPatients()) {
            tablePatients.getItems().add(patient);
        }
    }

    public void addPatient(Patient p){
        
    }
}
