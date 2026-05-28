import java.util.HashMap;

public class ProductWarehouse {
    private final HashMap<String, Product> products = new HashMap<>();

    public void addProduct(String barcode, Product product) {
        if (barcode == null || barcode.isBlank()) {
            throw new IllegalArgumentException("Штрихкод не может быть пустым");
        }
        products.put(barcode, product);
    }

    public Product findProduct(String barcode) {
        return products.get(barcode);
    }

    public Product removeProduct(String barcode) {
        return products.remove(barcode);
    }

    public void printAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Склад пуст.");
            return;
        }

        System.out.println("Содержимое склада:");
        products.forEach((barcode, product) -> {
            System.out.println("Штрихкод: " + barcode + " | " + product);
        });
    }

    public int size() {
        return products.size();
    }
}

