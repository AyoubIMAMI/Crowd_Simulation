import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskExecutorManager {
    Grid grid;
    static TaskExecutor topRightExecutor;
    static TaskExecutor botRightExecutor;
    static TaskExecutor botLeftExecutor;
    static TaskExecutor topLeftExecutor;

    public TaskExecutorManager(Grid grid) {
        this.grid = grid;
        topRightExecutor = new TaskExecutor(grid, GridQuarterPosition.TOP_RIGHT);
        botRightExecutor = new TaskExecutor(grid, GridQuarterPosition.BOT_RIGHT);
        botLeftExecutor = new TaskExecutor(grid, GridQuarterPosition.BOT_LEFT);
        topLeftExecutor = new TaskExecutor(grid, GridQuarterPosition.TOP_LEFT);
    }


    public static void changeExecutor(Entity entity, GridQuarterPosition quarterPosition) {
        switch (quarterPosition){
            case BOT_RIGHT -> botRightExecutor.addNewEntity(entity);
            case BOT_LEFT -> botLeftExecutor.addNewEntity(entity);
            case TOP_RIGHT -> topRightExecutor.addNewEntity(entity);
            case TOP_LEFT -> topLeftExecutor.addNewEntity(entity);
        }
    }

    public void runAll(List<Entity> entities) throws ExecutionException, InterruptedException {
        for(Entity entity: entities){
            GridQuarterPosition quarterPosition = GridQuarterPosition.getQuarterPosition(entity.currentPosition, grid);
            changeExecutor(entity, quarterPosition);
        }
        topRightExecutor.start();
        botRightExecutor.start();
        botLeftExecutor.start();
        topLeftExecutor.start();
    }


    public void shutdownAll() {
        topRightExecutor.getExecutor().shutdown();
        botRightExecutor.getExecutor().shutdown();
        botLeftExecutor.getExecutor().shutdown();
        topLeftExecutor.getExecutor().shutdown();
    }

    public void joinAll() throws InterruptedException {
        topRightExecutor.join();
        botRightExecutor.join();
        botLeftExecutor.join();
        topLeftExecutor.join();
    }
}
