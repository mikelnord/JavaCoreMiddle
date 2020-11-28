public class Human implements Essence {
    private String Name;
    private int length;
    private int height;
    private boolean isParticipant;

    public Human(String name, int length, int height) {
        Name = name;
        this.length = length;
        this.height = height;
        this.isParticipant = true;
    }

    @Override
    public void running(int length) {
        if (length <= this.length)
            System.out.println("Human " + Name + " running!");
        else {
            if (isParticipant) isParticipant = false;
            System.out.println("Human " + Name + " not running!");
        }
    }

    @Override
    public void jump(int height) {
        if (height <= this.height)
            System.out.println("Human " + Name + " jump!");
        else {
            if (isParticipant) isParticipant = false;
            System.out.println("Human " + Name + " not jump!");
        }
    }

    @Override
    public boolean getParticipant() {
        return isParticipant;
    }

}
