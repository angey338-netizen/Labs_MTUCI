# Лабораторная работа №8
## Аннотации, Stream API и java.util.concurrent

---

## Описание

Данная лабораторная работа посвящена изучению и практическому применению трёх ключевых механизмов языка Java: пользовательских аннотаций, функционального стиля обработки данных через Stream API и инструментов многопоточности из пакета `java.util.concurrent`. В ходе работы реализовано приложение, которое загружает данные из текстового файла, применяет к ним несколько независимых обработчиков параллельно и сохраняет результат в выходной файл. Работа направлена на развитие навыков проектирования расширяемых, потокобезопасных систем обработки данных с использованием метапрограммирования через Reflection API.

---

## Ключевые концепции

### Аннотации (Annotations)

Аннотация — это метаданные, прикреплённые к элементу программы (классу, методу, полю). Пользовательская аннотация объявляется через `@interface` и может содержать элементы-атрибуты:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataProcessor {
    String description() default "Обработчик данных";
}
```

- `@Retention(RetentionPolicy.RUNTIME)` — аннотация доступна во время выполнения программы (через Reflection).
- `@Target(ElementType.METHOD)` — аннотация применяется только к методам.
- `description()` — атрибут с дефолтным значением, позволяющий снабдить метод текстовым описанием.

### Reflection API

Reflection позволяет получить информацию о классах, методах и аннотациях во время выполнения программы. Используется для динамического обнаружения и вызова методов, помеченных аннотацией `@DataProcessor`:

```java
for (Method method : processor.getClass().getDeclaredMethods()) {
    if (method.isAnnotationPresent(DataProcessor.class)) {
        method.invoke(processor, data);
    }
}
```

### Stream API

Stream API предоставляет функциональный стиль обработки коллекций. Основные операции, использованные в работе:

| Метод | Описание |
|---|---|
| `.filter(predicate)` | Оставляет только элементы, удовлетворяющие условию |
| `.map(function)` | Преобразует каждый элемент |
| `.distinct()` | Удаляет дубликаты |
| `.sorted()` | Сортирует элементы |
| `.collect(Collectors.toList())` | Собирает результат в список |

### java.util.concurrent

Пакет предоставляет высокоуровневые инструменты для многопоточности:

- `ExecutorService` — пул потоков для параллельного выполнения задач.
- `Executors.newCachedThreadPool()` — создаёт пул, динамически расширяющийся под количество задач.
- `executor.awaitTermination(...)` — ожидает завершения всех запущенных задач.
- `CopyOnWriteArrayList` — потокобезопасный список для сбора результатов из нескольких потоков без явной синхронизации.

---

## Структура проекта

В репозитории представлены четыре файла, реализующие полный конвейер обработки данных:

- `DataProcessor.java` — пользовательская аннотация `@DataProcessor` для пометки методов обработки.
- `DataProcessors.java` — класс с тремя обработчиками данных: фильтрацией, трансформацией и агрегацией.
- `DataManager.java` — менеджер данных: регистрация обработчика, загрузка, многопоточная обработка, сохранение.
- `Main.java` — точка входа, демонстрирующая полный цикл работы приложения.

---

## Содержание заданий

### Задание 1: Пользовательская аннотация (`DataProcessor`)

**Описание**: Создана аннотация `@DataProcessor`, которая используется для пометки методов обработки данных и хранит их текстовое описание. Аннотация читается во время выполнения программы через Reflection API.

**Реализация**:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataProcessor {
    String description() default "Обработчик данных";
}
```

**Основная логика**:
- `@Retention(RetentionPolicy.RUNTIME)` обеспечивает доступность аннотации в `DataManager` через `method.isAnnotationPresent(DataProcessor.class)`.
- `@Target(ElementType.METHOD)` ограничивает применение только методами, предотвращая ошибочное использование на классах или полях.
- Атрибут `description` позволяет каждому методу-обработчику сообщать о своей задаче при запуске.

---

### Задание 2: Класс DataManager — загрузка, обработка, сохранение (`DataManager`)

**Описание**: Класс `DataManager` управляет всем жизненным циклом данных: регистрирует объект-обработчик, загружает строки из файла, запускает методы с аннотацией `@DataProcessor` параллельно через `ExecutorService` и сохраняет собранные результаты в выходной файл.

**Алгоритм метода `processData()`**:
```java
public void processData() throws Exception {
    ExecutorService executor = Executors.newCachedThreadPool();

    for (Method method : processor.getClass().getDeclaredMethods()) {
        if (method.isAnnotationPresent(DataProcessor.class)) {
            executor.execute(() -> {
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

    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.MINUTES);
    System.out.println("Обработка завершена. Строк в результате: " + results.size());
}
```

**Основная логика**:
- Через `getDeclaredMethods()` и `isAnnotationPresent()` метод автоматически находит все обработчики — добавление нового метода с `@DataProcessor` в `DataProcessors` подхватывается без изменения `DataManager`.
- Каждый найденный метод запускается в отдельном потоке через `executor.execute()`.
- Результаты собираются в `CopyOnWriteArrayList` — потокобезопасную коллекцию, не требующую явных блокировок.
- `executor.shutdown()` + `awaitTermination()` гарантируют, что `saveData()` вызывается только после завершения всех потоков.

