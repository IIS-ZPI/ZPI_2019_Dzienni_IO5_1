package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Controller {

    @FXML GridPane mainGridPane;  //parametr od formatowania głównego okna aplikacji

    public void showNewItemDialog() {                                                                                   // funkcja od wyskakującego okienka
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Okno z wynikiem i wykresem ");
       // dialog.setHeaderText("Taki nagłówek ale odgordzony od reszty");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(
                "/resultWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.show();

        } catch (IOException e) {
            System.out.println("Nie mogę załadować dialogu");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        //        Optional<ButtonType> result = dialog.showAndWait();

    }
}


