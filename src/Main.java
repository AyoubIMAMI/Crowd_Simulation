import java.util.List;
import java.util.Optional;
import java.lang.System;

import static java.lang.Thread.sleep;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {
    static long startTime = System.nanoTime();

    //grid of size length*height
    static int lines = 10;
    static int columns = 10;

    //number of entities on the grid
    static int entitiesNumber = 10;

    //sleep time in ms
    static int time = 0;

    //Display the grid where the crowd move
    public static void main(String[] args) throws InterruptedException {

        //create an instance to display the grid
        Display display = new Display(lines, columns);
        //create the grid
        Grid grid = new Grid(lines, columns, entitiesNumber);
        //create the position manager
        PositionManager positionManager = new PositionManager(grid);

        //create the entities
        for(int i = 0; i < entitiesNumber; i++){
            Position startPosition = positionManager.getRandomPosition(lines, columns);
            while(positionManager.isPositionTaken(startPosition))
                startPosition = positionManager.getRandomPosition(lines, columns);

            Position arrivalPosition = positionManager.defineArrivalPosition(lines, columns);
            Entity entity = new Entity(startPosition, arrivalPosition, positionManager, i);
            grid.addEntity(entity);

            System.out.println("arrival position:\ni:"+arrivalPosition.getI()+"\nj:"+arrivalPosition.getJ()+"\n\n");
        }

        /*
        Position startPosition = new Position(0, 0);
        Position arrivalPosition = new Position(0, 2);
        Entity entit = new Entity(startPosition, arrivalPosition, positionManager, 0);
        grid.addEntity(entit);
        startPosition = new Position(0, 2);
        arrivalPosition = new Position(0, 0);
        entit = new Entity(startPosition, arrivalPosition, positionManager, 1);
        grid.addEntity(entit);*/


        //create the grid appearance and display it
        display.displayGrid(grid);
        sleep(time);

        //Make all the entities move
        List<Entity> entitiesList = grid.getEntitiesList();
        while(entitiesList.size() != 0){
            for(Entity entity: entitiesList){
                Optional<Entity> potentialVictim = Optional.empty();
                boolean victimRevived = false;
                if(!entity.isArrived()) {
                    if(positionManager.canEntityBeRevive(entity)){
                        grid.revive(entity);
                        victimRevived = true;
                    }

                    else if(entity.isKilled()) {
                        entity.incrementKillTime();
                    }
                    else{
                        potentialVictim = entity.move();
                    }

                }
                else {
                    grid.destroy(entity);
                }

                display.updateGrid(entity, potentialVictim, victimRevived);
                sleep(time);
            }
            grid.cleanUp();
            entitiesList = grid.getEntitiesList();
        }
        display.close();

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Program ran for " + totalTime/(Math.pow(10,9)) + " seconds with a time sleep of " + time + " milliseconds.");
        System.out.println("Program ran for " + totalTime/(6*Math.pow(10,10)) + " minutes with a time sleep of " + time + " milliseconds.");
    }
}
