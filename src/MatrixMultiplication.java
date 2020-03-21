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
        MatrixProcessing matrixProcessing = new MatrixProcessing();
        //mA = matrixProcessing.loadArrayFromFile("matrixA.txt");
        //mB = matrixProcessing.loadArrayFromFile("matrixB.txt");
        mA = matrixProcessing.initArray(1000,1000);
        mB = matrixProcessing.initArray(1000,1000);
        m = mA.length;
        n = mB[0].length;
        o = mA[0].length;
        p = mB.length;
        res = new int[m][n];

        if (p == o) {
            System.out.println("Пул потоков: ");
            Scanner s = new Scanner(System.in);
            int threads = s.nextInt();

            if (threads <= 0)
                threads = Runtime.getRuntime().availableProcessors();

            System.out.println("Пул потоков: " + threads + " потоков");

            ExecutorService executor = Executors.newFixedThreadPool(threads);

            Buffer buffer = new Buffer(mA, mB);

            executor.execute(new ConcurrentQueueProducer(buffer, mA, mB));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < m; i++) {
                executor.execute(new ConcurrentQueueConsumer(res, buffer, mA, mB, threads));
            }

            executor.shutdown();

            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            long endTime = System.currentTimeMillis();

            //matrixProcessing.printArray(res);
            //matrixProcessing.saveArrayToFile(res,"res.txt");

            System.out.println("Время выполнения: " + (endTime - startTime) + "ms");
        } else
            System.out.println("Количество столбцов матрицы A не совпадает с количеством столбцов матрицы B");
    }
}
