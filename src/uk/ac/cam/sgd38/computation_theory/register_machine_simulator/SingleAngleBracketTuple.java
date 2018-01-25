package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

public class SingleAngleBracketTuple extends DoubleAngleBracketTuple {
    public SingleAngleBracketTuple(int x, int y) {
        super(x, y);
    }

    public SingleAngleBracketTuple(int packed) {
        super(packed + 1);
    }

    @Override
    public long getInt() {
        return super.getInt() - 1;
    }
}
