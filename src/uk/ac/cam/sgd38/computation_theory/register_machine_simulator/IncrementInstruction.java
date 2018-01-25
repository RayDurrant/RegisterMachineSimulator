package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

public class IncrementInstruction implements Instruction {

    private int mRegister;
    private int mNextLabel;

    public IncrementInstruction(int register, int nextLabel) {
        mRegister = register;
        mNextLabel = nextLabel;
    }

    @Override
    public void execute(MachineState m) {
        m.setRegister(mRegister, m.getRegister(mRegister) + 1);
        m.setCurrentInstruction(mNextLabel);
    };

    public String toString() {
        return "R_" + Integer.toString(mRegister) + "+ -> L_" + Integer.toString(mNextLabel);
    }


}
