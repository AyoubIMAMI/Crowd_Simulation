
/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {

    //time at which the program start running - needed to compute how much time the program ran
    static long startTime;

    //grid of size lines*columns - since the grid is split in 4 parts the minimum size is 2*2
    static int lines = 12;
    static int columns = 10;

    //number of entities on the grid - this number must be smaller than the grid of size lines*columns
    //entities cannot spawn on each other
    static int entitiesNumber = 20;

    //sleep time in ms - needed to simulate movements on the display
    static int sleepTime = 250;

    //kill time in ms - to simulate the time for which an entity (thread) will be dead
    //useful in the case: a thread per entity
    //Be aware that if you set it at 0, there can be a situation where:
    //when an entity A die, it will instantly respawn,
    //so if another entity B with a greater id wants its place it will wait
    //while the other entity A will keep dying and respawning
    //until the entity B wake up faster than the entity A respawn and takes the place
    static int killTime = 1000;

    //true: display the grid and the entities
    static boolean displayMode = true;

    //true: read the csv file to set up the grid - false: set up the grid with the class Main attributes
    //there is no security on what is written on the file,
    // therefore the file must respect the grid size and the entities declaration
    static boolean csvMode = false;

    public static void main(String[] args) throws Exception {
        //create the grid on which entities move
        Grid grid = new Grid(lines, columns);
        //create the position manager which decides of the entity next move: move, die, revive or exit
        PositionManager positionManager = new PositionManager(grid);
        //create an instance to display the grid
        Display display = new Display(grid);
        //create the csvManager
        CsvManager csvManager = new CsvManager("input.csv");

        //initialize and run the simulation - compute execution time
        Simulation simulation = new Simulation(grid, entitiesNumber, positionManager, display, sleepTime, csvManager, csvMode);

        simulation.initialize();
        simulation.launch();
        simulation.time(startTime);
    }
}
