import java.awt.*;
import java.util.*;

public class Entity {

    //entity current position
    private Position currentPosition;

    //entity previous position
    private Optional<Position> previousPosition;

    //arrival position
    private final Position arrivalPosition;

    //unique ID
    private final int id;

    //when an entity arrived to its arrival position, it is destroyed
    private boolean destroyed;

    private Color entityColor;

    private PositionManager positionManager;

    private boolean kill;

    private boolean killVisually;



    public Entity(Position currentPosition, Position arrivalPosition, PositionManager positionManager, int id) {
        this.previousPosition = Optional.empty();
        this.currentPosition = currentPosition;
        this.arrivalPosition = arrivalPosition;
        this.id = id;
        this.positionManager = positionManager;
        this.kill = false;
        this.destroyed = false;
        this.killVisually = false;
    }

    /**
     * Change the entity previous position with the current one, and the current one with the new one
     */
    public void move(){
        Position position = positionManager.getNewPosition(this.currentPosition, this.arrivalPosition);
        if(!positionManager.positionIsAlreadyTaken(position)) {
            positionManager.updatePositionOfEntity(this.currentPosition, position);
            this.previousPosition = Optional.of(currentPosition);
            this.currentPosition = position;
        }

        else {
            this.previousPosition = Optional.empty();
            if(positionManager.isThereAConflict(currentPosition, position)){
                positionManager.manageConflict(this, position);
            }
        }
    }

    /**
     * Check is the entity reached its arrival position
     * @return true if the entity reached its arrival position, false otherwise
     */
    public boolean isArrived() {
        return this.currentPosition.equals(arrivalPosition);
    }

    /**
     * Check if an entity has moved
     * @return true if the entity has moved
     */
    public boolean hasMoved() {
        return this.previousPosition.isPresent();
    }

    /**
     * When an entity arrived to its arrival position, it is destroyed
     */
    public void destroy() {
        positionManager.destroyPosition(this.currentPosition);
    }

    /**
     * Check if an entity is destroyed
     * @return destroyed attribute boolean
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Position getArrivalPosition() {
        return arrivalPosition;
    }

    public Optional<Position> getPreviousPosition() {
        return previousPosition;
    }

    public Color getEntityColor() {
        return entityColor;
    }

    public void setEntityColor(Color color) {
        this.entityColor = color;
    }

    public PositionManager getPositionManager() {
        return positionManager;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(currentPosition, entity.currentPosition) && Objects.equals(arrivalPosition, entity.arrivalPosition) && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition, arrivalPosition, id);
    }

    public void kill() {
        this.kill = true;
        positionManager.removePosition(currentPosition);
    }

    public void revive() {
        this.kill = false;
        positionManager.addPosition(currentPosition);
    }

    public boolean isKilled(){
        return this.kill;
    }

    public boolean isKillVisually() {
        return killVisually;
    }

    public void setKillVisually(boolean killVisually) {
        this.killVisually = killVisually;
    }
}
