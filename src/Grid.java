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

    //number of entities on the grid
    double entitiesNumber;

    //list of the entities on the grid
    List<Entity> entitiesList;

    //entities which reached their exit
    List<Entity> entitiesOut;

    //list of the entities current position
    List<Position> currentPositions;

    //grid where the entities move
    Optional<Entity>[][] grid;

    public Grid(int lines, int columns, double entitiesNumber) {
        this.entitiesList = new ArrayList<>();
        this.entitiesOut = new ArrayList<>();
        this.currentPositions = new ArrayList<>();
        this.lines = lines;
        this.columns = columns;
        this.entitiesNumber = entitiesNumber;
        grid = (Optional<Entity>[][]) new Optional<?>[lines][columns];
        createGrid();
    }

    /**
     * Create the lines*columns size grid where the entities move
     */
    private void createGrid() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = Optional.empty();
            }
        }
    }

    /**
     * Add an entity on the grid
     * @param entity entity to add to the grid
     */
    public void addEntity(Entity entity){
        Position position = entity.getCurrentPosition();
        this.entitiesList.add(entity);
        this.currentPositions.add(position);
        grid[position.getI()][position.getJ()] = Optional.of(entity);
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
     * Kill an entity - set its kill attribute to true
     * @param entity to kill
     */
    public void kill(Entity entity) {
        entity.setKill(true);
        this.currentPositions.remove(entity.getCurrentPosition());
    }

    /**
     * Revive entity - set its kill attribute to false and reset its kill time
     * @param entity to revive
     */
    public void revive(Entity entity) {
        entity.setKill(false);
        entity.resetKillTime();
        this.currentPositions.add(entity.getCurrentPosition());
    }

    /**
     * When an entity arrived to its arrival position, it is destroyed
     * @param entity to destroy
     */
    public void destroy(Entity entity) {
        removeCurrentPosition(entity.getCurrentPosition());
        entity.setDestroyed(true);
        entitiesOut.add(entity);
    }

    /**
     * Remove entities which got out from the entities list
     */
    public void cleanUp() {
        for(Entity entity : entitiesOut)
            entitiesList.remove(entity);

        entitiesOut.clear();
    }

    public Optional<Entity>[][] getGrid() {
        return grid;
    }

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    public List<Position> getCurrentPositions() {
        return currentPositions;
    }
}
