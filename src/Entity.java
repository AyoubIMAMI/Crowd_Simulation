import java.awt.*;
import java.util.*;

public class Entity {

    private Position startPosition;

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

    private int killTime;


    public Entity(Position startPosition, Position arrivalPosition, PositionManager positionManager, int id) {
        this.startPosition = startPosition;
        this.previousPosition = Optional.empty();
        this.currentPosition = startPosition;
        this.arrivalPosition = arrivalPosition;
        this.id = id;
        this.positionManager = positionManager;
        this.kill = false;
        this.destroyed = false;
        this.killVisually = false;
        this.killTime = 0;
    }

    /**
     * Change the entity previous position with the current one, and the current one with the new one
     */
    public void move(){
        Position position = positionManager.getNewPosition(this.currentPosition, this.arrivalPosition);
        if(!positionManager.isPositionTaken(position)) {
            positionManager.updatePositionOfEntity(this.currentPosition, position);
            this.previousPosition = Optional.of(currentPosition);
            this.currentPosition = position;
        }

        else
            positionManager.manageConflict(this, position);
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
        positionManager.destroyPosition(this);
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
        System.out.println(this.getEntityColor().toString()+"- Entity killed - "+this.getCurrentPosition());
    }

    public void revive() {
        this.kill = false;
        this.killTime = 0;
        this.currentPosition = startPosition;
        positionManager.addPosition(startPosition);
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

    public Position getStartPosition() {
        return this.startPosition;
    }

    public int getKillTime() {
        return this.killTime;
    }

    public void incrementKillTime() {
        this.killTime++;
    }
}
