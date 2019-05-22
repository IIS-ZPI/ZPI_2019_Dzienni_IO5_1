package controllers;

import Utils.Containers;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    StatisticsController statisticsController;

    CurrencyPairController currencyPairController;

    @FXML GridPane mainGridPane;  //parametr od formatowania głównego okna aplikacji

    @FXML ComboBox analiseType;

    @FXML ComboBox currency;

    @FXML ComboBox period;

    @FXML TextArea chartArea;

    @FXML LineChart lineChart;

    private Containers containers;          //klasa od pobierania listy walut z pliku

    private String fileWithCurrences = "Exchange Rate.txt";

    private List<String> currences;      // lista dostępnych walut

    private List<String> currencesPairs;     // lista dostepnych part walut

    @FXML public void initialize(){
        chartArea.setText("Tu się wyświętlą dane z analizy");

        containers = new Containers();
        currences = new ArrayList<>();
        currences = containers.readFile(fileWithCurrences);

        currencyPairController = new CurrencyPairController();
        currencesPairs = new ArrayList<>();
        currencesPairs = currencyPairController.getAvailableCurrencyPairs();

        lineChart.setVisible(false);
        currency.getItems().addAll(currences);

    }

    public int getAnaliseTypeIndex() {
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
            currency.getItems().clear();
            currency.setValue(currencesPairs.get(0));
            currency.getItems().addAll(currencesPairs);
        } else {
            lineChart.setVisible(false);
            chartArea.setVisible(true);
            currency.getItems().clear();
            currency.setValue(currences.get(0));
            currency.getItems().addAll(currences);

        }
        System.out.println("to działa" + getAnaliseTypeName());

    }

    @FXML public void showNewItemDialog() {
        statisticsController = new StatisticsController();

        System.out.println("currency name : " + getCurrencyName());
        chartArea.setText(getStatistics());

    }

}


