import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manage the simulation display
 */
public class Display {
    //lines*columns of the grid
    private int lines;
    private int columns;

    //window manager
    private final JFrame frame;
    //window content manager
    private static JPanel[][] jPanelList;

    //colors associated to entities
    private final List<Color> colorList;
    //number of different colors
    private final int colorsNumber;

    public Display(Grid grid) {
        this.lines = grid.lines;
        this.columns = grid.columns;
        this.colorList = new ArrayList<>();
        this.colorsNumber = colorListCreation();
        this.jPanelList = new JPanel[lines][columns];
        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
    }

    /**
     * Create and display the grid
     * @param grid where entities move to create and display
     */
    void displayGrid(Grid grid) {
        createGridAppearance(grid);
        frame.setVisible(true);
    }

    /**
     * Create the grid appearance
     * @param grid in which entities move
     */
    public void createGridAppearance(Grid grid){
        Optional<Entity>[][] matrix = grid.getGrid();

        //needed to browse the list since there can be more entities than colors
        int colorIndex = 0;

        //initialize the grid appearance
        for(int i = 0; i < lines; i ++){
            for(int j = 0; j < columns; j ++){
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

        frame.setLayout(new GridLayout(lines, columns));
    }

    /**
     * Update the grid appearance
     * @param entity involved in this round
     * @param victim true if the entity involved in this round got killed (conflict)
     * @param revived true if the entity involved in this round got revived
     */
    static void updateGrid(Entity entity, boolean victim, boolean revived){
        if (victim) {
            Position currentPosition = entity.getCurrentPosition();
            jPanelList[currentPosition.getI()][currentPosition.getJ()].setBackground(Color.white);
            entity.resetCurrentPosition();
            entity.resetPreviousPosition();
        }
        else if(entity.isArrived() && entity.isDestroyed()) {
            disappear(entity);
        }
        else if(entity.hasMoved()) {
            Position lastPosition = entity.getPreviousPosition().get();
            Position currentPosition = entity.getCurrentPosition();
            jPanelList[lastPosition.getI()][lastPosition.getJ()].setBackground(Color.WHITE);
            jPanelList[currentPosition.getI()][currentPosition.getJ()].setBackground(entity.getEntityColor());
        }
        else if(revived) {
            Position currentPosition = entity.getCurrentPosition();
            jPanelList[currentPosition.getI()][currentPosition.getJ()].setBackground(entity.getEntityColor());
        }
    }

    /**
     * Once the entity reach his final position, it disappears from the grid
     * @param entity to make disappeared
     */
    static void disappear(Entity entity) {
        JPanel jPanel = jPanelList[entity.getArrivalPosition().getI()][entity.getArrivalPosition().getJ()];
        jPanel.setBackground(Color.WHITE);
    }

    /**
     * Fill the colors list
     * @return the size of the list - needed to browse the list since there can be more entities than colors
     */
    int colorListCreation() {
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.PINK);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.YELLOW);

        return colorList.size();
    }

    /**
     * Close the display window
     */
    public void close() {
        this.frame.dispose();
    }

    public void setGrid(Grid grid) {
        this.lines = grid.lines;
        this.columns = grid.columns;
        this.jPanelList = new JPanel[lines][columns];
    }
}
