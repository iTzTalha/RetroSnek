package model;

public record Move(int row, int col, Direction direction) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return row == move.row && col == move.col && direction == move.direction;
    }
}