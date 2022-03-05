class ArrayOperations {
    public static void printCorners(int[][] twoDimArray) {
        // write your code here
        int indexFirstColumn = 0;
        int indexLastColumn = twoDimArray[0].length - 1;
        int indexFirstRow = 0;
        int indexLastRow = twoDimArray.length - 1;

        System.out.printf("%d %d\n", twoDimArray[indexFirstRow][indexFirstColumn], twoDimArray[indexFirstRow][indexLastColumn]);
        System.out.printf("%d %d", twoDimArray[indexLastRow][indexFirstColumn], twoDimArray[indexLastRow][indexLastColumn]);

    }
}