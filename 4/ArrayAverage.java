import java.util.Scanner;

public class ArrayAverage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите размер массива: ");
            int size = Integer.parseInt(scanner.nextLine());

            int[] arr = new int[size];

            System.out.println("Введите элементы массива через пробел:");
            String[] values = scanner.nextLine().trim().split("\\s+");

            int sum = 0;

            for (int i = 0; i < size; i++) {
                arr[i] = Integer.parseInt(values[i]);
                sum += arr[i];
            }

            System.out.println("Среднее арифметическое: " + (double) sum / size);

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введено не число.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ошибка: введено меньше элементов, чем нужно.");
        } finally {
            scanner.close();
        }
    }
}