package solutions;

import core_algorithms.GeneticAlgorithm;
import core_algorithms.Individual;
import problems.TSP;

import java.util.*;

public class GA_TSP extends GeneticAlgorithm<int[]> {
    private final TSP problem;

    public GA_TSP(int maxGen, double mRate, double elitism, TSP problem){
        super(maxGen, mRate, elitism);
        this.problem = problem;
    }

    public Individual<int[]> reproduce(Individual<int[]> p, Individual<int[]> q) {
        Random r = new Random();
        int startPos = r.nextInt(p.getChromosome().length);
        int endPos = r.nextInt(p.getChromosome().length);

        if(startPos > endPos) {
            int t = startPos;
            startPos = endPos;
            endPos = t;
        }

        int[] childChromosome = new int[p.getChromosome().length];
        Arrays.fill(childChromosome, -1);  // Assuming -1 means "empty" spot in the chromosome

        // Copy the segment from p (between startPos and endPos) into the child
        for (int i = startPos; i <= endPos; i++) {
            childChromosome[i] = p.getChromosome()[i];
        }

        // Use a set to track used genes in the child chromosome
        Set<Integer> usedGenes = new HashSet<>();
        for (int i = startPos; i <= endPos; i++) {
            usedGenes.add(childChromosome[i]);
        }

        // Fill in the remaining positions from q
        int qIndex = 0;
        for (int i = 0; i < childChromosome.length; i++) {
            if (childChromosome[i] == -1) {
                // Skip genes from q that are already in the child
                int initialQIndex = qIndex;  // Remember initial qIndex to prevent infinite loop
                while (usedGenes.contains(q.getChromosome()[qIndex])) {
                    qIndex++;
                    if (qIndex >= q.getChromosome().length) {
                        qIndex = 0;  // Loop back if we reach the end of q
                    }
                    // If we've looped through all genes, break out of the while loop
                    if (qIndex == initialQIndex) {
                        System.out.println("No available genes left to place in the child.");
                        break;
                    }
                }

                // If we were able to find a valid gene, place it in the child
                if (!usedGenes.contains(q.getChromosome()[qIndex])) {
                    childChromosome[i] = q.getChromosome()[qIndex];
                    usedGenes.add(childChromosome[i]);
                }
            }
        }

        // Return the new individual with the child chromosome and its fitness score
        return new Individual<>(childChromosome, calcFitnessScore(childChromosome));
    }

    public Individual<int[]> mutate(Individual<int[]> individual) {
        int[] chromosome = problem.generateNewState(individual.getChromosome());
        return new Individual<>(chromosome, calcFitnessScore(chromosome));
    }

    public double calcFitnessScore(int[] chromosome){
        return problem.cost(chromosome);
    }

    public List<Individual<int[]>> generateInitPopulation(int popSize){
        List<Individual<int[]>> population = new ArrayList<>(popSize);
        for(int i=0; i<popSize; i++){
            int[] chromosome = problem.getInitState();
            population.add(new Individual<>(chromosome,
                    calcFitnessScore(chromosome)));
        }
        return population;
    }

    public static void main(String[] args) {
        int MAX_GEN = 800;
        double MUTATION_RATE = 0.2;
        int POPULATION_SIZE = 1000;
        double ELITISM = 0.6;
        int SIZE = 5; //number of queens
        double tournament_K = .2;
        TSP problem = new TSP(SIZE);
        GA_TSP agent =
                new GA_TSP(MAX_GEN,MUTATION_RATE,ELITISM,problem);
        Individual<int[]> best =
                agent.evolve(agent.generateInitPopulation(POPULATION_SIZE), tournament_K);

        System.out.println("Best cost is: " + problem.cost(best.getChromosome()));
        System.out.print("Best solution: ");
        problem.printState(best.getChromosome());}
}
