package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing.DoubleAngleBracketTuple;

public class SingleAngleBracketTuple extends DoubleAngleBracketTuple {
    public SingleAngleBracketTuple(long x, long y) {
        super(x, y);
    }

    public SingleAngleBracketTuple(long packed) {
        super(packed + 1);
    }

    @Override
    public long getPacked() {
        return (super.getPacked() - 1);
    }
}
