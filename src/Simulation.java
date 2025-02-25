import java.util.HashMap;

public class Simulation {
    boolean isGoing = true;
    public static final Coordinates MAP_COORDINATES = new Coordinates(20, 10);
    public static int TIMER = 8000;

    Map map = new Map();
    MapVisual mapVisual = new MapVisual(map);

    public void nextTurn() throws InterruptedException {
        for (Coordinates coordinates : map.getIterationCreatureMap().keySet()) {
            Creature creature = (Creature) map.getEntities().get(coordinates);
            if (creature != null) {
                creature.makeMove(map);
                mapVisual.showMap();
            }
        }

        map.getIterationCreatureMap().clear();
        for (HashMap.Entry<Coordinates, Entity> entry : map.getEntities().entrySet()) {
            if (entry.getValue() instanceof Creature) {
                map.getIterationCreatureMap().put(entry.getKey(), (Creature) entry.getValue());
            }
        }
    }

    public void startSimulation() throws InterruptedException {
        initActions();
        while (isGoing) {
            turnActions();
        }
    }

    public void pauseSimulation() {
        isGoing = false;
        System.out.println("End of simulation");
    }

    public void initActions() throws InterruptedException {
//        ИСХОДНОЕ КОЛИЧЕСТВО
//        int predatorNumber = 17;
//        int herbivoreNumber = 26;
//        int grassNumber = 11;
//        int rockNumber = 5;
//        int treeNumber = 10;
        int predatorNumber = 3;
        int herbivoreNumber = 17;
        int grassNumber = 11;
        int rockNumber = 5;
        int treeNumber = 10;

        for (int i = 0; i < predatorNumber; i++) {
            Predator predator = (Predator) map.putEntityOnRandomCoords(map.getEntities(), new Predator());
            map.getIterationCreatureMap().put(predator.coordinates, predator);
        }
        for (int i = 0; i < herbivoreNumber; i++) {
            Herbivore herbivore = (Herbivore) map.putEntityOnRandomCoords(map.getEntities(), new Herbivore());
            map.getIterationCreatureMap().put(herbivore.coordinates, herbivore);
        }
        for (int i = 0; i < grassNumber; i++) {
            map.putEntityOnRandomCoords(map.getEntities(), new Grass());
        }
        for (int i = 0; i < rockNumber; i++) {
            map.putEntityOnRandomCoords(map.getEntities(), new Rock());
        }
        for (int i = 0; i < treeNumber; i++) {
            map.putEntityOnRandomCoords(map.getEntities(), new Tree());
        }

        System.out.println("Cycle 1");
        mapVisual.showMap();
    }

    public void turnActions() throws InterruptedException {
        nextTurn();
        if (MapVisual.GRASS_COUNTER <= 4) {
            map.addEntity(new Grass(), 2);
        }
        if (MapVisual.HERBIVORE_COUNTER <= 4) {
            map.addEntity(new Herbivore(), 2);
        }
        mapVisual.nextCycle();
    }

    public static void main(String[] args) throws InterruptedException {
        Simulation simulation = new Simulation();
        simulation.startSimulation();
        if (MapVisual.getCycleCounter() == 100) {
            System.out.println("It's been 100 cycles already, time to stop");
            simulation.pauseSimulation();
        }
    }
}