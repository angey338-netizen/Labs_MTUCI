# Лабораторная работа №6
## Коллекции и дженерики в Java

---

## Описание

Данная лабораторная работа посвящена изучению каркаса коллекций Java Collections Framework, а также практическому применению дженериков (Generics) для создания универсальных структур данных. В ходе работы реализованы три программы: анализ частоты слов в текстовом файле с использованием `Map`, обобщённый стек на основе массива, и система учёта продаж товаров с применением потокобезопасной коллекции `ConcurrentHashMap`. Работа направлена на развитие навыков выбора подходящей коллекции под конкретную задачу и написания типобезопасного обобщённого кода.

---

## Ключевые концепции

### Коллекции (Collections Framework)

Коллекции — это классы и интерфейсы Java для хранения и управления группами объектов. Основные интерфейсы:

- **`Collection`** — базовый интерфейс для всех коллекций.
- **`List`** — упорядоченная коллекция с доступом по индексу, допускает дубликаты (`ArrayList`, `LinkedList`).
- **`Set`** — неупорядоченная коллекция уникальных элементов (`HashSet`, `TreeSet`).
- **`Map`** — хранилище пар «ключ-значение», ключи уникальны (`HashMap`, `TreeMap`, `ConcurrentHashMap`).

### Дженерики (Generics)

Дженерики позволяют создавать классы и методы, работающие с любым типом данных, при этом сохраняя типобезопасность на этапе компиляции:

```java
public class Stack<T> {
    private T[] data;
    // T — параметр типа, подставляется при создании объекта
}

Stack<Integer> intStack = new Stack<>(10);
Stack<String> strStack = new Stack<>(5);
```

### HashMap

`HashMap` — реализация `Map` на основе хэш-таблицы. Обеспечивает добавление, поиск и удаление элементов за константное время O(1) в среднем. Порядок элементов не гарантируется:

```java
Map<String, Integer> map = new HashMap<>();
map.put("apple", 5);
map.get("apple");        // 5
map.containsKey("apple"); // true
```

### ConcurrentHashMap

`ConcurrentHashMap` — потокобезопасная реализация `Map`. В отличие от `HashMap`, допускает одновременный доступ из нескольких потоков без явной синхронизации. Метод `putIfAbsent()` атомарно добавляет элемент, только если его ещё нет:

```java
ConcurrentHashMap<String, ProductSales> sales = new ConcurrentHashMap<>();
sales.putIfAbsent(name, new ProductSales(name, price));
```

### AtomicInteger

`AtomicInteger` — потокобезопасный целочисленный счётчик из пакета `java.util.concurrent.atomic`. Метод `incrementAndGet()` атомарно увеличивает значение на 1 и возвращает новый результат, исключая состояние гонки при многопоточном доступе:

```java
AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet(); // 1
count.get();             // 1
```

### Iterator и обход коллекций

Коллекции можно обходить расширенным циклом `for-each`, который внутри использует `Iterator`:

```java
for (ProductSales product : sales.values()) {
    System.out.println(product.getName());
}
```

### Сортировка через Comparator

`Collections.sort()` принимает кастомный `Comparator` для определения порядка сортировки. Для сортировки по убыванию числового значения:

```java
Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
    @Override
    public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
        return b.getValue() - a.getValue(); // убывание
    }
});
```

---

## Структура проекта

В репозитории представлены шесть файлов, реализующих три независимых задания:

- `TopWords.java` — анализ частоты слов в текстовом файле, вывод топ-10 самых частых.
- `Stack.java` — обобщённый класс стека на основе массива с дженериком `<T>`.
- `StackDemo.java` — демонстрация работы класса `Stack`.
- `ProductSales.java` — класс, описывающий товар с потокобезопасным счётчиком продаж.
- `SalesTracker.java` — класс системы учёта продаж на основе `ConcurrentHashMap`.
- `SalesDemo.java` — демонстрация работы системы учёта продаж.

