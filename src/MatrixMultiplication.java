import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplication {
    static int[][] mA = null;

    static int[][] mB = null;

    static int m = 0;
    static int n = 0;
    static int[][] res = null;

    static void multiply(int start1, int start2, int start3, int m, int n, int o) {
        System.out.println("Работает " + Thread.currentThread().getName() + "\n");
        for (int i = start1; i < m; i++) {
            for (int j = start2; j < n; j++) {
                for (int k = start3; k < o; k++) {
                    res[i][j] += mA[i][k] * mB[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        MatrixProcessing arrayToFile = new MatrixProcessing();
        mA = arrayToFile.loadArrayFromFile("matrixA.txt");
        mB = arrayToFile.loadArrayFromFile("matrixB.txt");
        m = mA.length;
        n = mB[0].length;
        res = new int[m][n];

        if (m == n) {
            System.out.println("Количество потоков: ");
            Scanner s = new Scanner(System.in);
            int threads = s.nextInt();

            if (threads <= 1)
                threads = Runtime.getRuntime().availableProcessors();

            System.out.println("Количество потоков: " + threads);

            ExecutorService executor = Executors.newFixedThreadPool(threads);

            int start = 0;

            int m3 = m / threads;
            final int n1 = n;
            final int o1 = n;

            for (int x = 0; x < threads; x++) {
                final int s1 = start;
                final int m1 = m3;

                executor.submit(() -> multiply(0, 0, 0, m, n, n));
                start = m3;
                m3 += m / threads;
            }

            // If the dimension is odd - submit thread
            if(m % 2 != 0){
                final int f = start;
                final int m1 = m3;
                executor.submit(() -> multiply(f, f, f, m1, n1, o1));
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
