import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Matrix class : matrix where the crowd will move
 */

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

    public JFrame createGraphicGrid(){
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        for(int i = 0 ; i < this.length ; i ++){
            for(int j = 0 ; j < this.height ; j ++){
                JPanel p = new JPanel();
                p.setBorder(BorderFactory.createLineBorder(Color.orange));
                if(matrix[i][j].isPresent())
                    p.setBackground(Color.red);
                frame.getContentPane().add(p); // Adds Button to content pane of frame
            }
        }
        frame.setLayout(new GridLayout(3,3));
        return frame;
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
