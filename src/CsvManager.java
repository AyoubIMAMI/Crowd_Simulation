import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the csv file to set up the grid: lines and columns, and the entities: positions
 */
public class CsvManager {
    //csv path
    String csvPath;

    public CsvManager(String csvPath) {
        this.csvPath = csvPath;
    }

    /**
     * Create the grid using the csv file
     * @param positionManager which decides of the entity next move: move, die, revive or exit
     * @return the grid set up thanks to the csv file
     * @throws IOException file exception
     */
    public Grid getConfigurationGrid(PositionManager positionManager) throws IOException {
        ArrayList<Entity> allEntities = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        String row;
        int lines = 0;
        int columns = 0;

        int csvLineCounter = 0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(" ");
            if(data[0].equals("#"))continue;
            if(csvLineCounter == 0){
                lines = Integer.parseInt(data[0]);
                columns = Integer.parseInt(data[1]);
            }
            else{
                Position departure = new Position(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                Position arrival = new Position(Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                Entity entity = new Entity(departure, arrival, positionManager,csvLineCounter);
                allEntities.add(entity);
            }
            csvLineCounter++;
        }
        csvReader.close();
        return new Grid(lines, columns, allEntities);
    }

    /**
     * Write in the csv file the configuration of the grid
     * @param grid the grid to set up in the csv file
     * @throws IOException file exception
     */
    public void createConfigurationGrid(Grid grid) throws IOException {
        FileWriter csvWriter = new FileWriter(csvPath,  false);
        csvWriter.append(grid.getGrid()+" "+ grid.getColumns());
        csvWriter.append("\n");
        List<Entity> allEntities = grid.getEntitiesList();
        for(Entity entity : allEntities){
            Position departure = entity.getCurrentPosition();
            Position arrival = entity.getCurrentPosition();

            csvWriter.append(departure.getI()+" "+ departure.getJ()+" "+arrival.getI()+" "+ arrival.getJ());
            csvWriter.append("\n");
        }
        csvWriter.close();
    }
}
