public class Main {
    public static int workWithArray(String[][] strArray, int lenVn, int lenMas) throws MyArraySizeException, MyArrayDataException {
        if (strArray.length != lenVn) throw new MyArraySizeException("Неверный размер массива");
        int sum = 0, num = 0;

        for (String[] arr : strArray) {
            if (arr.length != lenMas) throw new MyArraySizeException("Неверный размер массива");
            for (int i = 0; i < arr.length; i++) {
                try {
                    int x = Integer.parseInt(arr[i]);
                    sum += x;
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Неверные данные массива по адресу [" + num + "]" + "[" + i + "]");
                }
            }
            num++;
        }
        return sum;
    }

    public static void main(String[] args) {
        String[][] arr = {{"0", "1", "2", "3"}, {"0", "1", "2", "3"}, {"0", "1", "2", "3"}, {"0", "1", "2", "3"}};
        try {
            System.out.println(workWithArray(arr, 4, 4));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }
}
