package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.MachineState;

public class IncrementInstruction implements Instruction {

    public static final String ASSEMBLY_CODE = "INC";

    private int mRegister;
    private int mNextLabel;

    public IncrementInstruction(int register, int nextLabel) {
        mRegister = register;
        mNextLabel = nextLabel;
    }

    public int getRegister() {return mRegister;}
    public int getNextLabel() {return mNextLabel;}

    @Override
    public void execute(MachineState m) {
        m.setRegister(mRegister, m.getRegister(mRegister) + 1);
        m.setCurrentInstruction(mNextLabel);
    }

    @Override
    public String toAssemblyString() {
        return ASSEMBLY_CODE + " " + Integer.toString(mRegister) + " " + Integer.toString(mNextLabel);
    }



    @Override
    public String toString() {
        return "R_" + Integer.toString(mRegister) + "+ -> L_" + Integer.toString(mNextLabel);
    }


}
