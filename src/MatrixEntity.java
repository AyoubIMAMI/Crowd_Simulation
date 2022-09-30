import java.util.ArrayList;
import java.util.Optional;

public class MatrixEntity {

    //length and height of the grid
    int length;
    int height;

    //number of people in the crowd
    double peopleNumber;

    //matrix which represent the grid
    Optional<Entity>[][] matrix;

    public MatrixEntity(int length, int height, double peopleNumber) {
        this.length = length;
        this.height = height;
        this.peopleNumber = peopleNumber;
        matrix = (Optional<Entity>[][]) new Optional<?>[length][height];
        createMatrix();
    }

    /**
     * Create the grid where the crowd move
     */
    private void createMatrix() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = Optional.empty();
            }
        }
    }

    /**
     * Update the positions of the entities
     *
     * @param listEntity list of entities
     */
    public void updateGrid(ArrayList<Entity> listEntity){
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                boolean emptyPosition = true;
                for (Entity entity : listEntity) {
                    if (entity.getCurrentPosition().equals(new Position(i, j))) {
                        matrix[i][j] = Optional.of(entity);
                        emptyPosition = false;
                        break;
                    }
                }
                if(emptyPosition) matrix[i][j] = Optional.empty();

            }
        }
    }


    public Optional<Entity>[][] getMatrix() {
        return matrix;
    }

    /**
     * Add an entity on the grid
     * @param entity entity to add to the grid
     */
    public void addEntity(Entity entity){
        Position position = entity.getCurrentPosition();
        matrix[position.getX()][position.getX()] = Optional.of(entity);
    }
}
