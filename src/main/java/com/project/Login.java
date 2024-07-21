package com.project;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {
    private App appRoot;

    public static Scene Login;
    public double WIDTH = 800;
    public double HEIGHT = 576;
    public ActionEvent onLogin;

    public Login(App appRoot){
        this.appRoot = appRoot;
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        // Pre load
        Image logoJavaHealthcare = new Image(getClass().getResource("/img/java-healthcare-logo.png").toString());

        // Controls & Styling
        Label lWelcomeTo = new Label("Welcome to");
        lWelcomeTo.setStyle("-fx-font-size: 3em; -fx-font-weight: bold");
        
        ImageView ivLogo = new ImageView(logoJavaHealthcare);
        ivLogo.setFitWidth(logoJavaHealthcare.getWidth()/3);
        ivLogo.setFitHeight(logoJavaHealthcare.getHeight()/3);
        
        Label lLoginTo = new Label("Login to get started");
        lLoginTo.setPrefWidth(WIDTH - 40);
        lLoginTo.setAlignment(Pos.CENTER_LEFT);
        lLoginTo.setStyle("-fx-font-size: 2em; -fx-padding: 20px 0 0 0;");
        
        Separator separator1 = new Separator();
        separator1.setMaxWidth(WIDTH - 80);
        separator1.setStyle("-fx-background-color: #AAAAAA;-fx-background-radius: 2px;");
        separator1.setHalignment(HPos.CENTER);

        HBox hboxUsername = new HBox();
        Label lUsername = new Label("Username: ");
        TextField tfUsername = new TextField();
        hboxUsername.setSpacing(10);
        hboxUsername.setAlignment(Pos.CENTER);
        hboxUsername.getChildren().addAll(lUsername, tfUsername);

        HBox hboxPassword = new HBox();
        Label lPassword = new Label("Password: ");
        PasswordField tfPassword = new PasswordField();
        hboxPassword.setSpacing(10);
        hboxPassword.setAlignment(Pos.CENTER);
        hboxPassword.getChildren().addAll(lPassword, tfPassword);
        
        Label hint = new Label("Hint: username and password is 'admin'");
        
        Button btnLogin = new Button("Login");
        btnLogin.setPrefWidth(80);
        btnLogin.setOnAction(e -> {
            // Login handeling here
            if(tfUsername.getText().trim().equals("admin") && tfPassword.getText().trim().equals("admin")){
                appRoot.refreshState(1);
            }else{
                hint.setStyle("-fx-text-fill: red;");
                hint.setText("Invalid username or password! Hint: username and password is 'admin'");
            }
        });

        // Parent
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0,40,40,40));
        root.setSpacing(20);
        root.getChildren().addAll(
            lWelcomeTo,
            ivLogo, 
            lLoginTo, 
            separator1, 
            hboxUsername, 
            hboxPassword, 
            btnLogin,
            hint
        );

        // Rendering scene
        Login = new Scene(root, this.WIDTH, this.HEIGHT);
        stage.setScene(Login);
        stage.setTitle("Welcome to Java healthcare");
        stage.show();
    }


}
