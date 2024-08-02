-- Create the Patients table
CREATE TABLE Patients (
    patient_id NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    age NUMBER NOT NULL,
    weight NUMBER(5,2) NOT NULL,
    height NUMBER(5,2) NOT NULL,
    phone LONG NOT NULL,
    address VARCHAR2(255),
    medical_history CLOB
);

-- Create the Appointments table
CREATE TABLE Appointments (
    appointment_id NUMBER NOT NULL,
    patient_id NUMBER,
    appointment_date VARCHAR2(100) NOT NULL,
    agenda VARCHAR2(255),
    doctor_name VARCHAR2(100),
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id)
);