package model;

import strategy.FoodSpawner;

public class Board {
    private final int rows;
    private final int cols;
    private final Cell[][] cells;

    private FoodSpawner foodSpawner;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid coordinates: row=" + row + ", col=" + col);
        }
        return cells[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setFoodSpawner(FoodSpawner foodSpawner) {
        this.foodSpawner = foodSpawner;
    }

    public void placeFood() {
        if (foodSpawner != null) {
            foodSpawner.spawn(this);
        }
    }

    public void display() {
        for (int row = -1; row <= rows; row++) {
            for (int col = -1; col <= cols; col++) {
                if (row == -1 || row == rows || col == -1 || col == cols) {
                    System.out.print(" + ");
                } else {
                    System.out.print(" " + cells[row][col] + " ");
                }
            }
            System.out.println();
        }
    }
}