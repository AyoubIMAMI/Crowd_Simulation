import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Entity {
    //entity current position
    private Position currentPosition;

    //entity previous position
    private Optional<Position> previousPosition;
    private final Position arrivalPosition;
    private final String uniqueID;

    public Entity(Position currentPosition, Position arrivalPosition) {
        this.previousPosition = Optional.empty();
        this.currentPosition = currentPosition;
        this.arrivalPosition = arrivalPosition;
        this.uniqueID = UUID.randomUUID().toString();
    }

    /**
     * Change the entity previous position with the current one, and the current one with the new one
     * @param position - the new entity position
     */
    public void move(Position position){
        this.previousPosition = Optional.of(currentPosition);
        this.currentPosition = position;
    }

    /**
     * Check is the entity reached its arrival position
     * @return true if the entity reached its arrival position, false otherwise
     */
    public boolean isArrived() {
        return this.currentPosition.equals(arrivalPosition);
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

    public boolean hasMoved() {
        return this.previousPosition.isPresent();
    }

}
