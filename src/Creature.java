import java.util.*;

public abstract class Creature extends Entity {
    protected int speed;
    protected int health;

    public abstract void makeMove(Map map);

    protected static Deque<Coordinates> BFS(Coordinates coordinates, Map map, Creature creature) {
        Queue<PathNode> queue = new ArrayDeque<>();
        List<Coordinates> visited = new ArrayList<>();
        PathNode endPathNode;
        Deque<Coordinates> path = new ArrayDeque<>();

        queue.offer(new PathNode(coordinates, null));

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();
            if ((creature.getClass().equals(Herbivore.class) && map.getEntities().get(current.getCoordinates()) instanceof Grass) ||
                    (creature.getClass().equals(Predator.class) && map.getEntities().get(current.getCoordinates()) instanceof Herbivore)) {
                endPathNode = new PathNode(current.getCoordinates(), current.getParent());

                while (endPathNode != null) {
                    path.offerFirst(endPathNode.getCoordinates());
                    endPathNode = endPathNode.getParent();
                }
                return path;
            }

            visited.add(current.getCoordinates());
            HashSet<PathNode> neighbours = PathNode.getNeighbours(current, map, creature);

            for (PathNode node : neighbours) {
                if (!visited.contains(node.getCoordinates())) {
                    queue.offer(node);
                    visited.add(node.getCoordinates());
                }
            }
        }
        return null;
    }

//    private static Deque<Coordinates> reversePath(Deque<Coordinates> queue) {
//        Stack<Coordinates> stack = new Stack<>();
//        while (!queue.isEmpty()) {
//            stack.push(queue.poll());
//        }
//        while (!stack.isEmpty()) {
//            queue.offer(stack.pop());
//        }
//        return queue;
//    }

    protected static Deque<Coordinates> A_STAR(Coordinates currentCoordinates, Map map, Creature creature) {
        Deque<PathNode> queue = new ArrayDeque<>();
        List<Coordinates> visited = new ArrayList<>();
        Coordinates targetCoordinates = getClosestTarget(currentCoordinates, map, creature);
        double heuristic = heuristicFunction(currentCoordinates, targetCoordinates);
        Deque<Coordinates> path = new ArrayDeque<>();

        queue.add(new PathNode(currentCoordinates, null));

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();
            while (heuristic != heuristicFunction(current.getCoordinates(), targetCoordinates)) {
                current = queue.poll();
            }

            if ((creature instanceof Herbivore && map.getEntities().get(current.getCoordinates()) instanceof Grass) ||
                    creature instanceof Predator && map.getEntities().get(current.getCoordinates()) instanceof Herbivore) {
                PathNode targetPathNode = new PathNode(current.getCoordinates(), current.getParent());

                while (targetPathNode != null) {
                    path.offerFirst(targetPathNode.getCoordinates());
                    targetPathNode = targetPathNode.getParent();
                }
                return path;
            }

            visited.add(currentCoordinates);
            HashSet<PathNode> neighbours = PathNode.getNeighbours(current, map, creature);

            for (PathNode node : neighbours) {
                if (!visited.contains(node.getCoordinates())) {
                    if (heuristicFunction(node.getCoordinates(), targetCoordinates) < heuristic) {
                        heuristic = heuristicFunction(node.getCoordinates(), targetCoordinates);
                        queue.add(node);
                        visited.add(node.getCoordinates());
                    }
                }
            }
        }
        return null;
    }

    private static double heuristicFunction(Coordinates startCoordinates, Coordinates endCoordinates) {
        double targetDistance = Double.MAX_VALUE;
        int xDifference = Math.abs(Math.subtractExact(startCoordinates.getX(), endCoordinates.getX()));
        int yDifference = Math.abs(Math.subtractExact(startCoordinates.getY(), endCoordinates.getY()));
        if (targetDistance > (Math.hypot(xDifference, yDifference))) {
            targetDistance = Math.hypot(xDifference, yDifference);
        }
        return targetDistance;
    }

    public static Coordinates getClosestTarget(Coordinates coordinates, Map map, Creature creature) {
        double minTargetDistance = Double.MAX_VALUE;
        int targetXCoord = 0;
        int targetYCoord = 0;
        for (Entity entity : map.getEntities().values()) {
            if (creature instanceof Herbivore && entity instanceof Grass grass) {
                int xDifference = Math.abs(Math.subtractExact(coordinates.getX(), grass.getCoordinates().getX()));
                int yDifference = Math.abs(Math.subtractExact(coordinates.getY(), grass.getCoordinates().getY()));
                if (minTargetDistance > (Math.hypot(xDifference, yDifference))) {
                    minTargetDistance = Math.hypot(xDifference, yDifference);
                    targetXCoord = grass.getCoordinates().getX();
                    targetYCoord = grass.getCoordinates().getY();
                }
            } else if (creature instanceof Predator && entity instanceof Herbivore herbivore) {
                int xDifference = Math.abs(Math.subtractExact(coordinates.getX(), herbivore.getCoordinates().getX()));
                int yDifference = Math.abs(Math.subtractExact(coordinates.getY(), herbivore.getCoordinates().getY()));
                if (minTargetDistance > (Math.hypot(xDifference, yDifference))) {
                    minTargetDistance = Math.hypot(xDifference, yDifference);
                    targetXCoord = herbivore.getCoordinates().getX();
                    targetYCoord = herbivore.getCoordinates().getY();
                }
            }
        }
        return new Coordinates(targetXCoord, targetYCoord);
    }
}
