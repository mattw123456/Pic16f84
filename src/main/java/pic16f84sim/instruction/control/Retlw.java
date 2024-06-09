package pic16f84sim.instruction.control;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.Stack;
import pic16f84sim.microcontroller.register.ProgramCounter;

/**
 * Return with literal in W
 * Datasheet: Page 66
 */
public class Retlw implements IPicInstruction {

    private final byte literal;

    public Retlw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public int execute(PIC16F84 pic) {
        ProgramCounter pc = pic.getProgramCounter();
        Stack stack = pic.getStack();

        pic.getWRegister().setWRegisterValue(literal);
        pc.setProgramCounterValue(stack.pop());

        return 2;
    }
}
