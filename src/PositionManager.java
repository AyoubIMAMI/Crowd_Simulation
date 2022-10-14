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

        int iSign = 0;
        int jSign = 0;

        if(iDifference != 0) iSign = iDifference/Math.abs(iDifference);
        if(jDifference != 0) jSign = jDifference/Math.abs(jDifference);

        if(iDifference == 0 && jDifference != 0)
            return new Position(currentPosition.getI(), currentPosition.getJ()+jSign);
        else if(iDifference != 0 && jDifference == 0)
            return new Position(currentPosition.getI()+iSign, currentPosition.getJ());
        else{
            if(iDifference < jDifference)
                return new Position(currentPosition.getI()+iSign, currentPosition.getJ());
            else
                return new Position(currentPosition.getI(), currentPosition.getJ()+jSign);
        }
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
     * @return a random position
     */
    public Position getRandomPosition() {
        Random random = new Random();
        return new Position(random.nextInt(0, Main.lines), random.nextInt(0, Main.columns));
    }

    public Position defineArrivalPosition() {
        int i;
        int j;

        Random random = new Random();
        int setLineOrColumn = random.nextInt(0, 2);
        int setZeroOrMax = random.nextInt(0, 2);

        //  ___  <- here
        // |  |
        // ---
        if(setLineOrColumn == 0 && setZeroOrMax == 0) {
            i = 0;
            j = random.nextInt(0, Main.columns-1);
        }

        //  ___
        // |  |
        // ---  <- here
        else if(setLineOrColumn == 0 && setZeroOrMax == 1) {
            i = Main.lines-1;
            j = random.nextInt(0, Main.columns-1);
        }

        //           ___
        // here ->  |  |
        //          ---
        else if(setLineOrColumn == 1 && setZeroOrMax == 0) {
            i = random.nextInt(0, Main.lines-1);
            j = 0;
        }

        //  ___
        // |  |  <- here
        // ---
        else {
            i = random.nextInt(0, Main.lines-1);
            j = Main.columns-1;
        }

        return new Position(i, j);
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

    public Entity manageConflict(Entity entity, Position conflictPosition){
        Optional<Entity> optionalEntity = findEntityByPosition(conflictPosition);
        Entity conflictEntity = optionalEntity.get();
        Entity entityToKill;
        if(entity.getId() < conflictEntity.getId())
            entityToKill = entity;
        else
            entityToKill = conflictEntity;
        grid.kill(entityToKill);

        return entityToKill;
    }


    public boolean canEntityBeRevive(Entity entity) {
        boolean killTimeEnd = (entity.getKillTime() == 2);
        boolean isStartingPositionTaken = isPositionTaken(entity.getStartPosition());
        boolean ImKilled = entity.isKilled();
        return !isStartingPositionTaken && ImKilled && killTimeEnd;
    }

    public void removeCurrentPosition(Position currentPosition) {
        grid.removeCurrentPosition(currentPosition);
    }
}
