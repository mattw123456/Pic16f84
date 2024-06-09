package pic16f84sim.instruction.byteoriented;

import pic16f84sim.instruction.StatusFlagChangerInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.DataMemory;
import pic16f84sim.microcontroller.register.WRegister;

/**
 * Add W and f. Sets flags: C, DC, Z
 * Datasheet: Page 57
 */
public class Addwf extends StatusFlagChangerInstruction {

    private final byte fileRegister;
    private final short destination;

    public Addwf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public int execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();
        WRegister wRegister = pic.getWRegister();

        int wValue = wRegister.getWRegisterValue() & 0xFF;
        int fValue = dataMemory.load(fileRegister) & 0xFF;

        int result = wValue + fValue;
        isValueEqualsZero((byte) result);
        hasOverflowOccured(result);
        checkDigitCarry(wValue, fValue);

        if(destination == 0) {
            wRegister.setWRegisterValue((byte) result);
        } else {
            dataMemory.store(fileRegister, (byte) result);
        }

        return 1;
    }
}
