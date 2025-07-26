package gui;

import model.*;
import strategy.NaiveFoodSpawner;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class GameFrame extends JFrame implements KeyListener {
    private Board board;
    private Snake snake;
    private GameState state;

    private final GamePanel panel;
    private Timer timer;

    public GameFrame() {
        initialize();

        panel = new GamePanel(board);
        add(panel);
        pack();
        setTitle("RetroSnek");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        addKeyListener(this);

        start();
    }

    private void initialize() {
        board = new Board(15, 15);

        snake = new Snake(Arrays.asList(
                board.getCell(7, 1),
                board.getCell(7, 2),
                board.getCell(7, 3)
        ));

        board.setFoodSpawner(new NaiveFoodSpawner());
        board.placeFood();

        state = GameState.INITIALIZED;
    }

    public void start() {
        state = GameState.STARTED;

        timer = new Timer(100, _ -> doStart());
        timer.start();
    }

    private void doStart() {
        panel.repaint();
        if (state == GameState.STARTED) {
            snake.move(snake.getDirection(), move -> {
                int row = move.row();
                int col = move.col();

                if (row < 0 || col < 0 || row >= board.getRows() || col >= board.getCols()) {
                    state = GameState.OVER;
                } else {
                    Cell next = board.getCell(row, col);
                    if (next.getContent() == CellContent.SNAKE) {
                        state = GameState.OVER;
                    } else {
                        boolean ateFood = next.getContent() == CellContent.FOOD;
                        snake.move(next, snake.getDirection(), ateFood);

                        if (ateFood) {
                            board.placeFood();
                        }
                    }
                }
            });

        } else if (state == GameState.OVER) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "GameFrame Over! Press R to reset or Esc to quit.");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (state == GameState.STARTED) {
            Direction current = snake.getDirection();
            Direction newDir = switch (code) {
                case KeyEvent.VK_UP -> Direction.UP;
                case KeyEvent.VK_DOWN -> Direction.DOWN;
                case KeyEvent.VK_LEFT -> Direction.LEFT;
                case KeyEvent.VK_RIGHT -> Direction.RIGHT;
                default -> current;
            };

            snake.setDirection(newDir);
        }

        switch (code) {
            case KeyEvent.VK_P -> {
                if (state == GameState.STARTED) {
                    state = GameState.PAUSED;
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "GameFrame Paused. Press R to resume.");
                }
            }
            case KeyEvent.VK_R -> {
                if (state == GameState.PAUSED) {
                    state = GameState.STARTED;
                    timer.start();
                } else if (state == GameState.OVER) {
                    initialize();
                    panel.setBoard(board);
                    state = GameState.STARTED;
                    timer.start();
                }
            }
            case KeyEvent.VK_ESCAPE -> {
                System.exit(0);
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}