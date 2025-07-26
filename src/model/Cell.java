package model;

import java.util.Objects;

public class Cell {
    private CellContent content;
    private final int row;
    private final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = CellContent.EMPTY;
    }

    public CellContent getContent() {
        return this.content;
    }

    public void setContent(CellContent content) {
        this.content = content;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col && content == cell.content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, row, col);
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
