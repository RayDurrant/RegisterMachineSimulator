# Register Machine Simulator
A Java simulation of the register machine described in https://www.cl.cam.ac.uk/teaching/1718/CompTheory/

##Installation

###As Binary
The Java JAR file can be found in the /bin directory.

###From Source
Upon compiling the source, EntryPoint.java is what is called by the JAR File.

Running EntryPoint in Pack mode is equivalent to running Packer
Running EntryPoint in Unpack mode is equivalent to running Unpacker
Running EntryPoint in Run mode is equivalent to running RegisterMachine

##How to use the Simulator
`java -jar RegisterMachineSimulator.jar (pack|unpack|run) args`

This simulator has 3 modes:

1. Pack mode: This takes a file of instructions and outputs the integer associated with this instruction
2. Unpack mode: This takes an integer and outputs the instructions associated with that integer
3. Run mode: This takes a set of instructions (or a packed integer), and a set of registers, and runs the program on the input registers.

###Pack Mode
`pack filepath`

The input argument is a filepath containing RegisterMachine source.

The output is an integer which codes the instructions

Currently due to limitations of the Long type only trivial programs can be correctly packed.

The program will warn you if the Packed result is likely incorrect.

###Unpack Mode
`unpack int [--assembly]`

The input argument is an integer which represents a program
The output argument is the program represented by the Integer.

If `--assembly` is specified, the output is in a format which can be read by the RegisterMachine in Run mode.

Otherwise, the output is pretty-printed in notation analogous to the lecture notes.

###Run Mode

`run (--packed int)|(filename) [regs]`

[regs] is a space-separated list of registers which will act as the input.

If no registers are specified it will be assumed that all registers are initialised to zero.

####Run from file
`run filename [regs]`

Example:

`run addr.regsrc 5 2`

####Run from packed integer
`run --packed int [regs]`

Example:

`run --packed 786432 3`

