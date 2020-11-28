public class Wall implements Obstacle {
    private int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void passage(Essence essence) {
        if (essence.getParticipant()) essence.jump(height);
    }
}
