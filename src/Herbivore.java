import java.util.Deque;
import java.util.Random;

public class Herbivore extends Creature {
    public Herbivore() {
    }

    public Herbivore(int speed, int health, Coordinates coordinates) {
        this.speed = speed;
        this.health = health;
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void makeMove(Map map) {
//        Deque<Coordinates> grassPath = BFS(coordinates, map, new Herbivore());
        String message = "";
        Deque<Coordinates> grassPath = A_STAR(coordinates, map, new Herbivore());
        if (grassPath == null) {
            return;
        }

        Coordinates grassCoords = grassPath.pollLast();
        Coordinates currentHerbivoreCoords = grassPath.pollFirst();
        Coordinates nextStep = null;

//        System.out.println("Координаты зайца: " + coordinates +
//                ", путь до травки: " + grassPath);

        if (grassPath.isEmpty()) {
            nextStep = grassCoords;
            message = "Herbivore from " + currentHerbivoreCoords + " ate grass";
        } else if (grassPath.size() <= speed) {
            nextStep = grassPath.pollLast();
            message = "Herbivore moved from " + currentHerbivoreCoords + " to " + nextStep;
        } else {
            for (int i = 0; i < speed; i++) {
                nextStep = grassPath.pollFirst();
            }
            message = "Herbivore moved from " + currentHerbivoreCoords + " to " + nextStep;
        }

        for (java.util.Map.Entry<Coordinates, Creature> entry : map.getIterationCreatureMap().entrySet()) {
            if (entry.getKey().equals(currentHerbivoreCoords) && entry.getValue() instanceof Herbivore) {
                map.getEntities().put(nextStep, entry.getValue());
                coordinates = nextStep;
                map.getEntities().remove(currentHerbivoreCoords);
            }
        }
        System.out.println(message);
    }

    @Override
    public Herbivore getRandomEntity(Coordinates coordinates) {
        return new Herbivore(new Random().nextInt(2, 6),
                new Random().nextInt(2, 7), coordinates);
    }
}
// УБРАННАЯ РЕАЛИЗАЦИЯ BFS В КЛАСС CREATURE
//    private Deque<Coordinates> BFS(Map map) {
//        Coordinates startNode = coordinates;
//        Queue<PathNode> queue = new ArrayDeque<>();
//        List<Coordinates> visited = new ArrayList<>();
//        PathNode endPathNode = null;
//        Deque<Coordinates> path = new ArrayDeque<>();
//
//        queue.offer(new PathNode(startNode, null));
//
//        while (!queue.isEmpty()) {
//            PathNode current = queue.poll();
//
//            if (map.entities.get(current.coordinates) instanceof Grass) {
//                System.out.println("Нашел травку " + current.coordinates);
//                endPathNode = new PathNode(current.coordinates, current.parent);
//
//                while (endPathNode != null) {
//                    path.offer(endPathNode.coordinates);
//                    endPathNode = endPathNode.parent;
//                }
//                return reversePath(path);
//            }
//
//            visited.add(current.coordinates);
//            HashSet<PathNode> neighbours = PathNode.getHerbivoreNeighbours(current, map);
//
//            for (PathNode node : neighbours) {
//                if (!visited.contains(node.coordinates)) {
//                    queue.offer(node);
//                    visited.add(node.coordinates);
//                }
//            }
//        }
//        return null;
//  ПРОШЛЫЙ КОД
//        double minGrassDistance = Double.MAX_VALUE;
//        int grassXCoord = 0;
//        int grassYCoord = 0;
//        for (Entity entity : map.entities.values()) {
//            if (entity instanceof Grass) {
//                int xDifference = Math.abs(Math.subtractExact(coordinates.x, entity.coordinates.x));
//                int yDifference = Math.abs(Math.subtractExact(coordinates.y, entity.coordinates.y));
//                if (minGrassDistance > (Math.hypot(xDifference, yDifference))) {
//                    minGrassDistance = Math.hypot(xDifference, yDifference);
//                    grassXCoord = entity.coordinates.x;
//                    grassYCoord = entity.coordinates.y;
//                }
//            }
//        }
//        System.out.println("Координаты ближайшей травки: " + grassXCoord + ", " + grassYCoord);
//
//            Coordinates endNode = new Coordinates(grassXCoord, grassYCoord);
//    }
//
//    private Deque<Coordinates> reversePath(Deque<Coordinates> queue) {
//        Stack<Coordinates> stack = new Stack<>();
//        while (!queue.isEmpty()) {
//            stack.push(queue.poll());
//        }
//        while (!stack.isEmpty()) {
//            queue.offer(stack.pop());
//        }
//        return queue;
//    }