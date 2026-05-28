public class SimpleTask1ArraySum {

    static int sum1 = 0;
    static int sum2 = 0;

    public static void main(String[] args) throws InterruptedException {
        int[] numbers = {8, 4, 1, 9, 3, 5, 8, 6, 10, 11};

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < numbers.length / 2; i++) {
                sum1 += numbers[i];
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = numbers.length / 2; i < numbers.length; i++) {
                sum2 += numbers[i];
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Сумма первой половины: " + sum1);
        System.out.println("Сумма второй половины: " + sum2);
        System.out.println("Общая сумма: " + (sum1 + sum2));
    }
}
