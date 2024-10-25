package ru.nstu.sudokugame.Model;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.sqrt;

public class SudokuGrid {

    public static int[][] createRandomGrid(int N) {
        int[][] matrix = new int[N][N];
        int n = (int) sqrt(N);
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {
                matrix[i][j] = ((i * n + i / n + j) % (n * n)) + 1;
            }
        }
        matrix = mix(2 * N, matrix, N);

        return matrix;
    }

    private static int[][] mix(int iterations, int[][] M, int N) {
        Random random = new Random();

        int[][][] tmp = {M};
        Runnable[] mixFunctions = {
                () -> {
                    tmp[0] = swapRowsInArea(tmp[0], N);
                },
                () -> {
                    tmp[0] = swapColumsInArea(tmp[0], N);
                },
                () -> {
                    tmp[0] = swapRowsArea(tmp[0], N);
                },
                () -> {
                    tmp[0] = swapColumsArea(tmp[0], N);
                },
                () -> {
                    tmp[0] = transposing(tmp[0], N);
                }
        };

        for (int i = 0; i < iterations; i++) {
            int idFunk = random.nextInt(mixFunctions.length);
            mixFunctions[idFunk].run();
        }
        return tmp[0];
    }

    private static int[][] swapRowsInArea(int[][] M, int N) {
        Random random = new Random();
        int n = (int) sqrt(N);

        int area = random.nextInt(0, n);
        int line1 = random.nextInt(0, n);
        int N1 = area * n + line1;

        int line2 = random.nextInt(0, n);
        while (line1 == line2) {
            line2 = random.nextInt(0, n);
        }
        int N2 = area * n + line2;

        int tmp[] = M[N2];
        M[N2] = M[N1];
        M[N1] = tmp;

        return M;
    }

    private static int[][] swapColumsInArea(int[][] M, int N) {
        M = transposing(M, N);
        M = swapRowsInArea(M, N);
        M = transposing(M, N);
        return M;
    }

    private static int[][] transposing(int[][] M, int N) {
        int answer[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                answer[j][i] = M[i][j];
            }
        }
        return answer;
    }

    private static int[][] swapRowsArea(int[][] M, int N) {
        Random random = new Random();
        int n = (int) sqrt(N);

        int area1 = random.nextInt(0, n);
        int area2 = random.nextInt(0, n);
        while (area1 == area2) {
            area2 = random.nextInt(0, n);
        }

        for (int i = 0; i < n; i++) {
            int N1 = area1 * n + i, N2 = area2 * n + i;
            int tmp[] = M[N2];
            M[N2] = M[N1];
            M[N1] = tmp;
        }
        return M;
    }

    private static int[][] swapColumsArea(int[][] M, int N) {
        M = transposing(M, N);
        M = swapRowsArea(M, N);
        M = transposing(M, N);
        return M;
    }

    public static int[][] createStartGrid(int[][] M, int N, int dif) {
        int[][] flook = new int[N][N];
        int iterator = 0;
        int difficulty = N * N;

        Random random = new Random();
        while (iterator < difficulty) {
            int i = random.nextInt(N), j = random.nextInt(N);
            if (flook[i][j] == 0) {
                flook[i][j] = 1;
                iterator += 1;

                int temp = M[i][j];
                M[i][j] = 0;
                difficulty -= 1;

                int[][] tempM = new int[M.length][];
                for (int ii = 0; ii < M.length; ii++) {
                    tempM[ii] = Arrays.copyOf(M[ii], M[ii].length);
                }

                if (!SudokuSolver.solveSudoku(tempM, 0, 0, N)) {
                    M[i][j] = temp;
                    difficulty += 1;
                    System.out.println("Конец");
                }
            }
        }
        System.out.println(difficulty);
        return M;
    }

    public static boolean gridWinCheck(int[][] M, int N) {
        int checkSum = 0;
        for (int i = 0; i < N; i++) {
            int rowSum = 0;
            checkSum = 0;
            for (int j = 0; j < N; j++) {
                rowSum += M[i][j];
                checkSum += j + 1;
            }
            if (checkSum != rowSum) {
                return false;
            }
        }

        for (int j = 0; j < N; j++) {
            int colSum = 0;
            checkSum = 0;
            for (int i = 0; i < N; i++) {
                colSum += M[i][j];
                checkSum += i + 1;
            }
            if (checkSum != colSum) {
                return false;
            }
        }

        return true;
    }

    public static void print(int[][] M, int N){
        for (int i=0;i<N;i++){
            for (int j=0; j<N;j++){
                System.out.print(M[i][j]); System.out.print(" ");
            }System.out.println();
        }System.out.println("___________________");
    }
}