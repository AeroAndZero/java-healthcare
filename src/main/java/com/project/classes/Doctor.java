package com.project.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Doctor {
    static String[] doctors = {
        "Dr. Mehrnaz Zhian",
        "Dr. Syed Raza",
        "Dr. Ayush Thakur",
        "Dr. Hemang Patel",
        "Dr. Marmik Patel"
    };

    // For Doctors
    public static String[] getDoctors(){
        return doctors;
    }

    public static ObservableList<String> getDoctorsProperty(){
        return FXCollections.observableArrayList(doctors);
    }
}
