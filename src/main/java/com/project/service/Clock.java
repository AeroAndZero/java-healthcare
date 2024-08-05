package com.project.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.project.components.TwoTextDisplay;

import javafx.application.Platform;

public class Clock implements Runnable {
    TwoTextDisplay ttd;
    boolean isTicking = false;
    
    Thread threadInstance;

    public Clock(TwoTextDisplay ttd){
        this.ttd = ttd;
    }

    public void tick(){
        System.out.println("Clock started ticking");

        try{
            while(isTicking){

                Platform.runLater(() -> {
                    ttd.setSubtitle(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
                });

                Thread.sleep(1000);
            }
        }catch(Exception e){
            System.out.println("Clock stopped interruptly: " + e.getMessage());
        }
    }

    public void start(){
        if(threadInstance == null){
            threadInstance = new Thread(this);
        }
        
        if(!threadInstance.isAlive()){
            threadInstance.start();
        }

        System.out.println("Thread is running");
    }

    public void stop(){
        isTicking = false;
        System.out.println("Clock stopped");
    }

    @Override
    public void run() {
        isTicking = true;
        System.out.println("Thread is running");
        tick();
    }
}
