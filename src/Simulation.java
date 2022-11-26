import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Initialize and run the simulation
 */
public class Simulation {
    //grid on which entities move
    public static Grid grid;
    //number of entities in the crowd
    int entitiesNumber;
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

    public Simulation(Grid grid, int entitiesNumber, PositionManager positionManager, Display display, int sleepTime, CsvManager csvManager, boolean csvMode) {
        Simulation.sleepTime = sleepTime;
        Simulation.grid = grid;
        this.entitiesNumber = entitiesNumber;
        this.display = display;
        this.positionManager = positionManager;
        this.csvManager = csvManager;
        this.csvMode = csvMode;
    }

    /**
     * Initialize the simulation: grid appearance and entities creation
     */
    public void initialize() throws IOException, InterruptedException {
        if(csvMode){
            grid = csvManager.getConfigurationGrid(positionManager);
            positionManager.setGrid(grid);
            display.setGrid(grid);
        }
        else{
            for(int i = 0; i < entitiesNumber; i++){
                Position startPosition = positionManager.getRandomPosition();
                while(positionManager.isPositionTaken(startPosition))
                    startPosition = positionManager.getRandomPosition();

                Position arrivalPosition = positionManager.defineArrivalPosition();
                Entity entity = new Entity(startPosition, arrivalPosition, i);
                grid.addEntity(entity);
            }
        }
    }



    /**
     * Run the simulation - round by round
     * @throws InterruptedException sleepTime
     */
    public void launch() throws InterruptedException {
        display.displayGrid(grid);
        //let the display appears
        sleep(sleepTime);

        List<Thread> allThreads = new ArrayList<>();
        for (Entity entity : grid.getEntitiesList()) {
            Thread t = new Thread(entity);
            allThreads.add(t);
            t.start();
        }

        //entitiesList = grid.getEntitiesList();

        for (Thread t : allThreads) {
            t.join();
        }
        display.close();
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
