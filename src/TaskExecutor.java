import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskExecutor {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<Entity> currentEntities = new ArrayList<>();
    private List<Entity> nextNewEntities = new ArrayList<>();
    private GridQuarterPosition quarter;
    private Grid grid;

    public TaskExecutor(GridQuarterPosition quarter, Grid grid) {
        this.quarter = quarter;
        this.grid = grid;
    }

    public void execute() throws ExecutionException, InterruptedException {
        List<Future<EntityTurnResult>> results = new ArrayList<>();

        while(!Simulation.entitiesList.isEmpty()) {
           copyNextEntitiesIntoCurrentEntities();
            for(Entity entity : currentEntities){
                results.add(executor.submit((Callable<EntityTurnResult>) entity));
            }
            for(Future<EntityTurnResult> futurResult : results){
                EntityTurnResult result = futurResult.get();
                if(result.isDestroyed())
                    Simulation.removeEntityWithId(result.getId());
            }
            for(Entity entity : currentEntities) {
                GridQuarterPosition quarterPosition = GridQuarterPosition.getQuarterPosition(entity.getCurrentPosition(), grid);
                if (!quarterPosition.equals(this.quarter)){
                    TaskExecutorManager.changeExecutor(entity, quarterPosition);
                    this.currentEntities.remove(entity);
                }
            }
        }
    }

    synchronized private void copyNextEntitiesIntoCurrentEntities() {
        this.currentEntities.addAll(nextNewEntities);
        nextNewEntities.clear();
    }

    public Grid getGrid() {
        return grid;
    }

    public GridQuarterPosition getQuarter() {
        return quarter;
    }

    public ExecutorService getBotRightExecutor() {
        return executor;
    }

    public List<Entity> getCurrentEntities() {
        return currentEntities;
    }

    synchronized public List<Entity> getNextNewEntities() {
        return nextNewEntities;
    }

    synchronized void addNewEntity(Entity entity){
        this.nextNewEntities.add(entity);
    }
}
