package ru.nstu.sudokugame.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ru.nstu.sudokugame.MainLauncher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    ComboBox difficultyComboBox;
    @FXML
    ComboBox gridNComboBox;
    @FXML
    Button startButton;
    @FXML
    Button exitButton;

    @FXML
    public void startButtonAction(ActionEvent event) throws IOException {
        int dif = 0;
        int N = 9;
        if (difficultyComboBox.getValue() == "Легко")
            dif = 0;
        else if (difficultyComboBox.getValue() == "Средне")
            dif = 1;
        else if (difficultyComboBox.getValue() == "Сложно")
            dif = 2;
        if (gridNComboBox.getValue() == "4x4")
            N = 4;
        else if (gridNComboBox.getValue() == "9x9")
            N = 9;
        else if (gridNComboBox.getValue() == "16x16")
            N = 16;
        else if (gridNComboBox.getValue() == "25x25")
            N = 25;

        GameController.setN(N);
        GameController.setDifficulty(dif);

        Parent gameParent = FXMLLoader.load(MainLauncher.class.getResource("game.fxml"));
        Scene gameScene = new Scene(gameParent);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(gameScene);
        mainStage.show();
    }

    @FXML
    public void exitButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        difficultyComboBox.getItems().addAll("Легко", "Средне", "Сложно");
        gridNComboBox.getItems().addAll("4x4", "9x9", "16x16", "25x25");
        difficultyComboBox.setValue("Легко");
        gridNComboBox.setValue("9x9");
    }


}
