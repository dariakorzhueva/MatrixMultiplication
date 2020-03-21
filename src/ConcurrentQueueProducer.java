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

class Buffer {
    private int p = 0;
    private int[][] mA = null;
    private int[][] mB = null;
    private int[][] res = new int[p][p];
    private int countElems = 0;

    public Buffer(int[][] a, int[][] b) {
        mA = a;
        mB = b;
        p = mA[0].length;
    }

    MultiplyInterfece mi;

    Queue<MultiplyInterfece> clQueue = new ConcurrentLinkedQueue<MultiplyInterfece>();

    public MultiplyInterfece get() {
        countElems--;
        return clQueue.poll();
    }

    public void put(MultiplyInterfece mi) {
        this.mi = mi;
        clQueue.add(mi);
        countElems++;
    }

    public int size() {
        return clQueue.size();
    }

    public boolean isEmpty(){
        if(clQueue.isEmpty())
            return true;
        else
            return false;
    }

    public int getSize(){
        return size();
    }
}