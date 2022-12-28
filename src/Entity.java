import java.awt.*;
import java.util.*;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

/**
 * Entity which move on the grid
 */
public class Entity implements Callable<EntityTurnResult> {
    //starting position
    protected final Position startPosition;
    //arrival position
    protected final Position arrivalPosition;
    //current position
    protected Position currentPosition;
    //optional previous position
    protected Optional<Position> previousPosition;
    //unique ID
    protected final int id;
    //entity color
    protected Color entityColor;
    //positionManager which decides of the entity next move: move, die, revive or exit
    //once killed, dead for at least 2 rounds
    protected final int killTime;
    //when an entity arrived to its arrival position, it is destroyed
    protected boolean destroyed;
    //grid
    protected Grid grid;

    int canRevive = 0;
    boolean dead = false;

    public Entity(Position startPosition, Position arrivalPosition, int id) {
        this.startPosition = startPosition;
        this.previousPosition = Optional.empty();
        this.currentPosition = startPosition;
        this.arrivalPosition = arrivalPosition;
        this.id = id;
        this.destroyed = false;
        this.killTime = Main.killTime;
        this.grid = Simulation.grid;
    }

    /**
     * Makes all the decision about the entity movement: move, die, revive and update the grid appearance
     */
    @Override
    public EntityTurnResult call() throws Exception {
        if(dead){
            if(canRevive >= 2){
                dead = false;
                revive();
            }
            else
                canRevive +=1;
        }
        else if (!isArrived())
            move();
        else
            destroy();

        if (Main.displayMode) {
            Display.updateGrid(this);
            sleep(Simulation.sleepTime);
        }

        return getEntityTurnResult();
    }

    protected EntityTurnResult getEntityTurnResult() {
        return new EntityTurnResult(this.id, this.destroyed);
    }


    /**
     * If possible, change the entity previous position with the current one, and the current one with the new one
     */
    public void move() throws Exception {
        Position position = PositionManager.getNewPosition(this.currentPosition, this.arrivalPosition);
        //grid.getBox(position.getI(), position.getJ()).arrive(this);
        MovementState state = grid.getBox(position.getI(), position.getJ()).arrive(this);
        System.out.println(this+": "+state);
        switch (state) {
            case MOVE -> moveTo(position);
            case IS_WAITING -> {}
            case DIE -> kill();
        }
    }

    /**
     * Update positions
     * @param position the new position
     */
    void moveTo(Position position) {
        this.previousPosition = Optional.of(currentPosition);
        this.currentPosition = position;
    }

    /**
     * Kill this entity: remove it from the box, update appearance and revive after a killTime
     */
    public void kill() throws Exception {
        grid.getBox(this.currentPosition.getI(), this.currentPosition.getJ()).depart(this);
        if (Main.displayMode)
            Display.disappear(this);

        resetCurrentPosition();
        resetPreviousPosition();
        dead = true;
        System.out.println("dead :(");
    }

    /**
     * Revive entity: put it in its start box if it is empty, otherwise wait and update appearance
     */
    public void revive() throws InterruptedException {
        if(grid.getBox(this.startPosition.getI(), this.startPosition.getJ()).getEntity().isEmpty()){
            grid.getBox(this.startPosition.getI(), this.startPosition.getJ()).setEntity(this);
            if (Main.displayMode)
                Display.reappear(this);
            System.out.println("Revive !!!");
        }
        else dead = true;
    }

    /**
     * When an entity arrived to its arrival position, it is destroyed: removed from its box
     */
    public void destroy() throws Exception {
        Box box = grid.getBox(this.currentPosition.getI(), this.currentPosition.getJ());
        Entity e = box.getEntity().get();
        box.depart(this);
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
    public String toString() {
        return "id: " + this.id + "    color: " + this.entityColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
