public class ConcurrentQueueConsumer implements Runnable {
    private Buffer buffer;
    private int m = 0;
    private int n = 0;
    private int threads = 8;
    private int[][] mA = null;
    private static int[][] mB = null;
    private static int[][] res = null;

    ConcurrentQueueConsumer(int[][] res, Buffer buffer, int[][] a, int[][] b, int _threads) {
        this.buffer = buffer;
        this.m = a.length;
        this.n = b[0].length;
        this.mA = a;
        this.mB = b;
        this.res = res;
        this.threads = _threads;
    }

    public int[][] getRes() {
        return res;
    }

    @Override
    public void run() {
        MultiplyInterfece mi;
        int row = -1;

        synchronized (buffer) {
            mi = buffer.get();
            row = buffer.getSize();
        }

        if (row != -1)
            res[row] = mi.multiply(row);

    }
}