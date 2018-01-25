package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.HaltInstruction;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.Instruction;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing.Unpacker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RegisterMachine {
    List<Instruction> mInstructions;

    public RegisterMachine(List<Instruction> instructions) {
        mInstructions = instructions;
    }

    public RegisterMachine(long instructions) {
        mInstructions = Unpacker.unpackInstructions(instructions);
    }

    public int run(boolean verbose) {
        return run(new TreeMap<Integer, Integer>(), verbose);
    }

    //Currently has bug which may cause indefinite looping
    //TODO: Determine if program will loop indefinitely; if it will do, don't run it
    public int run(Map<Integer, Integer> registers, boolean verbose) {
        MachineState m = new MachineState(registers);

        while (m.getCurrentInstruction() < mInstructions.size() &&
                !(mInstructions.get(m.getCurrentInstruction()) instanceof HaltInstruction)) {
            if (verbose)
                System.out.println(m.printMachineState());
            mInstructions.get(m.getCurrentInstruction()).execute(m);
        }

        if (verbose) {
            System.out.println(m.printMachineState());
            if (m.getCurrentInstruction() < mInstructions.size())
                System.out.println("Clean halt");
            else
                System.out.println("Erroneous halt");
        }

        //By convention, R0 is result of register machine
        return m.getRegister(0);
    }

}
