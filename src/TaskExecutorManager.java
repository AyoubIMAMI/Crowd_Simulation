import java.util.concurrent.Callable;

public class TaskExecutorManager {
    Grid grid;
    static TaskExecutor botRightExecutor;
    static TaskExecutor botLeftExecutor;
    static TaskExecutor topRightExecutor;
    static TaskExecutor topLeftExecutor;

    public TaskExecutorManager(Grid grid) {
        this.grid = grid;
        botRightExecutor = new TaskExecutor(GridQuarterPosition.BOT_RIGHT, grid);
        botLeftExecutor = new TaskExecutor(GridQuarterPosition.BOT_LEFT, grid);
        topRightExecutor = new TaskExecutor(GridQuarterPosition.TOP_RIGHT, grid);
        topLeftExecutor = new TaskExecutor(GridQuarterPosition.TOP_LEFT, grid);
    }


    public static void changeExecutor(Entity entity, GridQuarterPosition quarterPosition) {
        switch (quarterPosition){
            case BOT_RIGHT -> botRightExecutor.addNewEntity(entity);
            case BOT_LEFT -> botLeftExecutor.addNewEntity(entity);
            case TOP_RIGHT -> topRightExecutor.addNewEntity(entity);
            case TOP_LEFT -> topLeftExecutor.addNewEntity(entity);
        }
    }
}
