package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RegisterMachine {
    Map<Integer, Instruction> mInstructions;

    public RegisterMachine(Map<Integer, Instruction> instructions) {
        mInstructions = instructions;
    }

    public RegisterMachine(int instructions) {
        mInstructions = generateInstructions(instructions);
    }

    public int run(boolean verbose) {
        return run(new TreeMap<Integer, Integer>(), verbose);
    }

    //Currently has bug which may cause indefinite looping
    //TODO: Determine if program will loop indefinitely; if it will do, don't run it
    public int run(Map<Integer, Integer> registers, boolean verbose) {
        MachineState m = new MachineState(registers);

        while (mInstructions.containsKey(m.getCurrentInstruction()) &&
                !(mInstructions.get(m.getCurrentInstruction()) instanceof HaltInstruction)) {
            if (verbose)
                System.out.println(m.printMachineState());
            mInstructions.get(m.getCurrentInstruction()).execute(m);
        }

        if (verbose) {
            System.out.println(m.printMachineState());
            if (!mInstructions.containsKey(m.getCurrentInstruction()))
                System.out.println("Erroneous halt");
            else
                System.out.println("Clean halt");
        }

        //By convention, R0 is result of register machine
        return m.getRegister(0);
    }

    public String printInstructions() {
        List<String> instructionList = new ArrayList<>();

        for (Map.Entry instruction : mInstructions.entrySet()) {
            instructionList.add("L_" + instruction.getKey().toString() + ": " + instruction.getValue().toString());
        }

        return String.join("\n", instructionList);
    }

    public static Map<Integer, Instruction> generateInstructions(int codedInstructions) {
        int currPackedList = codedInstructions;
        Map<Integer, Instruction> instructionList = new TreeMap<>();
        int i = 0;
        while (currPackedList > 0) {
            AngleBracketTuple unpackedList = new DoubleAngleBracketTuple(currPackedList);
            int currInstruction = unpackedList.getX();

            if (currInstruction == 0) {
                instructionList.put(i, new HaltInstruction());
            }
            else {
                DoubleAngleBracketTuple unpackedInstruction = new DoubleAngleBracketTuple(currInstruction);
                if (unpackedInstruction.getX() % 2 == 0) {
                    //This means we have a register increment operation

                    instructionList.put(i, new IncrementInstruction(unpackedInstruction.getX() / 2, unpackedInstruction.getY()));
                }
                else {
                    int reg = (unpackedInstruction.getX() - 1) / 2;
                    AngleBracketTuple ls = new SingleAngleBracketTuple(unpackedInstruction.getY());
                    int l_1 = ls.getX();
                    int l_2 = ls.getY();
                    instructionList.put(i, new DecrementOrJumpInstruction(reg, l_1, l_2));
                }
            }

            currPackedList = unpackedList.getY();
            i++;
        }

        return instructionList;
    }


    public static void main(String[] args) {
        Map<Integer, Instruction> instructions = new TreeMap<>();

        instructions.put(0, new DecrementOrJumpInstruction(1, 1, 2));
        instructions.put(1, new IncrementInstruction(0, 0));
        instructions.put(2, new DecrementOrJumpInstruction(2, 3, 4));
        instructions.put(3, new IncrementInstruction(0, 2));
        instructions.put(4, new HaltInstruction());

        Map<Integer, Integer> initialRegisters = new TreeMap<>();
        initialRegisters.put(1, 1);
        initialRegisters.put(2, 2);

        RegisterMachine rm = new RegisterMachine(instructions);

        System.out.println("Instructions:");
        System.out.println(rm.printInstructions());

        System.out.println();

        System.out.println("Program trace");
        rm.run(initialRegisters, true);
    }

}
