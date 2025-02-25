import java.util.HashSet;

public class PathNode {
    private Coordinates coordinates;
    private PathNode parent;

    public PathNode(Coordinates coordinates, PathNode parent) {
        this.coordinates = coordinates;
        this.parent = parent;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public PathNode getParent() {
        return parent;
    }

    public static HashSet<PathNode> getNeighbours(PathNode pathNode, Map map, Creature creature) {
        HashSet<PathNode> neighbours = new HashSet<>();

        Coordinates[] neighboursArray = new Coordinates[]{
                new Coordinates(pathNode.coordinates.getX() + 1, pathNode.coordinates.getY() + 1),
                new Coordinates(pathNode.coordinates.getX(), pathNode.coordinates.getY() + 1),
                new Coordinates(pathNode.coordinates.getX() - 1, pathNode.coordinates.getY() + 1),
                new Coordinates(pathNode.coordinates.getX() + 1, pathNode.coordinates.getY()),
                new Coordinates(pathNode.coordinates.getX() - 1, pathNode.coordinates.getY()),
                new Coordinates(pathNode.coordinates.getX() - 1, pathNode.coordinates.getY() - 1),
                new Coordinates(pathNode.coordinates.getX(), pathNode.coordinates.getY() - 1),
                new Coordinates(pathNode.coordinates.getX() + 1, pathNode.coordinates.getY() - 1),
        };

        for (Coordinates current : neighboursArray) {
            if (creature.getClass().equals(Herbivore.class)) {
                if ((current.getX() <= map.getCoordinates().getX() - 1 &&
                        current.getX() >= 0 &&
                        current.getY() <= map.getCoordinates().getY()- 1 &&
                        current.getY() >= 0 &&
                        map.getEntities().get(current) == null) ||
                        map.getEntities().get(current) instanceof Grass) {
                    neighbours.add(new PathNode(current, pathNode));
                }
            } else {
                if ((current.getX() <= map.getCoordinates().getX() - 1 &&
                        current.getX() >= 0 &&
                        current.getY() <= map.getCoordinates().getY() - 1 &&
                        current.getY() >= 0 &&
                        map.getEntities().get(current) == null) ||
                        map.getEntities().get(current) instanceof Herbivore) {
                    neighbours.add(new PathNode(current, pathNode));
                }
            }
        }
        return neighbours;
    }
}
