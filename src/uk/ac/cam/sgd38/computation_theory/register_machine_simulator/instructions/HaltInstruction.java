package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.MachineState;

public class HaltInstruction implements Instruction {
    
    public static final String ASSEMBLY_CODE = "HALT";
    @Override
    public void execute(MachineState m) {

    }

    @Override
    public String toAssemblyString() {
        return ASSEMBLY_CODE;
    }
    

    public String toString() {
        return ASSEMBLY_CODE;
    }
}
