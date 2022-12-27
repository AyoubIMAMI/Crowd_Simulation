import java.awt.*;
import java.util.*;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

/**
 * Entity which move on the grid
 */
public class EntityTask extends Entity implements Callable<EntityTurnResult> {

    int canRevive = 0;
    boolean dead = false;

    public EntityTask(Position startPosition, Position arrivalPosition, int id) {
       super(startPosition,arrivalPosition,id);
       super.grid = SimulationTask.grid;
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
                sleep(SimulationTask.sleepTime);
            }

        return getEntityTurnResult();
    }

    protected EntityTurnResult getEntityTurnResult() {
        return new EntityTurnResult(this.id, this.destroyed);
    }

    /**
     * Kill this entity: remove it from the box, update appearance and revive after a killTime
     */
    @Override
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





}
