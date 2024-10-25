package ru.nstu.sudokugame.Model;

import static java.lang.Math.sqrt;

public class SudokuSolver {
    // N is the size of the 2D matrix   N*N
    //static int N = 9;

    /* Takes a partially filled-in grid and attempts
    to assign values to all unassigned locations in
    such a way to meet the requirements for
    Sudoku solution (non-duplication across rows,
    columns, and boxes) */
    static boolean solveSudoku(int grid[][], int row, int col, int N)
    {

        /*if we have reached the 8th
           row and 9th column (0
           indexed matrix) ,
           we are returning true to avoid further
           backtracking       */
        if (row == N - 1 && col == N)
            return true;

        // Check if column value  becomes 9 ,
        // we move to next row
        // and column start from 0
        if (col == N) {
            row++;
            col = 0;
        }

        // Check if the current position
        // of the grid already
        // contains value >0, we iterate
        // for next column
        if (grid[row][col] != 0)
            return solveSudoku(grid, row, col + 1, N);

        for (int num = 1; num < N + 1; num++) {

            // Check if it is safe to place
            // the num (1-9)  in the
            // given row ,col ->we move to next column
            if (isSafe(grid, row, col, num, N)) {

                /*  assigning the num in the current
                (row,col)  position of the grid and
                assuming our assigned num in the position
                is correct */
                grid[row][col] = num;

                // Checking for next
                // possibility with next column
                if (solveSudoku(grid, row, col + 1, N))
                    return true;
            }
            /* removing the assigned num , since our
               assumption was wrong , and we go for next
               assumption with diff num value   */
            grid[row][col] = 0;
        }
        return false;
    }

    // Check whether it will be legal
    // to assign num to the
    // given row, col
    static boolean isSafe(int[][] grid, int row, int col, int num, int N)
    {
        int n = (int) sqrt(N);
        // Check if we find the same num
        // in the similar row , we
        // return false
        for (int x = 0; x <= N - 1; x++)
            if (grid[row][x] == num)
                return false;

        // Check if we find the same num
        // in the similar column ,
        // we return false
        for (int x = 0; x <= N - 1; x++)
            if (grid[x][col] == num)
                return false;

        // Check if we find the same num
        // in the particular 3*3
        // matrix, we return false
        int startRow = row - row % n, startCol
                = col - col % n;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (grid[i + startRow][j + startCol] == num)
                    return false;

        return true;
    }
}
