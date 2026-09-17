import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class part2 {

    static final long TOTAL_POINTS = 50_000_000;

    public static void main(String[] args) throws InterruptedException {

        long start = System.nanoTime();

        long hits = 0;
        Random random = new Random();

        for (long i = 0; i < TOTAL_POINTS; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y <= 1) {
                hits++;
            }
        }

        long end = System.nanoTime();

        double pi = 4.0 * hits / TOTAL_POINTS;
        double time = (end - start) / 1_000_000.0;

        System.out.println("Single-thread:");
        System.out.println("Pi = " + pi);
        System.out.println("Time = " + time + " ms");


        AtomicLong totalHits = new AtomicLong(0);

        Thread[] threads = new Thread[4];

        start = System.nanoTime();

        for (int i = 0; i < 4; i++) {

            threads[i] = new Thread(() -> {

                Random r = new Random();

                for (long j = 0; j < TOTAL_POINTS / 4; j++) {

                    double x = r.nextDouble();
                    double y = r.nextDouble();

                    if (x * x + y * y <= 1) {
                        totalHits.incrementAndGet();
                    }
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        end = System.nanoTime();

        pi = 4.0 * totalHits.get() / TOTAL_POINTS;
        time = (end - start) / 1_000_000.0;

        System.out.println("\n4 threads + AtomicLong:");
        System.out.println("Pi = " + pi);
        System.out.println("Time = " + time + " ms");
    }
}
