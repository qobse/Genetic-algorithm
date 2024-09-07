//  provide me with basic GUI implementation of my project below using Java FX.
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.Insets;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class PuzzleGUI extends Application {

    private int gridSize = 4;

    private int[] topClues;
    private int[] rightClues;
    private int[] bottomClues;
    private int[] leftClues;
    

    @Override
    public void start(Stage primaryStage) {

        loadCluesFromFile("levels/instance1_copy_no_size.txt");

        // VBox root = new VBox();
        // root.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        for (int i = 0; i <= gridSize + 1; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints(25); // Width of columns
            grid.getColumnConstraints().add(colConstraints);
            RowConstraints rowConstraints = new RowConstraints(25); // Height of rows
            grid.getRowConstraints().add(rowConstraints);
        }

        // Scene scene = new Scene(root, 1200, 1200);
        TextField[][] slots = new TextField[gridSize][gridSize];
        // Label[][] clues = new Label[SIZE][SIZE];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                TextField slot = new TextField();
                slot.setPrefWidth(40);
                slot.setPrefHeight(40);
                slots[row][col] = slot;
                grid.add(slot, col + 1, row + 1);
            }
        }

        for (int i = 0; i < gridSize; i++) {
            Label topClue = new Label(String.valueOf(topClues[i]));
            grid.add(topClue, i + 1, 0);

            Label rightClue = new Label(String.valueOf(rightClues[i]));
            grid.add(rightClue, gridSize + 1, i + 1);

            Label bottomClue = new Label(String.valueOf(bottomClues[i]));
            grid.add(bottomClue, i + 1, gridSize + 1);

            Label leftClue = new Label(String.valueOf(leftClues[i]));
            grid.add(leftClue, 0, i + 1);
        }

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setTitle("Puzzle GUI");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }

    private void loadCluesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            topClues = parsClueLine(reader.readLine());
            rightClues = parsClueLine(reader.readLine());
            bottomClues = parsClueLine(reader.readLine());
            leftClues = parsClueLine(reader.readLine());

            gridSize = topClues.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] parsClueLine(String line) {
        String[] parts = line.split(" ");
        int[] clues = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            clues[i] = Integer.parseInt(parts[i]);
        }
        return clues;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

