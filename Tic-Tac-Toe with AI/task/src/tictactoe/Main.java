package tictactoe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String[] state = prepareInitState();

        boolean isExit = false;
        while (!isExit) {

            Player[] players = new Player[2];
            Command command = getCommand(players);

            switch (command) {
                case START:
                    playGame(state, players[0], players[1]);
                    break;
                case EXIT:
                    isExit = true;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    private static Command getCommand(Player[] players) {

        while (true) {
            System.out.print("Input command: ");
            String[] line = scanner.nextLine().split(" ");

            if (line[0].toUpperCase().equals("EXIT")) {
                return Command.EXIT;
            } else if (line[0].toUpperCase().equals("START")) {
                if (line.length == 3) {

                    int counter = 0;
                    for (Player value : Player.values()) {
                        if (line[1].toUpperCase().equals(value.name())) {
                            players[0] = value;
                            counter++;
                            break;
                        }
                    }

                    for (Player value : Player.values()) {
                        if (line[2].toUpperCase().equals(value.name())) {
                            players[1] = value;
                            counter++;
                            break;
                        }
                    }

                    if (counter == 2) {
                        return Command.START;
                    }

                }
                System.out.println("Bad parameters!");
            }
        }
    }

    private static void playGame(String[] state, Player player1, Player player2) {

        String[][] grid = makeGridFromState(state);
        printGrid(grid);

        boolean isNextRoundForX = checkXnext(state);

        int result = 0;
        while (result == 0) {

            int[] coords = new int[2];

            if (isNextRoundForX) {
                coords = playerMove(state, player1);
                grid[coords[0] - 1][coords[1] - 1] = "X";
                isNextRoundForX = false;
            } else {
                coords = playerMove(state, player2);
                grid[coords[0] - 1][coords[1] - 1] = "O";
                isNextRoundForX = true;
            }

            printGrid(grid);
            state = makeStateFromGrid(grid);
            result = analyzeState(state);
        }
    }

    private static int[] playerMove(String[] state, Player player) {

        switch (player) {
            case USER:
                return userMove(state);
            case EASY:
                return computerMove(state, Player.EASY);
            case MEDIUM:
                return computerMove(state, Player.MEDIUM);

        }
        return new int[0];
    }

    private static int[] userMove(String[] state) {
        boolean inputOK;
        int[] coords = new int[2];

        do {
            System.out.print("Enter the coordinates: ");
            inputOK = analyzeUserInput(state, coords);
        } while (!inputOK);

        return new int[]{coords[0], coords[1]};
    }

    private static int[] computerMove(String[] state, Player player) {
        boolean inputOK = false;
        int[] coords = new int[2];

        if (player == Player.EASY) {
            System.out.println("Making move level \"easy\"");
        } else  if (player == Player.MEDIUM) {
            System.out.println("Making move level \"medium\"");
        }

        do {
            if (player == Player.EASY) {
                inputOK = analyzeEasyInput(state, coords);
            } else if (player == Player.MEDIUM) {
                inputOK = analyzeMediumInput(state, coords);
            } else {
                throw new IllegalArgumentException();
            }
        } while (!inputOK);

        return new int[]{coords[0], coords[1]};
    }

    private static boolean checkXnext(String[] state) {
        int x = countLetterInGrid(state, "X");
        int y = countLetterInGrid(state, "O");

        return x <= y;
    }

    private static String[] prepareInitState() {

        String[] state = new String[9];

        //System.out.print("Enter the cells: ");
        //state  = scanner.nextLine().split("");

        Arrays.fill(state, " ");

        return state;
    }

    public static boolean analyzeUserInput(String[] state, int[] coords) {
        final String OCCUPIED = "This cell is occupied! Choose another one!";
        final String IS_NOT_NUMBER = "You should enter numbers!";
        final String WRONG_COORDINATES = "Coordinates should be from 1 to 3!";

        try {

            int counter = 0;

            do {
                String[] line = scanner.nextLine().split(" ");

                coords[counter] = Integer.parseInt(line[0]);
                counter++;

                if (line.length == 2) {
                    coords[counter] = Integer.parseInt(line[1]);
                    counter++;
                }
            } while (counter < 2);

        } catch (NumberFormatException e) {
            System.out.println(IS_NOT_NUMBER);
            return false;
        }

        int x = coords[0];
        int y = coords[1];

        if (x < 1 || x > 3 || y < 1 || y > 3) {
            System.out.println(WRONG_COORDINATES);
            return false;
        }

        if (isOccupied(state, x, y)) {
            System.out.println(OCCUPIED);
            return false;
        }

        return true;
    }

    public static boolean analyzeEasyInput(String[] state, int[] coords) {

        Random random = new Random();

        coords[0] = 1 + random.nextInt(3);
        coords[1] = 1 + random.nextInt(3);

        return !isOccupied(state, coords[0], coords[1]);
    }

    private static boolean analyzeMediumInput(String[] state, int[] coords) {
        Random random = new Random();

        boolean isComputerX = checkXnext(state);

        boolean isFound;

        if (isComputerX) {
            isFound = coordsToMediumWin(state, "X", coords);
        } else {
            isFound = coordsToMediumWin(state, "O", coords);
        }

        if (!isFound) {
            if (isComputerX) {
                isFound = coordsToMediumWin(state, "O", coords);
            } else {
                isFound = coordsToMediumWin(state, "X", coords);
            }
        }

        if (!isFound) {
            coords[0] = 1 + random.nextInt(3);
            coords[1] = 1 + random.nextInt(3);
        }

        return !isOccupied(state, coords[0], coords[1]);
    }

    public static int analyzeState(String[] state) {

        if (checkImpossible(state)) {
            System.out.println("Impossible");
            return -1;
        }

        if (isWinner(state, "X")) {
            System.out.println("X wins");
            return 1;
        }

        if (isWinner(state, "O")) {
            System.out.println("O wins");
            return 2;
        }

        if (countLetterInGrid(state, "X") + countLetterInGrid(state, "O") == 9) {
            System.out.println("Draw");
            return 3;
        }

        //System.out.println("Game not finished");
        //return 5;
        return 0;
    }

    private static boolean isOccupied(String[] state, int x, int y) {

        String[][] grid = makeGridFromState(state);
        String actual = grid[x - 1][y - 1];

        return !actual.equals(" ") && !actual.equals("_");
    }

    private static boolean checkImpossible(String[] state) {
        int countX = countLetterInGrid(state, "X");
        int countY = countLetterInGrid(state, "O");

        if (Math.abs(countX - countY) > 1) {
            return true;
        }

        boolean isWinnerX = isWinner(state, "X");
        boolean isWinnerY = isWinner(state, "O");

        return isWinnerX && isWinnerY;
    }

    private static boolean isWinner(String[] state, String letter) {
        String[][] grid = makeGridFromState(state);
        int count = 0;

        // check rows
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j].equals(letter)) {
                    count++;
                }
            }

            if (count == 3) {
                return true;
            }

            count = 0;
        }

        // check columns
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i].equals(letter)) {
                    count++;
                }
            }

            if (count == 3) {
                return true;
            }

            count = 0;
        }

        // check diagonals
        if (grid[0][0].equals(letter) && grid[1][1].equals(letter) && grid[2][2].equals(letter)) {
            return true;
        }

        if (grid[0][2].equals(letter) && grid[1][1].equals(letter) && grid[2][0].equals(letter)) {
            return true;
        }

        return false;

    }

    private static boolean coordsToMediumWin(String[] state, String letter, int[] coords) {
        String[][] grid = makeGridFromState(state);
        int countOccupied = 0;
        int countEmpty = 0;
        int xCoord = 0;
        int yCoord = 0;

        // check rows
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j].equals(letter)) {
                    countOccupied++;
                }

                if (grid[i][j].equals(" ") || grid[i][j].equals("_")) {
                    countEmpty++;
                    xCoord = i + 1;
                    yCoord = j + 1;
                }
            }

            if (countOccupied == 2 && countEmpty == 1) {
                coords[0] = xCoord;
                coords[1] = yCoord;
                return true;
            }

            countOccupied = 0;
            countEmpty = 0;
        }

        // check columns
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i].equals(letter)) {
                    countOccupied++;
                }

                if (grid[j][i].equals(" ") || grid[j][i].equals("_")) {
                    countEmpty++;
                    xCoord = j + 1;
                    yCoord = i + 1;
                }
            }

            if (countOccupied == 2 && countEmpty == 1) {
                coords[0] = xCoord;
                coords[1] = yCoord;
                return true;
            }

            countOccupied = 0;
            countEmpty = 0;
        }

        // check diagonals
        if ((grid[0][0].equals(" ") || grid[0][0].equals("_")) && grid[1][1].equals(letter) && grid[2][2].equals(letter)) {
            coords[0] = 1;
            coords[1] = 1;
            return true;
        }

        if (grid[0][0].equals(letter) && (grid[1][1].equals(" ") || grid[1][1].equals("_")) && grid[2][2].equals(letter)) {
            coords[0] = 2;
            coords[1] = 2;
            return true;
        }

        if (grid[0][0].equals(letter) && grid[1][1].equals(letter) && (grid[2][2].equals(" ") || grid[2][2].equals("_"))) {
            coords[0] = 3;
            coords[1] = 3;
            return true;
        }

        if ((grid[0][2].equals(" ") || grid[0][2].equals("_")) && grid[1][1].equals(letter) && grid[2][0].equals(letter)) {
            coords[0] = 1;
            coords[1] = 3;
            return true;
        }

        if (grid[0][2].equals(letter) && (grid[1][1].equals(" ") || grid[1][1].equals("_")) && grid[2][0].equals(letter)) {
            coords[0] = 2;
            coords[1] = 2;
            return true;
        }

        if (grid[0][2].equals(letter) && grid[1][1].equals(letter) && (grid[2][0].equals(" ") || grid[2][0].equals("_"))) {
            coords[0] = 3;
            coords[1] = 1;
            return true;
        }

        return false;
    }

    private static int countLetterInGrid(String[] state, String letter) {

        int result = 0;

        for (String s : state) {
            if (letter.equals(s)) {
                result++;
            }
        }

        return result;
    }

    private static String[][] makeGridFromState(String[] state) {

        return new String[][]{
                {state[0], state[1], state[2]},
                {state[3], state[4], state[5]},
                {state[6], state[7], state[8]}
        };
    }

    private static String[] makeStateFromGrid(String[][] grid) {

        return new String[]{
                grid[0][0], grid[0][1], grid[0][2],
                grid[1][0], grid[1][1], grid[1][2],
                grid[2][0], grid[2][1], grid[2][2]
        };
    }

    public static void printGrid(String[][] grid) {
        System.out.println("---------");

        for (int i = 0; i < grid.length; i++) {

            System.out.print("| ");

            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j] + " ");
            }

            System.out.println("|");
        }

        System.out.println("---------");
    }
}