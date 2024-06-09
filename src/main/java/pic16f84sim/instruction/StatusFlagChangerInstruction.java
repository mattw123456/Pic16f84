package pic16f84sim.instruction;

import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;

public abstract class StatusFlagChangerInstruction implements pic16f84sim.instruction.IPicInstruction {

    protected void isValueEqualsZero(byte result) {
        if(result == 0x0) {
            StatusRegisterHelper.setZFlag();
        } else {
            StatusRegisterHelper.resetZFlag();
        }
    }

    protected void checkDigitCarry(int w, int literal) {
        if((w & 0x0F) + (literal & 0x0F)  > 0x0F) {
            StatusRegisterHelper.setDCFlag();
        } else {
            StatusRegisterHelper.resetDCFlag();
        }
    }

    protected void hasOverflowOccured(int result) {
        if(result > 0x0FF) {
            StatusRegisterHelper.setCFlag();
        } else {
            StatusRegisterHelper.resetCFlag();
        }
    }

    public abstract int execute(PIC16F84 pic);
}
