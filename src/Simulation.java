import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static java.lang.Thread.sleep;

/**
 * Initialize and run the simulation
 */
public class Simulation {
    public Grid grid;
    public PositionManager positionManager;
    public Display display;
    public CsvManager csvManager;

    long sleepTime;

    boolean csvMode = false;

    public Simulation(Grid grid, PositionManager positionManager, Display display, int sleepTime, CsvManager csvManager, boolean csvMode) {
        this.sleepTime = sleepTime;
        this.grid = grid;
        this.display = display;
        this.positionManager = positionManager;
        this.csvManager = csvManager;
        this.csvMode = csvMode;
    }

    /**
     * Initialize the simulation: grid appearance and entities creation
     */
    public void initialize() throws IOException {
        if(csvMode){
            grid = csvManager.getConfigurationGrid(positionManager);
        }
        else{
            for(int i = 0; i < Main.entitiesNumber; i++){
                Position startPosition = positionManager.getRandomPosition();
                while(positionManager.isPositionTaken(startPosition))
                    startPosition = positionManager.getRandomPosition();

                Position arrivalPosition = positionManager.defineArrivalPosition();
                Entity entity = new Entity(startPosition, arrivalPosition, positionManager, i);
                grid.addEntity(entity);
            }
        }
        display.displayGrid(grid);
    }

    /**
     * Run the simulation - round by round
     * @throws InterruptedException sleepTime
     */
    public void run() throws InterruptedException {
        //let the display appears
        sleep(sleepTime);

        List<Entity> entitiesList = grid.getEntitiesList();
        while (entitiesList.size() != 0) {
            for (Entity entity : entitiesList) {
                playRound(entity);
            }
            grid.cleanUp();
            entitiesList = grid.getEntitiesList();
        }
        display.close();
    }

    /**
     * Make a move on an entity: move, revive or destroy. And update the display
     * @param entity entity to move on this round
     * @throws InterruptedException sleepTime
     */
    public void playRound(Entity entity) throws InterruptedException {
        Optional<Entity> potentialVictim = Optional.empty();
        boolean victimRevived = false;
        if (!entity.isArrived()) {
            if (positionManager.canEntityBeRevive(entity)) {
                grid.revive(entity);
                victimRevived = true;
            } else if (entity.isKilled())
                entity.incrementKillTime();
            else
                potentialVictim = entity.move();
        } else
            grid.destroy(entity);

        display.updateGrid(entity, potentialVictim, victimRevived);
        sleep(sleepTime);
    }

    /**
     * Compute the execution time
     * @param startTime time for which the program started
     */
    public void time(long startTime) {
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Program ran for " + totalTime / (Math.pow(10, 9)) + " seconds with a time sleep of " + sleepTime + " milliseconds.");
        System.out.println("Program ran for " + totalTime / (6 * Math.pow(10, 10)) + " minutes with a time sleep of " + sleepTime + " milliseconds.");
    }
}
