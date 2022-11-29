import java.awt.*;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Entity which move on the grid
 */
public class Entity implements Runnable {
    //starting position
    private final Position startPosition;
    //arrival position
    private final Position arrivalPosition;
    //current position
    private Position currentPosition;
    //optional previous position
    private Optional<Position> previousPosition;
    //unique ID
    private final int id;
    //entity color
    private Color entityColor;
    //positionManager which decides of the entity next move: move, die, revive or exit
    private final PositionManager positionManager;
    //once killed, dead for at least 2 rounds
    private final int killTime;
    //when an entity arrived to its arrival position, it is destroyed
    private boolean destroyed;
    //grid
    private final Grid grid;

    public Entity(Position startPosition, Position arrivalPosition, int id) {
        this.startPosition = startPosition;
        this.previousPosition = Optional.empty();
        this.currentPosition = startPosition;
        this.arrivalPosition = arrivalPosition;
        this.id = id;
        this.positionManager = new PositionManager(Simulation.grid);
        this.destroyed = false;
        this.killTime = Main.killTime;
        this.grid = Simulation.grid;
    }

    @Override
    public void run() {
        try {
            System.out.println(this);
            while (!isDestroyed()) {
                if (!isArrived())
                    move();
                else
                    destroy();

                Display.updateGrid(this);
                sleep(Simulation.sleepTime);
            }
        } catch (Exception ignored){ }
    }

    /**
     * If possible, change the entity previous position with the current one, and the current one with the new one
     */
    public void move() throws Exception {
        Position position = PositionManager.getNewPosition(this.currentPosition, this.arrivalPosition);
        MovementState state = grid.getBox(position.getI(), position.getJ()).arrive(this);

        switch (state) {
            case MOVE -> moveTo(position);
            case IS_WAITING -> { }
            case DIE -> kill();
        }
    }

    void moveTo(Position position) {
        this.previousPosition = Optional.of(currentPosition);
        this.currentPosition = position;
    }

    /**
     * Kill this entity - set its kill attribute to true
     */
    public void kill() throws Exception {
        grid.getBox(this.currentPosition.getI(), this.currentPosition.getJ()).depart(this);
        resetCurrentPosition();
        resetPreviousPosition();
        sleep(killTime);
        revive();
    }

    /**
     * Revive entity - set its kill attribute to false and reset its kill time
     */
    public void revive() throws InterruptedException {
        grid.getBox(this.startPosition.getI(), this.startPosition.getJ()).setEntity(this);
        Display.reappear(this);
    }

    /**
     * When an entity arrived to its arrival position, it is destroyed
     */
    public void destroy() throws Exception {
        grid.getBox(this.currentPosition.getI(), this.currentPosition.getJ()).depart(this);
        this.destroyed = true;
    }

    /**
     * Check if an entity has moved
     * @return true if the entity has moved, false otherwise
     */
    public boolean hasMoved() {
        return this.previousPosition.isPresent();
    }

    /**
     * Check is the entity reached its arrival position
     * @return true if the entity reached its arrival position, false otherwise
     */
    public boolean isArrived() {
        return this.currentPosition.equals(arrivalPosition);
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

    public int getId() {
        return id;
    }

    public void setEntityColor(Color color) {
        this.entityColor = color;
    }

    public void resetCurrentPosition() {
        this.currentPosition = this.startPosition;
    }

    public void resetPreviousPosition() {
        this.previousPosition = Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id && killTime == entity.killTime && destroyed == entity.destroyed
                && Objects.equals(startPosition, entity.startPosition)
                && Objects.equals(arrivalPosition, entity.arrivalPosition)
                && Objects.equals(currentPosition, entity.currentPosition)
                && Objects.equals(previousPosition, entity.previousPosition)
                && Objects.equals(entityColor, entity.entityColor)
                && Objects.equals(positionManager, entity.positionManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, arrivalPosition, currentPosition, previousPosition, id, entityColor,
                positionManager, killTime, destroyed);
    }

    @Override
    public String toString() {
        return "id: " + this.id + "    color: " + this.entityColor;
    }
}
