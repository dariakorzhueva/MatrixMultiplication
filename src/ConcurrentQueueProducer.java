import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentQueueProducer implements Runnable {
    private int m = 0;
    private int n = 0;
    private int p = 0;
    private int[][] mA = null;
    private static int[][] mB = null;
    private static int[][] res = null;
    Buffer buffer;

    ConcurrentQueueProducer(Buffer buffer, int[][] a, int[][] b) {
        this.buffer = buffer;
        this.m = a.length;
        this.n = b[0].length;
        this.mA = a;
        this.mB = b;
        this.res = new int[m][n];
        this.p = a[0].length;
    }

    @Override
    public void run() {
        MultiplyInterfece multiplyInterfece = (row) -> {
            System.out.println("Работает " + Thread.currentThread().getName() + " со строкой №" + row + "\n");
            for (int i = 0; i < n; i++)
                for (int j = 0; j < p; j++)
                    res[row][i] += mA[row][j] * mB[j][i];

            return res[row];
        };

        for (int i = 0; i < m; i++) {
            buffer.put(multiplyInterfece);
        }
    }
}