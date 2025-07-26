import model.*;
import strategy.NaiveFoodSpawner;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class Game extends JFrame implements KeyListener {
    private Board board;
    private Snake snake;
    private GameState state;
    private final long gameSpeed;

    public Game(long gameSpeed) {
        this.setTitle("RetroSnek");
        this.setSize(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(this);
        this.gameSpeed = gameSpeed;

        initialize();
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

    private void showMenu() {
        System.out.println("===========================");
        System.out.println("        RetroSnek");
        System.out.println("===========================\n");
        System.out.println("Controls:");
        System.out.println("→ Arrow Keys to move");
        System.out.println("→ P to pause");
        System.out.println("→ R to resume or reset");
        System.out.println("→ Esc to quit\n");

        System.out.println("Menu:");
        System.out.println("1. Start Game");
        System.out.println("2. Quit");

        System.out.print("\nEnter choice: ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine().trim();

        if (input.equals("1")) {
            start();
        } else if (input.equals("2")) {
            System.out.println("Goodbye!");
            System.exit(0);
        } else {
            System.out.println("Invalid input. Try again.\n");
            showMenu();
        }
    }

    public void start() {
        state = GameState.STARTED;
        GameState lastRenderedState = null;

        while (true) {
            if (state == GameState.STARTED) {
                clearConsole();
                board.display();
                lastRenderedState = GameState.STARTED;

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

            } else if (state == GameState.OVER && lastRenderedState != GameState.OVER) {
                clearConsole();
                board.display();
                System.out.println("\nGame Over! Press R to reset or Esc to quit.");
                lastRenderedState = GameState.OVER;

            } else if (state == GameState.PAUSED && lastRenderedState != GameState.PAUSED) {
                clearConsole();
                board.display();
                System.out.println("\nGame paused. Press R to resume or Esc to quit.");
                lastRenderedState = GameState.PAUSED;
            }

            try {
                Thread.sleep(gameSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void reset() {
        System.out.println("Restarting game...");
        initialize();
        state = GameState.STARTED;
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

            if (!newDir.equals(current.opposite())) {
                snake.setDirection(newDir);
            }
        }

        switch (code) {
            case KeyEvent.VK_P -> {
                if (state == GameState.STARTED) {
                    state = GameState.PAUSED;
                    System.out.println("Game Paused");
                }
            }
            case KeyEvent.VK_R -> {
                if (state == GameState.PAUSED || state == GameState.OVER) {
                    reset();
                    System.out.println("Game Reset");
                }
            }
            case KeyEvent.VK_ESCAPE -> {
                System.out.println("Quitting game...");
                System.exit(0);
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        Game game = new Game(200);
        game.showMenu();
    }
}