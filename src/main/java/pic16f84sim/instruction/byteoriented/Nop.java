package pic16f84sim.instruction.byteoriented;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;

/**
 * No Operation
 * Datasheet: Page 65
 */
public class Nop implements IPicInstruction {

    @Override
    public int execute(PIC16F84 pic) {
        return 1;
    }
}
