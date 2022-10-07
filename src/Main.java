import java.util.List;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {

    //grid of size length*height
    static int length = 10;
    static int height = 10;

    //number of entities on the grid
    static int entitiesNumber = 8;

    //Display the grid where the crowd move
    public static void main(String[] args) throws InterruptedException {

        Display display = new Display(length, height);
        Grid grid = new Grid(length, height, entitiesNumber);

        //create the entities
        for(int i = 0; i < entitiesNumber; i++){
            Position currentPosition = Position.getRandomPosition(length, height);
            while(Position.isPositionTaken(grid, currentPosition))
                currentPosition = Position.getRandomPosition(length, height);

            Position finalPosition = Position.getRandomPosition(length, height);
            Entity entity = new Entity(currentPosition, finalPosition);
            grid.addEntity(entity);
        }

        display.displayGrid(grid);

        //Make all the entities move
        List<Entity> entitiesList = grid.getEntitiesList();
        while(entitiesList.size() != 0){
            for(Entity entity: entitiesList){
                if(!entity.isArrived()) {
                    entity.move(Position.getNewPosition(entity.getCurrentPosition(), entity.getArrivalPosition()));
                    Thread.sleep(100);
                    display.updateGrid(grid.getEntitiesList());
                }

                else {
                    display.disappear(entity);
                }

            }
        }
    }
}
