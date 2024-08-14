import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm2 {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int maxGenerations;
    private List<Puzzle2> population;
    private Random random;
    private int generation;
    // private long elapsedTime;
    private List<Integer> bestFitnessPerGeneration;
    private List<Long> generationTimes;
    private int convergenceGeneration; //  to track the convergence generation
    private boolean hasConverged; // to indicate if the GA has converged

    public GeneticAlgorithm2(int populationSize, double mutationRate, double crossoverRate, int maxGenerations) {
        System.out.println("populationSize: " + populationSize + " mutationRate: " + mutationRate + " crossoverRate: "
                + crossoverRate + " maxGenerations: " + maxGenerations);
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.maxGenerations = maxGenerations;
        this.population = new ArrayList<>();
        this.random = new Random();
        this.bestFitnessPerGeneration = new ArrayList<>();
        this.generationTimes = new ArrayList<>();
        this.convergenceGeneration = -1; // Initialize with -1 indicating no convergence yet
        this.hasConverged = false; // Initialize as false
    }

    public void initializePopulation(int gameSize) {
        for (int i = 0; i < populationSize; i++) {
            population.add(new Puzzle2(gameSize));
        }
    }

    public void run() {
        this.generation = 0;
        int noImprovementCount = 0;
        double bestFitness = Double.NEGATIVE_INFINITY;
        double fitnessThreshold = 0;

        while (true) {
            long startTime = System.currentTimeMillis();
            List<Puzzle2> newPopulation = new ArrayList<>();

            while (newPopulation.size() < populationSize) {
                Puzzle2 parent1 = selectParent();
                Puzzle2 parent2 = selectParent();

                List<Puzzle2> children = crossover(parent1, parent2);
                newPopulation.addAll(children);
            }
            for (Puzzle2 individual : newPopulation) {
                mutate(individual);
            }
            population = newPopulation;

            // Check if a solution with a fitness score that meets the threshold has been
            // found
            Puzzle2 bestInGeneration = getBestSolution();

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            bestFitnessPerGeneration.add(bestInGeneration.fitness());
            generationTimes.add(elapsedTime);

            // Check for convergence
            if (!hasConverged && checkConvergence()) {
                convergenceGeneration = generation;
                hasConverged = true;
            }

            if (bestInGeneration.fitness() >= fitnessThreshold) {
                System.out.println("\nSolution found with fitness score meeting the threshold.");
                break;
            }

            // Check if the maximum number of generations has been reached
            if (generation >= maxGenerations) {
                System.out.println("\nMaximum number of generations reached.");
                break;
            }

            // Check if there has been no significant improvement in the best fitness score
            if (bestInGeneration.fitness() > bestFitness) {
                bestFitness = bestInGeneration.fitness();
                noImprovementCount = 0;
            } else {
                noImprovementCount++;
            }
            if (generation > 4000 && noImprovementCount >= 5000) {
                System.out.println(
                        "\nNo significant improvement in the best fitness score for a certain number of generations.");
                break;
            }

            this.generation++;
        }
    }

    private boolean checkConvergence() {

        double bestFitness = bestFitnessPerGeneration.get(bestFitnessPerGeneration.size() - 1);
        double convergenceThreshold = 0;
        return bestFitness >= convergenceThreshold;
    }

    public List<Integer> getBestFitnessPerGeneration() {
        return bestFitnessPerGeneration;
    }

    public List<Long> getGenerationTimes() {
        return generationTimes;
    }

    public int getConvergenceGeneration() {
        return convergenceGeneration;
    }

    public int getGeneration() {
        return generation;
    }

    private Puzzle2 selectParent() {
        Puzzle2 best = null;
        int tournamentSize = 10;
        for (int i = 0; i < tournamentSize; i++) {
            Puzzle2 competitor = population.get(random.nextInt(populationSize));
            if (best == null || competitor.fitness() > best.fitness()) {
                best = competitor;
            }
        }
        return best;
    }

    private List<Puzzle2> crossover(Puzzle2 parent1, Puzzle2 parent2) {
        if (random.nextDouble() > crossoverRate) {
            return List.of(new Puzzle2(parent1.getGameSize()), new Puzzle2(parent2.getGameSize()));
        }
        Puzzle2 child1 = new Puzzle2(parent1.getGameSize());
        Puzzle2 child2 = new Puzzle2(parent2.getGameSize());
        int crossoverPoint = random.nextInt(parent1.getGameSize());
        for (int i = 0; i < parent1.getGameSize(); i++) {
            for (int j = 0; j < parent1.getGameSize(); j++) {
                if (i < crossoverPoint) {
                    child1.setGrid(i, j, parent1.getBoard()[i][j].getValue());
                    child2.setGrid(i, j, parent2.getBoard()[i][j].getValue());
                } else {
                    child1.setGrid(i, j, parent2.getBoard()[i][j].getValue());
                    child2.setGrid(i, j, parent1.getBoard()[i][j].getValue());
                }
            }
        }
        return List.of(child1, child2);
    }

    private void mutate(Puzzle2 individual) {
        for (int i = 0; i < individual.getGameSize(); i++) {
            for (int j = 0; j < individual.getGameSize(); j++) {
                if (random.nextDouble() < mutationRate) {
                    individual.setGrid(i, j, random.nextInt(individual.getGameSize()) + 1);
                }
            }
        }
    }

    public Puzzle2 getBestSolution() {
        return population.stream().max((p1, p2) -> p1.fitness() - p2.fitness()).orElse(null);
    }

    public void printPopulation() {
        for (Puzzle2 puzzle : population) {
            puzzle.printBoard();
            System.out.println("Fitness: " + puzzle.fitness());
        }
    }
}
