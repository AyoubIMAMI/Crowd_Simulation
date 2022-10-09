import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Grid {

    //grid of size length*height
    int length;
    int height;

    //number of entities on the grid
    double entitiesNumber;

    //list of the entities on the grid
    List<Entity> entitiesList;

    //grid where the crowd move
    Optional<Entity>[][] grid;

    public Grid(int length, int height, double entitiesNumber) {
        this.entitiesList = new ArrayList<>();
        this.length = length;
        this.height = height;
        this.entitiesNumber = entitiesNumber;
        grid = (Optional<Entity>[][]) new Optional<?>[length][height];
        createGrid();
    }

    /**
     * Create the length*height size grid where the crowd move
     */
    private void createGrid() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = Optional.empty();
            }
        }
    }

    /**
     * Add an entity on the grid
     * @param entity entity to add to the grid
     */
    public void addEntity(Entity entity){
        this.entitiesList.add(entity);
        Position position = entity.getCurrentPosition();
        grid[position.getI()][position.getJ()] = Optional.of(entity);
    }

    public Optional<Entity>[][] getGrid() {
        return grid;
    }

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }
}
