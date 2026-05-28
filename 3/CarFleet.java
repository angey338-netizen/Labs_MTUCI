import java.util.HashMap;

public class BarCode {
    private final HashMap<String, Car> cars = new HashMap<>();

    public void addCar(String plateNumber, Car car) {
        if (plateNumber == null || plateNumber.isBlank()) {
            throw new IllegalArgumentException("Номерной знак не может быть пустым");
        }
        cars.put(plateNumber, car);
    }

    public Car findCar(String plateNumber) {
        return cars.get(plateNumber);
    }

    public Car removeCar(String plateNumber) {
        return cars.remove(plateNumber);
    }

    public void printAllCars() {
        if (cars.isEmpty()) {
            System.out.println("Автопарк пуст.");
            return;
        }

        for (HashMap.Entry<String, Car> entry : cars.entrySet()) {
            System.out.println("Номерной знак: " + entry.getKey() + " | " + entry.getValue());
        }
    }
}

