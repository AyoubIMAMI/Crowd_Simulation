import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class Display {

    //length*height of the grid
    private final int length;
    private final int height;

    private final JFrame frame;
    private final JPanel[][] jPanelList;

    public Display(int length, int height) {
        this.length = length;
        this.height = height;
        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        jPanelList = new JPanel[length][height];
    }

    /**
     * Create the grid and its appearance
     */
    public void updateGraphicGrid(Grid grid){
        //get the matrix
        Optional<Entity>[][] matrix = grid.getGrid();

        //initialize the grid appearance
        for(int i = 0 ; i < length ; i ++){
            for(int j = 0 ; j < height ; j ++){
                JPanel p = new JPanel();
                p.setBorder(BorderFactory.createLineBorder(Color.orange));
                if(matrix[i][j].isPresent())
                    p.setBackground(Color.red);
                jPanelList[i][j] = p;
                frame.getContentPane().add(p); // Adds Button to content pane of frame
            }
        }
        frame.setLayout(new GridLayout(length,height));
    }

    /**
     * Use listEntity and the jPanelList to update the graphic grid
     */
    void updateGrid(List<Entity> listEntity){
        for(Entity e: listEntity){
            if(e.hasMove()){
                Position lastPos = e.getLastPosition().get();
                Position currentPos = e.getCurrentPosition();

                jPanelList[lastPos.getI()][lastPos.getJ()].setBackground(Color.WHITE);
                jPanelList[currentPos.getI()][currentPos.getJ()].setBackground(Color.RED);
            }
        }
    }

    /**
     * Display and update the grid
     */
    void displayGrid(Grid grid) {
        updateGraphicGrid(grid);
        frame.setVisible(true);
    }

    void disappear(Entity entity) {
        jPanelList[entity.getCurrentPosition().getI()][entity.getCurrentPosition().getJ()].setBackground(Color.WHITE);
    }
}
