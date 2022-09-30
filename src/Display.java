import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Display {

    //length*height of the grid
    private static final int length = 3;
    private static final int height = 3;

    //number of people on the grid following these values : 2^exponent
    private static final int exponent = 0;

    public JFrame createGraphicGrid(){
        MatrixEntity matrixEntity = new MatrixEntity(length, height, Math.pow(2, exponent));
        matrixEntity.addEntity(new Entity(new Position(0,0), new Position(2,2)));

        Optional<Entity>[][] matrix = matrixEntity.getMatrix();

        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

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
