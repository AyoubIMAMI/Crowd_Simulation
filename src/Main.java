/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */

public class Main {
    private static final int length = 120;
    private static final int height = 100;
    private static final int exponent = 0;

    public static void main(String[] args) {
        Matrix matrix = new Matrix(length, height, Math.pow(2, exponent));
        matrix.createMatrix();
    }
}
