# Register Machine Simulator
A Java simulation of the register machine described in https://www.cl.cam.ac.uk/teaching/1718/CompTheory/

## Installation

### As Binary
The Java JAR file can be found in the /bin directory.

### From Source
Upon compiling the source, EntryPoint.java is what is called by the JAR File.

Running EntryPoint in Pack mode is equivalent to running Packer
Running EntryPoint in Unpack mode is equivalent to running Unpacker
Running EntryPoint in Run mode is equivalent to running RegisterMachine

## How to use the Simulator
`java -jar RegisterMachineSimulator.jar (pack|unpack|run) args`

This simulator has 3 modes:

1. Pack mode: This takes a file of instructions and outputs the integer associated with this instruction
2. Unpack mode: This takes an integer and outputs the instructions associated with that integer
3. Run mode: This takes a set of instructions (or a packed integer), and a set of registers, and runs the program on the input registers.

### Pack Mode
`pack filepath`

The input argument is a filepath containing RegisterMachine source.

The output is an integer which codes the instructions

Currently due to limitations of the Long type only trivial programs can be correctly packed.

The program will warn you if the Packed result is likely incorrect.

### Unpack Mode
`unpack int [--assembly]`

The input argument is an integer which represents a program
The output argument is the program represented by the Integer.

If `--assembly` is specified, the output is in a format which can be read by the RegisterMachine in Run mode.

Otherwise, the output is pretty-printed in notation analogous to the lecture notes.

Example:

`unpack 786432 --assembly`

Output:

```
DEC 0 0 2
HALT
```

### Run Mode

`run (--packed int)|(filename) [regs]`

[regs] is a space-separated list of registers which will act as the input.

If no registers are specified it will be assumed that all registers are initialised to zero.

#### Run from file
`run filename [regs]`

Example:

`run addr.regsrc 5 2`

This will execute the file addr.regsrc on the registers configured as such:

```
R_0 = 0
R_1 = 5
R_2 = 2
```

R_0 is the output register. Hence, the output will be:

```
Instructions:
L_0: R_1- -> L_1, L_2
L_1: R_0+ -> L_0
L_2: R_2- -> L_3, L_4
L_3: R_0+ -> L_2
L_4: HALT

Program trace
L_0: {R_0: 0,R_1: 5,R_2: 2}
L_1: {R_0: 0,R_1: 4,R_2: 2}
L_0: {R_0: 1,R_1: 4,R_2: 2}
L_1: {R_0: 1,R_1: 3,R_2: 2}
L_0: {R_0: 2,R_1: 3,R_2: 2}
L_1: {R_0: 2,R_1: 2,R_2: 2}
L_0: {R_0: 3,R_1: 2,R_2: 2}
L_1: {R_0: 3,R_1: 1,R_2: 2}
L_0: {R_0: 4,R_1: 1,R_2: 2}
L_1: {R_0: 4,R_1: 0,R_2: 2}
L_0: {R_0: 5,R_1: 0,R_2: 2}
L_2: {R_0: 5,R_1: 0,R_2: 2}
L_3: {R_0: 5,R_1: 0,R_2: 1}
L_2: {R_0: 6,R_1: 0,R_2: 1}
L_3: {R_0: 6,R_1: 0,R_2: 0}
L_2: {R_0: 7,R_1: 0,R_2: 0}
L_4: {R_0: 7,R_1: 0,R_2: 0}
Clean halt

Result = 7
```


#### Run from packed integer
`run --packed int [regs]`

Example:

`run --packed 786432 3`

Runs with the output:

```
Instructions:
L_0: R_0- -> L_0, L_2
L_1: HALT

Program trace
L_0: {R_0: 0,R_1: 3}
L_2: {R_0: 0,R_1: 3}
Erroneous halt

Result = 0
```

#### Assembly Code

Each line of a file must be in one of the following formats:

1. `HALT` will stop the program
2. `INC REGISTER NEXT_LABEL` will increment `REGISTER` and jump to `NEXT_LABEL`
3. `DEC REGISTER LABEL_1 LABEL_2` will decrement `REGISTER` and jump to `LABEL_1` if `REGISTER` is greater than zero, otherwise it will jump to `LABEL_2`

`REGISTER`, `NEXT_LABEL`, `LABEL_1`, and `LABEL_2` must be nonnegative integers.

The first line of the file represents L_0 in a theoretical register machine, the second line represents L_1, and so on.