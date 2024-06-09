package pic16f84sim.instruction.byteoriented;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.DataMemory;

/**
 * Increment f, Skip if 0
 * Datasheet: Page 63
 */
public class Incfsz implements IPicInstruction {

    private final byte fileRegister;
    private final short destination;

    public Incfsz(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public int execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();
        int cycles = 1;
        byte fValue = dataMemory.load(fileRegister);

        fValue++;
        if(fValue == 0) {
            pic.getProgramCounter().incrementProgramCounter();
            cycles = 2;
        }

        if(destination == 0) {
            pic.getWRegister().setWRegisterValue(fValue);
        } else {
            dataMemory.store(fileRegister, fValue);
        }

        return cycles;
    }
}
