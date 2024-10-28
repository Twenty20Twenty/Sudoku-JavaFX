package ru.nstu.sudokugame.Controller;

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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
    @FXML
    MenuItem menuCheat;
    private Model model;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // иниц модель
        model = new Model(N, difficulty);
        // создание PaneGrid для пользов интерф
        GridPane gridPane = createSudokuGridUI(model.getN());
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, null, null);
        Background background = new Background(backgroundFill);
        gridPane.setBackground(background);
        // настройка размеров
        anchorPane.setPrefWidth(N * 35 + (size - N) * 5 + 10);
        anchorPane.setPrefHeight(N * 35 + (size - N) * 5 + 35);
        menuBar.setPrefWidth(N * 35 + (size - N) * 5 + 10);
        anchorPane.getChildren().add(gridPane);
        gridPane.setLayoutY(30);
        gridPane.setLayoutX(5);
        // иниц обработчиков меню
        initAction();
    }

    public GridPane createSudokuGridUI(int N) {
        GridPane gridPane = new GridPane();
        int size = N + (int) sqrt(N) - 1;

        int pattern[][] = SudokuGrid.generateTemplateMatrix(N);
        int row = 0, col = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (pattern[j][i] == 1) {
                    gridPane.add(model.getTextFields()[row][col], j, i);
                }
                if (pattern[j][i] == 0) {
                    Pane cell = new Pane();
                    cell.setPrefWidth(5);
                    cell.setPrefHeight(5);
                    gridPane.add(cell, j, i);
                }
                if (pattern[0][j] == 1) {
                    col++;
                }
            }
            if (pattern[i][0] == 1) {
                row++;
            }
            col = 0;
        }
        return gridPane;
    }

    public void initAction() {
        menuCheat.setOnAction(event -> {
            model.cheat();
        });
        menuCheck.setOnAction(event -> {
            model.updateCurrentGrid();
            if (SudokuGrid.gridWinCheck(model.getCurrGrid(), model.getN(), model.getCheckSum())) {
                Stage dialog = new Stage();

                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle("Результат");

                Label label = new Label("Победа!");
                label.setAlignment(Pos.TOP_CENTER);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setFont(new Font(24));

                Button closeButton = new Button("Закрыть приложение");
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

                VBox dialogLayout = new VBox();
                dialogLayout.getChildren().addAll(label, buttonLayout);
                dialogLayout.setAlignment(Pos.CENTER);

                Scene dialogScene = new Scene(dialogLayout, 275, 200);
                dialog.setScene(dialogScene);
                dialog.showAndWait();
            } else {
                Stage dialog = new Stage();

                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle("Результаты");

                Label label = new Label("До победы еще далеко.\n Присутствуют ошибки.");
                label.setAlignment(Pos.TOP_CENTER);
                Button closeButton = new Button("Продолжить решение");
                closeButton.setOnAction(e -> dialog.close());

                VBox dialogLayout = new VBox();
                dialogLayout.getChildren().addAll(label, closeButton);
                dialogLayout.setAlignment(Pos.CENTER);

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
            label.setAlignment(Pos.CENTER);
            Button closeButton = new Button("Закрыть");
            closeButton.setOnAction(e -> dialog.close());

            VBox dialogLayout = new VBox();
            dialogLayout.getChildren().addAll(label, closeButton);
            dialogLayout.setAlignment(Pos.CENTER);

            Scene dialogScene = new Scene(dialogLayout, 200, 200);
            dialog.setScene(dialogScene);
            dialog.showAndWait();
        });
    }
}
