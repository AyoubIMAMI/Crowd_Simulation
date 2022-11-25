import java.util.Optional;

public class Box {

    //box position in the grid
    final Position boxPosition;
    //entity in the box
    Optional<Entity> entity;

    public Box(Position position) {
        this.boxPosition = position;
        this.entity = Optional.empty();
    }

    public Optional<Entity> getEntity() {
        return entity;
    }

    public void setEntity(Optional<Entity> entity) {
        this.entity = entity;
    }
}
