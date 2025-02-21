package core_algorithms;

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
            sum += i.getFitnessScore();
        }
        Individual<G> selected = null;
        do {
            double v = new Random().nextDouble(sum);
            double cumulativeSum = 0;
            for(Individual<G> i : population) {
                cumulativeSum += i.getFitnessScore();
                if(v <= cumulativeSum) {
                    selected = i;
                    break;
                }
            }
        } while(selected == individual);
        return selected;
    }


    //SECOND SELECTION IDEA
    public Individual<G> select_Tournament(List<Individual<G>> population, Individual<G> individual){
        return individual;
    }

    public abstract Individual<G> reproduce(Individual<G> p, Individual<G> q);

    public abstract Individual<G> mutate(Individual<G> individual);

    public abstract double calcFitnessScore(G chromosome);
    public Individual<G> evolve (List<Individual<G>> initPopulation) {
        List<Individual<G>> population = initPopulation;
        Collections.sort(population);
        int bestGen = 0;
        Individual<G> best = population.get(0);

        for(int generation = 1; generation <= MAX_GEN; generation++) {
            List<Individual<G>> offspring = new ArrayList<>();
            for(int i = 0; i < population.size(); i++) {
                Individual<G> p = select(population, null);
                Individual<G> q = select(population, p);
                Individual<G> child = reproduce(p, q);
                if(new Random().nextDouble() <= MUTATION) {
                    child = mutate(child);
                }
                offspring.add(child);
            }
            Collections.sort(offspring);
            List<Individual<G>> newPopulation = new ArrayList<>();
            int e = (int) (ELITISM * population.size());
            for(int i = 0; i < e; i++) {
                newPopulation.add(population.get(i));
            }
            for(int i = 0; i < population.size()-e; i++) {
                newPopulation.add(offspring.get(i));
            }
            population = newPopulation;
            Collections.sort(population);
            if(population.get(0).getFitnessScore() > best.getFitnessScore()) {
                best = population.get(0);
                bestGen = generation;
            }
        }

        System.out.println("best gen: " + bestGen);
        return population.get(0);
    }
}
