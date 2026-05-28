import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SimpleTask3WarehouseCyclicBarrier {

    static int[][] trips = {
            {50, 40, 60},
            {70, 30, 50},
            {40, 50, 60}
    };

    static int currentTrip = 0;

    static CyclicBarrier barrier = new CyclicBarrier(3, () -> {
        int totalWeight = trips[currentTrip][0]
                + trips[currentTrip][1]
                + trips[currentTrip][2];

        System.out.println("Собрано " + totalWeight + " кг. Грузчики едут на другой склад.");
        System.out.println("Товары разгружены. Грузчики вернулись.\n");

        currentTrip++;
    });

    public static void main(String[] args) throws InterruptedException {
        Thread loader1 = new Thread(new Loader(0), "Грузчик 1");
        Thread loader2 = new Thread(new Loader(1), "Грузчик 2");
        Thread loader3 = new Thread(new Loader(2), "Грузчик 3");

        loader1.start();
        loader2.start();
        loader3.start();

        loader1.join();
        loader2.join();
        loader3.join();

        System.out.println("Работа закончена. Все товары перенесены.");
    }

    static class Loader implements Runnable {
        int loaderNumber;

        Loader(int loaderNumber) {
            this.loaderNumber = loaderNumber;
        }

        @Override
        public void run() {
            try {
                for (int trip = 0; trip < trips.length; trip++) {
                    int weight = trips[trip][loaderNumber];

                    System.out.println(Thread.currentThread().getName()
                            + " взял товар весом " + weight + " кг");

                    System.out.println(Thread.currentThread().getName()
                            + " ждёт остальных грузчиков");

                    barrier.await();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                System.out.println("Барьер был нарушен.");
            }
        }
    }
}