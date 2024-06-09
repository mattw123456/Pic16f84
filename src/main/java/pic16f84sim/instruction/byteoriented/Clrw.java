package pic16f84sim.instruction.byteoriented;

import pic16f84sim.instruction.StatusFlagChangerInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.register.WRegister;

/**
 * Clear W. Sets flag: Z
 * Datasheet: Page 60
 */
public class Clrw extends StatusFlagChangerInstruction {

    @Override
    public int execute(PIC16F84 pic) {
        isValueEqualsZero((byte) 0);

        pic.getWRegister().setWRegisterValue((byte) 0);

        return 1;
    }
}
