package ru.nstu.sudokugame.Model;

public class GridTemplate {
    public static int[][] generateMatrix(int N) {

        int size = N + (int) Math.sqrt(N) - 1;
        int[][] matrix = new int[size][size];

        // Определяем размер мини-квадратов
        int miniSquareSize = (int) Math.sqrt(N);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Проверяем, нужно ли ставить ноль
                if ((i % (miniSquareSize + 1) == miniSquareSize) || (j % (miniSquareSize + 1) == miniSquareSize)) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = 1;
                }
            }
        }

        return matrix;
    }
}
