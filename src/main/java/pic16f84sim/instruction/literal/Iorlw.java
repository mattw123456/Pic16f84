package pic16f84sim.instruction.literal;

import pic16f84sim.instruction.StatusFlagChangerInstruction;
import pic16f84sim.microcontroller.PIC16F84;

/**
 * Inclusive OR literal with W. Sets flags: Z
 * Datasheet: Page 63
 */
public class Iorlw extends StatusFlagChangerInstruction {

    private byte literal;

    public Iorlw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public int execute(PIC16F84 pic) {
        byte result = (byte) (pic.getWRegister().getWRegisterValue() | literal);

        isValueEqualsZero(result);

        pic.getWRegister().setWRegisterValue(result);

        return 1;
    }
}
