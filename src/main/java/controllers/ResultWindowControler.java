package controllers;

import javafx.fxml.FXML;

import java.awt.*;

public class ResultWindowControler {
    @FXML Label mainLabel;



    public void setLabelAs(){
        mainLabel.setText("dupa");
        System.out.println(mainLabel.getText());
        System.out.println("dupa");
    }

}
