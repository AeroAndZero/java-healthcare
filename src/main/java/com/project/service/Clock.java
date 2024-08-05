package com.project.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.project.components.TwoTextDisplay;

import javafx.application.Platform;

public class Clock implements Runnable {
    TwoTextDisplay ttd;
    boolean isTicking = false;
    
    ExecutorService executorService;

    public Clock(TwoTextDisplay ttd){
        this.ttd = ttd;
        this.executorService = Executors.newCachedThreadPool();
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
        }
        catch (InterruptedException e) {
			System.out.println("Clock stopped unexpectedly"+e.getMessage());
			Thread.currentThread().interrupt();
		}
        catch(Exception e){
            System.out.println("Clock stopped interruptly: " + e.getMessage());
        }
    }

    public synchronized void start(){
        if(!isTicking){
            isTicking = true;
            executorService.execute(this);
            System.out.println("Thread is running");
        }
    }

    public synchronized void stop(){
        isTicking = false;
        executorService.shutdown();
        System.out.println("Clock stopped working");
        
    }

    @Override
    public void run() {
        tick();
    }
}
