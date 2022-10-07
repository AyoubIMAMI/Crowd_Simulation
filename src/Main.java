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

        display.displayGrid(matrixEntity);
        ArrayList<Entity> listEnt = matrixEntity.getListEntity();
        while(true){
            for(Entity entity: listEnt){
                entity.move(Position.getRandomLogicalPosition(entity.getCurrentPosition(), lenght, height));
            }
            Thread.sleep(750);
            display.updateGrid(matrixEntity.getListEntity());
        }
    }
}
