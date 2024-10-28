package ru.nstu.sudokugame.Model;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class Model {
    private int difficulty;
    private int N;
    private TextField[][] textFields;
    private int[][] startGrid;
    private int[][] randomGrid;
    private int[][] currGrid;
    private int checkSum = 0;

    public Model(int N, int dif) {
        this.N = N;
        this.difficulty = dif;
        checkSum = checkSumValue();

        start();
    }

    public void start() {
        // рандомная сетка
        randomGrid = SudokuGrid.createRandomGrid(N);
        // стартовая сетка
        startGrid = SudokuGrid.createStartGrid(randomGrid, N, difficulty);
        // инициализ масив
        textFields = new TextField[N][N];
        // заполнение масива новыми TextField
        initTextFields();
        // заполнение значениями
        fillTextField(startGrid);
        currGrid = new int[N][N];
    }

    public int checkSumValue() {
        return N * (N + 1) / 2;
    }

    public void initTextFields() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                TextField cell = new TextField();
                cell.setPrefWidth(35);
                cell.setPrefHeight(35);
                cell.setAlignment(Pos.CENTER);
                cell.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (!"1234567890".contains(keyEvent.getCharacter())) {
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
                    Font boldFont = Font.font("Arial", FontWeight.BOLD, 14);
                    textFields[i][j].setFont(boldFont);
                }
            }
        }
    }

    public void cheat() {

        int[][] solution = new int[startGrid.length][];
        for (int ii = 0; ii < startGrid.length; ii++) {
            solution[ii] = Arrays.copyOf(startGrid[ii], startGrid[ii].length);
        }

        SudokuSolver.solveSudoku(solution, 0, 0, N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (startGrid[i][j] == 0) {
                    textFields[i][j].setText(Integer.toString(solution[i][j]));
                    textFields[i][j].setEditable(true);
                }
            }
        }
    }

    public GridPane createSudokuGridUI(int N) {
        GridPane gridPane = new GridPane();
        int size = N + (int) sqrt(N) - 1;

        int pattern[][] = GridTemplate.generateMatrix(N);
        int row = 0, col = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (pattern[j][i] == 1) {
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
                if (!Objects.equals(textFields[i][j].getText(), "")) {
                    currGrid[i][j] = Integer.parseInt(textFields[i][j].getText());
                } else {
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

    public int getCheckSum() {
        return checkSum;
    }
}