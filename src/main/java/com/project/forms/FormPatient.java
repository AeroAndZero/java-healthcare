package com.project.forms;

import java.util.regex.Pattern;

import com.project.PatientTab;
import com.project.classes.Patient;
import com.project.components.FormHFields;
import com.project.components.FormVFields;
import com.project.data.DataContainer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormPatient extends Application {
    Patient patient;

    Scene PatientRecord;
    Stage stage;
    PatientTab patientTab;

    // Properties
    public int WIDTH = 290;
    public int HEIGHT = 445;
    public int PADDING = 20;

    Button btnCancel;
    Button btnSave;

    FormHFields hboxName;
    FormHFields hboxAge;
    FormHFields hboxWeight;
    FormHFields hboxHeight;
    FormHFields hboxPhone;
    FormVFields vboxAddress;
    FormVFields vboxMedicalHistory;

    public FormPatient(PatientTab patientTab){
        super();
        this.patientTab = patientTab;
        buildGUI();
    }

    public FormPatient(PatientTab patientTab, Patient p){
        super();
        this.patientTab = patientTab;
        this.patient = p;
        buildGUI();
    }

    public void buildGUI(){
        hboxName = new FormHFields(
            new Label("Name: "),
            new TextField()
        );

        hboxAge = new FormHFields(
            new Label("Age: "),
            new TextField()
        );

        hboxWeight = new FormHFields(
            new Label("Weight: "),
            new TextField()
        );

        hboxHeight = new FormHFields(
            new Label("Height: "),
            new TextField()
        );

        hboxPhone = new FormHFields(
            new Label("Phone: "),
            new TextField()
        );

        vboxAddress = new FormVFields(
            new Label("Address:"), 
            new TextArea()
        );

        vboxMedicalHistory = new FormVFields(
            new Label("Medical History:"), 
            new TextArea()
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
            hboxName,
            hboxAge, 
            hboxWeight, 
            hboxHeight, 
            hboxPhone,
            vboxAddress,
            vboxMedicalHistory,
            formControls
        );
        root.setSpacing(10);
        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(PADDING));

        PatientRecord = new Scene(root);

        // Filling form
        if(this.patient != null){
            ((TextField)hboxName.control2).setText(patient.getNameProperty().get());
            ((TextField)hboxAge.control2).setText(patient.getAgeProperty().getValue().toString());
            ((TextField)hboxWeight.control2).setText(patient.getWeightProperty().getValue().toString());
            ((TextField)hboxHeight.control2).setText(patient.getHeightProperty().getValue().toString());
            ((TextField)hboxPhone.control2).setText(patient.getPhoneProperty().getValue().toString());
            ((TextArea)vboxAddress.control2).setText(patient.getAddressProperty().get());
            ((TextArea)vboxMedicalHistory.control2).setText(patient.getMedicalHistoryProperty().get());
        }
    }

    public void attachEvents(){
        /*
        * Task - 3 (Handling IOException) is done below
        * by @Hemang Patel
        */
        btnSave.setOnAction(e -> {
            try {
                String name = ((TextField)hboxName.control2).getText();
                int age = 0;
                int weight = 0;
                int height = 0;
                long phone = 0;
                String address = ((TextArea)vboxAddress.control2).getText();
                String medicalHistory = ((TextArea)vboxMedicalHistory.control2).getText();

                // Validation
                if(name.trim().isEmpty()){
                    showErrorDialog("Invalid Input", "Name should not be empty");
                    return;
                }
                if (!name.matches("[a-zA-Z\\s]+")) {
                    showErrorDialog("Invalid Input", "Name should contain only alphabetic characters");
                    return;
                }

                try {
                    age = Integer.parseInt(((TextField)hboxAge.control2).getText());
                    if (age <= 0) {
                        showErrorDialog("Invalid Input", "Age must be a positive number.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showErrorDialog("Invalid Input", "Invalid Age format. Enter a valid number: " + ex.getMessage());
                    return;
                }

                try {
                    weight = Integer.parseInt(((TextField)hboxWeight.control2).getText());
                    if (weight <= 0) {
                        showErrorDialog("Invalid Input", "Weight must be a positive number.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showErrorDialog("Invalid Input", "Invalid Weight format. Enter a valid number: " + ex.getMessage());
                    return;
                }

                try {
                    height = Integer.parseInt(((TextField)hboxHeight.control2).getText());
                    if (height < 50 || height > 272) {
                        showErrorDialog("Invalid Input", "Height must be between 50 and 272 cm.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showErrorDialog("Invalid Input", "Invalid Height format. Enter a valid number: " + ex.getMessage());
                    return;
                }

                // Phone validation: Canadian phone number format
                String phoneText = ((TextField)hboxPhone.control2).getText();
                if (!Pattern.matches("^[2-9]\\d{2}[2-9]\\d{2}\\d{4}$", phoneText)) {
                    showErrorDialog("Invalid Input", "Invalid Phone format. Enter a valid Canadian phone number.");
                    return;
                } else {
                    phone = Long.parseLong(phoneText);
                }

                // Ensure required fields are not empty
                if (name.isEmpty() || address.isEmpty() || medicalHistory.isEmpty()) {
                    showErrorDialog("Invalid Input", "Please fill in all the required fields.");
                    return;
                }

                // Saving the patient
                if (patient == null) {
                    try {
                        DataContainer.addPatient(name, age, weight, height, phone, address, medicalHistory);
                    } catch (Exception ex) {
                        showErrorDialog("Error", "Error while adding patient: " + ex.getMessage());
                        return;
                    }
                } else {
                    try {
                        Patient newPatient = new Patient(patient.getId(), name, age, weight, height, phone, address, medicalHistory);
                        DataContainer.editPatient(patient.getId(), newPatient);
                    } catch (Exception ex) {
                        showErrorDialog("Error", "Error while editing patient: " + ex.getMessage());
                        return;
                    }
                }

                patientTab.renderPatients();
                stage.close();
            } catch (Exception ex) {
                showErrorDialog("Unexpected Error", "An unexpected error occurred: " + ex.getMessage());
            }
        });

        btnCancel.setOnAction(e -> {
            stage.close();
        });
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(stage);  
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setAlwaysOnTop(true);
        this.stage.setScene(PatientRecord);
        this.stage.initStyle(StageStyle.UTILITY);
        this.stage.setTitle("Patient Record");
        this.stage.setResizable(false);
        this.stage.show();
    }
}
