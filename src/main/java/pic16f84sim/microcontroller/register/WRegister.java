package pic16f84sim.microcontroller.register;

import io.reactivex.subjects.PublishSubject;

public class WRegister {

    private final PublishSubject<Byte> wRegisterSubject;

    private byte wRegister;

    public WRegister(PublishSubject<Byte> subject) {
        wRegisterSubject = subject;
    }

    public byte getWRegisterValue() {
        return wRegister;
    }

    public void setWRegisterValue(byte w) {
        wRegister = w;
        notifyChange();
    }

    private void notifyChange() {
        wRegisterSubject.onNext(wRegister);
    }
}
