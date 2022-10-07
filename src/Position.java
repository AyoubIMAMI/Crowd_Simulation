import java.util.Objects;
import java.util.Random;

public class Position {
    private int i;
    private int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public static Position getRandomPosition(int maxLength, int maxHeight) {
        Random random = new Random();
        return new Position(random.nextInt(0, maxLength-1), random.nextInt(0, maxHeight-1));
    }

    public static Position getRandomLogicalPosition(Position currentPos, Position arrivalPosition) {
        int iDifference = arrivalPosition.getI() - currentPos.getI();
        int jDifference = arrivalPosition.getJ() - currentPos.getJ();

        if(iDifference != 0) iDifference /= Math.abs(iDifference);
        if(jDifference != 0) jDifference /= Math.abs(jDifference);

        int xySum = iDifference + jDifference;

        if(xySum == -2) return new Position(currentPos.getI()-1, currentPos.getJ()-1);

        else if(xySum == 2) return new Position(currentPos.getI()+1, currentPos.getJ()+1);

        else if(xySum == 0 && iDifference == 1) return new Position(currentPos.getI()+1, currentPos.getJ()-1);

        else if(xySum == 0 && iDifference == -1) return new Position(currentPos.getI()-1, currentPos.getJ()+1);

        else if(xySum == 1 && iDifference == 1) return new Position(currentPos.getI()+1, currentPos.getJ());

        else if(xySum == -1 && iDifference == -1) return new Position(currentPos.getI()-1, currentPos.getJ());

        else if(xySum == 1 && iDifference == 0) return new Position(currentPos.getI(), currentPos.getJ()+1);

        else
            return new Position(currentPos.getI(), currentPos.getJ()-1); //if(xySum == -1 && iDifference == 0)
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Position){
            Position other = (Position) o;
            if(this.i == other.getI() && this.j == other.getJ()) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

}
