import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Puzzle2 {
    private Grid2[][] board;
    private int gameSize;
    private static final char EMPTY_CELL = '.';
    private static final int EMPTY = 0;
    private int[] topClues, bottomClues, leftClues, rightClues; // Clues for the puzzle2

    // default constructor
    public Puzzle2() {
        this.gameSize = 4;
        this.board = new Grid2[gameSize][gameSize];
        initializeBoard();
    }

    public Puzzle2(int gameSize) {
        this.gameSize = gameSize;
        //////////////////////////// 9x9
        // test 1
        // this.topClues = new int[] { 3, 4, 2, 2, 1, 3, 3, 3, 5 };
        // this.rightClues = new int[] { 3, 4, 3, 3, 3, 3, 2, 1, 2 };
        // this.bottomClues = new int[] { 3, 1, 3, 5, 5, 3, 3, 2, 2 };
        // this.leftClues = new int[] { 4, 2, 3, 3, 1, 4, 4, 4, 2 };
        // //////////////////////////// 8x8
        // test 2
        // this.topClues = new int[] { 3, 1, 5, 2, 2, 4, 2, 4 };
        // this.rightClues = new int[] { 4, 2, 2, 5, 2, 3, 3, 1 };
        // this.bottomClues = new int[] { 4, 4, 2, 2, 3, 3, 3, 1 };
        // this.leftClues = new int[] { 2, 2, 4, 1, 3, 2, 3, 4 };
        //////////////////////////// 7x7
        // test 7
        // this.topClues = new int[] { 2, 1, 5, 2, 3, 3, 3 };
        // this.rightClues = new int[] { 3, 4, 4, 1, 2, 2, 3 };
        // this.bottomClues = new int[] { 3, 6, 1, 2, 2, 2, 3 };
        // this.leftClues = new int[] { 2, 3, 1, 5, 3, 3, 2 };

        // test 6
        // this.topClues = new int[] { 3, 2, 3, 4, 6, 1, 7 };
        // this.rightClues = new int[] { 1, 3, 2, 5, 2, 3, 2 };
        // this.bottomClues = new int[] { 3, 3, 3, 2, 2, 1, 3 };
        // this.leftClues = new int[] { 3, 2, 2, 1, 3, 3, 4 };

        // test 5
        // this.topClues = new int[] { 3, 2, 2, 3, 1, 3, 2 };
        // this.rightClues = new int[] { 2, 4, 2, 3, 1, 3, 3 };
        // this.bottomClues = new int[] { 1, 4, 3, 2, 6, 2, 2 };
        // this.leftClues = new int[] { 2, 2, 4, 2, 3, 4, 1 };

        // test 4
        // this.topClues = new int[] { 1, 2, 2, 3, 3, 2, 4 };
        // this.rightClues = new int[] { 3, 3, 3, 2, 3, 2, 1 };
        // this.bottomClues = new int[] { 5, 2, 3, 4, 2, 2, 1 };
        // this.leftClues = new int[] { 1, 2, 2, 3, 3, 3, 3 };

        // test 3
        // this.topClues = new int[] { 4, 1, 2, 3, 3, 2, 2 };
        // this.rightClues = new int[] { 3, 1, 4, 2, 2, 3, 3 };
        // this.bottomClues = new int[] { 1, 3, 4, 2, 2, 3, 4 };
        // this.leftClues = new int[] { 2, 3, 2, 3, 4, 3, 1 };

        // this.topClues = new int[] { 2, 1, 5, 5, 3, 3, 2 };
        // this.rightClues = new int[] { 4, 1, 2, 3, 2, 2, 4 };
        // this.bottomClues = new int[] { 1, 3, 2, 2, 2, 4, 5 };
        // this.leftClues = new int[] { 2, 5, 3, 4, 3, 3, 1 };
        //////////////////////////// 6x6
        // this.topClues = new int[] { 1, 4, 2, 2, 2, 3 };
        // this.rightClues = new int[] { 4, 2, 2, 4, 1, 2 };
        // this.bottomClues = new int[] { 2, 1, 3, 4, 3, 2 };
        // this.leftClues = new int[] { 1, 3, 3, 2, 6, 2 };

        // test 6
        // this.topClues = new int[] { 4, 2, 2, 3, 4, 1 };
        // this.rightClues = new int[] { 1, 4, 2, 2, 2, 3 };
        // this.bottomClues = new int[] { 1, 2, 4, 2, 2, 4 };
        // this.leftClues = new int[] { 3, 3, 2, 3, 2, 1 };

        // test 5
        // this.topClues = new int[] { 4, 3, 4, 1, 2, 3 };
        // this.rightClues = new int[] { 3, 2, 1, 4, 3, 4 };
        // this.bottomClues = new int[] { 1, 2, 2, 3, 2, 3 };
        // this.leftClues = new int[] { 3, 4, 4, 2, 2, 1 };

        // test 4
        // this.topClues = new int[] { 2, 2, 5, 2, 3, 1 };
        // this.rightClues = new int[] { 1, 3, 2, 2, 4, 4 };
        // this.bottomClues = new int[] { 2, 4, 1, 2, 2, 3 };
        // this.leftClues = new int[] { 2, 2, 3, 2, 1, 2 };

        // test 3
        // difficult
        // this.topClues = new int[] { 4, 3, 2, 3, 2, 1 };
        // this.rightClues = new int[] { 1, 2, 2, 2, 4, 3 };
        // this.bottomClues = new int[] { 1, 3, 2, 3, 2, 3 };
        // this.leftClues = new int[] { 4, 3, 2, 3, 2, 1 };

        // test 2
        // this.topClues = new int[] { 2, 2, 3, 3, 3, 1 };
        // this.rightClues = new int[] { 1, 3, 3, 2, 2, 4 };
        // this.bottomClues = new int[] { 1, 2, 3, 2, 2, 3 };
        // this.leftClues = new int[] { 2, 2, 3, 3, 3, 1 };
        //////////////////////////// 5x5
        // test 6
        // this.topClues = new int[] { 1, 2, 5, 2, 3 };
        // this.rightClues = new int[] { 3, 2, 3, 1, 3 };
        // this.bottomClues = new int[] { 3, 2, 1, 2, 2 };
        // this.leftClues = new int[] { 1, 3, 2, 3, 2 };

        // test 5
        // this.topClues = new int[] { 3, 1, 2, 4, 2 };
        // this.rightClues = new int[] { 3, 3, 3, 1, 2 };
        // this.bottomClues = new int[] { 3, 5, 3, 1, 2 };
        // this.leftClues = new int[] { 2, 3, 1, 2, 2 };

        // test 4
        // this.topClues = new int[] { 2, 2, 1, 2, 3 };
        // this.rightClues = new int[] { 3, 2, 5, 1, 2 };
        // this.bottomClues = new int[] { 2, 3, 3, 1, 2 };
        // this.leftClues = new int[] { 2, 2, 1, 3, 2 };

        // test 3
        // this.topClues = new int[] { 5, 1, 2, 3, 3 };
        // this.rightClues = new int[] { 3, 2, 1, 2, 3 };
        // this.bottomClues = new int[] { 1, 3, 3, 2, 2 };
        // this.leftClues = new int[] { 2, 2, 3, 2, 1 };

        // test 2
        // this.topClues = new int[] { 5, 2, 1, 2, 2 };
        // this.rightClues = new int[] { 3, 2, 1, 2, 2 };
        // this.bottomClues = new int[] { 1, 2, 3, 4, 2 };
        // this.leftClues = new int[] { 3, 4, 3, 2, 1 };

        // this.topClues = new int[] { 2, 1, 2, 3, 4 };
        // this.rightClues = new int[] { 3, 3, 2, 4, 1 };
        // this.bottomClues = new int[] { 2, 3, 2, 3, 1 };
        // this.leftClues = new int[] { 2, 3, 3, 1, 2 };

        //////////////////////////// 4x4
        // this.topClues = new int[] { 3, 1, 3, 2 };
        // this.rightClues = new int[] { 2, 1, 3, 2 };
        // this.bottomClues = new int[] { 2, 4, 1, 2 };
        // this.leftClues = new int[] { 2, 3, 1, 2 };

        // test 7
        // this.topClues = new int[] { 4, 1, 2, 2 };
        // this.rightClues = new int[] { 2, 2, 1, 3 };
        // this.bottomClues = new int[] { 1, 4, 2, 2 };
        // this.leftClues = new int[] { 2, 3, 2, 1 };

        // test 6
        // this.topClues = new int[] { 1, 2, 2, 4 };
        // this.rightClues = new int[] { 4, 2, 2, 1 };
        // this.bottomClues = new int[] { 3, 3, 2, 1 };
        // this.leftClues = new int[] { 1, 2, 3, 3 };

        // test 5
        // this.topClues = new int[] { 2, 4, 1, 3 };
        // this.rightClues = new int[] { 2, 2, 1, 3 };
        // this.bottomClues = new int[] { 2, 1, 2, 2 };
        // this.leftClues = new int[] { 2, 1, 3, 2 };

        // test 4
        // this.topClues = new int[] { 2, 1, 3, 2 };
        // this.rightClues = new int[] { 2, 1, 2, 4 };
        // this.bottomClues = new int[] { 1, 2, 2, 3 };
        // this.leftClues = new int[] { 2, 3, 3, 1 };

        // test 3
        this.topClues = new int[] { 1, 4, 2, 2 };
        this.rightClues = new int[] { 2, 2, 1, 3 };
        this.bottomClues = new int[] { 4, 1, 2, 2 };
        this.leftClues = new int[] { 1, 2, 3, 2 };

        // this.topClues = new int[] { 3, 2, 2, 1 };
        // this.rightClues = new int[] { 1, 2, 4, 2 };
        // this.bottomClues = new int[] { 2, 3, 1, 3 };
        // this.leftClues = new int[] { 4, 2, 1, 2 };

        this.board = new Grid2[gameSize][gameSize];
        initializeBoard();

    }

    public void loadPuzzleFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            gameSize = Integer.parseInt(reader.readLine().trim());
            board = new Grid2[gameSize][gameSize];

            topClues = parseClueLine(reader.readLine());
            rightClues = parseClueLine(reader.readLine());
            bottomClues = parseClueLine(reader.readLine());
            leftClues = parseClueLine(reader.readLine());

            initializeBoard(); // Initialize the board with default values
        }
    }

    private int[] parseClueLine(String line) {
        String[] parts = line.trim().split("\\s+");
        int[] clues = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            clues[i] = Integer.parseInt(parts[i]);
        }
        return clues;
    }

    public void setClues(int[] topClues, int[] bottomClues, int[] leftClues, int[] rightClues) {
        this.topClues = topClues;
        this.bottomClues = bottomClues;
        this.leftClues = leftClues;
        this.rightClues = rightClues;
    }

    private void initializeBoard() {
        Random rand = new Random();
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                board[i][j] = new Grid2(rand.nextInt(gameSize) + 1);
            }
        }
    }

    public int fitness() {
        int fitness = 0;
        for (int i = 0; i < gameSize; i++) {
            fitness += fitnessRowOrColumn(i, true);
            fitness += fitnessRowOrColumn(i, false);
        }

        fitness += fitnessClues();

        return fitness;
    }

    private int fitnessRowOrColumn(int index, boolean checkRow) {
        HashSet<Integer> seen = new HashSet<>();
        int fitness = 0;
        for (int i = 0; i < gameSize; i++) {
            int value = checkRow ? board[index][i].getValue() : board[i][index].getValue();
            if (value == EMPTY || seen.contains(value) || value < 1 || value > gameSize) {
                fitness--;
            }
            seen.add(value);
        }
        return fitness;
    }

    private int fitnessClues() {
        int fitness = 0;
        fitness += fitnessCheckClues(topClues, false, true);
        fitness += fitnessCheckClues(bottomClues, false, false);
        fitness += fitnessCheckClues(leftClues, true, true);
        fitness += fitnessCheckClues(rightClues, true, false);
        return fitness;
    }

    private int fitnessCheckClues(int[] clues, boolean checkRow, boolean fromStart) {
        int fitness = 0;
        for (int line = 0; line < gameSize; line++) {
            int maxSeen = 0;
            int seenCount = 0;
            for (int pos = 0; pos < gameSize; pos++) {
                int idx = fromStart ? pos : gameSize - 1 - pos;
                int value = checkRow ? board[line][idx].getValue() : board[idx][line].getValue();
                if (value > maxSeen) {
                    maxSeen = value;
                    seenCount++;
                }
            }
            if (clues[line] != 0 && seenCount != clues[line]) {
                fitness--;
            }
        }
        return fitness;
    }

    public Grid2[][] getBoard() {
        return board;
    }

    public void setGrid(int row, int col, int value) {
        board[row][col].setValue(value);
    }

    public int getGameSize() {
        return gameSize;
    }

    public void printBoard() {
        StringBuilder sb = new StringBuilder();

        // Print top clues
        sb.append("    ");
        for (int clue : topClues) {
            sb.append(clue).append("   ");
        }
        sb.append("\n");

        // Print top border
        sb.append("  +");
        for (int i = 0; i < gameSize; i++) {
            sb.append("---+");
        }
        sb.append("\n");

        // Print board with left and right clues
        for (int i = 0; i < gameSize; i++) {
            sb.append(leftClues[i]).append(" |");
            for (int j = 0; j < gameSize; j++) {
                sb.append(" ").append(board[i][j].getValue()).append(" |");
            }
            sb.append(" ").append(rightClues[i]).append("\n");

            // Print row border
            sb.append("  +");
            for (int j = 0; j < gameSize; j++) {
                sb.append("---+");
            }
            sb.append("\n");
        }

        // Print bottom clues
        sb.append("    ");
        for (int clue : bottomClues) {
            sb.append(clue).append("   ");
        }
        sb.append("\n");

        System.out.println(sb.toString());
    }
}
