package controllers;

import Utils.Containers;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
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

    @FXML LineChart lineChart;

    private Containers containers;          //klasa od pobierania listy walut z pliku

    private String fileWithCurrences = "Exchange Rate.txt";

    @FXML public void initialize() {
        chartArea.setText("Tu się wyświętlą dane z analizy");
        containers = new Containers();
        lineChart.setVisible(false);
        currency.getItems().addAll(containers.readFile(fileWithCurrences));

    }

    public int getAnaliseTypeIndex() {
        //return analiseType.getValue().toString();
        return analiseType.getSelectionModel().getSelectedIndex();
    }

    public String getAnaliseTypeName() {
        return analiseType.getSelectionModel().getSelectedItem().toString();
    }

    public int getCurrencyIndex() {
        return currency.getSelectionModel().getSelectedIndex();
    }

    public String getCurrencyName() {
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
    }

    public String getStatistics() {
        statisticsController.setCurrency(getCurrencyName());
        statisticsController.setPeriodAndCalculate(getPeriodName());
        System.out.println(statisticsController.getStats());

        return statisticsController.getStats().toString();
    }

    @FXML public void checkAnaliseName() {
        if (getAnaliseTypeName().equals("Rozkład zmiam")) {
            lineChart.setVisible(true);
            chartArea.setVisible(false);
        } else {
            lineChart.setVisible(false);
            chartArea.setVisible(true);
        }
        System.out.println("to działa" + getAnaliseTypeName());

    }

    @FXML public void showNewItemDialog() {
        statisticsController = new StatisticsController();

        System.out.println("currency name : " + getCurrencyName());
        chartArea.setText(getStatistics());

    }

}


