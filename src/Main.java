import javax.swing.*;
import java.awt.*;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {
    //Display the grid where the crowd move

    public static void main(String[] args) {
        Display display = new Display();
        MatrixEntity matrixEntity = new MatrixEntity(3,3,3);
        Entity ent = new Entity(new Position(0,0), new Position(2,2));
        matrixEntity.addEntity(ent);
        display.displayGrid(matrixEntity);
        ent.move(new Position(1,1));
    }
}
