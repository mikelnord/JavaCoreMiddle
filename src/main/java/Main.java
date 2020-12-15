import java.util.Arrays;

public class Main {

    static final int size = 10000000;
    static final int h = size / 2;

    public static void metod1() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1f);
        long a = System.currentTimeMillis();
        fillArr(arr, 0);
        System.out.println("Время работы первого метода: " + (System.currentTimeMillis() - a));
    }

    public static void metod2() throws InterruptedException {
        float[] arr = new float[size];
        Arrays.fill(arr, 1f);
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);
        Runnable thread1 = () -> fillArr(arr1, 0);
        Runnable thread2 = () -> fillArr(arr2, h);
        Thread myThread1 = new Thread(thread1);
        Thread myThread2 = new Thread(thread2);
        myThread1.start();
        myThread2.start();
        myThread1.join();
        myThread2.join();
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        System.out.println("Время работы второго метода: " + (System.currentTimeMillis() - a));
    }

    public static void fillArr(float[] arr, int smech) {
        for (int j = 0; j < arr.length; j++) {
            int i = j + smech;
            arr[j] = (float) (arr[j] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void main(String[] args) {
        metod1();
        try {
            metod2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
