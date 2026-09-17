import java.util.Random;

public class Part1 {

    static long totalHits = 0;
    static final long TOTAL_POINTS = 50_000_000;

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[4];

        long pointsPerThread = TOTAL_POINTS / 4;

        for (int i = 0; i < 4; i++) {

            threads[i] = new Thread(() -> {

                Random random = new Random();

                for (long j = 0; j < pointsPerThread; j++) {

                    double x = random.nextDouble();
                    double y = random.nextDouble();

                    if (x * x + y * y <= 1) {
                        totalHits++;
                    }
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        double pi = 4.0 * totalHits / TOTAL_POINTS;

        System.out.println("Total hits: " + totalHits);
        System.out.println("Pi = " + pi);
    }
}