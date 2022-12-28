import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskExecutor extends Thread{

    private final Grid grid;
    private final GridQuarterPosition quarter;
    private final ExecutorService executor;
    private List<Entity> currentEntities;
    private List<Entity> nextNewEntities;

    public TaskExecutor(Grid grid, GridQuarterPosition quarter) {
        this.quarter = quarter;
        this.grid = grid;
        this.executor = Executors.newFixedThreadPool(1);
        this.currentEntities = new ArrayList<>();
        this.nextNewEntities = new ArrayList<>();
    }
    @Override
    public void run() {
        List<Future<EntityTurnResult>> results = new ArrayList<>();

        while(!Simulation.entitiesList.isEmpty()) {
            copyNextEntitiesIntoCurrentEntities();
            for(Entity entity : currentEntities){
                results.add(executor.submit((Callable<EntityTurnResult>) entity));
            }
            for(Future<EntityTurnResult> futureResult : results){
                EntityTurnResult result = null;
                try {
                    result = futureResult.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
                if(result.isDestroyed()){
                    Simulation.removeEntityWithId(Simulation.entitiesList, result.getId());
                    Simulation.removeEntityWithId(this.currentEntities, result.getId());
                }
            }
            List<Entity> removable = new ArrayList<>();
            for(Entity entity : currentEntities) {
                GridQuarterPosition quarterPosition = GridQuarterPosition.getQuarterPosition(entity.getCurrentPosition(), grid);
                if (!quarterPosition.equals(this.quarter)){
                    TaskExecutorManager.changeExecutor(entity, quarterPosition);
                    removable.add(entity);
                }
            }
            this.currentEntities.removeAll(removable);
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

    public ExecutorService getExecutor() {
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
