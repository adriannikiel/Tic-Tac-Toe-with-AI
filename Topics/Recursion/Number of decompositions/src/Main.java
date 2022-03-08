import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        partition(number, number, "");
    }

    private static void partition(int number, int max, String prefix) {
        if (number == 0) {
            System.out.println(prefix.trim());
        }
        for (int i = 1; i <= Math.min(max, number); i++) {
            partition(number - i, i, prefix + " " + i);
        }
    }

}