import java.util.Deque;
import java.util.HashMap;
import java.util.Random;

public class Predator extends Creature {
    private int damage;

    public Predator() {
    }

    public Predator(int speed, int health, Coordinates coordinates, int damage) {
        this.speed = speed;
        this.health = health;
        this.coordinates = coordinates;
        this.damage = damage;
    }

    @Override
    public void makeMove(Map map) {
//        Deque<Coordinates> herbivorePath = BFS(coordinates, map, new Predator());
        Deque<Coordinates> herbivorePath = A_STAR(coordinates, map, new Predator());
        String message = "";
        if (herbivorePath == null) {
            return;
        }

//        System.out.println("Координаты тигрули: " + coordinates +
//                ", путь до зайца: " + herbivorePath);

        Coordinates herbivoreCoords = herbivorePath.pollLast();
        Coordinates currentPredatorCoords = herbivorePath.pollFirst();
        Coordinates nextStep = coordinates;

        if (herbivorePath.isEmpty()) {
            attackHerbivore(map, herbivoreCoords);
            message = "Predator from " + currentPredatorCoords + " attacked herbivore from " + herbivoreCoords;
        } else if (herbivorePath.size() <= speed) {
            nextStep = herbivorePath.pollLast();
            message = "Predator moved from " + currentPredatorCoords + " to " + nextStep;
        } else {
            for (int i = 0; i < speed; i++) {
                nextStep = herbivorePath.pollFirst();
            }
            message = "Predator moved from " + currentPredatorCoords + " to " + nextStep;
        }

        for (HashMap.Entry<Coordinates, Creature> entry : map.getIterationCreatureMap().entrySet()) {
            if (entry.getKey().equals(currentPredatorCoords) && entry.getValue() instanceof Predator) {
                map.getEntities().put(nextStep, entry.getValue());
                coordinates = nextStep;
                if (currentPredatorCoords != coordinates) {
                    map.getEntities().remove(currentPredatorCoords);
                }
            }
        }
        System.out.println(message);
    }

    @Override
    public Predator getRandomEntity(Coordinates coordinates) {
        return new Predator(new Random().nextInt(1, 4), 1,
                coordinates, new Random().nextInt(1, 7));
    }

    private void attackHerbivore(Map map, Coordinates herbivoreCoordinates) {
        Herbivore herbivore = (Herbivore) map.getEntities().get(herbivoreCoordinates);
        if (herbivore.getHealth() <= damage) {
            map.getEntities().remove(herbivoreCoordinates);
        } else {
            herbivore.setHealth(herbivore.getHealth() - damage);
        }
    }
}
