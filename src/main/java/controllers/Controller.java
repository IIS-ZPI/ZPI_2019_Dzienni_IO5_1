package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class Controller {

    StatisticsController statisticsController;

    @FXML GridPane mainGridPane;  //parametr od formatowania głównego okna aplikacji

    @FXML ComboBox analiseType;

    @FXML ComboBox currency;

    @FXML ComboBox period;

    @FXML TextArea chartArea;

    @FXML Button calculateButton;

    //    @FXML Label testLabel;

    StringBuilder result;

    @FXML public void initialize() {
        chartArea.setText("Tu kiedyś będzie wykers .. i hope so");


    }

    public int getAnaliseTypeIndex() {
        //return analiseType.getValue().toString();
        return analiseType.getSelectionModel().getSelectedIndex();
    }

    public int getCurrencyIndex() {
        return currency.getSelectionModel().getSelectedIndex();
    }
    public String getCurrencyName(){
        return currency.getSelectionModel().getSelectedItem().toString();
    }

    public int getPeriodIndex() {
        return period.getSelectionModel().getSelectedIndex();
    }

    public String getPeriodName() {
        return period.getSelectionModel().getSelectedItem().toString();
    }

    public void getIndexes() {
        System.out.println("Analise type :" + getAnaliseTypeIndex());
        System.out.println("Currency :" + getCurrencyIndex());
        System.out.println("Period :" + getPeriodName());
        //        System.out.println("Period :" + getPeriodIndex());
    }

    public String getStatistics() {
        statisticsController.setCurrency(getCurrencyName());
        statisticsController.setPeriodAndCalculate(getPeriodName());
        System.out.println(statisticsController.getStats());

        return statisticsController.getStats().toString();
    }

    @FXML public void showNewItemDialog() {
        statisticsController = new StatisticsController();

        System.out.println("currency name : " + getCurrencyName());
        chartArea.setText(getStatistics());

    }


}


