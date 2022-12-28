import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * Initialize and run the simulation
 */
public class Simulation{
    //grid on which entities move
    public static Grid grid;
    //number of entities in the crowd
    int entitiesNumber;
    //entities list
    static List<Entity> entitiesList;
    //positionManager which decides of the entity next move: move, die, revive or exit
    public PositionManager positionManager;
    //display the grid
    public Display display;
    //manage csv file
    public CsvManager csvManager;
    //sleep time in ms - needed to simulate movements on the display
    static long sleepTime;
    //true: read the csv file to set up the grid - false: set up the grid with the class Main attributes
    boolean csvMode;

    /*ExecutorService botRightExecutor = Executors.newSingleThreadExecutor();
    ExecutorService botLeftExecutor = Executors.newSingleThreadExecutor();
    ExecutorService topRightExecutor = Executors.newSingleThreadExecutor();
    ExecutorService topLeftExecutor = Executors.newSingleThreadExecutor();*/

    TaskExecutorManager taskExecutorManager;


    public Simulation(Grid grid, int entitiesNumber, PositionManager positionManager, Display display, int sleepTime, CsvManager csvManager, boolean csvMode) {
        Simulation.sleepTime = sleepTime;
        Simulation.grid = grid;
        this.entitiesNumber = entitiesNumber;
        this.display = display;
        this.positionManager = positionManager;
        this.csvManager = csvManager;
        this.csvMode = csvMode;
        entitiesList = new ArrayList<>();
        taskExecutorManager =  new TaskExecutorManager(grid);
    }

    /**
     * Initialize the simulation: grid appearance and entities creation
     */
    public void initialize() throws Exception {
        if(csvMode){
            grid = csvManager.getConfigurationGrid();
            positionManager.setGrid(grid);

            if (Main.displayMode)
                display.setGrid(grid);
        }
        else{

            for(int id = 0; id < entitiesNumber; id++){
                Position startPosition = positionManager.getRandomPosition();
                while(positionManager.isPositionTaken(startPosition))
                    startPosition = positionManager.getRandomPosition();

                Position arrivalPosition = positionManager.defineArrivalPosition();
                Entity entity = new Entity(startPosition, arrivalPosition, id);
                grid.addEntity(entity);
                entitiesList.add(entity);

            }
        }
    }

    /**
     * Run the simulation - round by round
     * @throws InterruptedException sleepTime
     */
    public void launch() throws InterruptedException, ExecutionException {
        List<Future<EntityTurnResult>> results = new ArrayList<>();
        if (Main.displayMode)
            display.displayGrid(grid);
        //let the display appears
        sleep(sleepTime);

        Main.startTime = System.nanoTime();
        taskExecutorManager.runAll(entitiesList);
        taskExecutorManager.joinAll();
        taskExecutorManager.shutdownAll();
        if (Main.displayMode)
            display.close();
    }

    static synchronized public void removeEntityWithId(List<Entity> entities, int id) {
        for (Entity entity : entities)
            if (entity.getId() == id){
                entities.remove(entity);
                break;
            }
    }

    /**
     * Compute the execution time
     * @param startTime time for which the program started
     */
    public void time(long startTime) {
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Program ran for " + totalTime / (Math.pow(10, 6)) + " milliseconds with a time sleep of " + sleepTime + " milliseconds.");
        System.out.println("Program ran for " + totalTime / (Math.pow(10, 9)) + " seconds with a time sleep of " + sleepTime + " milliseconds.");
        System.out.println("Program ran for " + totalTime / (6 * Math.pow(10, 10)) + " minutes with a time sleep of " + sleepTime + " milliseconds.");
    }


}
