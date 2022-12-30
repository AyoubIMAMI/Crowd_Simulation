import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Task which will manage the entities of a specific grid area
 */
public class TaskExecutor extends Thread {

    //grid
    private final Grid grid;
    //quarter of the grid
    private final GridQuarterPosition quarter;
    //task
    private final ExecutorService executor;
    //entities to manage
    private List<Entity> currentEntities;
    //entities which joined the quarter
    private List<Entity> nextNewEntities;

    public TaskExecutor(Grid grid, GridQuarterPosition quarter) {
        this.quarter = quarter;
        this.grid = grid;
        this.executor = Executors.newSingleThreadExecutor();
        this.currentEntities = new ArrayList<>();
        this.nextNewEntities = new ArrayList<>();
    }

    /**
     * Manage entities movements in this quarter
     * which also means arrival and departure from this quarter
     */
    @Override
    public void run() {
        List<Future<EntityTurnResult>> results = new ArrayList<>();

        while(!Simulation.entitiesList.isEmpty()) {
            copyNextEntitiesIntoCurrentEntities();
            for(Entity entity : currentEntities){
                results.add(executor.submit(entity));
            }
            for(Future<EntityTurnResult> futureResult : results){
                EntityTurnResult result;
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
                    TaskExecutorManager.placeExecutor(entity, quarterPosition);
                    removable.add(entity);
                }
            }
            this.currentEntities.removeAll(removable);
        }
    }

    /**
     * Add the new coming entities in the quarter to the entities which already are in the quarter
     * that the task manages
     */
    synchronized private void copyNextEntitiesIntoCurrentEntities() {
        this.currentEntities.addAll(nextNewEntities);
        nextNewEntities.clear();
    }

    synchronized void addNewEntity(Entity entity){
        this.nextNewEntities.add(entity);
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
