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

        int startPos = r.nextInt(1, p.getChromosome().length - 1);
        int endPos = r.nextInt(startPos + 1, p.getChromosome().length);

        int[] childChromosome = new int[p.getChromosome().length];

        for (int i = startPos; i <= endPos; i++) {
            childChromosome[i] = q.getChromosome()[i];
        }

        int currentPos = 0;
        for (int i = 0; i < p.getChromosome().length; i++) {
            if (currentPos == startPos) {
                currentPos = endPos + 1;
            }

            int cityFromP = p.getChromosome()[i];
            if (!contains(childChromosome, cityFromP)) {
                childChromosome[currentPos] = cityFromP;
                currentPos++;
            }
        }
        return new Individual<>(childChromosome, calcFitnessScore(childChromosome));
    }

    private boolean contains(int[] array, int num) {
        for (int i : array) {
            if (i == num) {
                return true;
            }
        }
        return false;
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
        int MAX_GEN = 50;
        double MUTATION_RATE = 0.3;
        int POPULATION_SIZE = 1000;// 500: size of 5 and 6 //
        double ELITISM = 0.3;
        int SIZE = 5;
        double tournament_K = -1;

        TSP problem = new TSP(SIZE);
        GA_TSP agent =
                new GA_TSP(MAX_GEN,MUTATION_RATE,ELITISM,problem);
        Individual<int[]> best =
                agent.evolve(agent.generateInitPopulation(POPULATION_SIZE), tournament_K);

        System.out.println("Best cost is: " + problem.cost(best.getChromosome()));
        System.out.print("Best solution: ");
        problem.printState(best.getChromosome());}
}
