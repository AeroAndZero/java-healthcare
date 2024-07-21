package com.project.components;

import javafx.scene.control.Control;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class FormVFields extends VBox{
    public Control control1;
    public Control control2;
    
    public FormVFields(Control control1, Control control2){
        super();
        
        this.control1 = control1;
        this.control2 = control2;
        
        this.getChildren().addAll(control1, control2);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);
    }
}
