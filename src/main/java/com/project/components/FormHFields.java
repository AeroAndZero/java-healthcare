package com.project.components;

import javafx.scene.control.Control;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class FormHFields extends HBox{
    public Control control1;
    public Control control2;
    
    public FormHFields(Control control1, Control control2){
        super();

        this.control1 = control1;
        this.control2 = control2;
        
        this.getChildren().addAll(control1, control2);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);
    }
}
