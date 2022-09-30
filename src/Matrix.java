public class Matrix {
    int length; //length of the grid
    int height; //height of the grid
    double peopleNumber; //number of people in the crowd
    int[][] matrix;

    public Matrix(int length, int height, double peopleNumber) {
        this.length = length;
        this.height = height;
        this.peopleNumber = peopleNumber;
        matrix = new int[length][height];
    }

    /**
     * Create the grid where the crowd move
     */
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
