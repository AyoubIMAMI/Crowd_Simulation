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

        Generation of all the entities
        for(int i = 0 ; i < 3 ; i++){
            Position currentPosition = Position.getRandomPosition(length, height);
            //matrixEntity.list
            for (Entity entity : matrixEntity.listEntity) {
                if(entity.getCurrentPosition().equals(currentPosition))
                    break;
            }
            break;

            Position finalPosition = Position.getRandomPosition(length,height);
            Entity entity = new Entity(currentPosition, finalPosition);
            matrixEntity.addEntity(entity);
        }

        /*
        matrixEntity.addEntity(new Entity(new Position(0,0), new Position(4,4)));
        matrixEntity.addEntity(new Entity(new Position(0,4), new Position(0,0)));
        matrixEntity.addEntity(new Entity(new Position(5,5), new Position(5,0)));
        */
        display.displayGrid(matrixEntity);

        //Make moves all the entities
        ArrayList<Entity> listEnt = matrixEntity.getListEntity();
        while(true){
            for(Entity entity: listEnt){
                entity.move(Position.getRandomLogicalPosition(entity.getCurrentPosition(), length, height));
            }
            Thread.sleep(750);
            display.updateGrid(matrixEntity.getListEntity());
        }
    }
}
