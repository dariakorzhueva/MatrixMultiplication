/**
 * Производитель задач
 */
public class ConcurrentQueueProducer implements Runnable {
    private int m;
    private int n;
    private int p;
    private int[][] mA;
    private static int[][] mB;
    private static int[][] res;
    Buffer buffer;

    /**
     * Инициализация
     *
     * @param buffer очередь задач
     * @param a первая матрица
     * @param b вторая матрица
     */
    ConcurrentQueueProducer(Buffer buffer, int[][] a, int[][] b) {
        this.buffer = buffer;
        this.m = a.length;
        this.n = b[0].length;
        this.p = a[0].length;
        this.mA = a;
        this.mB = b;
        this.res = new int[m][n];
    }

    /**
     * Запуск потока
     */
    @Override
    public void run() {
        // Инициализация функционального интерфейса лямбда-выражением по вычислению строки
        MultiplyInterfece multiplyInterfece = (row) -> {
            System.out.println("Работает " + Thread.currentThread().getName() + " со строкой №" + row + "\n");
            for (int i = 0; i < n; i++)
                for (int j = 0; j < p; j++)
                    res[row][i] += mA[row][j] * mB[j][i];

            return res[row];
        };

        buffer.put(multiplyInterfece);
    }
}