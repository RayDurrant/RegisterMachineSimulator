package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

public class DecrementOrJumpInstruction implements Instruction{

    private int mNextLabelIfNotZero;
    private int mNextLabelIfZero;
    private int mRegister;

    public DecrementOrJumpInstruction(int register, int labelIfNotZero, int labelIfZero) {
        mRegister = register;
        mNextLabelIfNotZero = labelIfNotZero;
        mNextLabelIfZero = labelIfZero;
    }

    @Override
    public void execute(MachineState m) {
        if (m.getRegister(mRegister) == 0)
            m.setCurrentInstruction(mNextLabelIfZero);
        else {
            m.setRegister(mRegister, m.getRegister(mRegister) - 1);
            m.setCurrentInstruction(mNextLabelIfNotZero);
        }
    }

    public String toString() {
        return "R_" +
                Integer.toString(mRegister) + "- -> L_" +
                Integer.toString(mNextLabelIfNotZero) + ", L_" +
                Integer.toString(mNextLabelIfZero);
    }
}
