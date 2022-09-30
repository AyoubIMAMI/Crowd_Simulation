import java.util.Objects;
import java.util.UUID;

public class Entity {
    private Position currentPosition;
    private final Position arrivalPosition;
    private final String uniqueID;

    public Entity(Position currentPosition, Position arrivalPosition) {
        this.currentPosition = currentPosition;
        this.arrivalPosition = arrivalPosition;
        this.uniqueID = UUID.randomUUID().toString();
    }

    public void move(Position position){
        this.currentPosition = position;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Position getArrivalPosition() {
        return arrivalPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(currentPosition, entity.currentPosition) && Objects.equals(arrivalPosition, entity.arrivalPosition) && Objects.equals(uniqueID, entity.uniqueID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition, arrivalPosition, uniqueID);
    }
}
