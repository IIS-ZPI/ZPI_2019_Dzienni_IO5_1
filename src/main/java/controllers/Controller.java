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



    @FXML public void initialize() {
        chartArea.setText("Tu się wyświętlą dane z analizy");

        containers = new Containers();
        currences = new ArrayList<>();
        currences = containers.readFile(fileWithCurrences);

        currencyPairController = new CurrencyPairController();
        currencesPairs = new ArrayList<>();
        currencesPairs = currencyPairController.getAvailableCurrencyPairs();

        lineChart.setVisible(false);
        currency.getItems().addAll(currences);
        period.getItems().addAll(setAllPeriods());

    }

    private List<String> setAllPeriods(){
        List<String> periods = new ArrayList<>();
        periods.add("1 tydzień");
        periods.add("2 tygodnie");
        periods.add("1 miesiac");
        periods.add("kwartal");
        periods.add("pół roku");
        periods.add("rok");
        return periods;
    }

    private List<String> setPeriodsForDistributionOfChanges(){
        List<String> periods = new ArrayList<>();
        periods.add("1 miesiac");
        periods.add("kwartal");
        return periods;
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

    public String getStatistics() {                         // funkcja od ustawiania parametrów do Miar starystycznych
        statisticsController.setCurrency(getCurrencyName());
        statisticsController.setPeriodAndCalculate(getPeriodName());
        System.out.println(statisticsController.getStats());

        return statisticsController.getStats().toString();
    }

    public void distributionOfChanges(){                //funkcja do ustawiania parametów do Rozkładu zmian
//        currencyPairController.setPeriod();
    }

    @FXML public void checkAnaliseName() {
        if (getAnaliseTypeName().equals("Rozkład zmian")) {
            lineChart.setVisible(true);
            chartArea.setVisible(false);

            currency.getItems().clear();
            currency.setValue(currencesPairs.get(0));
            currency.getItems().addAll(currencesPairs);

            period.getItems().clear();
            period.setValue(setPeriodsForDistributionOfChanges().get(0));
            period.getItems().addAll(setPeriodsForDistributionOfChanges());
        } else {
            lineChart.setVisible(false);
            chartArea.setVisible(true);

            currency.getItems().clear();
            currency.setValue("USD");
//            currency.setValue(currences.get(0));
            currency.getItems().addAll(currences);

            period.getItems().clear();
            period.setValue(setAllPeriods().get(0));
            period.getItems().addAll(setAllPeriods());

        }
        System.out.println("to działa" + getAnaliseTypeName());

    }

    @FXML public void showNewItemDialog() {
        switch (getAnaliseTypeName()) {
            case "Wyznaczanie ilości sesji": {
                System.out.println(getAnaliseTypeName());
                break;
            }
            case "Miary statystyczne": {
                System.out.println(getAnaliseTypeName());
                statisticsController = new StatisticsController();
                System.out.println("currency name : " + getCurrencyName());
                chartArea.setText(getStatistics());
                break;
            }
            case "Rozkład zmian": {
                System.out.println(getAnaliseTypeName());
                break;
            }
        }

        //         <String fx:value="Wyznaczanie ilości sesji"/>
        //                    <String fx:value="Miary statystyczne"/>
        //                    <String fx:value="Rozkład zmian"/>

    }

}


