package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.RegisterMachine;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.*;

import java.util.ArrayList;
import java.util.List;

public class Unpacker {

    public static List<Instruction> unpackInstructions(long codedInstructions) {
        long currPackedList = codedInstructions;
        List<Instruction> instructionList = new ArrayList<>();
        while (currPackedList > 0) {
            AngleBracketTuple unpackedList = new DoubleAngleBracketTuple(currPackedList);
            long currInstruction = unpackedList.getX();

            if (currInstruction == 0) {
                instructionList.add(new HaltInstruction());
            }
            else {
                DoubleAngleBracketTuple unpackedInstruction = new DoubleAngleBracketTuple(currInstruction);
                if (unpackedInstruction.getX() % 2 == 0) {
                    //This means we have a register increment operation

                    instructionList.add(new IncrementInstruction((int)unpackedInstruction.getX() / 2, (int)unpackedInstruction.getY()));
                }
                else {
                    int reg = (int)(unpackedInstruction.getX() - 1) / 2;
                    AngleBracketTuple ls = new SingleAngleBracketTuple(unpackedInstruction.getY());
                    int l_1 = (int)ls.getX();
                    int l_2 = (int)ls.getY();
                    instructionList.add(new DecrementOrJumpInstruction(reg, l_1, l_2));
                }
            }

            currPackedList = unpackedList.getY();
        }

        return instructionList;
    }

    public static void main(String[] args) {
        long packed = 0;

        try {
            packed = Long.parseLong(args[0]);
        }
        catch (NumberFormatException e) {
            System.err.println("Arguments: int [--assembly]\nOr your number is too large");
            System.exit(1);
        }

        String instructionString;

        if (args.length > 1 && args[1].equals("--assembly"))
            instructionString = (new RegisterMachine(unpackInstructions(packed))).printAssembly();
        else
            instructionString = (new RegisterMachine(unpackInstructions(packed))).printInstructions();

        System.out.println(instructionString);

    }

}
