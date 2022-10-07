/**
 * @author LE BIHAN Léo
 * @author IMAMI Ayoub
 *
 * Main class
 */
public class Main {
    //Display the grid where the crowd move

    public static void main(String[] args) throws InterruptedException {
        Display display = new Display();

        //matrix creation
        MatrixEntity matrixEntity = new MatrixEntity(3,3,3);
        //entity creation
        Entity entity = new Entity(new Position(0,0), new Position(2,2));
        matrixEntity.addEntity(entity);

        display.displayGrid(matrixEntity);

        entity.move(new Position(2,1));
        Thread.sleep(750);
        display.updateGrid(matrixEntity.getListEntity());

        entity.move(new Position(1,1));
        Thread.sleep(750);
        display.updateGrid(matrixEntity.getListEntity());
    }
}
