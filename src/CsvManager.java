import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvManager {
    String csvPath;

    public CsvManager(String csvPath) {
        this.csvPath = csvPath;
    }

    /**
     * Create a Grid using the csv file
     * @param positionManager
     * @return
     * @throws IOException
     */
    public Grid getConfigurationGrid(PositionManager positionManager) throws IOException {
        ArrayList<Entity> allEntities = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        String row;
        int lines = 0;
        int columns = 0;

        int i = 0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(" ");
            if(i==0){
                lines = Integer.valueOf(data[0]);
                columns = Integer.valueOf(data[1]);
            }
            else{
                Position departure = new Position(Integer.valueOf(data[0]), Integer.valueOf(data[1]));
                Position arrival = new Position(Integer.valueOf(data[2]), Integer.valueOf(data[3]));
                allEntities.add(new Entity(departure, arrival,positionManager,i));
            }
            i++;
        }
        csvReader.close();
        return new Grid(lines, columns, allEntities);
    }

    /**
     * Write in the csv file the configuration of the grid
     * @param grid
     * @throws IOException
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
