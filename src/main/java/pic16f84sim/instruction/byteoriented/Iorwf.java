package pic16f84sim.instruction.byteoriented;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.instruction.StatusFlagChangerInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.DataMemory;
import pic16f84sim.microcontroller.register.WRegister;

/**
 * Inclusive OR W with f. Sets flag: Z
 * Datasheet: Page 64
 */
public class Iorwf extends StatusFlagChangerInstruction {

    private final byte fileRegister;
    private final short destination;

    public Iorwf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public int execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();
        WRegister wRegister = pic.getWRegister();

        byte wValue = wRegister.getWRegisterValue();
        byte fValue = dataMemory.load(fileRegister);

        byte result = (byte) (wValue | fValue);
        isValueEqualsZero(result);

        if(destination == 0) {
            wRegister.setWRegisterValue(result);
        } else {
            dataMemory.store(fileRegister, result);
        }

        return 1;
    }
}
