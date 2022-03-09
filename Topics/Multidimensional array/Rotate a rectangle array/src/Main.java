import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] matrix = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        int[][] rotatedMatrix = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int rowIndex = n - 1 - j;
                int columnIndex = i;
                rotatedMatrix[i][j] = matrix[rowIndex][columnIndex];
            }
        }

        // print matrix
        for (int[] row : rotatedMatrix) {
            String rowToPrint = Arrays.toString(row)
                    .replace(",", "")
                    .replace("[", "")
                    .replace("]", "")
                    .trim();
            System.out.println(rowToPrint);
        }
    }
}