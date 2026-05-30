import java.util.List;
import java.util.stream.Collectors;

// Класс содержит ВСЕ обработчики данных в одном месте
// Каждый метод - отдельный тип обработки, помеченный @DataProcessor
public class DataProcessors {

    // --- ОБРАБОТЧИК 1: Фильтрация ---
    // Оставляет только строки длиной 5 и более символов
    @DataProcessor(description = "Фильтрация коротких строк")
    public List<String> filter(List<String> data) {
        return data.stream()
                   .filter(line -> line.trim().length() >= 5)  // условие: длина >= 5
                   .collect(Collectors.toList());
    }

    // --- ОБРАБОТЧИК 2: Трансформация ---
    // Убирает пробелы по краям и переводит в верхний регистр
    @DataProcessor(description = "Трансформация: trim + toUpperCase")
    public List<String> transform(List<String> data) {
        return data.stream()
                   .map(line -> line.trim().toUpperCase())  // trim() + toUpperCase() для каждой строки
                   .collect(Collectors.toList());
    }

    // --- ОБРАБОТЧИК 3: Агрегация ---
    // Убирает дубликаты и сортирует
    @DataProcessor(description = "Агрегация: удаление дубликатов и сортировка")
    public List<String> aggregate(List<String> data) {
        return data.stream()
                   .distinct()  // убрать повторения
                   .sorted()    // отсортировать
                   .collect(Collectors.toList());
    }
}
