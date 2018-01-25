package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

public interface Instruction {

    void execute(MachineState m);

    @Override
    String toString();
}