**Алгоритм метода `loadData()`**:
```java
public void loadData(String source) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
        data = reader.lines().collect(Collectors.toList());
    }
    System.out.println("Загружено строк: " + data.size());
}
```

---

### Задание 3: Обработчики данных — фильтрация, трансформация, агрегация (`DataProcessors`)

**Описание**: Класс `DataProcessors` содержит три независимых метода обработки данных, каждый из которых помечен аннотацией `@DataProcessor`. Методы используют Stream API для лаконичной и выразительной обработки списков строк.

**Обработчик 1 — Фильтрация**:
```java
@DataProcessor(description = "Фильтрация коротких строк")
public List<String> filter(List<String> data) {
    return data.stream()
               .filter(line -> line.trim().length() >= 5)
               .collect(Collectors.toList());
}
```
Оставляет только строки длиной не менее 5 символов после удаления пробелов по краям.

**Обработчик 2 — Трансформация**:
```java
@DataProcessor(description = "Трансформация: trim + toUpperCase")
public List<String> transform(List<String> data) {
    return data.stream()
               .map(line -> line.trim().toUpperCase())
               .collect(Collectors.toList());
}
```
Убирает пробелы по краям каждой строки и переводит её в верхний регистр.

**Обработчик 3 — Агрегация**:
```java
@DataProcessor(description = "Агрегация: удаление дубликатов и сортировка")
public List<String> aggregate(List<String> data) {
    return data.stream()
               .distinct()
               .sorted()
               .collect(Collectors.toList());
}
```
Удаляет повторяющиеся строки и сортирует оставшиеся в алфавитном порядке.

---

### Точка входа (`Main`)

**Описание**: Класс `Main` демонстрирует полный цикл работы приложения в пяти последовательных шагах.

**Алгоритм**:
```java
public class Main {
    public static void main(String[] args) throws Exception {
        DataManager manager = new DataManager();

        manager.registerDataProcessor(new DataProcessors()); // 1. Регистрация обработчика

        manager.loadData("input.txt");    // 2. Загрузка данных

        manager.processData();            // 3. Параллельная обработка

        manager.saveData("output.txt");   // 4. Сохранение результата
    }
}
```

**Пример вывода в консоль**:
```
Зарегистрирован: DataProcessors

Загружено строк: 10

[pool-1-thread-1] Фильтрация коротких строк
[pool-1-thread-2] Трансформация: trim + toUpperCase
[pool-1-thread-3] Агрегация: удаление дубликатов и сортировка
Обработка завершена. Строк в результате: 27

Сохранено в: output.txt

=== Готово! ===
```

---

## Запуск программы

### Подготовка входного файла:

Создайте файл `input.txt` в той же директории, что и скомпилированные классы. Файл должен содержать произвольный текст — по одной строке на каждую запись:
```
apple
banana
Hi
orange
banana
apple
kiwi
ok
strawberry
```

### Компиляция всех файлов:
```bash
javac DataProcessor.java DataProcessors.java DataManager.java Main.java
```

Или все сразу:
```bash
javac *.java
```

### Запуск:
```bash
java Main
```

После выполнения в директории появится файл `output.txt` с результатами всех трёх обработчиков.

---

## Важные замечания

### Порядок компиляции

Аннотация `DataProcessor` должна быть скомпилирована первой, так как от неё зависят `DataProcessors` и `DataManager`. При компиляции через `javac *.java` компилятор разрешает зависимости автоматически.

### CopyOnWriteArrayList вместо ArrayList

Результаты из параллельных потоков записываются в `CopyOnWriteArrayList`. Использование обычного `ArrayList` в многопоточной среде приводит к `ConcurrentModificationException` или потере данных, поскольку `ArrayList` не является потокобезопасным.

### Lambda и checked exceptions

Внутри `executor.execute(() -> { ... })` нельзя напрямую пробрасывать проверяемые исключения (`checked exceptions`). Поэтому вызов `method.invoke(...)` обёрнут в `try-catch`, а исключение обрабатывается локально внутри потока.

### Расширяемость через аннотации

Архитектура построена так, что для добавления нового обработчика достаточно написать новый метод с аннотацией `@DataProcessor` в классе `DataProcessors`. Изменять `DataManager` или `Main` не нужно — Reflection API обнаружит новый метод автоматически.

### awaitTermination и корректное завершение

`executor.shutdown()` прекращает приём новых задач, но не прерывает выполняющиеся потоки. Последующий вызов `awaitTermination(1, TimeUnit.MINUTES)` блокирует главный поток до завершения всех задач или истечения таймаута, гарантируя корректный порядок: сначала обработка, затем сохранение.

---

## Дополнительные возможности для расширения

- Добавить в аннотацию `@DataProcessor` атрибут `order` для задания приоритета выполнения обработчиков.
- Реализовать поддержку нескольких входных файлов в методе `loadData()` с параллельной загрузкой через `ExecutorService`.
- Расширить `DataManager` методом `getStatistics()`, который с помощью Stream API выводит количество строк до и после каждого обработчика.
- Добавить обработчик на основе регулярных выражений, фильтрующий строки по заданному шаблону.
- Реализовать логирование каждого шага обработки в отдельный файл с указанием имени потока и временной метки.
- Использовать `CompletableFuture` вместо `ExecutorService` для более гибкой цепочки асинхронных обработчиков.
- Добавить возможность передавать параметры конфигурации обработчиков через атрибуты аннотации `@DataProcessor`.
