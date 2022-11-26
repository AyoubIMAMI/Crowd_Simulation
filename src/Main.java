import java.io.IOException;
import java.lang.System;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {
    //time at which the program start running - needed to compute how much time the program ran
    static long startTime = System.nanoTime();

    //grid of size lines*columns
    static int lines = 1;
    static int columns = 10;

    //number of entities on the grid - this number must be smaller than the grid of size lines*columns
    //entities cannot spawn on each other
    static int entitiesNumber = 2;

    //sleep time in ms - needed to simulate movements on the display
    static int sleepTime = 750;

    //true: creates as many threads as entities
    //false: one thread deals with all the entities
    static boolean threadsMode = false;

    //true: read the csv file to set up the grid - false: set up the grid with the class Main attributes
    static boolean csvMode = false;

    public static void main(String[] args) throws InterruptedException, IOException {
        //create the grid on which entities move
        Grid grid = new Grid(lines, columns);
        //create the position manager which decides of the entity next move: move, die, revive or exit
        PositionManager positionManager = new PositionManager(grid);
        //create an instance to display the grid
        Display display = new Display(grid);
        //create the csvManager
        CsvManager csvManager = new CsvManager("input.csv");

        //initialize and run the simulation - compute execution time
        Simulation simulation = new Simulation(grid, entitiesNumber, positionManager, display, sleepTime, csvManager, csvMode, threadsMode);
        simulation.initialize();
        simulation.launch();
        simulation.time(startTime);
    }
}
