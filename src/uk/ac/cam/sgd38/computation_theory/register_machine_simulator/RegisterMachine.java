package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.*;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing.Packer;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing.Unpacker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class RegisterMachine {
    List<Instruction> mInstructions;

    public RegisterMachine(List<Instruction> instructions) {
        mInstructions = instructions;
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

    public String printInstructions() {
        List<String> instructionStrings = new ArrayList<>();

        for (int i = 0; i < mInstructions.size(); i++) {
            instructionStrings.add("L_" + Integer.toString(i) + ": " + mInstructions.get(i).toString());
        }

        return String.join("\n", instructionStrings);
    }

    public String printAssembly() {
        List<String> instructionStrings = new ArrayList<>();

        for (int i = 0; i < mInstructions.size(); i++) {
            instructionStrings.add(mInstructions.get(i).toAssemblyString());
        }

        return String.join("\n", instructionStrings);
    }

    public long packInstructions() {
        return Packer.packInstructions(mInstructions);
    }

    private static Map<Integer, Integer> createRegisters(List<Integer> l) {
        Map<Integer, Integer> regs = new TreeMap<>();

        regs.put(0, 0);

        int i = 1;
        for (Integer val: l) {
            regs.put(i, val);
            i++;
        }

        return regs;
    }

    public static void main(String[] args) throws IOException {
        List<Instruction> l = null;
        List<Integer> regs = new ArrayList<>();
        String[] regStrings;

        try {
            String regString;

            if (args[0].equals("--packed")) {
                long instrs = Long.parseLong(args[1]);
                l = Unpacker.unpackInstructions(instrs);

                if (args.length > 2)
                    regStrings = Arrays.copyOfRange(args, 2, args.length);
                else regStrings = null;
            }
            else {
                String path = args[0];
                l = Instruction.getInstructionsFromFile(path);
                if (args.length > 1)
                    regStrings = Arrays.copyOfRange(args, 1, args.length);
                else regStrings = null;
            }

            if (regStrings != null) {
                for (String reg : regStrings)
                    regs.add(Integer.parseInt(reg));
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find file " + args[0]);
            System.exit(2);
        }
        catch (InstructionInterpretationException e) {
            System.out.println(e.getMessage());
            System.exit(3);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Arguments: (--packed int)|(filepath) [regs]");
            System.exit(1);
        }

        RegisterMachine rm = new RegisterMachine(l);

        System.out.println("Instructions:");
        System.out.println(rm.printInstructions());

        System.out.println();
        System.out.println("Program trace");
        int res = rm.run(createRegisters(regs), true);

        System.out.println();
        System.out.println("Result = " + Integer.toString(res));
    }

}
