package pic16f84sim.instruction.control;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;

/**
 * Go to address
 * Datasheet: Page 62
 */
public class Goto implements IPicInstruction {

    private short address;

    public Goto(short opcode) {
        address = (short) (opcode & 0x07FF);
    }

    @Override
    public int execute(PIC16F84 pic) {
        short pclathValue = pic.getDataMemory().load((byte) 0x0A);
        pclathValue = (short) ((pclathValue & 0b00011000) << 8);

        short nextPc = (short) (address | pclathValue);
        pic.getProgramCounter().setProgramCounterValue(nextPc);

        return 2;
    }
}
