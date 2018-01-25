package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

import org.apache.commons.cli.Options;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.DecrementOrJumpInstruction;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.HaltInstruction;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.IncrementInstruction;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.Instruction;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing.Unpacker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

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

    public static void main(String[] args) {
        List<Instruction> l = null;
        List<Integer> regs = new ArrayList<>();

        try {
            String regString;

            if (args[0].equals("--packed")) {
                long instrs = Long.parseLong(args[1]);
                l = Unpacker.unpackInstructions(instrs);
                regString = args[2];
            }
            else {
                String path = args[0];
                l = InstructionInterpreter.getInstructionsFromFile(path);
                regString = args[1];
            }

            String[] registersAsString = regString.split(", *");

            for (String reg: registersAsString)
                regs.add(Integer.parseInt(reg));

        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find file " + args[0]);
            System.exit(2);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        catch (InstructionInterpretationException e) {
            System.out.println(e.getMessage());
            System.exit(3);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("java Main.class (--packed int)|(filepath) regs");
            System.exit(1);
        }

        RegisterMachine rm = new RegisterMachine(l);

        System.out.println("Instructions:");
        System.out.println(InstructionInterpreter.printInstructions(l));

        System.out.println();
        System.out.println("Program trace");
        int res = rm.run(createRegisters(regs), true);

        System.out.println();
        System.out.println("Result = " + Integer.toString(res));
    }
}
