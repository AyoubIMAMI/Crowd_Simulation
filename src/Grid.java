import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Grid on which entities will move
 */
public class Grid {

    //grid of size lines*columns
    int lines;
    int columns;

    //list of the entities on the grid
    List<Entity> entitiesList;

    //entities which reached their exit
    List<Entity> entitiesOut;

    //list of the entities current position
    List<Position> currentPositions;

    //grid where the entities move
    Box[][] grid;

    public Grid(int lines, int columns) {
        this.entitiesList = new ArrayList<>();
        this.entitiesOut = new ArrayList<>();
        this.currentPositions = new ArrayList<>();
        this.lines = lines;
        this.columns = columns;
        grid = new Box[lines][columns];
        createGrid();
    }

    public Grid(int lines, int columns, ArrayList<Entity> entities) throws InterruptedException {
        this.entitiesList = new ArrayList<>();
        this.entitiesOut = new ArrayList<>();
        this.currentPositions = new ArrayList<>();
        this.lines = lines;
        this.columns = columns;
        grid = new Box[lines][columns];
        createGrid();
        fillGrid(entities);
    }

    private void fillGrid(ArrayList<Entity> entities) throws InterruptedException {
        for(Entity entity : entities)
            addEntity(entity);
    }

    /**
     * Create the lines*columns size grid where the entities move
     */
    private void createGrid() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new Box(new Position(i, j));
            }
        }
    }

    /**
     * Add an entity on the grid
     * @param entity entity to add to the grid
     */
    public void addEntity(Entity entity) throws InterruptedException {
        Position position = entity.getCurrentPosition();
        this.entitiesList.add(entity);
        this.currentPositions.add(position);
        getBox(position.getI(), position.getJ()).arrive(entity);
    }

    /**
     * Add the position to the list of current position
     * @param position to add
     */
    public void addCurrentPosition(Position position) {
        this.currentPositions.add(position);
    }

    /**
     * Remove the position from the list of current position
     * @param position to remove
     */
    public void removeCurrentPosition(Position position) {
        this.currentPositions.remove(position);
    }

    /**
     * Remove entities which got out from the entities list
     */
    public synchronized void cleanUp(Entity entity) {
        entitiesList.remove(entity);
        entitiesOut.clear();
    }

    public Box[][] getGrid() {
        return grid;
    }

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    public List<Position> getCurrentPositions() {
        return currentPositions;
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    boolean isPositionTaken(Position position) {
        return this.currentPositions.contains(position);
    }

    public Box getBox(int i, int j) {
        return grid[i][j];
    }
}