---

## Содержание заданий

### Задание 1: Анализ частоты слов (`TopWords`)

**Описание**: Программа считывает текстовый файл, подсчитывает частоту каждого слова и выводит топ-10 наиболее часто встречающихся слов в порядке убывания.

**Используемые коллекции**:
- `HashMap<String, Integer>` — хранение пар «слово — количество вхождений».
- `ArrayList` — список записей `Map.Entry` для последующей сортировки.

**Предварительная обработка слов**:
- Каждое слово приводится к нижнему регистру через `toLowerCase()`.
- Из слова удаляются все небуквенные символы (знаки препинания, цифры) с помощью `replaceAll("[^a-zA-Z]", "")`.
- Пустые строки после очистки пропускаются через `continue`.

**Алгоритм подсчёта частоты**:
```java
while (scanner.hasNext()) {
    String word = scanner.next().toLowerCase();
    word = word.replaceAll("[^a-zA-Z]", "");

    if (word.isEmpty()) {
        continue;
    }

    if (wordMap.containsKey(word)) {
        wordMap.put(word, wordMap.get(word) + 1); // увеличиваем счётчик
    } else {
        wordMap.put(word, 1); // первое вхождение
    }
}
```

**Сортировка по убыванию частоты**:
```java
List<Map.Entry<String, Integer>> list = new ArrayList<>(wordMap.entrySet());

Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
    @Override
    public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
        return b.getValue() - a.getValue(); // b - a для убывания
    }
});
```

**Вывод топ-10**:
```java
for (int i = 0; i < 10 && i < list.size(); i++) {
    System.out.println((i + 1) + ". " + list.get(i).getKey() + " - " + list.get(i).getValue());
}
```
Условие `i < list.size()` защищает от ошибки, если в файле менее 10 уникальных слов.

**Обработка ошибок**:
- `FileNotFoundException` — если файл `ttt.txt` не найден, выводится сообщение «Файл не найден!» и программа завершается через `return`.

**Пример вывода**:
```
Топ-10 самых частых слов:
1. the - 47
2. and - 35
3. of - 28
4. to - 24
5. a - 21
6. in - 18
7. is - 15
8. that - 13
9. it - 11
10. for - 10
```

**Пример ошибки — файл не найден**:
```
Файл не найден!
```

---

### Задание 2: Обобщённый стек (`Stack`, `StackDemo`)

**Описание**: Реализован класс `Stack<T>` — универсальный стек на основе массива фиксированной ёмкости. Стек работает по принципу LIFO (Last In — First Out): последний добавленный элемент извлекается первым.

**Класс `Stack<T>`**:

```java
public class Stack<T> {
    private T[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public Stack(int capacity) {
        data = (T[]) new Object[capacity]; // приведение типа через Object[]
        size = 0;
    }
}
```

Аннотация `@SuppressWarnings("unchecked")` подавляет предупреждение компилятора о непроверяемом приведении типа, которое неизбежно при создании обобщённого массива в Java.

**Метод `push()` — добавление элемента**:
```java
public void push(T element) {
    if (size == data.length) {
        throw new IllegalStateException("В стеке больше нет места.");
    }
    data[size] = element;
    size++;
}
```
- Перед добавлением проверяется, не заполнен ли стек.
- При переполнении выбрасывается `IllegalStateException`.
- Новый элемент помещается на позицию `size`, затем `size` увеличивается.

**Метод `pop()` — извлечение элемента**:
```java
public T pop() {
    if (size == 0) {
        throw new IllegalStateException("Стек пуст.");
    }
    size--;
    T element = data[size];
    data[size] = null; // обнуляем ссылку для garbage collector
    return element;
}
```
- Перед извлечением проверяется, не пуст ли стек.
- `size` уменьшается, элемент сохраняется, ссылка в массиве обнуляется.
- Обнуление `null` важно для корректной работы сборщика мусора (GC).

