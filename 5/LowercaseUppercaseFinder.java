import java.util.Scanner;

public class LowercaseUppercaseFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите текст:");
        String text = scanner.nextLine();

        try {
            String result = text.replaceAll("([a-z])([A-Z])", "!$1$2!");
            System.out.println("Результат:");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}

