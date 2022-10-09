import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PositionManager {

    private List<Position> allCurrentPositions;

    public PositionManager() {
        this.allCurrentPositions = new ArrayList<>();
    }

    public void addPosition(Position p){
        this.allCurrentPositions.add(p);
    }

    /**
     * Compute the new position the reach the arrival position
     * @param currentPosition entity current position
     * @param arrivalPosition entity arrival position
     * @return the new position, closest to the arrival
     */
    public static Position getNewPosition(Position currentPosition, Position arrivalPosition) {
        int iDifference = arrivalPosition.getI() - currentPosition.getI();
        int jDifference = arrivalPosition.getJ() - currentPosition.getJ();

        if(iDifference != 0) iDifference /= Math.abs(iDifference);
        if(jDifference != 0) jDifference /= Math.abs(jDifference);

        int xySum = iDifference + jDifference;

        if(xySum == -2) return new Position(currentPosition.getI()-1, currentPosition.getJ()-1);

        else if(xySum == 2) return new Position(currentPosition.getI()+1, currentPosition.getJ()+1);

        else if(xySum == 0 && iDifference == 1) return new Position(currentPosition.getI()+1, currentPosition.getJ()-1);

        else if(xySum == 0 && iDifference == -1) return new Position(currentPosition.getI()-1, currentPosition.getJ()+1);

        else if(xySum == 1 && iDifference == 1) return new Position(currentPosition.getI()+1, currentPosition.getJ());

        else if(xySum == -1 && iDifference == -1) return new Position(currentPosition.getI()-1, currentPosition.getJ());

        else if(xySum == 1 && iDifference == 0) return new Position(currentPosition.getI(), currentPosition.getJ()+1);

        else
            return new Position(currentPosition.getI(), currentPosition.getJ()-1); //if(xySum == -1 && iDifference == 0)
    }

    /**
     * Check if a position is taken or not to avoid overlay spawning entities
     * @param grid the grid
     * @param currentPosition spawning position
     * @return true if the position is already taken
     */
    static boolean isPositionTaken(Grid grid, Position currentPosition) {
        for (Entity entity : grid.entitiesList) {
            if(entity.getCurrentPosition().equals(currentPosition))
                return true;
        }
        return false;
    }

    /**
     * Get random spawning position
     * @param maxLength max length value available
     * @param maxHeight max height value available
     * @return a random position
     */
    public static Position getRandomPosition(int maxLength, int maxHeight) {
        Random random = new Random();
        return new Position(random.nextInt(0, maxLength-1), random.nextInt(0, maxHeight-1));
    }

    public List<Position> getAllCurrentPositions() {
        return allCurrentPositions;
    }

    public boolean positionIsAlreadyTaken(Position p){
        return this.allCurrentPositions.contains(p);
    }

    public void updatePositionOfEntity(Position currentPosition, Position newPosition){
        this.allCurrentPositions.remove(currentPosition);
        this.allCurrentPositions.add(newPosition);
    }
}
