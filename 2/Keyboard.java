public class Keyboard extends Peripheral {
    private String layout;      // раскладка (QWERTY, AZERTY...)
    private boolean backlight;  // наличие подсветки
    private String switches;    // тип переключателей (Mechanical, Membrane...)

    public Keyboard(String name, int price, int weight,
                    String layout, boolean backlight, String switches) {
        super(name, price, weight);
        this.layout = layout;
        this.backlight = backlight;
        this.switches = switches;
    }

    public Keyboard() {
        super("Standard Keyboard", 50, 800);
        this.layout = "QWERTY";
        this.backlight = true;
        this.switches = "Mechanical";
    }

    // Геттеры
    public String getLayout() {
        return layout;
    }

    public boolean isBacklight() {
        return backlight;
    }

    public String getSwitches() {
        return switches;
    }

    // Сеттеры
    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setBacklight(boolean backlight) {
        this.backlight = backlight;
    }

    public void setSwitches(String switches) {
        this.switches = switches;
    }

    @Override
    public void showInfo() {
        System.out.println("Клавиатура: " + name +
                ", раскладка: " + layout +
                ", подсветка: " + (backlight ? "есть" : "нет") +
                ", переключатели: " + switches +
                ", цена: " + price + " у.е., вес: " + weight + " г");
    }
}