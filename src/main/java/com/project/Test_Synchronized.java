package com.project;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.project.classes.DatabaseObject.OPERATION;
import com.project.classes.Patient;
import com.project.database.PatientDBO;

public class Test_Synchronized {

    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        PatientDBO patientDBO = new PatientDBO();
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                int uniqueId = idGenerator.getAndIncrement();
                Patient patient = new Patient(uniqueId, "John Doe", 30, 70, 180, 1234567890L, "123 Main St", "Healthy");
                patientDBO.setOperation(OPERATION.ADD, patient);
                patientDBO.execute();
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
