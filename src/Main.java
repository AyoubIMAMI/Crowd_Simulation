import java.util.List;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {

    //grid of size length*height
    static int lines = 1;
    static int columns = 10;

    //number of entities on the grid
    static int entitiesNumber = 2;

    //sleep time in ms
    static int time = 1000;

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

        //create the grid appearance and display it
        display.displayGrid(grid);
        Thread.sleep(time);

        //Make all the entities move
        List<Entity> entitiesList = grid.getEntitiesList();
        int i=0;
        while(!grid.allEntitiesExited(entitiesNumber)){
            System.out.println("-----------tour n°"+i+"-----------");
            for(Entity entity: entitiesList){
                if(!entity.isArrived()) {
                    if(positionManager.canEntityBeRevive(entity)){
                        grid.revive(entity);
                        System.out.println(entity.getEntityColor().toString()+"- Entity revive"+entity.getCurrentPosition());
                    }

                    else if(entity.isKilled()) {
                        entity.incrementKillTime();
                    }
                    else{
                        entity.move();
                        System.out.println(entity.getEntityColor().toString()+"- Entity move - "+entity.getCurrentPosition());
                    }

                }
                else if(!entity.isDestroyed()) {
                    System.out.println(entity.getEntityColor().toString()+"- Entity arrived - "+entity.getCurrentPosition());
                    grid.destroy(entity);
                }

                display.updateGrid(grid);
                Thread.sleep(time);
            }
            i++;
            System.out.println("--------------------------------------------------------------------------------number of out entity : " + grid.getEntitiesOut().size());
        }
        display.close();
        System.out.println("END");
    }
}
