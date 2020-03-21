import java.io.*;
import java.util.Random;

/**
 * Обработка матриц
 */
public class MatrixProcessing {
    /**
     * Инициализация матрицы
     *
     * @param m количество строк
     * @param n количество столбцов
     */
    public int[][] initArray(int m, int n) {
        int[][] arr = new int[m][m];
        Random r = new Random();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = r.nextInt();
            }
        }

        return arr;
    }

    /**
     * Вывод матрицы на консоль
     *
     * @param arr матрица, которую необходимо вывести
     */
    public void printArray(int[][] arr) {
        if(arr == null && arr.length == 0)
            return;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Сохранение матрицы в файл
     *
     * @param arr матрица, которую необходимо сохранить
     * @param path файл или путь к файлу (включая сам файл)
     */
    public void saveArrayToFile(int[][] arr, String path) {
        if(arr == null && arr.length == 0)
            return;

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(String.valueOf(arr.length));
            bw.newLine();
            bw.write(String.valueOf(arr[0].length));
            bw.newLine();
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    bw.write(String.valueOf(arr[i][j]));
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка матрицы из файла
     *
     * @param path файл или путь к файлу (включая сам файл)
     */
    public int[][] loadArrayFromFile(String path) {
        int[][] arr = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            int rows = Integer.parseInt(br.readLine());
            int cols = Integer.parseInt(br.readLine());

            arr = new int[rows][cols];

            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    arr[i][j] = Integer.parseInt(br.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }

    /**
     * Транспонирование матрицы
     *
     * @param arr матрица, которую необходимо транспонировать
     */
    public void transpose(int[][] arr){
        if(arr.length != arr[0].length)
            return;

        int buffer;

        for (int i = 0; i < arr.length; i++)
        {
            for (int j = 0; j < i; j++)
            {
                buffer = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = buffer;
            }
        }
    }
}