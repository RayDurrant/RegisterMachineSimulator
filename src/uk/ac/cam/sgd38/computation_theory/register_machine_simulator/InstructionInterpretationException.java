package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

public class InstructionInterpretationException extends Exception {
    public InstructionInterpretationException(int lineNumber) {
        super("There is an error in formatting the instructions. Check line " + Integer.toString(lineNumber));
    }
}