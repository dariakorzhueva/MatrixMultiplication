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
        m = a.length;
        n = b[0].length;
        p = a[0].length;
        mA = a;
        mB = b;
        res = new int[p][p];

    }

    @Override
    public void run() {
        MultiplyInterfece multiplyInterfece = (row, col) -> {
            System.out.println("Работает " + Thread.currentThread().getName() + " с ячейкой [" + row + "," + col + "]\n");

            for (int i = 0; i < n; i++)
                res[row][col] += mA[row][i] * mB[i][col];

            return res[row][col];
        };
        for (int i = 0; i < m * n; i++) {
            buffer.put(multiplyInterfece);
        }
    }
}

class Buffer {
    int n = 0;
    int p = 0;
    int[][] mA = null;
    int[][] mB = null;
    int[][] res = new int[p][p];

    public Buffer(int _n, int[][] a, int[][] b) {
        n = _n;
        mA = a;
        mB = b;
        p = mA[0].length;
    }

    MultiplyInterfece mi;

    Queue<MultiplyInterfece> clQueue = new ConcurrentLinkedQueue<MultiplyInterfece>();

    public MultiplyInterfece get() {
        return clQueue.poll();
    }

    public void put(MultiplyInterfece mi) {
        this.mi = mi;
        clQueue.add(mi);
    }

}