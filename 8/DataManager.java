import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

// DataManager - управляет данными: загрузка, обработка, сохранение
public class DataManager {

    private Object processor;                                    // объект-обработчик данных
    private List<String> data = new ArrayList<>();               // исходные данные из файла
    private List<String> results = new CopyOnWriteArrayList<>(); // результаты (потокобезопасный список)

    // 1. Регистрация объекта-обработчика
    public void registerDataProcessor(Object processor) {
        this.processor = processor;
        System.out.println("Зарегистрирован: " + processor.getClass().getSimpleName());
    }

    // 2. Загрузка данных из текстового файла
    public void loadData(String source) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            data = reader.lines().collect(Collectors.toList());
        }
        System.out.println("Загружено строк: " + data.size());
    }

    // 3. Многопоточная обработка: находим @DataProcessor методы через Reflection и запускаем параллельно
    @SuppressWarnings("unchecked")
    public void processData() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (Method method : processor.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(DataProcessor.class)) {

                executor.execute(() -> {  // запускаем каждый метод в отдельном потоке
                    try {
                        System.out.println("[" + Thread.currentThread().getName() + "] "
                                + method.getAnnotation(DataProcessor.class).description());
                        List<String> methodResult = (List<String>) method.invoke(processor, data);
                        results.addAll(methodResult);
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }
                });

            }
        }

        executor.shutdown();                             // больше не принимаем задачи
        executor.awaitTermination(1, TimeUnit.MINUTES); // ждём завершения всех потоков
        System.out.println("Обработка завершена. Строк в результате: " + results.size());
    }

    // 4. Сохранение результатов в файл
    public void saveData(String destination) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {
            for (String line : results) {
                writer.write(line);
                writer.newLine();
            }
        }
        System.out.println("Сохранено в: " + destination);
    }
}
