package uk.ac.cam.sgd38.computation_theory.register_machine_simulator;

import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing.Packer;
import uk.ac.cam.sgd38.computation_theory.register_machine_simulator.packing.Unpacker;

import java.io.IOException;
import java.util.Arrays;

public class EntryPoint {

    public static void main(String[] args) {
        try {
            if (args[0].equals("pack")) {
                Packer.main(Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equals("unpack")) {
                Unpacker.main(Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equals("run")) {
                RegisterMachine.main(Arrays.copyOfRange(args, 1, args.length));
            } else {
                System.out.println("Arguments: (pack|unpack|run) args");
                System.exit(1);
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Arguments: (pack|unpack|run) args");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("An unknown IO Exception occurred.");
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
