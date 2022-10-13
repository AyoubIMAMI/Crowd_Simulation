import org.junit.jupiter.api.Test;

import java.util.List;

public class entityCollision {
    static int lines = 1;
    static int columns = 10;

    static int entitiesNumber = 2;

    static int time = 1000;


    //create an instance to display the grid
    Display display = new Display(lines, columns);
    //create the grid
    Grid grid = new Grid(lines, columns, entitiesNumber);
    //create the position manager
    PositionManager positionManager = new PositionManager(grid);


    @Test
    public void twoEntitiesWillWalkAtTheSamePlace() throws InterruptedException {
        Position startPosition1 = new Position(0,0);
        Position arrivalPosition1 = new Position(0,6);
        Entity entity1 = new Entity(startPosition1, arrivalPosition1, positionManager, 0);
        grid.addEntity(entity1);

        Position startPosition2 = new Position(0,6);
        Position arrivalPosition2 = new Position(0,0);
        Entity entity2 = new Entity(startPosition2, arrivalPosition2, positionManager, 0);
        grid.addEntity(entity2);

        display.displayGrid(grid);

        List<Entity> entitiesList = grid.getEntitiesList();
        int i=0;
        while(entitiesList.size() != 0){
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
                else {
                    System.out.println(entity.getEntityColor().toString()+"- Entity arrived - "+entity.getCurrentPosition());
                    grid.destroy(entity);
                }

                display.updateGrid(entity);
                Thread.sleep(time);
            }
            grid.cleanUp();
            entitiesList = grid.getEntitiesList();
            i++;
            System.out.println("--------------------------------------------------------------------------------number of out entity : " + grid.getEntitiesOut().size());
        }
        display.close();
        System.out.println("END");
    }
}
