package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing.AngleBracketTuple;

public class DoubleAngleBracketTuple implements AngleBracketTuple {
    private long mX;
    private long mY;

    @Override
    public long getX() {
        return mX;
    }

    @Override
    public long getY() {
        return mY;
    }

    @Override
    public long getPacked() {
        return ((1 << mX) * (2 * mY + 1));
    }

    public DoubleAngleBracketTuple(long x, long y) {
        mX = x;
        mY = y;
    }

    public DoubleAngleBracketTuple(long packed) {
        if (packed == 0) throw new IllegalArgumentException();

        int x;
        for(x = 0; packed % 2 == 0; packed = packed / 2, x++);

        packed = packed >>> 1;

        mX = x;
        mY = packed;
    }
}
