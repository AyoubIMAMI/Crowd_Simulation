import java.util.Optional;

/**
 * Box which contains an entity
 */
public class Box {

    //box position in the grid
    final Position boxPosition;
    //entity in the box
    Optional<Entity> entity;

    public Box(Position position) {
        this.boxPosition = position;
        this.entity = Optional.empty();
    }

    /**
     * Put the entity in the box if it is empty, otherwise die if the entity id is smaller or wait if its id is greater
     * than the one of the entity already in the box
     * @param entity that wants to go in the box
     * @return a state MOVE, DIE ord IS_WAITING depending on the situation described above
     * @throws Exception exception
     */
    synchronized MovementState arrive(Entity entity) throws Exception {
        if (this.entity.isEmpty()) {
            Simulation.grid.getBox(entity.getCurrentPosition().getI(), entity.getCurrentPosition().getJ()).depart(entity);
            this.entity = Optional.of(entity);
            return MovementState.MOVE;
        }
        if (entity.getId() < this.entity.get().getId())
                return MovementState.DIE;

        wait();
        return MovementState.IS_WAITING;
    }

    /**
     * Freed the box so another entity can go in
     * @param entity entity that wants to leave the box
     * @throws Exception exception
     */
    synchronized void depart(Entity entity) throws Exception {
        //TODO SECURITY ISSUE, 2 ENTITIES CALLS THE METHOD ON THE SAME BOX
        //if (!this.entity.get().equals(entity))
            //throw new Exception("[SECURITY] Not the same entity!");

        this.entity = Optional.empty();
        notifyAll();
    }

    /**
     * Put the entity in the box. If the box is not empty, the entity (thread) waits
     * @param entity entity to put in the box
     * @throws InterruptedException exception
     */
    synchronized void setEntity(Entity entity) throws InterruptedException {
        if (this.entity.isPresent())
            wait();

        this.entity = Optional.of(entity);
    }

    public Optional<Entity> getEntity() {
        return entity;
    }
}
