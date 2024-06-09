package pic16f84sim.instruction.literal;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;

/**
 * Move literal to W register
 * Datasheet: Page 64
 */
public class Movlw implements IPicInstruction {

    private byte literal;

    public Movlw(short opcode) {
        literal = (byte) opcode;
    }

    @Override
    public int execute(PIC16F84 pic) {
        pic.getWRegister().setWRegisterValue(literal);
        return 1;
    }
}