**Метод `peek()` — просмотр верхнего элемента**:
```java
public T peek() {
    if (size == 0) {
        throw new IllegalStateException("Стек пуст.");
    }
    return data[size - 1];
}
```
- Возвращает верхний элемент **без его удаления**.
- Индекс верхнего элемента всегда равен `size - 1`.

**Демонстрация работы (`StackDemo`)**:
```java
Stack<Integer> stack = new Stack<>(10);

stack.push(1);
stack.push(2);
stack.push(3);

System.out.println(stack.pop());  // 3 — LIFO: последний вошёл, первый вышел
System.out.println(stack.peek()); // 2 — просмотр без удаления

stack.push(4);

System.out.println(stack.pop());  // 4
```

**Пример вывода**:
```
3
2
4
```

**Пример ошибки — переполнение стека**:
```java
Stack<Integer> stack = new Stack<>(2);
stack.push(1);
stack.push(2);
stack.push(3); // IllegalStateException: В стеке больше нет места.
```

**Пример ошибки — извлечение из пустого стека**:
```java
Stack<Integer> stack = new Stack<>(5);
stack.pop(); // IllegalStateException: Стек пуст.
```

---

### Задание 3: Система учёта продаж — Вариант 4 (`ProductSales`, `SalesTracker`, `SalesDemo`)

**Описание**: Реализована система учёта продаж товаров в магазине. Для хранения данных используется потокобезопасная коллекция `ConcurrentHashMap`, счётчик продаж каждого товара реализован через `AtomicInteger`.

**Класс `ProductSales` — модель товара**:
```java
public class ProductSales {
    private String name;
    private double price;
    private AtomicInteger count;

    public ProductSales(String name, double price) {
        this.name = name;
        this.price = price;
        this.count = new AtomicInteger(0); // начальное значение счётчика
    }

    public void increaseCount() {
        count.incrementAndGet(); // атомарное увеличение на 1
    }

    public double getSum() {
        return price * getCount(); // выручка по данному товару
    }
}
```

- `AtomicInteger` обеспечивает потокобезопасное изменение счётчика без явной синхронизации.
- Метод `getSum()` вычисляет суммарную выручку: цена × количество продаж.

**Класс `SalesTracker` — система учёта**:

Метод `addSale()` — регистрация продажи:
```java
public void addSale(String name, double price) {
    sales.putIfAbsent(name, new ProductSales(name, price));
    sales.get(name).increaseCount();
}
```
- `putIfAbsent()` добавляет новый товар, только если его ещё нет в карте — атомарная операция `ConcurrentHashMap`.
- После этого счётчик продаж существующего или только что добавленного товара увеличивается.

Метод `getTotalSum()` — общая выручка:
```java
public double getTotalSum() {
    double total = 0;
    for (ProductSales product : sales.values()) {
        total = total + product.getSum();
    }
    return total;
}
```

Метод `getMostPopularProduct()` — самый продаваемый товар:
```java
public String getMostPopularProduct() {
    String bestName = "";
    int bestCount = -1;

    for (ProductSales product : sales.values()) {
        if (product.getCount() > bestCount) {
            bestCount = product.getCount();
            bestName = product.getName();
        }
    }

    return bestName + " (продано: " + bestCount + ")";
}
```
- Линейный обход всех товаров с поиском максимума по счётчику продаж.
- Начальное значение `bestCount = -1` гарантирует, что любой товар с продажами будет выбран.

**Демонстрация работы (`SalesDemo`)**:
```java
SalesTracker tracker = new SalesTracker();

tracker.addSale("Ноутбук", 75000);
tracker.addSale("Мышь", 1500);
tracker.addSale("Мышь", 1500);
tracker.addSale("Клавиатура", 3500);
tracker.addSale("Монитор", 18000);
tracker.addSale("Монитор", 18000);
tracker.addSale("Монитор", 18000);

tracker.printSales();
System.out.println("Общая сумма продаж: " + tracker.getTotalSum());
System.out.println("Самый популярный товар: " + tracker.getMostPopularProduct());
```

