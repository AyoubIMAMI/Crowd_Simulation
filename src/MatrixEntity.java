import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatrixEntity {

    List<Entity> listEntity;

    //length and height of the grid
    int length;
    int height;

    //number of people in the crowd
    double peopleNumber;

    //matrix which represent the grid
    Optional<Entity>[][] matrix;

    public MatrixEntity(int length, int height, double peopleNumber) {
        this.listEntity = new ArrayList<>();
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


    public Optional<Entity>[][] getMatrix() {
        return matrix;
    }

    /**
     * Add an entity on the grid
     * @param entity entity to add to the grid
     */
    public void addEntity(Entity entity){
        this.listEntity.add(entity);
        Position position = entity.getCurrentPosition();
        matrix[position.getI()][position.getJ()] = Optional.of(entity);
    }

    public List<Entity> getListEntity() {
        return listEntity;
    }
}
