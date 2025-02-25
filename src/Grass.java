public class Grass extends Entity {
    public Grass() {
    }

    public Grass(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public Grass getRandomEntity(Coordinates coordinates) {
        return new Grass(coordinates);
    }
}
