import java.util.ArrayList;
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
    static int height = 12;

    //number of entities on the grid
    static int entitiesNumber = 10;

    //Display the grid where the crowd move
    public static void main(String[] args) throws InterruptedException {

        //create an instance to display the grid
        Display display = new Display(length, height);
        //create the grid
        Grid grid = new Grid(length, height, entitiesNumber);
        //create the position manager
        PositionManager positionManager = new PositionManager();

        //create the entities
        for(int i = 0; i < entitiesNumber; i++){
            Position currentPosition = positionManager.getRandomPosition(length, height);
            while(positionManager.isPositionTaken(grid, currentPosition))
                currentPosition = positionManager.getRandomPosition(length, height);

            Position arrivalPosition = positionManager.getRandomPosition(length, height);
            Entity entity = new Entity(currentPosition, arrivalPosition, positionManager);
            grid.addEntity(entity);
            positionManager.addPosition(currentPosition);

            /*
            System.out.println("depart i: " + currentPosition.getI() + "    j: " + currentPosition.getJ());
            System.out.println("depart i: " + arrivalPosition.getI() + "    j: " + arrivalPosition.getJ());
            System.out.println();
            */
        }

        /*
        Position currentPosition = new Position(4, 0);
        Position finalPosition = new Position(4, 9);
        Entity entit = new Entity(currentPosition, finalPosition);
        grid.addEntity(entit);
        Position.allCurrentPositions.add(currentPosition);

        currentPosition = new Position(5, 1);
        finalPosition = new Position(0, 1);
        entit = new Entity(currentPosition, finalPosition);
        grid.addEntity(entit);
        Position.allCurrentPositions.add(currentPosition);*/

        //create the grid appearance and display it
        display.displayGrid(grid);

        //Make all the entities move
        List<Entity> entitiesList = grid.getEntitiesList();
        while(entitiesList.size() != 0){
            for(Entity entity: entitiesList){
                if(!entity.isArrived()) {
                    entity.move();
                    Thread.sleep(500);
                    display.updateGrid(entity);
                }

                else {
                    display.disappear(entity);
                }

            }
        }
    }
}

//TODO Proposer une autre position que la meilleure possible pour éviter le blocage
