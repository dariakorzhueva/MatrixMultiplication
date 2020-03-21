public class ConcurrentQueueConsumer implements Runnable {
    private Buffer buffer;
    private static int[][] res = null;

    ConcurrentQueueConsumer(int[][] res, Buffer buffer) {
        this.buffer = buffer;
        this.res = res;
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