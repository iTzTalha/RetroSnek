package model;

public enum CellContent {
    EMPTY(" "),
    SNAKE("X"),
    FOOD("@");

    public final String content;

    CellContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
