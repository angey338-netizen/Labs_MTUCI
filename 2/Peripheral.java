public abstract class Peripheral {
    protected String name;
    protected int price;      
    protected int weight;     
    protected static int count = 0;

    public Peripheral(String name, int price, int weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        count++;
    }

    public Peripheral() {
        this("Unknown", 100, 500);
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public static int getCount() {
        return count;
    }

    // Сеттеры
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public abstract void showInfo();

    // Перегрузка метода applyDiscount
    public void applyDiscount(int percent) {
        price = price * (100 - percent) / 100;
        System.out.println("Применена скидка " + percent + "%. Новая цена: " + price);
    }
}