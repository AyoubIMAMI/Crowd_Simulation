import java.util.ArrayList;

/**
 * Grid on which entities will move
 */
public class Grid {

    //grid of size lines*columns
    int lines;
    int columns;

    //grid where the entities move
    Box[][] grid;

    public Grid(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        grid = new Box[lines][columns];
        createGrid();
    }

    public Grid(int lines, int columns, ArrayList<Entity> entities) throws Exception {
        this.lines = lines;
        this.columns = columns;
        grid = new Box[lines][columns];
        createGrid();
        fillGrid(entities);
    }

    private void fillGrid(ArrayList<Entity> entities) throws Exception {
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
     * Add an entity to a box of the grid
     * @param entity entity to add
     */
    public void addEntity(Entity entity) throws InterruptedException {
        Position position = entity.getCurrentPosition();
        getBox(position.getI(), position.getJ()).setEntity(entity);
    }

    public Box[][] getGrid() {
        return grid;
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public Box getBox(int i, int j) {
        return grid[i][j];
    }
}
