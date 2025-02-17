package problems;
import java.util.Random;

public class TSP implements Problem<int[]>{
    private final int NumberOfCities;

    public TSP(int n) {
        NumberOfCities = n;
    }

    public int[] generateNewState(int[] current) {
        Random r = new Random();
        int index1 = r.nextInt(1,NumberOfCities-1);
        int index2 = r.nextInt(1,NumberOfCities-1);
        while (index1 == index2) {
            index2 = r.nextInt(1, NumberOfCities-1);
        }
        return swap(current, index1, index2);

    }

    private int[] swap(int[] state, int i, int j) {
        int[] newState = state.clone();
        newState[i] = newState[i] ^ newState[j];
        newState[j] = newState[i] ^ newState[j];
        newState[i] = newState[i] ^ newState[j];

        return newState;
    }
    public double cost(int[] state) {
        int[][] costMatrix = {
                {0, 3, 4, 2, 7},
                {3, 0, 4, 6, 3},
                {4, 4, 0, 5, 8},
                {2, 6, 5, 0, 6},
                {7, 3, 8, 6, 0}};

        int cost = 0;

        for(int i = 1; i < state.length; i++) {
            int row = state[i-1];
            int col = state[i];
            cost += costMatrix[row][col];

        }
        return cost;
    }

    public int[] getInitState() {
        Random r = new Random();
        int[] state = new int[NumberOfCities + 1];
        for(int i = 0; i < NumberOfCities; i++) {
            state[i] = i;
        }

        for (int i = state.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            swap(state, i, j);
        }
        state[NumberOfCities] = state[0];
        return state;
    }

    public double goalCost() {
        return 0;
    }

    public void printState(int[] state) {
        System.out.print("This is the final tour: ");
        for(int i : state) {
            System.out.printf("%d, ", i);
        }
        System.out.printf("\nThis is the final path cost: %.1f\n", cost(state));
    }
}
