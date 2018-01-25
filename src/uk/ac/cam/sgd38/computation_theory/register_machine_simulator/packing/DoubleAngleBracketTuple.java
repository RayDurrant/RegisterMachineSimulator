package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing;

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
        if (mX > 63) {
            System.err.println("Warning: Program is large and packed result may not reflect real value");
        }

        return ((1 << mX) * (2 * mY + 1));
    }

    public DoubleAngleBracketTuple(long x, long y) {
        mX = x;
        mY = y;
    }

    public DoubleAngleBracketTuple(long packed) {
        if (packed == 0) throw new IllegalArgumentException();

        int x = 0;
        while (packed % 2 == 0) {
            packed = packed >>> 1;
            x++;
        }

        packed = packed >>> 1;

        mX = x;
        mY = packed;
    }
}
