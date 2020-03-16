import java.util.Scanner;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class MatrixMultiplication {
    static int m = 0;
    static int n = 0;
    static int o = 0;
    static int p = 0;

    static int[][] mA = null;
    static int[][] mB = null;
    static int[][] res = null;

    public static void main(String[] args) {
        MatrixProcessing arrayToFile = new MatrixProcessing();
        mA = arrayToFile.loadArrayFromFile("matrixA.txt");
        mB = arrayToFile.loadArrayFromFile("matrixB.txt");
        m = mA.length;
        n = mB[0].length;
        o = mA[0].length;
        p = mB.length;
        res = new int[m][n];

        if (p == o) {
            System.out.println("Количество потоков: ");
            Scanner s = new Scanner(System.in);
            int threads = s.nextInt();

            if (threads <= 1)
                threads = Runtime.getRuntime().availableProcessors();

            System.out.println("Количество потоков: " + threads);

            ExecutorService executor = Executors.newFixedThreadPool(threads);

            long startTime = System.currentTimeMillis();

            Buffer buffer = new Buffer(mA, mB);

            executor.execute(new ConcurrentQueueProducer(buffer, mA, mB));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; !buffer.isEmpty() && i < threads; i++)
                executor.execute(new ConcurrentQueueConsumer(buffer, mA, mB, threads));

            executor.shutdown();

            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            long endTime = System.currentTimeMillis();

            //arrayToFile.printArray(res);

            System.out.println("Время выполнения: " + (endTime - startTime) + "ms");
        } else
            System.out.println("Количество столбцов матрицы A не совпадает с количеством столбцов матрицы B");
    }
}
