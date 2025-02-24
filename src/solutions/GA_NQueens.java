package solutions;

import core_algorithms.GeneticAlgorithm;
import core_algorithms.Individual;
import problems.NQueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GA_NQueens extends GeneticAlgorithm<int[]> {
    private final NQueens problem;

    public GA_NQueens(int maxGen, double mRate, double elitism, NQueens problem){
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

        int[] childChromosome = Arrays.copyOf(p.getChromosome(), p.getChromosome().length);
        for(int i = 0; i < startPos; i++) {
            childChromosome[i] = q.getChromosome()[i];
        }
        for(int i = endPos + 1; i<q.getChromosome().length; i++) {
            childChromosome[i] = q.getChromosome()[i];
        }
        return new Individual<>(childChromosome, calcFitnessScore(childChromosome));
    }

    public Individual<int[]> mutate(Individual<int[]> individual) {
        int[] chromosome = problem.generateNewState(individual.getChromosome());
        return new Individual<>(chromosome, calcFitnessScore(chromosome));
    }

    public double calcFitnessScore(int[] chromosome){
        return problem.getN()*(problem.getN() - 1) / (double)2 - problem.cost(chromosome);
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
        int MAX_GEN = 200;
        double MUTATION_RATE = 0.1;
        int POPULATION_SIZE = 5000;
        double ELITISM = 0.4;
        int SIZE = 32; //number of queens
        double tournament_K = .2;
        NQueens problem = new NQueens(SIZE);
        GA_NQueens agent =
                new GA_NQueens(MAX_GEN,MUTATION_RATE,ELITISM,problem);
        Individual<int[]> best =
                agent.evolve(agent.generateInitPopulation(POPULATION_SIZE), tournament_K);
        System.out.println("Best solution:");
        problem.printState(best.getChromosome());
        System.out.println("Best cost is: "+problem.cost(best.getChromosome()));
    }
}
