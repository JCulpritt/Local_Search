package core_algorithms;

public class Individual<G> implements Comparable<Individual<G>> {
    private final G chromosome;

    private final double fitnessScore;

    public Individual(G chromosome, double fitnessScore) {
        this.chromosome = chromosome;
        this.fitnessScore = fitnessScore;
    }

    public G getChromosome() {
        return chromosome;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }
    @Override
    public String toString() {
        return "Individial{" +
                "chromosome=" + chromosome +
                ", firnessScore=" + fitnessScore +
                '}';
    }

    public int compareTo(Individual<G> i) {
        return Double.compare(
                i.getFitnessScore(),
                this.getFitnessScore()
        );
    }
}
