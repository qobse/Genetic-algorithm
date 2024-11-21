/**
 * Main.java
 * 
 * This class serves as the entry point for running the Genetic Algorithm (GA) to solve the Skyscraper puzzle.
 * It initializes the GA, starts a timer to track the elapsed time, and prints the progress and results of the GA.
 * 
 * Author: Yaqoob Yaghoubi
 * Date: 
 * 
 * Description:
 * This class reads the puzzle file path from the command-line arguments, initializes the GA with specified parameters,
 * and runs the GA. It also tracks the elapsed time using a Timer and prints the fitness progress, generation times,
 * and the best solution found by the GA.
 */

import java.util.Timer;
import java.util.TimerTask;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide a file path to the puzzle file as a command-line argument.");
            return;
        }
        
        String filePath = args[0];
        GeneticAlgorithm2 ga = new GeneticAlgorithm2(380, 0.09, 0.9, 4500);

        // Start the timer in a separate thread
        Timer timer = new Timer();
        class MyTimerTask extends TimerTask {
            private long startTime = System.currentTimeMillis();
            private long elapsedTime = 0;

            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                long milliseconds = elapsed % 1000;
                long seconds = (elapsed / 1000) % 60;
                long minutes = (elapsed / (1000 * 60)) % 60;
                long hours = (elapsed / (1000 * 60 * 60)) % 24;
                elapsedTime = elapsed;
                System.out.printf("\rElapsed time: %02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
            }

            public long getElapsedTime() {
                return elapsedTime;
            }
        }

        MyTimerTask task = new MyTimerTask();

        timer.scheduleAtFixedRate(task, 0, 100); // Update every 1 mili second

        ga.initializePopulation(filePath);

        ga.run();

        timer.cancel(); // Stop the timer when done

        List<Integer> fitnessProgress = ga.getBestFitnessPerGeneration();
        List<Long> generationTimes = ga.getGenerationTimes();
        int convergenceGeneration = ga.getConvergenceGeneration();
        long totalTime = task.getElapsedTime();

        System.out.println("\nFitness progress:");

        for (int i = 0; i < fitnessProgress.size(); i++) {
            System.out.println("Generation " + i + ": " + fitnessProgress.get(i));
            System.out.printf("Generation %d: Fitness = %d, Time = %03d ms%n", i + 1, fitnessProgress.get(i), generationTimes.get(i));
        }

        if (convergenceGeneration != -1) {
            System.out.printf("GA converged at generation %d%n", convergenceGeneration + 1);
        } else {
            System.out.println("GA did not converge within the maximum number of generations.");
        }

        Puzzle2 bestSolution = ga.getBestSolution();
        System.out.println("\nBest solution found with fitness: " + bestSolution.fitness() + ", Total time: " + totalTime + " ms, Convergence generation: " + (convergenceGeneration + 1));

        bestSolution.printBoard();
    }
}
