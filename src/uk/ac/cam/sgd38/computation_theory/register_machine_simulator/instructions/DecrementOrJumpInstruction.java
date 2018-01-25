package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.MachineState;

public class DecrementOrJumpInstruction implements Instruction {

    public static final String ASSEMBLY_CODE = "DEC";

    private int mNextLabelIfNotZero;
    private int mNextLabelIfZero;
    private int mRegister;

    public int getRegister() {return mRegister;}
    public int getNextLabelIfNotZero() {return mNextLabelIfNotZero;}
    public int getNextLabelIfZero() {return mNextLabelIfZero;}

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

    @Override
    public String toAssemblyString() {
        return ASSEMBLY_CODE + " " + Integer.toString(mRegister) + " " +
                Integer.toString(mNextLabelIfZero) + " "  +
                Integer.toString(mNextLabelIfZero);
    }


    @Override
    public String toString() {
        return "R_" +
                Integer.toString(mRegister) + "- -> L_" +
                Integer.toString(mNextLabelIfNotZero) + ", L_" +
                Integer.toString(mNextLabelIfZero);
    }
}
