package com.project.classes;

import java.io.Serializable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Patient implements Serializable {
    int id;
    String name;
    int age;
    int weight;
    int height;
    long phone;
    String address;
    String medicalHistory;

    public int getId() {
        return id;
    }
    
    public ObservableValue<Integer> getIdProperty(){
        return new ReadOnlyObjectWrapper<>(id);
    }

    public String getName() {
        return name;
    }

    public StringProperty getNameProperty(){
        return new ReadOnlyStringWrapper(name);
    }

    public int getAge() {
        return age;
    }

    public ObservableValue<Integer> getAgeProperty(){
        return new ReadOnlyObjectWrapper<>(age);
    }

    public int getWeight() {
        return weight;
    }

    public ObservableValue<Integer> getWeightProperty(){
        return new ReadOnlyObjectWrapper<>(weight);
    }
    
    public int getHeight() {
        return height;
    }
    
    public ObservableValue<Integer> getHeightProperty(){
        return new ReadOnlyObjectWrapper<>(height);
    }

    public long getPhone() {
        return phone;
    }

    public ObservableValue<Long> getPhoneProperty(){
        return new ReadOnlyObjectWrapper<>(phone);
    }

    public String getAddress() {
        return address;
    }
    
    public StringProperty getAddressProperty(){
        return new ReadOnlyStringWrapper(address);
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public StringProperty getMedicalHistoryProperty(){
        return new ReadOnlyStringWrapper(medicalHistory);
    }

    // For returning patients
    public Patient(int id, String name, int age, int weight, int height, long phone, String address, String medicalHistory){
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.phone = phone;
        this.address = address;
        this.medicalHistory = medicalHistory;
    }

    // For new patients
    public Patient(int id, String name, int age, long phone){
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;

        this.weight = -1;
        this.height = -1;
        this.address = "";
        this.medicalHistory = "";
    }
}
