class ArrayOperations {
    public static void reverseElements(int[][] twoDimArray) {
        // write your code here

        for (int i = 0; i < twoDimArray.length; i++) {
            int[] row = twoDimArray[i];

            for (int j = 0; j < row.length / 2; j++) {
                int temp = row[j];
                row[j] = row[row.length - j - 1];
                row[row.length - j - 1] = temp;
            }
        }
    }

/*    public static void main(String[] args) {

        int[][] twoDimArray = {
                {0, 0, 9, 9},
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        };

        reverseElements(twoDimArray);

    }*/
}
