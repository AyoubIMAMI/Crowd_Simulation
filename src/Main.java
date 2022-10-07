import java.util.List;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {

    static int length = 10;
    static int height = 10;
    static int peopleNumber = 8;

    //Display the grid where the crowd move
    public static void main(String[] args) throws InterruptedException {

        Display display = new Display(length, height);
        Grid grid = new Grid(length, height, peopleNumber);


        for(int i = 0 ; i < peopleNumber ; i++){
            Position currentPosition = Position.getRandomPosition(length, height);
            while(isPositionTaken(grid, currentPosition))
                currentPosition = Position.getRandomPosition(length, height);

            Position finalPosition = Position.getRandomPosition(length, height);
            Entity entity = new Entity(currentPosition, finalPosition);
            grid.addEntity(entity);

            System.out.println("current position i: " + currentPosition.getI() + "    j: " + currentPosition.getJ());
            System.out.println("arrival position i: " + finalPosition.getI() + "    j: " + finalPosition.getJ());
            System.out.println();
        }

        display.displayGrid(grid);

        //Make moves all the entities
        List<Entity> listEnt = grid.getEntitiesList();
        while(true){
            for(Entity entity: listEnt){
                if(!entity.isArrived()) {
                    entity.move(Position.getRandomLogicalPosition(entity.getCurrentPosition(), entity.getArrivalPosition()));
                    Thread.sleep(100);
                    display.updateGrid(grid.getEntitiesList());
                }

                else
                    display.disappear(entity);
            }
        }
    }

    static boolean isPositionTaken(Grid grid, Position currentPosition) {
        for (Entity entity : grid.entitiesList) {
            if(entity.getCurrentPosition().equals(currentPosition))
                return true;
        }
        return false;
    }
}
