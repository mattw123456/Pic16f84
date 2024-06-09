package pic16f84sim.instruction.byteoriented;

import pic16f84sim.instruction.StatusFlagChangerInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.DataMemory;

/**
 * Increment f. Sets flag: Z
 * Datasheet: Page 62
 */
public class Incf extends StatusFlagChangerInstruction {

    private final byte fileRegister;
    private final short destination;

    public Incf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public int execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();

        byte fValue = dataMemory.load(fileRegister);
        fValue++;

        isValueEqualsZero(fValue);

        if(destination == 0) {
            pic.getWRegister().setWRegisterValue(fValue);
        } else {
            dataMemory.store(fileRegister, fValue);
        }

        return 1;
    }
}
