package uk.ac.cam.sgd38.computation_theory.register_machine_simulator.instructions;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.InstructionInterpretationException;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.MachineState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Instruction {

    void execute(MachineState m);

    String toAssemblyString();

    static List<Instruction> getInstructionsFromFile(String path) throws IOException, InstructionInterpretationException {

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
}
