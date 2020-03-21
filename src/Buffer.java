import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class Buffer {
    private int p = 0;
    private int[][] mA = null;

    public Buffer(int[][] a) {
        mA = a;
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