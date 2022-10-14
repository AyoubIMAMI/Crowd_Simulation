import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //grid where the crowd move
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
     * Create the length*height size grid where the crowd move
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

    public void addCurrentPosition(Position position) {
        this.currentPositions.add(position);
    }

    public void removeCurrentPosition(Position position) {
        this.currentPositions.remove(position);
    }

    public void kill(Entity entity) {
        entity.setKill(true);
        this.currentPositions.remove(entity.getCurrentPosition());
        //System.out.println(entity.getEntityColor().toString()+"- Entity killed - "+entity.getCurrentPosition());
    }

    public void revive(Entity entity) {
        entity.setKill(false);
        entity.resetKillTime();
        this.currentPositions.add(entity.getCurrentPosition());
    }

    /**
     * When an entity arrived to its arrival position, it is destroyed
     */
    public void destroy(Entity entity) {
        removeCurrentPosition(entity.getCurrentPosition());
        entity.setDestroyed(true);
        entitiesOut.add(entity);
    }

    public void cleanUp() {
        for(Entity entity : entitiesOut)
            entitiesList.remove(entity);

        entitiesOut.clear();
    }

    public void entityExit(Entity entity) {
        this.entitiesOut.add(entity);
    }

    public Optional<Entity>[][] getGrid() {
        return grid;
    }

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    public List<Entity> getEntitiesOut() {
        return entitiesOut;
    }

    public List<Position> getCurrentPositions() {return currentPositions;}

    public boolean allEntitiesExited(int entitiesNumber) {
        return this.entitiesOut.size() == entitiesNumber;
    }
}
