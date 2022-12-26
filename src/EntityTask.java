import java.awt.*;
import java.util.*;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

/**
 * Entity which move on the grid
 */
public class EntityTask extends Entity implements Callable<EntityTurnResult> {


    public EntityTask(Position startPosition, Position arrivalPosition, int id) {
       super(startPosition,arrivalPosition,id);
    }

    /**
     * Makes all the decision about the entity movement: move, die, revive and update the grid appearance
     */
    @Override
    public EntityTurnResult call() {
        try {
            if (!isArrived())
                move();
            else
                destroy();

            if (Main.displayMode) {
                Display.updateGrid(this);
                sleep(Simulation.sleepTime);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return getEntityTurnResult();
    }

    protected EntityTurnResult getEntityTurnResult() {
        return new EntityTurnResult(this.id, this.destroyed);
    }

}
