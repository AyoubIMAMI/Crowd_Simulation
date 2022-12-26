public class EntityTurnResult {
    boolean isDestroyed;
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
