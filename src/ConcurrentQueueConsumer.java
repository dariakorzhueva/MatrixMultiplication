import static java.lang.Thread.sleep;

public class ConcurrentQueueConsumer implements Runnable {
    Buffer buffer;
    private int m = 0;
    private int n = 0;
    private int p = 0;
    private int[][] mA = null;
    private static int[][] mB = null;
    private static int[][] res = null;
    int threads = 8;

    ConcurrentQueueConsumer(Buffer buffer, int[][] a, int[][] b, int _threads) {
        this.buffer = buffer;
        m = a.length;
        n = b[0].length;
        p = a[0].length;
        mA = a;
        mB = b;
        res = new int[p][p];
        threads = _threads;
    }

    @Override
    public void run() {
        try {
            sleep(1000);
            MultiplyInterfece mi = buffer.get();
            final int cellsForThread = (m * n) / threads;
            int firstIndex = 0;

            for (int threadIndex = threads - 1; threadIndex >= 0; threadIndex--) {
                int lastIndex = firstIndex + cellsForThread;

                // Если количество ячеек не делится нацело на количество потоков
                if (threadIndex == 0) {
                    lastIndex = m * n;
                }

                for (int i = firstIndex; i < lastIndex; i++) {
                    final int x = i;
                    final int y = n;
                    res[x / y][x % y] = mi.multiply(x / y, x % y);
                }

                firstIndex = lastIndex;
            }

            new MatrixProcessing().printArray(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}