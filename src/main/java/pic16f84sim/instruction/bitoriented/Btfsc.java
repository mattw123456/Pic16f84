package pic16f84sim.instruction.bitoriented;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.DataMemory;

/**
 * Bit Test f, Skip if Clear
 * Datasheet: Page 58
 */
public class Btfsc implements IPicInstruction {

    private final byte fileRegister;
    private final int bitPosition;

    public Btfsc(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        bitPosition = (opcode & 0x0380) >> 7;
    }

    @Override
    public int execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();
        byte bitToSet = 1;

        byte fValue = dataMemory.load(fileRegister);
        bitToSet = (byte) (bitToSet << bitPosition);
        fValue = (byte) (fValue & (bitToSet));

        if(fValue == 0) {
            pic.getProgramCounter().incrementProgramCounter();
            return 2;
        }
        return 1;
    }
}
