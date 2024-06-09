package pic16f84sim.instruction.byteoriented;

import pic16f84sim.instruction.StatusFlagChangerInstruction;
import pic16f84sim.microcontroller.PIC16F84;

/**
 * Clear f. Sets flag: Z
 * Datasheet: Page 60
 */
public class Clrf extends StatusFlagChangerInstruction {

    private final byte fileRegister;

    public Clrf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
    }

    @Override
    public int execute(PIC16F84 pic) {
        isValueEqualsZero((byte) 0);

        pic.getDataMemory().store(fileRegister, (byte) 0);

        return 1;
    }
}
