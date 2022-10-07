import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {
    //Display the grid where the crowd move

    public static void main(String[] args) throws InterruptedException {
        Display display = new Display();
        MatrixEntity matrixEntity = new MatrixEntity(3,3,3);
        Entity entity = new Entity(new Position(0,0), new Position(2,2));
        matrixEntity.addEntity(entity);
        display.displayGrid(matrixEntity);
        entity.move(new Position(2,1));
        Thread.sleep(2000);
        display.updateGridV2(matrixEntity.getListEntity());
        entity.move(new Position(1,1));
    }
}
