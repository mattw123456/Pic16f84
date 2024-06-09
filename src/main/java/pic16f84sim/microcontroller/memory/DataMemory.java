package pic16f84sim.microcontroller.memory;

import pic16f84sim.microcontroller.register.ProgramCounter;
import io.reactivex.subjects.PublishSubject;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Datasheet: Page 13, 14, 19
 */
public class DataMemory {

    private final PublishSubject<byte[]> gprSubject;
    private final PublishSubject<byte[]> sfrSubject;
    private final ProgramCounter programCounter;

    private final byte[] ramBank0;
    private final byte[] ramBank1;

    public DataMemory(ProgramCounter programCounter, PublishSubject<byte[]> gprSubject, PublishSubject<byte[]> sfrSubject) {
        this.programCounter = programCounter;
        this.gprSubject = gprSubject;
        this.sfrSubject = sfrSubject;

        ramBank0 = new byte[128];
        ramBank1 = new byte[128];
    }

    public void syncPcAndPcl(byte newPclValue) {
        ramBank0[0x02] = newPclValue;
        ramBank1[0x02] = newPclValue;
    }

    public void store(byte fileRegisterAddress, byte valueToStore) {
        byte bankSelectBit;
        byte normalizedAddress = (byte) (fileRegisterAddress % 128);

        if(normalizedAddress == 0x0) {
            byte fsrAddress = getFSR();
            bankSelectBit = (byte) (fsrAddress >> 7);
            normalizedAddress = (byte) (fsrAddress & 0b01111111);

        } else {
            bankSelectBit = getRP0Flag();
        }

        if(normalizedAddress < 0) {
            normalizedAddress = (byte) (normalizedAddress + 128);
            bankSelectBit = 1;
        }

        if(normalizedAddress > 0x4F) {
            // TODO: Error msg

        } else if(normalizedAddress > 0x0B) {
            storeInGeneralPurposeRegister(normalizedAddress, valueToStore);
            notifyChangeInGPR();

        } else {
            storeInSpecialFunctionRegister(bankSelectBit, normalizedAddress, valueToStore);
            notifyChangeInSFR();
        }
    }

    private void storeInGeneralPurposeRegister(byte fileRegisterAddress, byte valueToStore) {
        ramBank0[fileRegisterAddress] = valueToStore;
    }

    private void storeInSpecialFunctionRegister(byte bankSelectBit, byte fileRegisterAddress, byte valueToStore) {
        switch (fileRegisterAddress) {
            case 0x02: // PCL
                ramBank0[fileRegisterAddress] = valueToStore;
                ramBank1[fileRegisterAddress] = valueToStore;

                copyToProgramCounter();
                break;
            case 0x03: // STATUS
            case 0x04: // FSR
            case 0x0A: // PCLATH
            case 0x0B: // INTCON
                ramBank0[fileRegisterAddress] = valueToStore;
                ramBank1[fileRegisterAddress] = valueToStore;
                break;
            case 0x01: // TMR0 (Bank0) or OPTION (Bank1)
            case 0x05: // PORTA (Bank0) or TRISA (Bank1)
            case 0x06: // PORTB (Bank0) or TRISB (Bank1)
            case 0x08: // EEDATA (Bank0) or EECON1 (Bank1)
                if(bankSelectBit == 0) {
                    ramBank0[fileRegisterAddress] = valueToStore; // TMR0
                } else {
                    ramBank1[fileRegisterAddress] = valueToStore; // OPTION
                }
                break;
            case 0x09:
                if(bankSelectBit == 0) {
                    ramBank0[fileRegisterAddress] = valueToStore; // EEADR
                }
                break;
            default:
                // Do nothing for file addresses: 00h (indirect addr.), 07h (undefined)
                break;
        }
    }

    private void copyToProgramCounter() {
        short pclValue = ramBank0[0x02];
        short pclathValue = ramBank0[0x0A];

        pclValue = (short) (pclValue & 0xFF);
        pclathValue = (short) ((pclathValue & 0x1F) << 8);

        int newPcValue = pclValue | pclathValue;
        programCounter.setProgramCounterValue(newPcValue);
    }

    public byte load(byte fileRegisterAddress) {
        byte bankSelectBit;
        byte normalizedAddress = (byte) (fileRegisterAddress % 128);

        if(normalizedAddress == 0x0) {
            byte fsrAddress = getFSR();
            bankSelectBit = (byte) (fsrAddress >> 7);
            normalizedAddress = (byte) (fsrAddress & 0b01111111);

        } else {
            bankSelectBit = getRP0Flag();
        }

        if(normalizedAddress < 0) {
            normalizedAddress = (byte) (normalizedAddress + 128);
            bankSelectBit = 1;
        }

        if(normalizedAddress > 0x4F) {
            // TODO: Error msg
            return 0;

        } else if(normalizedAddress > 0x0B) {
            return loadFromGeneralPurposeRegister(normalizedAddress);

        } else {
            return loadFromSpecialFunctionRegister(bankSelectBit, normalizedAddress);
        }
    }

    private byte loadFromGeneralPurposeRegister(byte fileRegisterAddress) {
        return ramBank0[fileRegisterAddress];
    }

    private byte loadFromSpecialFunctionRegister(byte bankSelectBit, byte fileRegisterAddress) {
        if(bankSelectBit == 0) {
            return ramBank0[fileRegisterAddress];
        } else {
            return ramBank1[fileRegisterAddress];
        }
    }

    private byte getRP0Flag() {
        return (byte) (ramBank0[0x03] & 0b00100000);
    }

    private byte getFSR() {
        return ramBank0[0x04];
    }

    private void notifyChangeInGPR() {
        gprSubject.onNext(ramBank0);
    }

    private void notifyChangeInSFR() {
        sfrSubject.onNext(ArrayUtils.addAll(ramBank0, ramBank1));
    }
}
