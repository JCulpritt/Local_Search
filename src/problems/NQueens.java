package problems;

import java.util.Arrays;
import java.util.Random;

public class NQueens implements Problem<int[]>{
    private final int N;
    public NQueens(int n){
        this.N = n;
    }

    public int[] generateNewState(int[] current) {
        Random r =  new Random();
        int column = r.nextInt(N);
        int currrntRow = current[column];
        int newRow;
        do{
            newRow = r.nextInt(N);
        }while(newRow==currrntRow);
        int[] newState = Arrays.copyOf(current, current.length);
        newState[column] = newRow;
        return newState;
    }

    public double cost(int[] state){
        int conflicts = 0;
        for(int i = 0; i < N-1; i++) {
            for(int j=i+1; j<N; j++) {
                //checking all the row conflicts
                if(state[i] == state[j]) {
                    conflicts++;
                }
                //check all the diagonal conflicts
                if(Math.abs(state[i] - state[j]) == Math.abs(i-j)){
                    conflicts++;
                }
            }
        }
        return conflicts;
    }
}
