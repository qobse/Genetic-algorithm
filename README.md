4           #game size
3 1 3 2     #Top clues (from left to right)
2 1 3 2     #Right clues (from top to bottom)
2 4 1 2     #Bottom clues (from left to right)
2 3 1 2     #Left clues (from top to bottom)

to run:
javac Puzzle.java
than
java Puzzle ./levels/instance1.txt


In the context of a genetic algorithm:

A gene typically represents a single characteristic or attribute. In this case, it represents a cell value.
A chromosome is a collection of genes. Here, it represents a solution for a skyscraper, which is a set of cell values.
A population is a set of chromosomes. In this context, it represents a set of solutions for a skyscraper.
This is a common way to structure a genetic algorithm, where the goal is to evolve a population of potential solutions (chromosomes) over time to find an optimal or near-optimal solution. Each solution is composed of a set of attributes (genes), and the genetic algorithm uses mechanisms similar to natural selection and genetics to evolve the solutions.

TODO:
Finding the optimal parameters for a genetic algorithm (GA) can be a complex task. Here are some strategies you can use:

Grid Search: This is a brute force method where you define a set of possible values for each parameter, and then try out every possible combination of these parameters. For each combination, you run the GA and measure its performance. The combination that gives the best performance is then chosen as the optimal parameters. This method can be very time-consuming, especially if you have many parameters or if the set of possible values for each parameter is large.

Random Search: This is similar to grid search, but instead of trying out every possible combination, you randomly select a certain number of combinations to try out. This method can be less time-consuming than grid search, but there's no guarantee that you'll find the optimal parameters.

Bayesian Optimization: This is a more sophisticated method that builds a probabilistic model of the function mapping from parameters to performance, and then uses this model to select the most promising parameters to try out next. This method can be more efficient than both grid search and random search, but it requires more complex machinery.

Genetic Algorithm: Ironically, you can use a GA to optimize the parameters of your GA. In this meta-GA, each individual is a set of parameters for your GA, and the fitness of an individual is the performance of your GA when run with these parameters. You then use the GA operations (selection, crossover, mutation) to evolve the population of individuals over time, hopefully converging on the optimal parameters.

Gradient-based Optimization: If your performance measure is differentiable with respect to the parameters, you can use gradient-based optimization methods (like gradient descent) to find the optimal parameters. However, this is often not the case for GAs.

Remember that the optimal parameters can depend on the specific problem instance (in this case, the size of the puzzle), so you might need to find different optimal parameters for different puzzle sizes. Also, keep in mind that GAs are stochastic, so the performance can vary between runs even with the same parameters. Therefore, you should run the GA multiple times with the same parameters and average the performance to get a reliable estimate.