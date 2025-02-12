package problems;
//S is the data type of states
public interface Problem<S> {
    public S generateNewState(S current);

    public double cost(S state);

    public S getInitState();

    public double goalCost();

    public void printState(S state);

}
