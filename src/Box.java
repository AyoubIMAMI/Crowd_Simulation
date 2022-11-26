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

    public synchronized boolean setEntity(Entity entity) {
        if(this.entity.isPresent())return false;
        this.entity = Optional.of(entity);
        return true;
    }

}
