import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Display {

    //length*height of the grid
    private static final int length = 3;
    private static final int height = 3;

    //number of people on the grid following these values : 2^exponent
    private static final int exponent = 0;

    /**
     * Create the grid and its appearance
     *
     * @return JFrame
     */
    public JFrame createGraphicGrid(){

        //create the matrix and put entities on it
        MatrixEntity matrixEntity = new MatrixEntity(length, height, Math.pow(2, exponent));
        matrixEntity.addEntity(new Entity(new Position(0,0), new Position(2,2)));

        //get the matrix
        Optional<Entity>[][] matrix = matrixEntity.getMatrix();

        //create the window
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        //initialize the grid appearance
        for(int i = 0 ; i < length ; i ++){
            for(int j = 0 ; j < height ; j ++){
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

    /**
     * Display and update the grid
     */
    void displayGrid() {
        JFrame frame = createGraphicGrid();
        frame.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //matrixEntity.move();
        frame = createGraphicGrid();
        frame.repaint();
    }
}
