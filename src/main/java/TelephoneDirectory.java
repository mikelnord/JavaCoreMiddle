import java.util.ArrayList;
import java.util.List;

public class TelephoneDirectory {

    private static class TelephoneRecord {
        private final String name;
        private final String telephoneNumber;

        public TelephoneRecord(String name, String telephoneNumber) {
            this.name = name;
            this.telephoneNumber = telephoneNumber;
        }

        @Override
        public String toString() {
            return "{name= " + name + ", telephoneNumber='" + telephoneNumber + '}';
        }
    }

    private final List<TelephoneRecord> list;

    public TelephoneDirectory() {
        this.list = new ArrayList<>();
    }

    public void add(String name, String telephoneNumber) {
        list.add(new TelephoneRecord(name, telephoneNumber));
    }

    public void displayTelephoneDirectory() {
        for (TelephoneRecord telR : list) {
            System.out.println(telR);
        }
    }

    public void get(String name) {
        List<String> listTelephone = new ArrayList<>();
        for (TelephoneRecord telR : list) {
            if ((telR.name).equals(name)) listTelephone.add(telR.telephoneNumber);
        }
        if (listTelephone.size() == 0) {
            System.out.println("Для заданного имени " + name + " не найдены телефонные номера");
        } else {
            System.out.println("Телефоны " + name + ": ");
            for (String telephone : listTelephone) {
                System.out.println("             " + telephone);
            }
        }
    }
}
