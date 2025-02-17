package problems;

import java.net.SocketOption;
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

    public double goalCost() {
        return 0;
    }

    public int[] getInitState(){
        Random r = new Random();
        int[] state = new int[N];
        for(int i = 0; i < N; i++) {
            state[i] = r.nextInt(N);
        }
        return state;
    }

    public void printState(int[] state) {
        for(int row = 0; row < N; row++) {
            for(int col = 0; col < N; col++) {
                if(state[col] == row){
                    System.out.print("Q");
                }
                else{
                    System.out.print("*");
                }
            }
            System.out.print("\n");
        }
    }
}
