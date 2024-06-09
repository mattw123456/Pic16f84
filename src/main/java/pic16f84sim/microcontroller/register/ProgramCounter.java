package pic16f84sim.microcontroller.register;

import pic16f84sim.microcontroller.PIC16F84;
import io.reactivex.subjects.PublishSubject;

public class ProgramCounter {

    private final PublishSubject<Integer> pcSubject;
    private final PIC16F84 picController;

    private int programCounter;

    public ProgramCounter(PIC16F84 pic, PublishSubject<Integer> subject) {
        pcSubject = subject;
        picController = pic;

        programCounter = 0;
    }

    public void resetProgramCounter() {
        setProgramCounterValue(0);
    }

    public void incrementProgramCounter() {
        programCounter++;

        if(programCounter > 0x3FF) {
            programCounter = 0;
            // TODO: Overflow in PC occurred
        }

        byte newPclValue = (byte) programCounter;
        picController.getDataMemory().syncPcAndPcl(newPclValue);

        notifyUpdate();
    }

    public int getProgramCounterValue() {
        return programCounter;
    }

    public void setProgramCounterValue(int newCounterCalue) {
        programCounter = newCounterCalue;
        notifyUpdate();
    }

    private void notifyUpdate() {
        pcSubject.onNext(programCounter);
    }
}
