/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Matrix class : matrix where the crowd will move
 */

public class Matrix {
    int m;
    int n;
    int[][] matrix;

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
    }

    void createMatrix() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = -1;
            }
        }
    }

    int[][] getMatrix() {
        return matrix;
    }

}
