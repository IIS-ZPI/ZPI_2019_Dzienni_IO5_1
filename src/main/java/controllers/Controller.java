package controllers;

import Utils.Containers;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import models.CountSessionStateModel;
import models.CurrencyModel;
import models.RateModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    StatisticsController statisticsController;

    CurrencyPairController currencyPairController;

    SessionCountController sessionCountController;

    @FXML GridPane mainGridPane;  //parametr od formatowania głównego okna aplikacji

    @FXML ComboBox analiseType;

    @FXML ComboBox currency;

    @FXML ComboBox period;

    @FXML TextArea chartArea;

    @FXML LineChart<String, Number> lineChart;

    @FXML CategoryAxis xAxis;

    @FXML NumberAxis yAxis;

    XYChart.Series<String, Number> series;

    private Containers containers;          //klasa od pobierania listy walut z pliku

    private String fileWithCurrences = "Exchange Rate.txt";

    private List<String> currences;      // lista dostępnych walut

    private List<String> currencesPairs;     // lista dostepnych part walut

    @FXML public void initialize() {
        chartArea.setText("Tu się wyświętlą dane z analizy");

        xAxis.setLabel("Dzień");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Cena");
        yAxis.setForceZeroInRange(false);

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

    private List<String> setAllPeriods() {
        List<String> periods = new ArrayList<>();
        periods.add("1 tydzień");
        periods.add("2 tygodnie");
        periods.add("1 miesiac");
        periods.add("kwartal");
        periods.add("pół roku");
        periods.add("rok");
        return periods;
    }

    private List<String> setPeriodsForDistributionOfChanges() {
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
        statisticsController = new StatisticsController();
        statisticsController.setCurrency(getCurrencyName());
        statisticsController.setPeriodAndCalculate(getPeriodName());
        System.out.println(statisticsController.getStats());

        return statisticsController.getStats().toString();
    }

    public void distributionOfChanges() {                //funkcja do ustawiania parametów do Rozkład
        currencyPairController.setPeriod(getPeriodName());
        currencyPairController.setChosedCurrencyPair(getCurrencyName());
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel = currencyPairController.calculateCurrencyPair();

        List<RateModel> rateModels = new ArrayList<>();
        rateModels = currencyModel.getRates();
        if (!lineChart.getData().isEmpty()) {
            lineChart.getData().clear();
        }
        series = new XYChart.Series();

        for (RateModel rateModel : rateModels) {

            series.getData().add((new XYChart.Data(rateModel.getEffectiveDate(), rateModel.getMid())));
        }
        lineChart.getData().add(series);

    }

    public CountSessionStateModel getSessionCount() {
        sessionCountController = new SessionCountController();
        sessionCountController.setCurrency(getCurrencyName());
        sessionCountController.setPeriodAndCalculate(getPeriodName());

        return sessionCountController.getCountSessionStateModel();
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

    @FXML public void showNewItemDialog() throws ParseException {
        switch (getAnaliseTypeName()) {
            case "Wyznaczanie ilości sesji": {
                System.out.println(getAnaliseTypeName());
                System.out.println();
                if (getSessionCount() == null)
                    chartArea.setText("Cannot calculate sessions count");
                else
                    chartArea.setText(getSessionCount().toString());               //tu podać string do wyświetlenia w gui


                break;
            }
            case "Miary statystyczne": {
                System.out.println(getAnaliseTypeName());
                System.out.println("currency name : " + getCurrencyName());
                chartArea.setText(getStatistics());
                break;
            }
            case "Rozkład zmian": {
                distributionOfChanges();
                System.out.println(getAnaliseTypeName());
                break;
            }
        }

    }

}


