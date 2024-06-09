package pic16f84sim.microcontroller.memory;

import pic16f84sim.microcontroller.PIC16F84;
import pic16f84sim.microcontroller.register.ProgramCounter;
import pic16f84sim.simulator.Simulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProgramMemory {

    private static final Logger LOGGER = LogManager.getLogger(ProgramMemory.class);

    private final short[] programMemory;

    public ProgramMemory() {
        programMemory = new short[8192];
    }

    public void loadOpcodeIntoProgramMemory(String addressOfOpcode, String opcode) {
        programMemory[Short.parseShort(addressOfOpcode, 16)] = Short.parseShort(opcode, 16);
        LOGGER.info("Stored " + opcode + " in address: " + addressOfOpcode);
    }

    public short getNextInstruction() {
        ProgramCounter pc = Simulator.getInstance().getPicController().getProgramCounter();
        return programMemory[pc.getProgramCounterValue()];
    }
}
