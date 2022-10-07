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
     * Create the grid appearance
     */
    public void createGridAppearance(Grid grid){
        //get the grid
        Optional<Entity>[][] matrix = grid.getGrid();

        //initialize the grid appearance
        for(int i = 0 ; i < length ; i ++){
            for(int j = 0 ; j < height ; j ++){
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.orange));
                panel.setBackground(Color.white);

                if(matrix[i][j].isPresent())
                    panel.setBackground(Color.red);

                jPanelList[i][j] = panel;
                frame.getContentPane().add(panel); // Adds Button to content pane of frame
            }
        }

        frame.setLayout(new GridLayout(length,height));
    }

    /**
     * Use entitiesList and the jPanelList to update the graphic grid
     */
    void updateGrid(List<Entity> entitiesList){
        for(Entity entity: entitiesList){
            if(entity.hasMoved()){
                Position lastPosition = entity.getLastPosition().get();
                Position currentPosition = entity.getCurrentPosition();

                jPanelList[lastPosition.getI()][lastPosition.getJ()].setBackground(Color.WHITE);
                jPanelList[currentPosition.getI()][currentPosition.getJ()].setBackground(Color.RED);
            }
        }
    }

    /**
     * Display and update the grid
     */
    void displayGrid(Grid grid) {
        createGridAppearance(grid);
        frame.setVisible(true);
    }

    /**
     * Once the entity reach his final position, it disappeared from the grid
     * @param entity to make disappeared
     */
    void disappear(Entity entity) {
        jPanelList[entity.getCurrentPosition().getI()][entity.getCurrentPosition().getJ()].setBackground(Color.WHITE);
    }
}
