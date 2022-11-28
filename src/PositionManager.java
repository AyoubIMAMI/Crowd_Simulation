import java.util.*;

/**
 * Manage the entities position
 */
public class PositionManager {

    //grid where the entities move
    private Grid grid;

    public PositionManager(Grid grid) {
        this.grid = grid;
    }

    /**
     * Get a random spawning position
     * @return a random position
     */
    public Position getRandomPosition() {
        Random random = new Random();
        return new Position(random.nextInt(grid.lines), random.nextInt(grid.columns));
    }

    /**
     * Define an arrival position - can only be on the grid borders
     * @return arrival position
     */
    public Position defineArrivalPosition() {
        int i;
        int j;

        Random random = new Random();
        //define if the position will be on the first/last line or the first/last column
        int setLineOrColumn = random.nextInt(2);
        //define if the position will be on the first line/column or the last line/column
        int setZeroOrMax = random.nextInt(2);

        //  ___  <- here
        // |  |
        // ---
        if(setLineOrColumn == 0 && setZeroOrMax == 0) {
            i = 0;
            j = random.nextInt(grid.columns);
        }

        //  ___
        // |  |
        // ---  <- here
        else if(setLineOrColumn == 0) {
            i = grid.lines-1;
            j = random.nextInt(grid.columns);
        }

        //           ___
        // here ->  |  |
        //          ---
        else if(setZeroOrMax == 0) {
            i = random.nextInt(grid.lines);
            j = 0;
        }

        //  ___
        // |  |  <- here
        // ---
        else {
            i = random.nextInt(grid.lines);
            j = grid.columns-1;
        }

        return new Position(i, j);
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
     * Check if a position is taken or not to avoid overlay entities
     * @param position position to check
     * @return true if the position is already taken, false otherwise
     */
    boolean isPositionTaken(Position position) {
        return grid.getBox(position.getI(), position.getJ()).entity.isPresent();
    }

    /**
     * Find an entity knowing its position
     * @param position entity researched position
     * @return the entity found
     */
    public Optional<Entity> findEntityByPosition(Position position){
        return grid.getBox(position.getI(), position.getJ()).getEntity();
    }

    /**
     * Define the entity to kill between two entities - the one with the lowest id
     * @param entity which enter in conflict
     * @param conflictPosition conflict position
     * @return the entity to kill //TODO
     */
    public boolean manageConflict(Entity entity, Position conflictPosition){
        System.out.println("manage conflict");
        Entity conflictEntity = findEntityByPosition(conflictPosition).get();
        return entity.getId() < conflictEntity.getId();
    }

    //verouiller la case, rehcercher et s'il n'y a personne alors on prend la case et on libère le verrou
    //enter et exit synchrnized

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
