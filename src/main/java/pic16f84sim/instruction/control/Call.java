package pic16f84sim.instruction.control;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.register.ProgramCounter;

/**
 * Call subroutine
 * Datasheet: Page 59
 */
public class Call implements IPicInstruction {

    private final short address;

    public Call(short opcode) {
        address = (short) (opcode & 0x07FF);
    }

    @Override
    public int execute(PIC16F84 pic) {
        ProgramCounter pc = pic.getProgramCounter();
        
        // No +1 needed, because the pc is already incremented at this point
        pic.getStack().push((short) (pc.getProgramCounterValue()));

        short pclathValue = pic.getDataMemory().load((byte) 0x0A);
        pclathValue = (short) ((pclathValue & 0b00011000) << 8);

        short nextPc = (short) (address | pclathValue);
        pc.setProgramCounterValue(nextPc);

        return 2;
    }
}
