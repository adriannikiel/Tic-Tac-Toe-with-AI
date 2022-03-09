class ArrayOperations {
    public static int[][][] createCube() {
        // write your code here

        int[][][] cube = new int[3][3][3];

        int[] column1 = {0, 1, 2};
        int[] column2 = {3, 4, 5};
        int[] column3 = {6, 7, 8};

        int[][] columns = {
                column1,
                column2,
                column3
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cube[i][j] = columns[j];
            }
        }

        return cube;

    }
}