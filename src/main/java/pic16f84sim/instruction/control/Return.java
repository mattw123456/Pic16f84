package pic16f84sim.instruction.control;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.Stack;
import pic16f84sim.microcontroller.register.ProgramCounter;

/**
 * Return from Subroutine
 * Datasheet: Page 66
 */
public class Return implements IPicInstruction {

    @Override
    public int execute(PIC16F84 pic) {
        ProgramCounter pc = pic.getProgramCounter();
        Stack stack = pic.getStack();

        pc.setProgramCounterValue(stack.pop());
        return 2;
    }
}
