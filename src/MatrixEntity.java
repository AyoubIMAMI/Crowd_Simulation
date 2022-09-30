import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MatrixEntity {
    int length; //length of the grid
    int height; //height of the grid
    double peopleNumber; //number of people in the crowd
    Optional<Entity>[][] matrix;
    @SuppressWarnings("unchecked")
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



    public Optional<Entity>[][] getMatrix() {
        return matrix;
    }

    public void addEntity(Entity e){
        Position pos = e.getCurrentPos();
        matrix[pos.getX()][pos.getX()] = Optional.of(e);
    }

    public void moveEntity(Entity e){
        for(int i = 0 ; i < this.length ; i ++){
            for(int j = 0 ; j < this.height ; j ++){
                if(matrix[i][j].isPresent()){
                    Entity entity = matrix[i][j].get();
                }
            }
        }
    }

}
