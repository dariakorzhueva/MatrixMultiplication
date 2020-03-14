import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplication {
    static int[][] mA = null;

    static int[][] mB = null;

    static int m = 0;
    static int n = 0;
    static int o = 0;
    static int[][] res = new int[m][n];

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
        o = mB.length;

        if (mA.length == mB[0].length && mA[0].length == mB.length) {
            int cores = Runtime.getRuntime().availableProcessors();
            System.out.println("Количество потоков (ядер): " + cores);
            ExecutorService executor = Executors.newFixedThreadPool(cores);

            int m2 = 0;
            int n2 = 0;
            int o2 = 0;

            for (int x = 0; x < m; x++)
                for (int y = 0; y < n; y++) {
                    final int start1 = m2;
                    final int start2 = n2;
                    final int start3 = o2;
                    final int m1 = m/cores;
                    final int n1 = n/cores;
                    final int o1 = o/cores;
                    executor.submit(() -> multiply(start1, start2, start3, m1, n1, o1));
                    m2 = m1;
                    n2 = n1;
                    o2 = o1;
                }

            executor.shutdown();

            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            // TODO: print result matrix
            arrayToFile.printArray(res);
        } else if (mA.length != mB[0].length)
            System.out.println("Количество строк матрицы A не совпадает с количеством столбцов матрицы B");
        else if (mA[0].length == mB.length)
            System.out.println("Количество строк матрицы B не совпадает с количеством столбцов матрицы A");
    }
}
