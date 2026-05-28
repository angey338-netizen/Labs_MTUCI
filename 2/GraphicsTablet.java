public class GraphicsTablet extends Peripheral {
    private String size;            // размер (A6, A5...)
    private int pressureLevels;     // уровни давления
    private String connectivity;    // тип подключения (USB, Bluetooth...)

    public GraphicsTablet(String name, int price, int weight,
                          String size, int pressureLevels, String connectivity) {
        super(name, price, weight);
        this.size = size;
        this.pressureLevels = pressureLevels;
        this.connectivity = connectivity;
    }

    public GraphicsTablet() {
        super("Standard Tablet", 150, 600);
        this.size = "Medium";
        this.pressureLevels = 2048;
        this.connectivity = "USB";
    }

    // Геттеры
    public String getSize() {
        return size;
    }

    public int getPressureLevels() {
        return pressureLevels;
    }

    public String getConnectivity() {
        return connectivity;
    }

    // Сеттеры
    public void setSize(String size) {
        this.size = size;
    }

    public void setPressureLevels(int pressureLevels) {
        this.pressureLevels = pressureLevels;
    }

    public void setConnectivity(String connectivity) {
        this.connectivity = connectivity;
    }

    @Override
    public void showInfo() {
        System.out.println("Графический планшет: " + name +
                ", размер: " + size +
                ", уровней давления: " + pressureLevels +
                ", подключение: " + connectivity +
                ", цена: " + price + " у.е., вес: " + weight + " г");
    }
}
