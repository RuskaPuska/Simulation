import java.util.Arrays;

public class MapVisual {
    private Map map;
    private String[][] mapVisualized;

    private static int cycleCounter = 1;
    public static int HERBIVORE_COUNTER;
    public static int PREDATOR_COUNTER;
    public static int GRASS_COUNTER;
    public static int ROCK_COUNTER;
    public static int TREE_COUNTER;

    public MapVisual(Map map) {
        this.map = map;
        mapVisualized = new String[map.getCoordinates().getX() + 1][map.getCoordinates().getY() + 1];
    }

    public static int getCycleCounter() {
        return cycleCounter;
    }

    public void render() {
        HERBIVORE_COUNTER = 0;
        PREDATOR_COUNTER = 0;
        GRASS_COUNTER = 0;
        ROCK_COUNTER = 0;
        TREE_COUNTER = 0;

        for (String[] lines : mapVisualized) {
            Arrays.fill(lines, ".");
        }

        for (Entity entity : map.getEntities().values()) {
            int x = entity.coordinates.getX();
            int y = entity.coordinates.getY();
            if (entity instanceof Herbivore) {
                mapVisualized[x][y] = "\uD83D\uDC30";
                HERBIVORE_COUNTER++;
            } else if (entity instanceof Predator) {
                mapVisualized[x][y] = "\uD83E\uDD81";
                PREDATOR_COUNTER++;
            } else if (entity instanceof Grass) {
                mapVisualized[x][y] = "\uD83C\uDF31";
                GRASS_COUNTER++;
            } else if (entity instanceof Rock) {
                mapVisualized[x][y] = "\uD83D\uDDFF";
                ROCK_COUNTER++;
            } else if (entity instanceof Tree) {
                mapVisualized[x][y] = "\uD83C\uDF33";
                TREE_COUNTER++;
            }
        }

        System.out.println("Herbivores: " + HERBIVORE_COUNTER);
        System.out.println("Predators: " + PREDATOR_COUNTER);
        System.out.println("Grass: " + GRASS_COUNTER);
        System.out.println("Rocks: " + ROCK_COUNTER);
        System.out.println("Trees: " + TREE_COUNTER);

        for (int i = map.getCoordinates().getY() - 1; i >= 0; i--) {
            for (int j = 0; j < map.getCoordinates().getX(); j++) {
                System.out.format("%-5s", mapVisualized[j][i]);
            }
            System.out.println("\n");
        }
    }

    public void showMap() throws InterruptedException {
        render();
        Thread.sleep(Simulation.TIMER);
    }

    public void nextCycle() throws InterruptedException {
        showMap();
        System.out.println("Cycle " + ++cycleCounter);
    }
}