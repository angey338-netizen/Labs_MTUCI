public class Main {
    public static void main(String[] args) throws Exception {

        // 1. Создаём DataManager
        DataManager manager = new DataManager();

        // 2. Создаём объект с обработчиками и регистрируем его
        manager.registerDataProcessor(new DataProcessors());

        System.out.println();

        // 3. Загружаем данные из файла input.txt
        manager.loadData("input.txt");
        System.out.println();

        // 4. Запускаем обработку (многопоточно, через аннотации и Stream API)
        manager.processData();
        System.out.println();

        // 5. Сохраняем результат в output.txt
        manager.saveData("output.txt");

        System.out.println("\n=== Готово! ===");
    }
}
