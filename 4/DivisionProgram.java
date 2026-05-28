import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DivisionProgram {

    public static double divide(double a, double b) throws CustomDivisionException {
        if (b == 0) {
            throw new CustomDivisionException("Делить на ноль нельзя.");
        }
        return a / b;
    }

    public static void log(String text) {
        try (FileWriter writer = new FileWriter("logic.txt", true)) {
            writer.write(text + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите первое число: ");
            double a = Double.parseDouble(scanner.nextLine());

            System.out.print("Введите второе число: ");
            double b = Double.parseDouble(scanner.nextLine());

            System.out.println("Результат: " + divide(a, b));

        } catch (CustomDivisionException e) {
            System.out.println("Ошибка: " + e.getMessage());
            log(e.getClass().getSimpleName() + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введено не число.");
            log(e.getClass().getSimpleName() + ": введено не число.");
        } finally {
            scanner.close();
        }
    }
}