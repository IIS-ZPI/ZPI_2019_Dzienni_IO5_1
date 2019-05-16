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
        statisticsController.setPeriodAndCalculate(getPeriodName());
        System.out.println(statisticsController.getStats());

        return statisticsController.getStats().toString();
    }

    @FXML public void showNewItemDialog() {
        statisticsController = new StatisticsController();

        System.out.println("Zoba tera");
        chartArea.setText(getStatistics());

    }

    //    @FXML
    //    public void showNewItemDialog() {                                                                                   // funkcja od wyskakującego okienka
    //        Dialog<ButtonType> dialog = new Dialog<>();
    //        dialog.initOwner(mainGridPane.getScene().getWindow());
    //        dialog.setTitle("Okno z wynikiem i wykresem ");
    //        // dialog.setHeaderText("Taki nagłówek ale odgordzony od reszty");
    //        FXMLLoader fxmlLoader = new FXMLLoader();
    //        fxmlLoader.setLocation(getClass().getResource("/resultWindow.fxml"));
    ////        ResultWindowControler resultWindowControler = new ResultWindowControler();
    //        ResultWindowControler resultWindowControler = fxmlLoader.<ResultWindowControler>getController();
    //
    //        statisticsController = new StatisticsController();
    //        try {
    //            dialog.getDialogPane().setContent(fxmlLoader.load());
    //            dialog.show();
    //            getIndexes(); // do debugu wyświetla indeksy wybranych comboBoxów
    //            getStatistics();
    //            resultWindowControler.setLabelAs();
    //
    //        } catch (IOException e) {
    //            System.out.println("Nie mogę załadować dialogu");
    //            e.printStackTrace();
    //            return;
    //        }
    //        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    //
    //        //        Optional<ButtonType> result = dialog.showAndWait();
    //
    //    }
}


