package gui;

import model.Board;
import model.Cell;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Board board;

    public GamePanel(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.BLACK);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / board.getCols();
        int cellHeight = getHeight() / board.getRows();

        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                Cell cell = board.getCell(r, c);
                switch (cell.getContent()) {
                    case SNAKE -> g.setColor(Color.GREEN);
                    case FOOD -> g.setColor(Color.RED);
                    default -> g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(c * cellWidth, r * cellHeight, cellWidth - 1, cellHeight - 1);
            }
        }
    }
}