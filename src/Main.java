import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {
    private static final int length = 3;
    private static final int height = 3;
    private static final int exponent = 0;

    public static void main(String[] args) {
        Matrix matrix = new Matrix(length, height, Math.pow(2, exponent));
        matrix.createMatrix();
        MatrixEntity matrixEntity = new MatrixEntity(length, height, Math.pow(2, exponent));

        Entity ent = new Entity(new Position(0,0), new Position(2,2));
        ArrayList<Entity> listEntity = new ArrayList<>();
        listEntity.add(ent);
        matrixEntity.addEntity(ent);

        JFrame frame = matrixEntity.createGraphicGrid();
        frame.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ent.move(new Position(1,1));       //matrixEntity.move();
        matrixEntity.updateGrid(listEntity);
        System.out.println("fiiin");
        frame.removeAll();
        //frame = matrixEntity.createGraphicGrid();
        frame.validate();
        frame.repaint();
    }
}
