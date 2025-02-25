import java.util.Random;

public class Rock extends Entity {
    private Random random;

    public Rock() {
    }

    public Rock(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Rock getRandomEntity(Coordinates coordinates) {
        return new Rock(coordinates);
    }
}
