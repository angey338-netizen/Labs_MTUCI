public class Main {
    public static void main(String[] args) {

        // Создание объектов
        Keyboard k = new Keyboard("Gaming Keyboard", 120, 900,
                                   "QWERTY", true, "Cherry MX");
        Headphones h = new Headphones("Bass Boost", 150, 250,
                                      "Closed-back", 64, "10-28000");
        GraphicsTablet t = new GraphicsTablet("Pro Art", 300, 800,
                                              "A5", 4096, "Bluetooth");

        // Вывод информации
        k.showInfo();
        h.showInfo();
        t.showInfo();

        // ДЕМОНСТРАЦИЯ ГЕТТЕРОВ
        System.out.println("\n--- Использование геттеров ---");
        System.out.println("Название клавиатуры: " + k.getName());
        System.out.println("Цена наушников: " + h.getPrice());

        // ДЕМОНСТРАЦИЯ СЕТТЕРОВ
        System.out.println("\n--- Использование сеттеров ---");
        k.setName("Ultimate Gaming Keyboard");
        System.out.println("Новое имя клавиатуры: " + k.getName());

        // ДЕМОНСТРАЦИЯ ПЕРЕГРУЗКИ (applyDiscount)
        System.out.println("\n--- Демонстрация перегрузки applyDiscount ---");
        k.applyDiscount(10);   // скидка 10%
       

        // ДЕМОНСТРАЦИЯ ПОЛИМОРФИЗМА
        System.out.println("\n--- Полиморфизм ---");
        Peripheral[] devices = {k, h, t};
        for (Peripheral p : devices) {
            p.showInfo();
        }

        // СТАТИЧЕСКИЙ СЧЁТЧИК
        System.out.println("\nВсего объектов периферии: " + Peripheral.getCount());
    }
}
