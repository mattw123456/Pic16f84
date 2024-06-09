package pic16f84sim.microcontroller.register;

import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.memory.DataMemory;

/**
 * Datasheet: Page 27
 */
public class Timer0 {

    private static final byte TMR0_ADDRESS = 0x01;
    private static final byte INTCON_ADDRESS = 0x0B;

    private final PIC16F84 picController;

    private boolean nextIncOverflow;

    public Timer0(PIC16F84 pic) {
        picController = pic;

        nextIncOverflow = false;
    }

    public void increaseTimerCounter() {
        DataMemory dataMemory = picController.getDataMemory();

        byte timerCounter = dataMemory.load(TMR0_ADDRESS);

        if(timerCounter == (byte) 0xFF) {
            byte intconValue = dataMemory.load(INTCON_ADDRESS);
            intconValue = (byte) (intconValue | 0b00000100);

            dataMemory.store(INTCON_ADDRESS, intconValue);
        }

        timerCounter++;

        dataMemory.store(TMR0_ADDRESS, timerCounter);
    }

    public boolean checkForTimer0Interrupt() {
        byte intconValue = picController.getDataMemory().load(INTCON_ADDRESS);
        return (intconValue & 0b10100100) == 0xA4;
    }
}
