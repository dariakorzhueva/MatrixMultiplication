import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentQueue implements Runnable {

    private ConcurrentLinkedQueue<MultiplyInterfece> concurrentLinkedQueue = new ConcurrentLinkedQueue<MultiplyInterfece>();
    private int m = 0;
    private int n = 0;
    private int[][] mA = null;
    private static int[][] mB = null;
    private static int[][] res = null;

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    @FunctionalInterface
    interface MultiplyInterfece {
        int multiply(int row, int col);
    }

    public ConcurrentQueue(int[][] a, int[][] b) {
        mA = a;
        mB = b;
        m = mA.length;
        n = mB[0].length;
    }

    private void multiply(int row, int col) {
        System.out.println("Работает " + Thread.currentThread().getName() + " с ячейкой [" + row + "," + col + "]\n");

        for (int i = 0; i < n; i++)
            res[row][col] += mA[row][i] * mB[i][col];
    }

    public MultiplyInterfece dequeueItem() {
        if (!concurrentLinkedQueue.isEmpty()) {
            System.out.println("Queue size: " + concurrentLinkedQueue.size());
            return concurrentLinkedQueue.poll();
        } else {
            return null;
        }
    }

    private void enqueueItem(MultiplyInterfece item) {
        concurrentLinkedQueue.add(item);
    }

    public int getQueueSize() {
        if (!concurrentLinkedQueue.isEmpty()) {
            return concurrentLinkedQueue.size();
        } else {
            return 0;
        }
    }

    public void run() {
        MultiplyInterfece multiplyInterfece = (row, col) -> {
            System.out.println("Работает " + Thread.currentThread().getName() + " с ячейкой [" + row + "," + col + "]\n");

            for (int i = 0; i < n; i++)
                res[row][col] += mA[row][i] * mB[i][col];

            return res[row][col];
        };
        for (int i = 0; i < m*n; i++) {
            enqueueItem(multiplyInterfece);
        }
    }
}