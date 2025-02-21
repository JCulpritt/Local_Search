package solutions;

import core_algorithms.GeneticAlgorithm;
import core_algorithms.Individual;
import problems.NQueens;

import java.util.Arrays;
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
}
