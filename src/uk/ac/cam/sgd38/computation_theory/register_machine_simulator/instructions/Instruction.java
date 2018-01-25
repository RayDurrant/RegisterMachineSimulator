package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.MachineState;

public interface Instruction {

    void execute(MachineState m);

    String toAssemblyString();
}
