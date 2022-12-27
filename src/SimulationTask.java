import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * Initialize and run the simulation
 */
public class SimulationTask {
    //grid on which entities move
    public static Grid grid;
    //number of entities in the crowd
    int entitiesNumber;
    //entities list
    static List<EntityTask> entitiesList;
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

    List<Runnable> part1 = new ArrayList<Runnable>();
    List<Runnable> part2 = new ArrayList<Runnable>();
    List<Runnable> part3 = new ArrayList<Runnable>();
    List<Runnable> part4 = new ArrayList<Runnable>();
    ExecutorService botRightExecutor = Executors.newSingleThreadExecutor();
    ExecutorService botLeftExecutor = Executors.newSingleThreadExecutor();
    ExecutorService topRightExecutor = Executors.newSingleThreadExecutor();
    ExecutorService topLeftExecutor = Executors.newSingleThreadExecutor();



    public SimulationTask(Grid grid, int entitiesNumber, PositionManager positionManager, Display display, int sleepTime, CsvManager csvManager, boolean csvMode) {
        SimulationTask.sleepTime = sleepTime;
        SimulationTask.grid = grid;
        this.entitiesNumber = entitiesNumber;
        this.display = display;
        this.positionManager = positionManager;
        this.csvManager = csvManager;
        this.csvMode = csvMode;
        entitiesList = new ArrayList<>();
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
                EntityTask entity = new EntityTask(startPosition, arrivalPosition, id);
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
        int tour =0;
        while(!entitiesList.isEmpty()){
            System.out.println("tour n°"+tour++);
            for(EntityTask r : entitiesList){
                GridQuarterPosition quarterPosition = getQuarterPosition(r.getCurrentPosition());
                switch (quarterPosition){
                    case BOT_RIGHT -> results.add(botRightExecutor.submit((Callable<EntityTurnResult>) r));
                    case BOT_LEFT -> results.add(botLeftExecutor.submit((Callable<EntityTurnResult>) r));
                    case TOP_RIGHT -> results.add(topRightExecutor.submit((Callable<EntityTurnResult>) r));
                    case TOP_LEFT -> results.add(topLeftExecutor.submit((Callable<EntityTurnResult>) r));
                }
            }
            for(Future<EntityTurnResult> futurResult : results){
                //System.out.println("wainting a result...");
                EntityTurnResult result = futurResult.get();
                //System.out.println("The result: "+result);
                if(result.isDestroyed())
                    removeEntityWithId(result.getId());
            }
            System.out.println("fin du tour");
        }

        if (Main.displayMode)
            display.close();
    }

    private void removeEntityWithId(int id) {
    for (EntityTask entity : entitiesList)
        if (entity.getId() == id){
            entitiesList.remove(entity);
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

    private GridQuarterPosition getQuarterPosition(Position currentPosition) {
        int i = currentPosition.getI();
        int j = currentPosition.getJ();
        if(i > grid.lines)
            if(j > grid.columns) return GridQuarterPosition.BOT_RIGHT;
            else return GridQuarterPosition.BOT_LEFT;
        else
            if(j > grid.columns) return GridQuarterPosition.TOP_RIGHT;
            else return GridQuarterPosition.TOP_LEFT;
    }
}
