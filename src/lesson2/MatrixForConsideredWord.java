package lesson2;

import java.util.ArrayList;
import java.util.Arrays;

class MatrixForConsideredWord {

    private int[][] resultMatrix;

    MatrixForConsideredWord(int width, int height) {
        resultMatrix = new int[height][width];
        for (int[] row: resultMatrix) {
            Arrays.fill(row, 0);
        }
    }

    boolean searchWord(ArrayList<String> matrix, String word) {
        int width = matrix.get(0).length();
        int height = matrix.size();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (search(matrix, word, i, j, 0, width, height)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean search(ArrayList<String> matrix, String word, int currentRow, int currentColumn,
                           int currentIndex, int width, int height) {
        if (resultMatrix[currentRow][currentColumn] != 0 || word.charAt(currentIndex) != matrix.get(currentRow).charAt(currentColumn)) {
            return false;
        }
        if (currentIndex == word.length() - 1) {
            resultMatrix[currentRow][currentColumn] = 1;
            return true;
        }
        boolean up = false, down = false, right = false, left = false;
        resultMatrix[currentRow][currentColumn] = 1;
        if (currentRow + 1 < height && (resultMatrix[currentRow + 1][currentColumn] == 0) && matrix.get(currentRow + 1).charAt(currentColumn) == word.charAt(currentIndex + 1)) {
            down = search(matrix, word, currentRow + 1, currentColumn, currentIndex + 1, width, height);
        }
        if (currentRow - 1 >= 0 && (resultMatrix[currentRow - 1][currentColumn] == 0) && (word.charAt(currentIndex + 1) == matrix.get(currentRow - 1).charAt(currentColumn))) {
            up = search(matrix, word, currentRow - 1, currentColumn, currentIndex + 1, width, height);
        }
        if (currentColumn + 1 < width && (resultMatrix[currentRow][currentColumn + 1] == 0) && (word.charAt(currentIndex + 1) == matrix.get(currentRow).charAt(currentColumn + 1))) {
            right = search(matrix, word, currentRow, currentColumn + 1, currentIndex + 1, width, height);
        }
        if ((currentColumn - 1 >= 0 && (resultMatrix[currentRow][currentColumn - 1] == 0) && word.charAt(currentIndex + 1) == matrix.get(currentRow).charAt(currentColumn - 1))) {
            left = search(matrix, word, currentRow, currentColumn - 1, currentIndex + 1, width, height);
        }
        return down || up || right || left;
    }
}


