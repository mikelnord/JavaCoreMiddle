public class Main {
    public static void main(String[] args) {
        Essence[] essences = {new Human("Ivan Ivanovich", 2000, 150), new Cat("Vaska", 500, 200),
                new Robot("2134", 5000, 50), new Human("Petr Petrovich", 1000, 100)};

        Obstacle[] obstacles = {new Treadmill(1000), new Wall(100), new Treadmill(1500)};

        for (Obstacle obstacle : obstacles)
            for (Essence essence : essences)
                obstacle.passage(essence);
    }
}
