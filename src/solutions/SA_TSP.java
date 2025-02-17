package solutions;

import core_algorithms.SimulatedAnnealing;
import problems.TSP;

public class SA_TSP extends SimulatedAnnealing<int[]> {
    private final static long INIT_TIME = 1;
    private final static double INIT_TEMP = 1e13;
    private final static long MAX_TIME = 100_000_000;

    public SA_TSP(TSP problem) {
        super(INIT_TIME, INIT_TEMP, problem);
    }

    public double schedule(long time, double temp) {
        return temp*(1-time/(double)MAX_TIME);
    }

    public static void main(String[] args) {
        SA_TSP agent = new SA_TSP(new TSP(5));
        agent.search();
    }
}
