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
        int length = 6;
        int height = 6;
        Display display = new Display(length,height);
        MatrixEntity matrixEntity = new MatrixEntity(length,height,3);

        for(int i = 0 ; i < 3 ; i++){
            Position currentPos = Position.getRandomPosition(0,2);
            Position finalPos = Position.getRandomPosition(0,2);
            Entity ent = new Entity(currentPos, finalPos);
            matrixEntity.addEntity(ent);
        }

        /*
        matrixEntity.addEntity(new Entity(new Position(0,0), new Position(4,4)));
        matrixEntity.addEntity(new Entity(new Position(0,4), new Position(0,0)));
        matrixEntity.addEntity(new Entity(new Position(5,5), new Position(5,0)));
*/
        display.displayGrid(matrixEntity);
        ArrayList<Entity> listEnt = matrixEntity.getListEntity();
        for(Entity entity: listEnt){
            entity.move(Position.getRandomLogicalPosition(entity.getCurrentPosition(), length, height));
            display.updateGrid(matrixEntity.getListEntity());
            Thread.sleep(750);
        }
    }
}
