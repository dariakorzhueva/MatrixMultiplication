import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

            final int cellsForThread = (m * n) / threads;
            int firstIndex = 0;

            for (int threadIndex = threads - 1; threadIndex >= 0; threadIndex--) {
                int lastIndex = firstIndex + cellsForThread;

                // Если количество ячеек не делится нацело на количество потоков
                if (threadIndex == 0) {
                    lastIndex = m * n;
                }

                for (int i = firstIndex; i < lastIndex; i++) {
                    final int x = i;
                    final int y = n;

                    executor.submit(() -> multiply(x / y, x % y));
                }

                firstIndex = lastIndex;
            }

            executor.shutdown();

            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            arrayToFile.printArray(res);
        } else
            System.out.println("Количество столбцов матрицы A не совпадает с количеством столбцов матрицы B");
    }
}
