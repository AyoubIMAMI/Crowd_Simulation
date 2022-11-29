import java.util.Optional;
import java.util.concurrent.Semaphore;

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

    synchronized void depart(Entity entity) throws Exception {
        //TODO PROBLEME AVEC CETTE SECURITE, A REVOIR (PENSER AUX EUALS ET HASHCODE COMME SOURCE DE PROBLEME)
        //if (!this.entity.get().equals(entity))
            //throw new Exception("[SECURITY] Not the same entity!");

        this.entity = Optional.empty();
        notifyAll();
    }

    synchronized void setEntity(Entity entity) throws InterruptedException {
        if (this.entity.isPresent())
            wait();

        this.entity = Optional.of(entity);
    }
}
