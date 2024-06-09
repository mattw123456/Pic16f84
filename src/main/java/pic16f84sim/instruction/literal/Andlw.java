package pic16f84sim.instruction.literal;

import pic16f84sim.instruction.StatusFlagChangerInstruction;
import pic16f84sim.microcontroller.PIC16F84;

/**
 * AND literal with W. Sets flags: Z
 * Datasheet: Page 57
 */
public class Andlw extends StatusFlagChangerInstruction {

    private byte literal;

    public Andlw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public int execute(PIC16F84 pic) {
        byte result = (byte) (pic.getWRegister().getWRegisterValue() & literal);

        isValueEqualsZero(result);

        pic.getWRegister().setWRegisterValue(result);

        return 1;
    }
}
