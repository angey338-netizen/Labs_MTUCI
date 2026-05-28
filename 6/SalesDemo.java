public class SalesDemo {
    public static void main(String[] args) {
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
    }
}
