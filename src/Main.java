import java.util.List;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {

    //grid of size length*height
    static int length = 100;
    static int height = 120;

    //number of entities on the grid
    static int entitiesNumber = 50;

    //sleep time in ms
    static int time = 1;

    //Display the grid where the crowd move
    public static void main(String[] args) throws InterruptedException {

        //create an instance to display the grid
        Display display = new Display(length, height);
        //create the grid
        Grid grid = new Grid(length, height, entitiesNumber);
        //create the position manager
        PositionManager positionManager = new PositionManager();


        /*
        for(int i = 0 ; i < 12 ; i+=2){
            Entity e = new Entity(new Position(0,i), new Position(5,4), positionManager, i);
            grid.addEntity(e);
            positionManager.addPosition(e.getCurrentPosition());
        }
        */

/*
        Entity e1 = new Entity(new Position(0,0), new Position(0,6), positionManager, 0);
        grid.addEntity(e1);
        positionManager.addPosition(e1.getCurrentPosition());
        positionManager.addEntity(e1);


        Entity e2 = new Entity(new Position(0,6), new Position(0,0), positionManager, 1);
        grid.addEntity(e2);
        positionManager.addPosition(e2.getCurrentPosition());
        positionManager.addEntity(e2);*/



        //create the entities

        for(int i = 0; i < entitiesNumber; i++){
            Position startPosition = positionManager.getRandomPosition(length, height);
            while(positionManager.isPositionTaken(startPosition))
                startPosition = positionManager.getRandomPosition(length, height);

            Position arrivalPosition = positionManager.getRandomPosition(length, height);
            Entity entity = new Entity(startPosition, arrivalPosition, positionManager, i);
            grid.addEntity(entity);
            positionManager.addPosition(startPosition);
            positionManager.addEntity(entity);


            System.out.println("depart i: " + startPosition.getI() + "    j: " + startPosition.getJ());
            System.out.println("depart i: " + arrivalPosition.getI() + "    j: " + arrivalPosition.getJ());
            System.out.println();


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
        Position.allCurrentPositions.add(currentPosition);
        */

        //create the grid appearance and display it
        display.displayGrid(grid);
        Thread.sleep(time);

        //Make all the entities move
        List<Entity> entitiesList = grid.getEntitiesList();
        int i=0;
        while(!positionManager.allEntitiesExited()){
            System.out.println("-----------tour n°"+i+"-----------");
            for(Entity entity: entitiesList){
                if(!entity.isArrived()) {
                    if(positionManager.canEntityBeRevive(entity)){
                        entity.revive();
                        display.reviveVisually(entity);
                        System.out.println(entity.getEntityColor().toString()+"- Entity revive"+entity.getCurrentPosition());
                    }

                    else if(entity.isKilled()) {
                        entity.incrementKillTime();
                    }
                    else{
                        entity.move();
                        System.out.println(entity.getEntityColor().toString()+"- Entity move - "+entity.getCurrentPosition());
                        display.updateGrid(entity);
                    }

                }
                else {
                    System.out.println(entity.getEntityColor().toString()+"- Entity arrived - "+entity.getCurrentPosition());
                    display.disappear(entity);
                    entity.destroy();
                }

                display.updateGrid(entity);
                Thread.sleep(time);
            }
            i++;
            System.out.println("--------------------------------------------------------------------------------number of out entity : " + positionManager.getEntitiesOut().size());
        }
        display.close();
        System.out.println("END");
    }
}
