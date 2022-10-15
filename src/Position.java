import java.util.Objects;

/**
 * Positions of the moving entities
 */
public class Position {

    //(i, j) position coordinates
    private int i;
    private int j;

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

    @Override
    public String toString() {
        return "["+i+";"+j+"]";
    }
}
