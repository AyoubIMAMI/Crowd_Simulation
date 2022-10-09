import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Position {

    //(i, j) position coordinates
    private int i;
    private int j;

    //current position of the entities - need to avoid entities overlay
    static List<Position> allCurrentPositions = new ArrayList<>();

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Position other){
            return this.i == other.getI() && this.j == other.getJ();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

}
