public class Treadmill implements Obstacle {
    private int length;

    public Treadmill(int length) {
        this.length = length;
    }

    @Override
    public void passage(Essence essence) {
        if (essence.getParticipant()) essence.running(length);
    }
}
