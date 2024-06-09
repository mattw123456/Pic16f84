package pic16f84sim.instruction;

import pic16f84sim.microcontroller.PIC16F84;

public interface IPicInstruction {

    public int execute(PIC16F84 pic);
}
