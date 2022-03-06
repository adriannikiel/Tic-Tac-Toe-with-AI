package tictactoe;

public enum Commands {
    START("start"), EXIT("exit"), EASY("easy"), USER("user");

    final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
