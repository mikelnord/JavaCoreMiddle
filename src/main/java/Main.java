import java.util.*;

public class Main {

    public static ArrayList<String> createArray() {
        return new ArrayList<>(Arrays.asList("Москва", "Санкт-Петербург", "Санкт-Петербург", "Петербург", "Новосибирск", "Екатеринбург", "Нижний Новгород",
                "Казань", "Самара", "Омск", "Челябинск", "Ростов-на-Дону", "Ростов-на-Дону", "Уфа", "Волгоград", "Красноярск",
                "Пермь", "Воронеж", "Саратов", "Краснодар", "Санкт-Петербург"));
    }

    public static void checkUniq(List<String> list) {
        Set<String> set = new HashSet<>(list);
        System.out.println("Количество уникальных слов в массиве = " + set.size());
        for (String elem : set) {
            System.out.println(elem);
        }
    }

    public static void countVx(List<String> list) {
        Set<String> set = new HashSet<>(list);
        for (String elem : set) {
            System.out.println(elem + ": " + Collections.frequency(list, elem));
        }
    }

    public static void main(String[] args) {

        List<String> list = createArray();
        checkUniq(list);
        System.out.println("Количество элементов в массиве: ");
        countVx(list);

        TelephoneDirectory telephoneDirectory = new TelephoneDirectory();
        telephoneDirectory.add("Ivanov", "1234567890");
        telephoneDirectory.add("Sidorov", "1112223334");
        telephoneDirectory.add("Sidorov", "3332224445");
        telephoneDirectory.add("Petrov", "0987654321");
        telephoneDirectory.add("Sidorov", "1231234567");
        telephoneDirectory.get("Sidorov");
        telephoneDirectory.get("Ivanova");
        telephoneDirectory.get("Petrov");
        telephoneDirectory.displayTelephoneDirectory();

    }

}
