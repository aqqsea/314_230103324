import java.util.Random;

public class Part3 {

    static final long TOTAL_POINTS = 100_000_000;

    public static void main(String[] args) throws InterruptedException {

        int[] threadCounts = {1, 2, 4, 8, 16, 32};

        double time1 = 0;

        System.out.println("Threads | Runtime (ms) | Speedup | Efficiency");

        for (int threadsCount : threadCounts) {

            Thread[] threads = new Thread[threadsCount];
            long[] localHits = new long[threadsCount];

            long start = System.nanoTime();

            for (int i = 0; i < threadsCount; i++) {

                final int index = i;
                long points = TOTAL_POINTS / threadsCount;

                threads[i] = new Thread(() -> {

                    Random random = new Random();
                    long hits = 0;

                    for (long j = 0; j < points; j++) {

                        double x = random.nextDouble();
                        double y = random.nextDouble();

                        if (x * x + y * y <= 1) {
                            hits++;
                        }
                    }

                    localHits[index] = hits;
                });

                threads[i].start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            long totalHits = 0;

            for (long hits : localHits) {
                totalHits += hits;
            }

            long end = System.nanoTime();

            double runtime = (end - start) / 1_000_000.0;

            if (threadsCount == 1) {
                time1 = runtime;
            }

            double speedup = time1 / runtime;
            double efficiency = speedup / threadsCount * 100;

            System.out.printf(
                    "%7d | %13.2f | %7.2fx | %9.2f%%%n",
                    threadsCount, runtime, speedup, efficiency
            );
        }
    }
}
