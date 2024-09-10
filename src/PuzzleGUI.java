import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PuzzleGUI extends Application {

    private int gridSize = 4;

    private int[] topClues = null;
    private int[] rightClues = null;
    private int[] bottomClues = null;
    private int[] leftClues = null;

    @Override
    public void start(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        // File chooser button
        Button loadFileButton = new Button("Load Puzzle File");
        loadFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Puzzle File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show the open dialog and get the file selected
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                loadCluesFromFile(file);
                populatePuzzleGrid(grid); // Refresh the grid after loading clues
            }
        });

        // Create a VBox to hold both the button and the puzzle grid
        VBox root = new VBox(10); // Spacing between elements
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(loadFileButton, grid);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Puzzle GUI");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }

    private void populatePuzzleGrid(GridPane grid) {
        // Clear only the grid slots and clues, but keep the button intact
        grid.getChildren().clear();

        // Populate the grid only if clues are not null
        if (topClues != null && rightClues != null && bottomClues != null && leftClues != null) {
            // Re-initialize the grid slots with the updated grid size
            for (int i = 0; i <= gridSize + 1; i++) {
                ColumnConstraints colConstraints = new ColumnConstraints(25); // Width of columns
                if (grid.getColumnConstraints().size() <= i) {
                    grid.getColumnConstraints().add(colConstraints);
                }
                RowConstraints rowConstraints = new RowConstraints(25); // Height of rows
                if (grid.getRowConstraints().size() <= i) {
                    grid.getRowConstraints().add(rowConstraints);
                }
            }

            TextField[][] slots = new TextField[gridSize][gridSize];

            // Initialize grid cells (slots)
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    TextField slot = new TextField();
                    slot.setPrefWidth(40);
                    slot.setPrefHeight(40);
                    slots[row][col] = slot;
                    grid.add(slot, col + 1, row + 1);
                }
            }

            // Add the clues to the grid
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
        }
    }

    private void loadCluesFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            topClues = parsClueLine(reader.readLine());
            rightClues = parsClueLine(reader.readLine());
            bottomClues = parsClueLine(reader.readLine());
            leftClues = parsClueLine(reader.readLine());

            gridSize = topClues.length; // Update grid size based on the loaded file
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
