import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Display {

    //length*height of the grid
    private final int length;
    private final int height;

    private final JFrame frame;
    private final JPanel[][] jPanelList;

    private final List<Color> colorList;
    private final int colorsNumber;

    public Display(int length, int height) {
        this.length = length;
        this.height = height;
        this.jPanelList = new JPanel[length][height];

        this.colorList = new ArrayList<>();
        this.colorsNumber = colorListCreation();

        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
    }

    /**
     * Create the grid appearance
     */
    public void createGridAppearance(Grid grid){
        //get the grid
        Optional<Entity>[][] matrix = grid.getGrid();

        int colorIndex = 0;

        //initialize the grid appearance
        for(int i = 0 ; i < length ; i ++){
            for(int j = 0 ; j < height ; j ++){
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

                if(matrix[i][j].isPresent()) {
                    Color colorToSet = colorList.get(colorIndex % colorsNumber);
                    panel.setBackground(colorToSet);
                    matrix[i][j].get().setEntityColor(colorToSet);
                    colorIndex++;
                }

                else
                    panel.setBackground(Color.white);

                jPanelList[i][j] = panel;
                frame.getContentPane().add(panel); // Adds Button to content pane of frame
            }
        }

        frame.setLayout(new GridLayout(length,height));
    }

    /**
     * Update the graphic grid if the entity has moved
     */
    void updateGrid(Grid grid){
        List<Entity> entitiesList = grid.getEntitiesList();
        for(Entity entity : entitiesList) {
            if(entity.isArrived()) {
                disappear(entity);
            }

            else if(entity.hasMoved() && entity.isDestroyed()) {
                Position lastPosition = entity.getPreviousPosition().get();
                Position currentPosition = entity.getCurrentPosition();

                jPanelList[lastPosition.getI()][lastPosition.getJ()].setBackground(Color.WHITE);
                jPanelList[currentPosition.getI()][currentPosition.getJ()].setBackground(entity.getEntityColor());
            }

            else if(entity.isKilled() && entity.getKillTime() == 0) {
                Position currentPosition = entity.getCurrentPosition();
                jPanelList[currentPosition.getI()][currentPosition.getJ()].setBackground(Color.white);
            }


        }
    }

    /**
     * Update and display the grid
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
        JPanel jPanel = jPanelList[entity.getArrivalPosition().getI()][entity.getArrivalPosition().getJ()];
        jPanel.setBackground(Color.WHITE);
    }

    public void reviveVisually(Entity entity) {
        JPanel jPanel = jPanelList[entity.getCurrentPosition().getI()][entity.getCurrentPosition().getJ()];
        jPanel.setBackground(entity.getEntityColor());
        System.out.println("nooooooooooooooooow");
    }

    int colorListCreation() {
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.PINK);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.YELLOW);

        return colorList.size();
    }

    public void close() {
        this.frame.dispose();
    }


}
