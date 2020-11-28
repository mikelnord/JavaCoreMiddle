public class Cat implements Essence {
    private String Name;
    private int length;
    private int height;
    private boolean isParticipant;

    public Cat(String name, int length, int height) {
        Name = name;
        this.length = length;
        this.height = height;
        this.isParticipant = true;
    }

    @Override
    public void running(int length) {
        if (length <= this.length)
            System.out.println("Cat " + Name + " running!");
        else {
            if (isParticipant) isParticipant = false;
            System.out.println("Cat " + Name + " not running!");
        }
    }

    @Override
    public void jump(int height) {
        if (height <= this.height)
            System.out.println("Cat " + Name + " jump!");
        else {
            if (isParticipant) isParticipant = false;
            System.out.println("Cat " + Name + " not running!");
        }
    }

    @Override
    public boolean getParticipant() {
        return isParticipant;
    }

}
