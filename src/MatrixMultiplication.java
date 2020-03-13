import com.sun.xml.internal.bind.v2.TODO;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {
    static int[][] mA =
            {{1, 0, 0},
                    {0, 1, 0},
                    {0, 0, 1}};

    static int[][] mB =
            {{1, 0, 0},
                    {0, 1, 0},
                    {0, 0, 1}};

    static int m = mA.length;
    static int n = mB[0].length;
    static int o = mB.length;
    static int[][] res = new int[m][n];

    static Callable callable(int start1, int start2, int start3, int m, int n, int o) {
        return () -> {
            for (int i = start1; i < m; i++) {
                for (int j = start2; j < n; j++) {
                    for (int k = start3; k < o; k++) {
                        res[i][j] += mA[i][k] * mB[k][j];
                    }
                }
            }
            return true;
        };
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<Boolean>> callables = Arrays.asList(
                callable(0, 0, 0, m, n, o));

        // TODO: add in cycle tasks into list

        try {
            executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(System.out::println);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        // TODO: print result matrix
    }
}
