import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        drawStar(n);
    }

    private static void drawStar(int n) {

        char[][] star = new char[n][n];

        for (int i = 0; i < star.length; i++) {
            for (int j = 0; j < star[i].length; j++) {
                star[i][j] = '.';
            }
        }

        int middle = star.length / 2;

        for (int j = 0; j < star.length; j++) {
            star[middle][j] = '*';
        }

        for (int i = 0; i < star.length; i++) {
            star[i][middle] = '*';
        }

        for (int i = 0; i < star.length; i++) {
            star[i][i] = '*';
            star[i][star.length - 1 - i] = '*';
        }


        for (char[] rowStar : star) {
            for (char sign : rowStar) {
                System.out.print(sign + " ");
            }
            System.out.println();
        }
    }
}