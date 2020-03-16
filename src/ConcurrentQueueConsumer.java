public class ConcurrentQueueConsumer implements Runnable {
    Buffer buffer;
    private int m = 0;
    private int n = 0;
    private int p = 0;
    private int row = 0;
    private int col = 0;
    int threads = 8;
    private int[][] mA = null;
    private static int[][] mB = null;
    private static int[][] res = null;

    ConcurrentQueueConsumer(Buffer buffer, int[][] a, int[][] b, int _threads, int row, int col) {
        this.buffer = buffer;
        this.m = a.length;
        this.n = b[0].length;
        this.p = a[0].length;
        this.mA = a;
        this.mB = b;
        this.res = new int[p][p];
        this.threads = _threads;
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        MultiplyInterfece mi = buffer.get();
        res[row][col] = mi.multiply(row, col);
    }
}