package com.project;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import com.project.data.DataContainer;
import com.project.database.DatabaseManager;

public class App extends Application {
    // Properties
    Image appLogo = new Image(getClass().getResource("/img/logo.png").toString());

    Stage stage;
    int CURRENT_STATE = 0;

    DataContainer dataContainer = new DataContainer();

    @Override
    public void start(Stage stage) throws IOException {
        // Global stage settings
        this.stage = stage;
        this.stage.setResizable(false);
        this.stage.getIcons().add(appLogo);
        
        refreshState(0);

        this.stage.setOnCloseRequest(e -> {
            DatabaseManager.closeConnection();
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public void refreshState(int STATE){
        CURRENT_STATE = STATE;

        this.stage.close();

        try{
        switch (CURRENT_STATE) {
            case 0:
                Login login = new Login(this);
                login.start(this.stage);
                break;
        
            case 1:
                Homescreen homescreen = new Homescreen();
                homescreen.start(this.stage);
                break;

            default:
                break;
        }
        }catch(Exception e){
            System.out.println("Failed to change state:");
            System.out.println(e);
        }
    }

}