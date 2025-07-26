package model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;

public class Snake {
    private Direction direction;
    private final Deque<Cell> body;

    public Snake(List<Cell> initialCells) {
        body = new ArrayDeque<>();
        for (Cell cell : initialCells) {
            cell.setContent(CellContent.SNAKE);
            body.offer(cell);
        }
        this.direction = Direction.RIGHT;
    }

    public void move(Direction dir, Consumer<Move> consumer) {
        if (body.isEmpty()) return;

        Cell head = body.peekLast();
        int nextRow = head.getRow() + dir.deltaRow();
        int nextCol = head.getCol() + dir.deltaCol();

        Move move = new Move(nextRow, nextCol, dir);
        consumer.accept(move);
    }

    public void move(Cell newHead, Direction direction, boolean grow) {
        body.offer(newHead);
        newHead.setContent(CellContent.SNAKE);

        if (!grow) {
            Cell tail = body.poll();
            if (tail != null) {
                tail.setContent(CellContent.EMPTY);
            }
        }

        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}