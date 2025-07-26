package model;

public enum Direction {
    RIGHT(0, 1),
    LEFT(0, -1),
    UP(-1, 0),
    DOWN(1, 0);

    private final int deltaRow;
    private final int deltaCol;

    Direction(int row, int col) {
        this.deltaRow = row;
        this.deltaCol = col;
    }

    public int deltaRow() {
        return deltaRow;
    }

    public int deltaCol() {
        return deltaCol;
    }

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
