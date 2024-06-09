package pic16f84sim.instruction.control;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;

public class Sleep implements IPicInstruction {

    @Override
    public int execute(PIC16F84 pic) {
        return 1;
    }
}
