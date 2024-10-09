import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.io.File;
import java.io.IOException;

public class PuzzleGUI extends Application {

    private int gridSize = 4;
    private int[] topClues = null;
    private int[] rightClues = null;
    private int[] bottomClues = null;
    private int[] leftClues = null;

    private GeneticAlgorithm2 ga;
    private TextField[][] slots;
    private TextArea outputArea;
    private File loadedPuzzleFile = null;

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
                loadedPuzzleFile = file; // Keep track of the selected file
            }
        });

        // GA controls
        Label popSizeLabel = new Label("Population Size:");
        TextField popSizeInput = new TextField("300");

        Label mutationRateLabel = new Label("Mutation Rate:");
        TextField mutationRateInput = new TextField("0.09");

        Label crossoverRateLabel = new Label("Crossover Rate:");
        TextField crossoverRateInput = new TextField("0.9");

        Label maxGenLabel = new Label("Max Generations:");
        TextField maxGenInput = new TextField("4500");

        Button startGAButton = new Button("Start Genetic Algorithm");
        startGAButton.setOnAction(event -> {
            if (loadedPuzzleFile == null) {
                showAlert("Please load a puzzle file before starting the GA.");
                return;
            }

            try {
                int populationSize = Integer.parseInt(popSizeInput.getText());
                double mutationRate = Double.parseDouble(mutationRateInput.getText());
                double crossoverRate = Double.parseDouble(crossoverRateInput.getText());
                int maxGenerations = Integer.parseInt(maxGenInput.getText());

                ga = new GeneticAlgorithm2(populationSize, mutationRate, crossoverRate, maxGenerations);
                ga.initializePopulation(loadedPuzzleFile.getPath());

                outputArea.appendText("Starting Genetic Algorithm...\n");
                runGAAndDisplayResult(grid);

            } catch (NumberFormatException e) {
                outputArea.appendText("Error: Invalid GA parameters\n");
            }
        });

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Create a VBox to hold GA controls and output
        VBox gaControls = new VBox(10);
        gaControls.setAlignment(Pos.CENTER);
        gaControls.setPadding(new Insets(10, 10, 10, 10));
        gaControls.getChildren().addAll(
                popSizeLabel, popSizeInput,
                mutationRateLabel, mutationRateInput,
                crossoverRateLabel, crossoverRateInput,
                maxGenLabel, maxGenInput,
                startGAButton, outputArea
        );

        // Create a VBox to hold both the button, grid and GA controls
        VBox root = new VBox(10); // Spacing between elements
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(loadFileButton, grid, gaControls);

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setTitle("Puzzle GUI with GA");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.show();
    }

    private void runGAAndDisplayResult(GridPane grid) {
        new Thread(() -> {
            ga.run();
            Puzzle2 bestSolution = ga.getBestSolution();
            // Update the grid in the UI thread
            javafx.application.Platform.runLater(() -> updateGridWithSolution(grid, bestSolution));
            outputArea.appendText("GA completed!\n");
            outputArea.appendText("Best fitness per generation:\n");
            for (int fitness : ga.getBestFitnessPerGeneration()) {
                outputArea.appendText(fitness + "\n");
            }

            outputArea.appendText("\nConverged at generation: " + ga.getConvergenceGeneration() + "\n");
            outputArea.appendText("Total generations: " + ga.getGeneration() + "\n");
        }).start();
    }

    private void updateGridWithSolution(GridPane grid, Puzzle2 solution) {
        Grid2[][] board = solution.getBoard();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                slots[i][j].setText(String.valueOf(board[i][j].getValue()));
            }
        }
    }

    private void populatePuzzleGrid(GridPane grid) {
        grid.getChildren().clear();

        if (topClues != null && rightClues != null && bottomClues != null && leftClues != null) {
            slots = new TextField[gridSize][gridSize];

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

            // Initialize grid cells (slots)
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    TextField slot = new TextField();
                    slot.setPrefWidth(40);
                    slot.setPrefHeight(40);
                    slots[row][col] = slot;
                    grid.add(slot, col + 1, row + 1);

                    // Add event filter to accept only numeric input
                    slot.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            slot.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    });
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
        try {
            Puzzle2 puzzle = new Puzzle2(file.getPath());
            topClues = puzzle.getTopClues();
            rightClues = puzzle.getRightClues();
            bottomClues = puzzle.getBottomClues();
            leftClues = puzzle.getLeftClues();
            gridSize = topClues.length; // Update grid size based on the loaded file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
