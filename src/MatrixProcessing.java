import java.io.*;
import java.util.Random;

public class MatrixProcessing {
    public static void initArray(int[][] arr) {
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = r.nextInt();
            }
        }
    }

    public static void printArray(int[][] arr) {
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

    public static void saveArrayToFile(int[][] arr, String path) {
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

    public static int[][] loadArrayFromFile(String path) {
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
}