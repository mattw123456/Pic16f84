package pic16f84sim.instruction.control;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.DataMemory;
import pic16f84sim.microcontroller.memory.Stack;
import pic16f84sim.microcontroller.register.ProgramCounter;

/**
 * Return from interrupt
 * Datasheet: Page 65
 */
public class Retfie implements IPicInstruction {

    @Override
    public int execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();
        ProgramCounter pc = pic.getProgramCounter();
        Stack stack = pic.getStack();

        byte intconValue = dataMemory.load((byte) 0x0B);
        intconValue = (byte) (intconValue | 0b10000000);
        dataMemory.store((byte) 0x0B, intconValue);

        pc.setProgramCounterValue(stack.pop());

        return 2;
    }
}
