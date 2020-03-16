import java.util.Scanner;
import java.util.concurrent.*;

public class MatrixMultiplication {
    static int m = 0;
    static int n = 0;
    static int o = 0;

    static int[][] mA = null;
    static int[][] mB = null;
    static int[][] res = null;

    static void multiply(int row, int col) {
        System.out.println("Работает " + Thread.currentThread().getName() + " с ячейкой [" + row + "," + col + "]\n");

        for (int i = 0; i < o; i++)
            res[row][col] += mA[row][i] * mB[i][col];
    }

    public static void main(String[] args) {
        MatrixProcessing arrayToFile = new MatrixProcessing();
        mA = arrayToFile.loadArrayFromFile("matrixA.txt");
        mB = arrayToFile.loadArrayFromFile("matrixB.txt");
        m = mA.length;
        n = mB[0].length;
        o = mA[0].length;
        res = new int[m][n];

        if (m == o) {
            System.out.println("Количество потоков: ");
            Scanner s = new Scanner(System.in);
            int threads = s.nextInt();

            if (threads <= 1)
                threads = Runtime.getRuntime().availableProcessors();

            System.out.println("Количество потоков: " + threads);

            ExecutorService executor = Executors.newFixedThreadPool(threads);

            long startTime = System.currentTimeMillis();

            Buffer buffer = new Buffer(10, mA, mB);

            executor.execute(new ConcurrentQueueProducer(buffer, mA, mB));

            // TODO: need to fix thread's work
            for (int j = 0; j < threads; j++) {
                executor.execute(new ConcurrentQueueConsumer(buffer,mA,mB,threads));
            }

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
