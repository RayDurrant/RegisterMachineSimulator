package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

import com.sun.org.apache.bcel.internal.generic.InstructionList;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InstructionInterpreter {

    public static List<Instruction> getInstructionsFromFile(String path) throws IOException, InstructionInterpretationException {

        List<Instruction> instructionList = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(path));

        String s = in.readLine();
        int lineNum = 1;

        while (s != null) {
            String[] assemblyLine = s.split(" +");
            int register;

            try {
                switch (assemblyLine[0]) {
                    case IncrementInstruction.ASSEMBLY_CODE:
                        register = Integer.parseInt(assemblyLine[1]);
                        int nextInstr = Integer.parseInt(assemblyLine[2]);
                        instructionList.add(new IncrementInstruction(register, nextInstr));
                        break;
                    case DecrementOrJumpInstruction.ASSEMBLY_CODE:
                        register = Integer.parseInt(assemblyLine[1]);
                        int nextIfNonZero = Integer.parseInt(assemblyLine[2]);
                        int nextIfZero = Integer.parseInt(assemblyLine[3]);
                        instructionList.add(new DecrementOrJumpInstruction(register, nextIfNonZero, nextIfZero));
                        break;
                    case HaltInstruction.ASSEMBLY_CODE:
                        instructionList.add(new HaltInstruction());
                        break;
                    default:
                        in.close();
                        throw new InstructionInterpretationException(lineNum);

                }
            }
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                in.close();
                throw new InstructionInterpretationException(lineNum);
            }

            lineNum++;
            s = in.readLine();
        }

        in.close();
        return instructionList;
    }

    public static String printInstructions(List<Instruction> l) {
        List<String> instructionStrings = new ArrayList<>();

        for (int i = 0; i < l.size(); i++) {
            instructionStrings.add("L_" + Integer.toString(i) + ": " + l.get(i).toString());
        }

        return String.join("\n", instructionStrings);
    }

    public static String printAssembly(List<Instruction> l) {
        List<String> instructionStrings = new ArrayList<>();

        for (int i = 0; i < l.size(); i++) {
            instructionStrings.add(l.get(i).toAssemblyString());
        }

        return String.join("\n", instructionStrings);
    }


}
