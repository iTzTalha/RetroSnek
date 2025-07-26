package strategy;

import model.Board;
import model.Cell;
import model.CellContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NaiveFoodSpawner implements FoodSpawner {
    private final Random random = new Random();

    @Override
    public void spawn(Board board) {
        List<Cell> emptyCells = new ArrayList<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Cell cell = board.getCell(row, col);
                if (cell.getContent() == CellContent.EMPTY) {
                    emptyCells.add(cell);
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            Cell randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
            randomCell.setContent(CellContent.FOOD);
        }
    }
}
