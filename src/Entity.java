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
        if(o instanceof Entity){
            Entity other = (Entity) o;

        }
        return false;
    }
}
