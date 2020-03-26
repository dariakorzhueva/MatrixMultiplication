import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Умножение матриц
 */
public class MatrixMultiplication {
    static int m = 0;
    static int n = 0;

    static int[][] mA = null;
    static int[][] mB = null;
    static int[][] res = null;

    /**
     * Точка входа в приложение
     *
     * @param args массив значений в метод с помощью командной строки
     */
    public static void main(String[] args) {
        // Инициализация матриц
        MatrixProcessing matrixProcessing = new MatrixProcessing();
        //mA = matrixProcessing.loadArrayFromFile("matrixA.txt");
        //mB = matrixProcessing.loadArrayFromFile("matrixB.txt");
        mA = matrixProcessing.initArray(500,500);
        mB = matrixProcessing.initArray(500,500);
        m = mA.length;
        n = mB[0].length;
        res = new int[m][n];

        // Если количество столбцов первой матрицы равно количеству строк второй, то производим умножение
        if (mB.length == mA[0].length) {
            // Транспонирование второй матрицы
            //mB = matrixProcessing.transpose(mB);

            System.out.println("Пул потоков: ");
            Scanner s = new Scanner(System.in);
            int threads = s.nextInt();

            if (threads <= 0)
                threads = Runtime.getRuntime().availableProcessors();

            System.out.println("Пул из " + threads + " потоков");

            // Инициализация исполнителя одним потоком
            ExecutorService executor1 = Executors.newFixedThreadPool(threads);
            Buffer buffer = new Buffer();

            // Инициализация и запуск потока-производителя, заполняющего очередь задач
            ConcurrentQueueProducer cqp = new ConcurrentQueueProducer(buffer, mA, mB, res);
            // Помещение функциональных интерфейсов в очередь задач
            for (int i = 0; i < m; i++) {
                executor1.execute(cqp);
            }

            // Ожидание завершения потоков-производителей
            executor1.shutdown();

            try {
                executor1.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            // Инициализация исполнителя фиксированным пулом потоков, содержащим threads потоков
            ExecutorService executor2 = Executors.newFixedThreadPool(threads);

            // Запускаем потоки-исполнители
            long startTime = System.currentTimeMillis();

            ConcurrentQueueConsumer cqc = new ConcurrentQueueConsumer(res, buffer);
            for (int i = 0; i < m; i++) {
                executor2.execute(cqc);
            }

            // Ожидание завершения потоков
            executor2.shutdown();

            try {
                executor2.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            long endTime = System.currentTimeMillis();

            //matrixProcessing.printArray(res);
            //matrixProcessing.saveArrayToFile(res,"res.txt");

            System.out.println("Время выполнения: " + (endTime - startTime) + "ms");
        } else
            System.out.println("Количество столбцов матрицы A не совпадает с количеством столбцов матрицы B");
    }
}
