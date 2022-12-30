/**
 * Entity state result of a round
 */
public class EntityTurnResult {

    //attribute to know if the entity has been destroyed on this turn
    boolean isDestroyed;
    //entity id
    int id;

    public EntityTurnResult(int id, boolean isDestroyed) {
        this.id = id;
        this.isDestroyed = isDestroyed;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public int getId() {
        return id;
    }
}
