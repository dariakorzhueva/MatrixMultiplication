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

            if (threads == 0)
                threads = Runtime.getRuntime().availableProcessors();

            System.out.println("Количество потоков: " + threads);

            ExecutorService executor = Executors.newFixedThreadPool(threads);

            int start = 0;

            int m3 = m / threads;
            final int n1 = n;
            final int o1 = n;

            for (int x = 0; x < m / threads; x++) {
                final int start1 = start;
                final int start2 = start;
                final int start3 = start;
                final int m1 = m3;


                executor.submit(() -> multiply(start1, start2, start3, m1, n1, o1));
                start = m3;
                m3 += m / threads;
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
