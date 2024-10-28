package ru.nstu.sudokugame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class SudokuApp extends javafx.application.Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage mainStage) throws IOException {

        Parent root = FXMLLoader.load(MainLauncher.class.getResource("mainPage.fxml"));
        Scene mainPageScene = new Scene(root);

        mainStage.setMaximized(false);
        mainStage.setResizable(false);
        mainStage.setTitle("Sudoku Game");
        mainStage.getIcons().add(new Image(MainLauncher.class.getResourceAsStream("icon-logo.png")));

        mainStage.setScene(mainPageScene);
        mainStage.show();
    }
}