/**
 * Производитель задач
 */
public class ConcurrentQueueProducer implements Runnable {
    private int m;
    private int n;
    private int p;
    private int o;
    private int[][] mA;
    private static int[][] mB;
    private static int[][] res;
    Buffer buffer;

    /**
     * Инициализация
     *
     * @param buffer очередь задач
     * @param a      первая матрица
     * @param b      вторая матрица
     * @param res    результирующая матрица
     */
    ConcurrentQueueProducer(Buffer buffer, int[][] a, int[][] b, int[][] res) {
        this.buffer = buffer;
        this.m = a.length;
        this.n = b[0].length;
        this.p = a[0].length;
        this.o = b.length;
        this.mA = a;
        this.mB = b;
        this.res = res;
    }

    /**
     * Обычное умножение матриц
     */
    public MultiplyInterfece basicMult() {
        return (row) -> {
            System.out.println("Работает " + Thread.currentThread().getName() + " со строкой №" + row + "\n");
            for (int i = 0; i < n; i++)
                for (int j = 0; j < p; j++)
                    res[row][i] += mA[row][j] * mB[j][i];

            return res[row];
        };
    }

    /**
     * Умножение матриц с предварительно транспонированной второй матрицей
     */
    public MultiplyInterfece transposeMult() {
        return (row) -> {
            System.out.println("Работает " + Thread.currentThread().getName() + " со строкой №" + row + "\n");
            for (int i = 0; i < o; i++)
                for (int j = 0; j < p; j++)
                    res[row][i] += mA[row][j] * mB[i][j];

            return res[row];
        };
    }

    /**
     * Запуск потока
     */
    @Override
    public void run() {
        // Инициализация функционального интерфейса лямбда-выражением по вычислению строки
        MultiplyInterfece multiplyInterfece;
        //multiplyInterfece = basicMult();
        multiplyInterfece = transposeMult();

        buffer.put(multiplyInterfece);
    }
}