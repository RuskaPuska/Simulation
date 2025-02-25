import java.util.Random;

public class Tree extends Entity {
    private Random random;

    public Tree() {
    }

    public Tree(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Tree getRandomEntity(Coordinates coordinates) {
        return new Tree(coordinates);
    }
}
