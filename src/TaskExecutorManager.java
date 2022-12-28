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

    public void runAll() throws ExecutionException, InterruptedException {
        topRightExecutor.execute();
        botRightExecutor.execute();
        botLeftExecutor.execute();
        topLeftExecutor.execute();
    }


    public void shutdownAll() {
        topRightExecutor.getExecutor().shutdown();
        botRightExecutor.getExecutor().shutdown();
        botLeftExecutor.getExecutor().shutdown();
        topLeftExecutor.getExecutor().shutdown();
    }
}
