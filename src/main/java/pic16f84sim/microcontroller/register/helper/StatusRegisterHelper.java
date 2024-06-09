package pic16f84sim.microcontroller.register.helper;

import pic16f84sim.microcontroller.memory.DataMemory;
import pic16f84sim.simulator.Simulator;

/**
 * Datasheet: Page 14
 *
 * Bit 0: C
 * Bit 1: DC
 * Bit 2: Z
 * Bit 5: RP0
 */
public class StatusRegisterHelper {

    private static final byte ADDRESS_OF_STATUS_REGISTER = 0x03;

    public static void setCFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        statusRegisterValue = (byte) (statusRegisterValue | 0b00000001);

        dataMemory.store(ADDRESS_OF_STATUS_REGISTER, statusRegisterValue);
    }

    public static void resetCFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        statusRegisterValue = (byte) (statusRegisterValue & 0b11111110);

        dataMemory.store(ADDRESS_OF_STATUS_REGISTER, statusRegisterValue);
    }

    public static byte getCFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        return (byte) (statusRegisterValue & 0b00000001);
    }

    public static void setDCFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        statusRegisterValue = (byte) (statusRegisterValue | 0b00000010);

        dataMemory.store(ADDRESS_OF_STATUS_REGISTER, statusRegisterValue);
    }

    public static void resetDCFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        statusRegisterValue = (byte) (statusRegisterValue & 0b11111101);

        dataMemory.store(ADDRESS_OF_STATUS_REGISTER, statusRegisterValue);
    }

    public static byte getDCFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        return (byte) ((statusRegisterValue & 0b00000010) >> 1);
    }

    public static void setZFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        statusRegisterValue = (byte) (statusRegisterValue | 0b00000100);

        dataMemory.store(ADDRESS_OF_STATUS_REGISTER, statusRegisterValue);
    }

    public static void resetZFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        statusRegisterValue = (byte) (statusRegisterValue & 0b11111011);

        dataMemory.store(ADDRESS_OF_STATUS_REGISTER, statusRegisterValue);
    }

    public static byte getZFlag() {
        DataMemory dataMemory = Simulator.getInstance().getPicController().getDataMemory();

        byte statusRegisterValue = dataMemory.load(ADDRESS_OF_STATUS_REGISTER);
        return (byte) ((statusRegisterValue & 0b00000100) >> 2);
    }
}
