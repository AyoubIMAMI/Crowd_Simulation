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
    static int lines = 10;
    static int columns = 10;

    //number of entities on the grid - this number must be smaller than the grid of size lines*columns
    //entities cannot spawn on each other
    static int entitiesNumber = 10;

    //sleep time in ms - needed to simulate movements on the display
    static int time = 200;

    public static void main(String[] args) throws InterruptedException, IOException {
        //create the grid
        Grid grid = new Grid(lines, columns, entitiesNumber);
        //create the position manager
        PositionManager positionManager = new PositionManager(grid);
        //create an instance to display the grid
        Display display = new Display(lines, columns);
        //create the csvManager
        CsvManager csvManager = new CsvManager("input.csv");

        //initialize and run the simulation - compute execution time
        Simulation simulation = new Simulation(grid, positionManager, display, time, csvManager, true);
        simulation.initialize();
        simulation.run();
        simulation.time(startTime);
    }
}
