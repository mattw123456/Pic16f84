package pic16f84sim.instruction.byteoriented;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.DataMemory;
import pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;

/**
 * Rotate Right f through Carry. Sets flag: C
 * Datasheet: Page 67
 */
public class Rrf implements IPicInstruction {

    private final byte fileRegister;
    private final short destination;

    public Rrf(short opcode) {
        fileRegister = (byte) (opcode & 0x007F);
        destination = (short) (opcode & 0x0080);
    }

    @Override
    public int execute(PIC16F84 pic) {
        DataMemory dataMemory = pic.getDataMemory();

        byte fValue = dataMemory.load(fileRegister);
        byte cFlag = StatusRegisterHelper.getCFlag();
        cFlag = (byte) (cFlag << 7);

        if((fValue & 0x01) == 0) {
            StatusRegisterHelper.resetCFlag();
        } else {
            StatusRegisterHelper.setCFlag();
        }

        byte result = (byte) (fValue >> 1);
        result = (byte) (result | cFlag);

        if(destination == 0) {
            pic.getWRegister().setWRegisterValue((byte) result);
        } else {
            dataMemory.store(fileRegister, (byte) result);
        }

        return 1;
    }
}
