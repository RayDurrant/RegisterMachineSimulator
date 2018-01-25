package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MachineState {
    private int mCurrentInstruction;
    private Map<Integer, Integer> mRegisters;

    public void setCurrentInstruction(int instruction) {
        mCurrentInstruction = instruction;
    }

    public int getRegister(int register) {
        if (mRegisters.containsKey(register))
            return mRegisters.get(register);
        else return 0;
    }

    public void setRegister(int register, int value) {
        mRegisters.put(register, value);
    }

    public int getCurrentInstruction() {
        return mCurrentInstruction;
    }

    public MachineState(Map<Integer, Integer> registers) {
        mRegisters = new TreeMap<Integer, Integer>();

        for (Map.Entry<Integer, Integer> register: registers.entrySet())
            mRegisters.put(register.getKey(), register.getValue());
    }

    public MachineState() {
        mRegisters = new TreeMap<Integer, Integer>();
    }

    public String printRegisters() {
        List<String> registerNotation = new ArrayList<>();
        for (Map.Entry<Integer, Integer> register : mRegisters.entrySet()) {
            registerNotation.add("R_" + Long.toString(register.getKey()) + ": " + Long.toString(register.getValue()));
        }

        return "{" + String.join(", ", registerNotation) + "}";
    }

    public String printMachineState() {
        return "L_" + Long.toString(getCurrentInstruction()) + ": " + printRegisters();
    }


}