**Пример вывода**:
```
Проданные товары:
Ноутбук    | цена: 75000.0 | продано: 1 | сумма: 75000.0
Мышь       | цена: 1500.0  | продано: 2 | сумма: 3000.0
Клавиатура | цена: 3500.0  | продано: 1 | сумма: 3500.0
Монитор    | цена: 18000.0 | продано: 3 | сумма: 54000.0

Общая сумма продаж: 135500.0
Самый популярный товар: Монитор (продано: 3)
```

---

## Запуск программы

### Компиляция:

**Задание 1:**
```bash
javac TopWords.java
```

**Задание 2:**
```bash
javac Stack.java StackDemo.java
```

**Задание 3:**
```bash
javac ProductSales.java SalesTracker.java SalesDemo.java
```

Или все файлы сразу:
```bash
javac *.java
```

### Выполнение:

**Задание 1 — анализ частоты слов:**
```bash
# Предварительно создать файл ttt.txt в той же директории
java TopWords
```

**Задание 2 — демонстрация стека:**
```bash
java StackDemo
```

**Задание 3 — система учёта продаж:**
```bash
java SalesDemo
```

---

## Важные замечания

### Почему не `new T[]` в дженериках

Java не позволяет создавать массив обобщённого типа напрямую (`new T[]`), так как информация о типе стирается во время компиляции (type erasure). Обходное решение — создать массив `Object[]` и привести его к `T[]`:

```java
data = (T[]) new Object[capacity];
```

Аннотация `@SuppressWarnings("unchecked")` подавляет связанное предупреждение компилятора.

### HashMap vs ConcurrentHashMap

`HashMap` не является потокобезопасным: одновременный доступ из нескольких потоков может привести к повреждению данных. `ConcurrentHashMap` решает эту проблему, сегментируя внутреннюю структуру и блокируя только нужный сегмент при записи, что обеспечивает высокую производительность при конкурентном доступе.

### putIfAbsent() — атомарная операция

Комбинация `containsKey()` + `put()` не является потокобезопасной: между проверкой и добавлением другой поток может успеть изменить карту. Метод `putIfAbsent()` выполняет оба действия атомарно, что критично при многопоточном использовании.

### AtomicInteger vs int

Обычный `int` при конкурентной записи из нескольких потоков подвержен состоянию гонки: два потока одновременно прочитают значение, увеличат его и запишут, потеряв одно из приращений. `AtomicInteger.incrementAndGet()` выполняет чтение, увеличение и запись как одну неделимую операцию.

### Обнуление ссылки в pop()

После извлечения элемента из стека в методе `pop()` ссылка в массиве обнуляется (`data[size] = null`). Без этого объект продолжал бы существовать в памяти, так как массив хранил бы на него ссылку, не позволяя сборщику мусора его удалить (утечка памяти).

### Порядок элементов в ConcurrentHashMap

Как и `HashMap`, `ConcurrentHashMap` не гарантирует порядок обхода элементов. Порядок вывода товаров в `printSales()` может отличаться от порядка их добавления.

---

## Дополнительные возможности для расширения

- Добавление метода `isEmpty()` и `size()` в класс `Stack<T>` для проверки состояния стека.
- Реализация динамического расширения массива в `Stack<T>` при переполнении (аналог `ArrayList`).
- Добавление временных меток в `SalesTracker` для хранения истории продаж по времени.
- Расширение `TopWords` для поддержки кириллических слов через изменение regex-шаблона очистки.
- Вынесение пути к файлу `ttt.txt` в аргументы командной строки `args[0]` для гибкости запуска.
- Реализация метода `getTop(int n)` в `SalesTracker` для получения n наиболее популярных товаров.
- Добавление сохранения отчёта о продажах в файл с использованием `FileWriter`.
- Реализация метода `clear()` для стека с обнулением всех ссылок в массиве.
