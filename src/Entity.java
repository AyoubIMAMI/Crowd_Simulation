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
    //has been killed
    private boolean dead;
    //once killed, dead for at least 2 rounds
    private int killTime;
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
        this.dead = false;
        this.destroyed = false;
        this.killTime = 0;
        this.grid = Simulation.grid;
    }

    /**
     * If possible, change the entity previous position with the current one, and the current one with the new one
     * @return true if the entity has been killed during the round
     */
    public boolean move() throws Exception {
        Position position = PositionManager.getNewPosition(this.currentPosition, this.arrivalPosition);
        MovementState state = grid.getBox(position.getI(), position.getJ()).arrive(this);

        switch (state) {
            case MOVE -> moveTo(position);
            case IS_WAITING -> { }
            case DIE -> kill();
        }

        return isKilled();
    }

    void moveTo(Position position) {
        this.previousPosition = Optional.of(currentPosition);
        this.currentPosition = position;
    }

    @Override
    public void run() {
        System.out.println(this);
        while(!isDestroyed()) {
            boolean revived = false;
            if (!isArrived()) {
                if (isKilled()) {
                    try {
                        revive();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    revived = true;
                } else if (this.dead)
                    incrementKillTime();
                else {
                    try {
                        move();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else {
                try {
                    destroy();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            Display.updateGrid(this, isKilled(), revived);
            try {
                sleep(Simulation.sleepTime);
            } catch (InterruptedException ignored) {}
        }
    }

    /**
     * Kill this entity - set its kill attribute to true
     */
    public void kill() throws Exception {
        grid.getBox(this.currentPosition.getI(), this.currentPosition.getJ()).depart(this);
        this.dead = true;
    }

    /**
     * Revive entity - set its kill attribute to false and reset its kill time
     */
    public void revive() throws InterruptedException {
        grid.getBox(this.startPosition.getI(), this.startPosition.getJ()).setEntity(this);
        this.dead = false;
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

    /**
     * Check if an entity is killed
     * @return kill attribute boolean
     */
    public boolean isKilled(){
        return this.dead;
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

    public Position getStartPosition() {
        return this.startPosition;
    }

    public int getKillTime() {
        return this.killTime;
    }

    public void setEntityColor(Color color) {
        this.entityColor = color;
    }

    public void resetKillTime() {
        this.killTime = 0;
    }

    public void resetCurrentPosition() {
        this.currentPosition = this.startPosition;
    }

    public void resetPreviousPosition() {
        this.previousPosition = Optional.empty();
    }

    public void incrementKillTime() {
        if(killTime == 2)
            killTime = 0;
        else
            this.killTime++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id && dead == entity.dead && killTime == entity.killTime && destroyed == entity.destroyed
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
                positionManager, dead, killTime, destroyed);
    }

    @Override
    public String toString() {
        return "id: " + this.id + "    color: " + this.entityColor;
    }
}
