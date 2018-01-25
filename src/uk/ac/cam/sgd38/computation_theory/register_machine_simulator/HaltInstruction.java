package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

public class HaltInstruction implements Instruction {
    @Override
    public void execute(MachineState m) {

    }

    public String toString() {
        return "HALT";
    }
}
