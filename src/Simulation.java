import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;

public class Simulation {
    public Grid grid;
    public PositionManager positionManager;
    public Display display;
    static long startTime = System.nanoTime();
    static long sleepTime;


    public Simulation(int sleepTime, Grid grid, Display display, PositionManager positionManager) {
        this.sleepTime = sleepTime;
        this.grid = grid;
        this.display = display;
        this.positionManager = positionManager;
    }

    public void run() throws InterruptedException {
        display.displayGrid(grid);
        sleep(sleepTime);

        List<Entity> entitiesList = grid.getEntitiesList();
        while (entitiesList.size() != 0) {
            for (Entity entity : entitiesList) {
                playRound(entity);
            }
            grid.cleanUp();
            entitiesList = grid.getEntitiesList();
        }
        display.close();

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Program ran for " + totalTime / (Math.pow(10, 9)) + " seconds with a time sleep of " + sleepTime + " milliseconds.");
        System.out.println("Program ran for " + totalTime / (6 * Math.pow(10, 10)) + " minutes with a time sleep of " + sleepTime + " milliseconds.");
    }

    public void playRound(Entity entity) throws InterruptedException {
        Optional<Entity> potentialVictim = Optional.empty();
        boolean victimRevived = false;
        if (!entity.isArrived()) {
            if (positionManager.canEntityBeRevive(entity)) {
                grid.revive(entity);
                victimRevived = true;
            } else if (entity.isKilled())
                entity.incrementKillTime();
            else
                potentialVictim = entity.move();
        } else
            grid.destroy(entity);

        display.updateGrid(entity, potentialVictim, victimRevived);
        sleep(sleepTime);
    }
}
