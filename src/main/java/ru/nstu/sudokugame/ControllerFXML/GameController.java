package ru.nstu.sudokugame.ControllerFXML;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nstu.sudokugame.MainLauncher;
import ru.nstu.sudokugame.Model.Model;
import ru.nstu.sudokugame.Model.SudokuGrid;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.sqrt;

public class GameController implements Initializable {
    static private Model model;
    static int N = 9;
    static int size = 5;
    static int difficulty;
    @FXML
    AnchorPane anchorPane;
    @FXML
    MenuBar menuBar;
    @FXML
    MenuItem menuCheck;
    @FXML
    MenuItem menuReset;
    @FXML
    MenuItem menuBackToStart;
    @FXML
    MenuItem menuCloseApp;
    @FXML
    MenuItem menuAuthors;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new Model(N, difficulty);
        GridPane gridPane = model.createSudokuGridUI(N);
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, null, null);
        Background background = new Background(backgroundFill);
        gridPane.setBackground(background);

        anchorPane.setPrefWidth(N * 30 + (size - N) * 5 + 10);
        anchorPane.setPrefHeight(N * 30 + (size - N) * 5 + 35);
        menuBar.setPrefWidth(N * 30 + (size - N) * 5 + 10);
        anchorPane.getChildren().add(gridPane);
        gridPane.setLayoutY(30);
        gridPane.setLayoutX(5);

        initAction();
    }

    public void initAction(){
        menuCheck.setOnAction(event -> {
            model.updateCurrentGrid();
            SudokuGrid.print(model.getCurrGrid(), model.getN());;
            if ( SudokuGrid.gridWinCheck(model.getCurrGrid(), model.getN())){
                Stage dialog = new Stage();

                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle("Результат");

                Label label = new Label("Победа!");
                label.setAlignment(Pos.TOP_CENTER);

                Button closeButton = new Button("Закрыть");
                closeButton.setOnAction(e -> {
                    dialog.close();
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.close();
                });

                Button menuButton = new Button("Вернуться в меню");
                menuButton.setOnAction(e -> {
                    dialog.close();
                    Stage mainStage = (Stage) menuBar.getScene().getWindow();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(MainLauncher.class.getResource("mainPage.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Scene mainPageScene = new Scene(root);

                    mainStage.setMaximized(false);
                    mainStage.setResizable(false);
                    mainStage.setTitle("Sudoku Game");

                    mainStage.setScene(mainPageScene);
                    mainStage.show();
                });

                HBox buttonLayout = new HBox();
                buttonLayout.getChildren().addAll(menuButton, closeButton);

                VBox dialogLayout = new VBox(10);
                dialogLayout.getChildren().addAll(label, buttonLayout);
                dialogLayout.setAlignment(Pos.BOTTOM_CENTER);

                Scene dialogScene = new Scene(dialogLayout, 200, 200);
                dialog.setScene(dialogScene);
                dialog.showAndWait();
            }
            else {
                Stage dialog = new Stage();

                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle("Результаты");

                Label label = new Label("До победы еще далеко.\n Присутствуют ошибки.");
                label.setAlignment(Pos.TOP_CENTER);
                Button closeButton = new Button("Продолжить решение");
                closeButton.setOnAction(e -> dialog.close());

                VBox dialogLayout = new VBox(10);
                dialogLayout.getChildren().addAll(label, closeButton);
                dialogLayout.setAlignment(Pos.BOTTOM_CENTER);

                Scene dialogScene = new Scene(dialogLayout, 200, 200);
                dialog.setScene(dialogScene);
                dialog.showAndWait();
            }
        });
        menuReset.setOnAction(event -> {
            model.fillTextField(model.getStartGrid());
        });
        menuBackToStart.setOnAction(event -> {
            Stage mainStage = (Stage) menuBar.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(MainLauncher.class.getResource("mainPage.fxml"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Scene mainPageScene = new Scene(root);

            mainStage.setMaximized(false);
            mainStage.setResizable(false);
            mainStage.setTitle("Sudoku Game");

            mainStage.setScene(mainPageScene);
            mainStage.show();
        });
        menuCloseApp.setOnAction(event -> {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.close();
        });
        menuAuthors.setOnAction(event -> {
            Stage dialog = new Stage();

            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Авторы");

            Label label = new Label("Лабораторная работа №1\nСтуденты:\nЛипатов Н.А., Драйко А.В.\nГруппа: АВТ-243");
            Button closeButton = new Button("Закрыть");
            closeButton.setOnAction(e -> dialog.close());

            VBox dialogLayout = new VBox();
            dialogLayout.getChildren().addAll(label, closeButton);

            Scene dialogScene = new Scene(dialogLayout, 200, 200);
            dialog.setScene(dialogScene);
            dialog.showAndWait();
        });
    }

    public static void setN(int n) {
        N = n;
        size = N + (int) sqrt(N) - 1;
    }

    public static void setSize(int size) {
        GameController.size = size;
    }

    public static void setDifficulty(int difficulty) {
        GameController.difficulty = difficulty;
    }
}
