import java.util.Objects;
import java.util.UUID;

public class Entity {
    private Position currentPos;
    private Position finalPos;

    private String uniqueID;
    public Entity(Position currentPos, Position finalPos) {
        this.currentPos = currentPos;
        this.finalPos = finalPos;
        this.uniqueID = UUID.randomUUID().toString();
    }

    public Position getCurrentPos() {
        return currentPos;
    }

    public Position getFinalPos() {
        return finalPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(currentPos, entity.currentPos) && Objects.equals(finalPos, entity.finalPos) && Objects.equals(uniqueID, entity.uniqueID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPos, finalPos, uniqueID);
    }
}
