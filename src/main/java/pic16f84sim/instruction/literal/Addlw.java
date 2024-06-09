package pic16f84sim.instruction.literal;

import pic16f84sim.instruction.StatusFlagChangerInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;

/**
 * Add literal and value of W register. Sets flags: C, DC and Z
 * Datasheet: Page 57
 */
public class Addlw extends StatusFlagChangerInstruction {

    private byte literal;

    public Addlw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public int execute(PIC16F84 pic) {
        int w = pic.getWRegister().getWRegisterValue() & 0xFF;
        int tempLiteral = literal & 0xFF;
        int result = w + tempLiteral;

        isValueEqualsZero((byte) result);
        hasOverflowOccured(result);
        checkDigitCarry(w, literal);

        pic.getWRegister().setWRegisterValue((byte) result);

        return 1;
    }
}
