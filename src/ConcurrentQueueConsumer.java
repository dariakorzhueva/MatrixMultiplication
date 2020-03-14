public class ConcurrentQueueClient implements Runnable {

    private ConcurrentQueue concurrentQueue;
    private int threads = 2;
    private static int[][] res = null;

    public ConcurrentQueueClient(ConcurrentQueue concurrentQueue, int threads) {
        this.threads = threads;
        this.concurrentQueue = concurrentQueue;
    }

    public void run() {
        boolean stopCondition = (concurrentQueue.getQueueSize() == 0);

        int m = concurrentQueue.getM();
        int n = concurrentQueue.getN();
        res = new int[m][n];

        while (!stopCondition) {
            for (int k = 0; k < concurrentQueue.getQueueSize(); k++) {
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
                        res[x / y][x % y] = concurrentQueue.dequeueItem().multiply(x / y, x % y);
                    }

                    firstIndex = lastIndex;
                }

                new MatrixProcessing().printArray(res);
            }
            stopCondition = (concurrentQueue.getQueueSize() == 0);
        }

        System.out.println("Client thread exiting...");
    }
}