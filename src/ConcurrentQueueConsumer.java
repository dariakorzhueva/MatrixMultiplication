/**
 * Потребитель очереди
 */
public class ConcurrentQueueConsumer implements Runnable {
    private Buffer buffer;
    private static int[][] res = null;

    /**
     * Инициализация
     *
     * @param buffer очередь задач
     * @param res результирующая матрица
     */
    ConcurrentQueueConsumer(int[][] res, Buffer buffer) {
        this.buffer = buffer;
        this.res = res;
    }

    /**
     * Запуск потока
     */
    @Override
    public void run() {
        MultiplyInterfece mi;
        int row = -1;

        // Синхронизация по объекту buffer
        // При попадании потока в этот блок
        // Происходит захват мьютекса объекта buffer
        synchronized (buffer) {
            mi = buffer.get();
            row = buffer.getSize();
        }

        if (row != -1)
            res[row] = mi.multiply(row);
    }
}