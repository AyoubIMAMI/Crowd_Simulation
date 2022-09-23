public class Entity {
    private Position currentPos;
    private Position finalPos;

    public Entity(Position currentPos, Position finalPos) {
        this.currentPos = currentPos;
        this.finalPos = finalPos;
    }

    public Position getCurrentPos() {
        return currentPos;
    }

    public Position getFinalPos() {
        return finalPos;
    }
}
