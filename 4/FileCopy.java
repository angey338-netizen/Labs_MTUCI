import java.io.*;

public class FileCopy {
    public static void main(String[] args) {
        FileReader reader = null;
        FileWriter writer = null;

        try {
            reader = new FileReader("source.txt"); // Откуда читаем
            writer = new FileWriter("recording.txt"); // Куда пишем

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c); // Пишем байт за байтом
            }

            System.out.println("Файл скопирован.");

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден.");
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом.");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии входного файла.");
            }

            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии выходного файла.");
            }
        }
    }
}
