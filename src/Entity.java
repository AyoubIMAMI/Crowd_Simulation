import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Entity {
    private Position currentPosition;
    private Optional<Position> lastPosition;
    private final Position arrivalPosition;
    private final String uniqueID;

    public Entity(Position currentPosition, Position arrivalPosition) {
        this.lastPosition = Optional.empty();
        this.currentPosition = currentPosition;
        this.arrivalPosition = arrivalPosition;
        this.uniqueID = UUID.randomUUID().toString();
    }

    public void move(Position position){
        this.lastPosition = Optional.of(currentPosition);
        this.currentPosition = position;
    }
    public void resetLastPosition(){
        this.lastPosition = Optional.empty();
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Position getArrivalPosition() {
        return arrivalPosition;
    }

    public Optional<Position> getLastPosition() {
        return lastPosition;
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

    public boolean hasMove() {
        return this.lastPosition.isPresent();
    }
}
