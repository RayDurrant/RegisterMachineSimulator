package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class DoubleAngleBracketTuple implements AngleBracketTuple {
    private int mX;
    private int mY;

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
    }

    @Override
    public long getInt() {
        return 1 << mX * (2 * mY + 1);
    }

    public DoubleAngleBracketTuple(int x, int y) {
        mX = x;
        mY = y;
    }

    public DoubleAngleBracketTuple(int packed) {
        if (packed == 0) throw new IllegalArgumentException();

        int x;
        for(x = 0; packed % 2 == 0; packed = packed / 2, x++);

        packed = packed >>> 1;

        mX = x;
        mY = packed;
    }
}
