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

    synchronized boolean arrive(Entity entity) throws InterruptedException {
        if (this.entity.isPresent()) {
            return entity.getId() < this.entity.get().getId();
        }
        this.entity = Optional.of(entity);
        return true;
    }

    synchronized void depart() {
        this.entity = Optional.empty();
        notifyAll();
    }

}
