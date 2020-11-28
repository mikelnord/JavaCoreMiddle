public class Robot implements Essence {
    private String id;
    private int length;
    private int height;
    private boolean isParticipant;

    public Robot(String id, int length, int height) {
        this.id = id;
        this.length = length;
        this.height = height;
        this.isParticipant = true;
    }

    @Override
    public void running(int length) {
        if (length <= this.length)
            System.out.println("Robot " + id + " running!");
        else {
            if (isParticipant) isParticipant = false;
            System.out.println("Robot " + id + " not running!");
        }
    }

    @Override
    public void jump(int height) {
        if (height <= this.height)
            System.out.println("Robot " + id + " jump!");
        else {
            if (isParticipant) isParticipant = false;
            System.out.println("Robot " + id + " not jump!");
        }
    }

    @Override
    public boolean getParticipant() {
        return isParticipant;
    }

}
