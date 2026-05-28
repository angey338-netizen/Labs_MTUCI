public class Main {
    public static void main(String[] args) {

        System.out.println("=== Задание 1. Собственная HashTable ===");
        HashTable<String, Integer> table = new HashTable<>(16);

        table.put("apple", 5);
        table.put("banana", 3);
        table.put("orange", 7);

        System.out.println("Размер таблицы: " + table.size());
        System.out.println("Значение по ключу banana: " + table.get("banana"));

        table.put("banana", 10);
        System.out.println("Обновленное значение banana: " + table.get("banana"));

        System.out.println("Удалено значение по ключу apple: " + table.remove("apple"));
        System.out.println("Размер таблицы после удаления: " + table.size());
        System.out.println("Пуста ли таблица? " + table.isEmpty());

       System.out.println("=== Вариант 5: Хэш-таблица для учёта продуктов ===");

        ProductWarehouse warehouse = new ProductWarehouse();

        // Добавление продуктов
        warehouse.addProduct("4601234567890", new Product("Молоко", 89.90, 50));
        warehouse.addProduct("4609876543210", new Product("Хлеб", 35.50, 100));
        warehouse.addProduct("4605555555555", new Product("Сыр", 250.00, 30));

        System.out.println("\nВсе продукты на складе:");
        warehouse.printAllProducts();

        // Поиск по штрихкоду
        System.out.println("\nПоиск продукта по штрихкоду 4609876543210:");
        Product found = warehouse.findProduct("4609876543210");
        System.out.println(found != null ? found : "Продукт не найден");

        // Обновление продукта (изменение количества)
        System.out.println("\nОбновление количества хлеба:");
        Product bread = warehouse.findProduct("4609876543210");
        if (bread != null) {
            bread.setQuantity(bread.getQuantity() - 10);
            System.out.println("Обновлённый продукт: " + bread);
        }

        // Удаление продукта
        System.out.println("\nУдаление продукта по штрихкоду 4601234567890:");
        Product removed = warehouse.removeProduct("4601234567890");
        System.out.println("Удалён: " + removed);

        System.out.println("\nСклад после удаления:");
        warehouse.printAllProducts();

        System.out.println("\nКоличество товаров на складе: " + warehouse.size());
    }
}

