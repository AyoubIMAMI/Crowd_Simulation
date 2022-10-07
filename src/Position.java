import java.util.Objects;
import java.util.Random;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position getRandomPosition(int min, int max) {
        Random random = new Random();
        return new Position(random.nextInt(max - min + 1) + min, random.nextInt(max - min + 1) + min);
    }

    public static Position getRandomLogicalPosition(Position currentPos, int lenght, int height) {
        int x = currentPos.getX();
        int y = currentPos.getY();
        int sideDeplacementChoice = 1;
        int heightDeplacementChoice = 1;
        if(x == lenght-1) sideDeplacementChoice = -1; // gauche
        if(y == height-1) heightDeplacementChoice = -1; // gauche

        return new Position(x+sideDeplacementChoice, y+heightDeplacementChoice);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Position){
            Position other = (Position) o;
            if(this.x == other.getX() && this.y == other.getY()) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
