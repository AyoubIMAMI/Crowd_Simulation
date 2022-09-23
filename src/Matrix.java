/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Matrix class : matrix where the crowd will move
 */

public class Matrix {
    int length;
    int height;
    double peopleNumber;
    int[][] matrix;

    public Matrix(int length, int height, double peopleNumber) {
        this.length = length;
        this.height = height;
        this.peopleNumber = peopleNumber;
    }

    void createMatrix() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = -1;
            }
        }
    }

    int[][] getMatrix() {
        return matrix;
    }

}
