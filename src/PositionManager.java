import java.util.*;

public class PositionManager {

    private Grid grid;

    public PositionManager(Grid grid) {
        this.grid = grid;
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
     * @param position spawning position
     * @return true if the position is already taken
     */
    boolean isPositionTaken(Position position) {
        return grid.getCurrentPositions().contains(position);
    }

    /**
     * Get random spawning position
     * @param maxLength max length value available
     * @param maxHeight max height value available
     * @return a random position
     */
    public static Position getRandomPosition(int maxLength, int maxHeight) {
        Random random = new Random();
        return new Position(random.nextInt(0, maxLength), random.nextInt(0, maxHeight));
    }

    public void updatePositionOfEntity(Position currentPosition, Position newPosition){
        grid.removeCurrentPosition(currentPosition);
        grid.addCurrentPosition(newPosition);
    }

    public Optional<Entity> findEntityByPosition(Position position){
        for(Entity entity: grid.getEntitiesList()){
            if(entity.getCurrentPosition().equals(position))
                return Optional.of(entity);
        }
        return Optional.empty();
    }

    public void manageConflict(Entity entity, Position conflictPosition){
        Optional<Entity> optionalEntity = findEntityByPosition(conflictPosition);
        Entity conflictEntity = optionalEntity.get();
        Entity entityToKill;
        if(entity.getId() < conflictEntity.getId())
            entityToKill = entity;
        else
            entityToKill = conflictEntity;
        grid.kill(entityToKill);
    }


    public boolean canEntityBeRevive(Entity entity) {
        boolean killTime = (entity.getKillTime() == 2);
        boolean isStartingPositionTaken = isPositionTaken(entity.getStartPosition());
        boolean ImKilled = entity.isKilled();
        return !isStartingPositionTaken && ImKilled && killTime;
    }

    public void removeCurrentPosition(Position currentPosition) {
        grid.removeCurrentPosition(currentPosition);
    }
}
