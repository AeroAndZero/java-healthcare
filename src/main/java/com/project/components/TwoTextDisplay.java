package com.project.components;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TwoTextDisplay extends VBox{
    public String titleStyle = "-fx-font-size: 2em;";
    public String subtitleStyle = "-fx-font-size: 3em; -fx-font-weight: bold";

    public String title = "";
    public String subtitle = "";

    private Label lTitle;
    private Label lSubtitle;

    public TwoTextDisplay(String title, String subtitle){
        super();
        this.title = title;
        this.subtitle = subtitle;

        lTitle = new Label(this.title);
        lTitle.setStyle(titleStyle);

        lSubtitle = new Label(this.subtitle);
        lSubtitle.setStyle(subtitleStyle);

        this.getChildren().addAll(lTitle, lSubtitle);
    }

    public void setTitle(String text){
        lTitle.setText(text);
    }

    public void setSubtitle(String text){
        lSubtitle.setText(text);
    }

    public Label getTitleLabel(){
        return lTitle;
    }

    public Label getSubtitleLabel(){
        return lSubtitle;                                          
    }
}
