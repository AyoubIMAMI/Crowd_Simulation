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
        Entity entity = new Entity(new Position(0,0), new Position(2,2));
        matrixEntity.addEntity(entity);
        display.displayGrid(matrixEntity);
        entity.move(new Position(1,1));
    }
}
