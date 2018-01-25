package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.InstructionInterpretationException;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.RegisterMachine;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Packer {

    private static long packListElement(long element, long rest) {
        return new DoubleAngleBracketTuple(element, rest).getPacked();
    }

    public static long packList(List<Long> l) {
        long packedList = 0; //empty list

        //we will pack list backwards so it can be unpacked forwards
        for (int i = l.size() - 1; i >= 0; i--) {
            packedList = packListElement(l.get(i), packedList);
        }

        return packedList;
    }

    private static long packInstructionElement(Instruction i) {
        if (i instanceof HaltInstruction) return 0;
        if (i instanceof IncrementInstruction) {
            IncrementInstruction ii = (IncrementInstruction)i;
            return new DoubleAngleBracketTuple(2 * ii.getRegister(), ii.getNextLabel()).getPacked();
        }
        if (i instanceof DecrementOrJumpInstruction) {
            DecrementOrJumpInstruction di = (DecrementOrJumpInstruction)i;
            long z = new SingleAngleBracketTuple(di.getNextLabelIfNotZero(), di.getNextLabelIfZero()).getPacked();
            long t = new DoubleAngleBracketTuple(2 * di.getRegister() + 1, z).getPacked();
            return t;
        }
        else throw new IllegalArgumentException();
    }

    public static long packInstructions(List<Instruction> instructions) {
        List<Long> listOfPackedInstructions = new LinkedList<>();

        for (Instruction i: instructions)
            listOfPackedInstructions.add(packInstructionElement(i));

        return packList(listOfPackedInstructions);
    }

    //This takes a file of instructions, reads it, and returns
    public static void main(String[] args) throws IOException {

        String path = args[0];
        try {
            List<Instruction> l = Instruction.getInstructionsFromFile(path);
            RegisterMachine rm = new RegisterMachine(l);
            System.out.println(Long.toString(rm.packInstructions()));
        }
        catch (FileNotFoundException e) {
            System.out.println("File at path " + path + " was not found.");
            System.exit(2);
        }
        catch(InstructionInterpretationException e) {
            System.out.println(e.getMessage());
            System.exit(3);
        }
    }
}
