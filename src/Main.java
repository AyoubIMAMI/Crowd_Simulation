import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {
    //Display the grid where the crowd move

    public static void main(String[] args) throws InterruptedException {
        int lenght = 3;
        int height = 3;
        Display display = new Display(lenght,height);
        MatrixEntity matrixEntity = new MatrixEntity(lenght,height,3);
        for(int i = 0 ; i < 3 ; i++){
            Position currentPos = Position.getRandomPosition(0,2);
            Position finalPos = Position.getRandomPosition(0,2);
            Entity ent = new Entity(currentPos, finalPos);
            matrixEntity.addEntity(ent);
        }
        display.displayGrid(matrixEntity);
        ArrayList<Entity> listEnt = matrixEntity.getListEntity();
        for(Entity entity: listEnt){
            entity.move(Position.getRandomLogicalPosition(entity.getCurrentPosition(), lenght, height));
        }
        Thread.sleep(2000);
        display.updateGridV2(matrixEntity.getListEntity());
    }
}
