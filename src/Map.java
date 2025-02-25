import java.util.HashMap;
import java.util.Random;

public class Map {
    private Coordinates coordinates;
    private HashMap<Coordinates, Entity> entities = new HashMap<>();
    private HashMap<Coordinates, Creature> iterationCreatureMap = new HashMap<>();

    public Map() {
        this.coordinates = Simulation.MAP_COORDINATES;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public HashMap<Coordinates, Creature> getIterationCreatureMap() {
        return iterationCreatureMap;
    }

    public HashMap<Coordinates, Entity> getEntities() {
        return entities;
    }

    public Coordinates getRandomCoordinates() {
        return new Coordinates(new Random().nextInt(coordinates.getX()),
                new Random().nextInt(coordinates.getY()));
    }

    public Entity putEntityOnRandomCoords(HashMap<Coordinates, Entity> entityList, Entity entity) {
        Coordinates coordinates = getRandomCoordinates();
        Entity randomEntity = entity.getRandomEntity(coordinates);
        Entity putEntity = entityList.putIfAbsent(coordinates, randomEntity);
        while (putEntity != null) {
            coordinates = getRandomCoordinates();
            randomEntity = entity.getRandomEntity(coordinates);
            putEntity = entityList.putIfAbsent(coordinates, randomEntity);
        }
        return randomEntity;
    }

    public void addEntity(Entity entity, int number) {
        for (int i = 0; i < number; i++) {
            putEntityOnRandomCoords(entities, entity);
        }
    }
}
