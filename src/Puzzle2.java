import java.util.Random;

import src.Grid2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Puzzle2 {
    private Grid2[][] board;
    private int gameSize;
    private static final int EMPTY = 0;
    private static int[] topClues, bottomClues, leftClues, rightClues;

    public Puzzle2(int gameSize) {
        this.gameSize = gameSize;
        this.board = new Grid2[gameSize][gameSize];
        initializeBoard();
    }

    public Puzzle2(String filePath) throws IOException {
        if (topClues == null || bottomClues == null || leftClues == null || rightClues == null) {
            loadPuzzleFromFile(filePath);
        }
        this.gameSize = topClues.length; // Assuming all clues have the same length
        this.board = new Grid2[gameSize][gameSize];
        initializeBoard();
        loadPuzzleFromFile(filePath);
    }

    public void loadPuzzleFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            topClues = parseClueLine(reader.readLine());
            rightClues = parseClueLine(reader.readLine());
            bottomClues = parseClueLine(reader.readLine());
            leftClues = parseClueLine(reader.readLine());

            initializeBoard();
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
