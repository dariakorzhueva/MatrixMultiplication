public class ConcurrentQueueConsumer implements Runnable {
    Buffer buffer;
    private int m = 0;
    private int n = 0;
    private int threads = 8;
    private int[][] mA = null;
    private static int[][] mB = null;
    private static int[][] res = null;

    ConcurrentQueueConsumer(Buffer buffer, int[][] a, int[][] b, int _threads) {
        this.buffer = buffer;
        this.m = a.length;
        this.n = b[0].length;
        this.mA = a;
        this.mB = b;
        this.res = new int[m][n];
        this.threads = _threads;
    }

    @Override
    public void run() {
        MultiplyInterfece mi = buffer.get();
        int row = buffer.getCountElems()-1;
        res[row] = mi.multiply(row);

        new MatrixProcessing().printArray(res);
    }
}