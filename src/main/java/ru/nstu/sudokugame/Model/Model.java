package ru.nstu.sudokugame.Model;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

import static java.lang.Math.sqrt;

public class Model {
    private int difficulty;
    private int N;
    private TextField[][] textFields;
    private int[][] startGrid;
    private int[][] randomGrid;
    private int[][] currGrid;

    public Model(int N, int dif) {
        this.N = N;
        this.difficulty = dif;

        start();
    }

    public void start() {
        randomGrid = SudokuGrid.createRandomGrid(N);
        startGrid = SudokuGrid.createStartGrid(randomGrid, N, difficulty);
        textFields = new TextField[N][N];
        initTextFields();
        fillTextField(startGrid);
        currGrid = new int[N][N];
    }

    public void initTextFields() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                TextField cell = new TextField();
                cell.setPrefWidth(30);
                cell.setPrefHeight(30);
                cell.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if ("/-*&!@#$%^&*()_+}{[]\"'.<>~`\\|qwertyuiop[]asdfghjkl;'zxcvbnm,./йцукенгшщзхъфывапролджэячсмитьбю. ".contains(keyEvent.getCharacter())) {
                            keyEvent.consume();
                        }
                    }
                });
                textFields[i][j] = cell;
            }
        }
    }

    public void fillTextField(int[][] grid) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0) {
                    textFields[i][j].setText("");
                    textFields[i][j].setEditable(true);
                } else {
                    textFields[i][j].setText(Integer.toString(grid[i][j]));
                    textFields[i][j].setEditable(false);
                    Font boldFont = Font.font("Arial", FontWeight.BOLD, 12);
                    textFields[i][j].setFont(boldFont);
                }
            }
        }
    }

    public GridPane createSudokuGridUI(int N) {
        GridPane gridPane = new GridPane();
        int size = N + (int) sqrt(N) - 1;

        int pattern[][] = gridTemplate.generateMatrix(N);
        int row = 0, col = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (pattern[j][i] == 1) {
                    TextField cell = new TextField();
                    cell.setPrefWidth(30);
                    cell.setPrefHeight(30);
                    cell.setAlignment(Pos.CENTER);
                    gridPane.add(textFields[row][col], j, i);
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

    public void updateCurrentGrid() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!Objects.equals(textFields[i][j].getText(), "")){
                    currGrid[i][j] = Integer.parseInt(textFields[i][j].getText());
                }
                else {
                    currGrid[i][j] = 0;
                }
            }
        }
    }

    public int[][] getCurrGrid() {
        return currGrid;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getN() {
        return N;
    }

    public int[][] getStartGrid() {
        return startGrid;
    }
}