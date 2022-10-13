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
    static int lines = 130;
    static int columns = 130;

    //number of entities on the grid
    static int entitiesNumber = 10000;

    //sleep time in ms
    static int time = 1;

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

            Position arrivalPosition = positionManager.getRandomPosition(lines, columns);
            Entity entity = new Entity(startPosition, arrivalPosition, positionManager, i);
            grid.addEntity(entity);

            System.out.println("departure i: " + startPosition.getI() + "    j: " + startPosition.getJ());
            System.out.println("arrival   i: " + arrivalPosition.getI() + "    j: " + arrivalPosition.getJ());
            System.out.println();
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
        int i=0;
        while(entitiesList.size() != 0){
            System.out.println("-----------tour n°"+i+"-----------");
            for(Entity entity: entitiesList){
                Optional<Entity> potentialVictim = Optional.empty();
                boolean victimRevived = false;
                if(!entity.isArrived()) {
                    if(positionManager.canEntityBeRevive(entity)){
                        grid.revive(entity);
                        victimRevived = true;
                        System.out.println(entity.getEntityColor().toString()+"- Entity revive"+entity.getCurrentPosition());
                    }

                    else if(entity.isKilled()) {
                        entity.incrementKillTime();
                    }
                    else{
                        potentialVictim = entity.move();
                        System.out.println(entity.getEntityColor().toString()+"- Entity move - "+entity.getCurrentPosition());
                    }

                }
                else {
                    System.out.println(entity.getEntityColor().toString()+"- Entity arrived - "+entity.getCurrentPosition());
                    grid.destroy(entity);
                }

                display.updateGrid(entity, potentialVictim, victimRevived);
                sleep(time);
            }
            grid.cleanUp();
            entitiesList = grid.getEntitiesList();
            i++;
            System.out.println("--------------------------------------------------------------------------------number of out entity : " + grid.getEntitiesOut().size());
        }
        display.close();
        System.out.println("END\n\n");



        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Program ran for " + totalTime/(6*Math.pow(10,10)) + " minutes.");
    }
}
