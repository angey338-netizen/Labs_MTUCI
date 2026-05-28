public class Headphones extends Peripheral {
    private String type;          
    private int connectivity;       
    private String frequency;     
    public Headphones(String name, int price, int weight,
                      String type, int connectivity, String frequency) {
        super(name, price, weight);
        this.type = type;
        this.connectivity = connectivity;
        this.frequency = frequency;
    }

    public Headphones() {
        super("Standard Headphones", 80, 200);
        this.type = "Over-ear";
        this.connectivity = 32;
        this.frequency = "20-20000";
    }

    // Геттеры
    public String getType() {
        return type;
    }

    public int getConnectivity() {
        return connectivity;
    }

    public String getFrequency() {
        return frequency;
    }

    // Сеттеры
    public void setType(String type) {
        this.type = type;
    }

    public void setConnectivity(int connectivity) {
        this.connectivity = connectivity;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public void showInfo() {
        System.out.println("Наушники: " + name +
                ", тип: " + type +
                ", импеданс: " + connectivity + " Ом" +
                ", диапазон: " + frequency + " Гц" +
                ", цена: " + price + " у.е., вес: " + weight + " г");
    }
}
