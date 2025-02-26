package core_algorithms;

import java.util.Random;

import java.util.*;

public abstract class GeneticAlgorithm<G> {

    private final int MAX_GEN;
    private final double MUTATION;
    private final double ELITISM;

    public GeneticAlgorithm(int maxGen, double mutation, double elitism){
        this.MAX_GEN = maxGen;
        this.MUTATION = mutation;
        this.ELITISM = elitism;

    }
    //FIRST SELECTION IDEA
    public Individual<G> select(List<Individual<G>> population, Individual<G> individual) {
        double sum = 0;
        for(Individual<G> i : population) {
            sum += 1.0 / i.getFitnessScore();
        }
        Individual<G> selected = null;
        do {
            double v = new Random().nextDouble(sum);
            double cumulativeSum = 0;
            for(Individual<G> i : population) {
                cumulativeSum += 1.0 / i.getFitnessScore();
                if(v <= cumulativeSum) {
                    selected = i;
                    break;
                }
            }
        } while(selected == individual);
        return selected;
    }


    //SECOND SELECTION IDEA
    public Individual<G> select(List<Individual<G>> population, Individual<G> individual, double K) {
        Random r = new Random();
        int tournamentSize = (int) (K * population.size());

        Individual<G> best = null;

        for (int i = 0; i < tournamentSize; i++) {
            Individual<G> candidate = population.get(r.nextInt(population.size()));
            if (best == null || (candidate.getFitnessScore() < best.getFitnessScore() && candidate != individual)) {
                best = candidate;
            }
        }
        return best;
    }


    public abstract Individual<G> reproduce(Individual<G> p, Individual<G> q);

    public abstract Individual<G> mutate(Individual<G> individual);

    public abstract double calcFitnessScore(G chromosome);
    public Individual<G> evolve (List<Individual<G>> initPopulation, double K){
        List<Individual<G>> population = initPopulation;
        population.sort(Comparator.comparingDouble(Individual::getFitnessScore));
        int bestGen = 0;
        Individual<G> best = population.getFirst();
        for (int generation = 1; generation <= MAX_GEN; generation++) {
            List<Individual<G>> offspring = new ArrayList<>();
            for (int i=0; i<population.size(); i++) {
                Individual<G> p;
                Individual<G> q;
                if(K >= 1) {
                    p = select(population, null, K);
                    q = select(population, p, K);
                }
                else {
                    p = select(population, null);
                    q = select(population, p);
                }
                Individual<G> child = reproduce(p, q);
                if (new Random().nextDouble() <= MUTATION) {
                    child = mutate(child);
                }
                offspring.add(child);
            }
            population.sort(Comparator.comparingDouble(Individual::getFitnessScore));
            List<Individual<G>> newPopulation = new ArrayList<>();
            int e = (int) (ELITISM * population.size());
            for (int i=0; i<e; i++) {
                newPopulation.add(population.get(i));
            }
            for (int i=0; i<population.size()-e; i++) {
                newPopulation.add(offspring.get(i));
            }
            population = newPopulation;
            population.sort(Comparator.comparingDouble(Individual::getFitnessScore));
            //I changed this below statement because it was picking out the largest fitness score which is actually worse
            if (population.getFirst().getFitnessScore() < best.getFitnessScore()){
                best = population.getFirst();
                bestGen = generation;
            }
        }
        System.out.println("best gen: "+bestGen);
        return population.getFirst();
    }
}
