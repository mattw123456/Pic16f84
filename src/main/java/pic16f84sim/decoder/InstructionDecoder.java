package pic16f84sim.decoder;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.instruction.bitoriented.Bcf;
import pic16f84sim.instruction.bitoriented.Bsf;
import pic16f84sim.instruction.bitoriented.Btfsc;
import pic16f84sim.instruction.bitoriented.Btfss;

import pic16f84sim.instruction.byteoriented.Addwf;
import pic16f84sim.instruction.byteoriented.Andwf;
import pic16f84sim.instruction.byteoriented.Clrf;
import pic16f84sim.instruction.byteoriented.Clrw;
import pic16f84sim.instruction.byteoriented.Comf;
import pic16f84sim.instruction.byteoriented.Decf;
import pic16f84sim.instruction.byteoriented.Decfsz;
import pic16f84sim.instruction.byteoriented.Incf;
import pic16f84sim.instruction.byteoriented.Incfsz;
import pic16f84sim.instruction.byteoriented.Iorwf;
import pic16f84sim.instruction.byteoriented.Movf;
import pic16f84sim.instruction.byteoriented.Movwf;
import pic16f84sim.instruction.byteoriented.Nop;
import pic16f84sim.instruction.byteoriented.Rlf;
import pic16f84sim.instruction.byteoriented.Rrf;
import pic16f84sim.instruction.byteoriented.Subwf;
import pic16f84sim.instruction.byteoriented.Swapf;
import pic16f84sim.instruction.byteoriented.Xorwf;
import pic16f84sim.instruction.control.Call;
import pic16f84sim.instruction.control.Clrwdt;
import pic16f84sim.instruction.control.Goto;
import pic16f84sim.instruction.control.Retfie;
import pic16f84sim.instruction.control.Retlw;
import pic16f84sim.instruction.control.Return;
import pic16f84sim.instruction.control.Sleep;
import pic16f84sim.instruction.literal.Addlw;
import pic16f84sim.instruction.literal.Andlw;
import pic16f84sim.instruction.literal.Iorlw;
import pic16f84sim.instruction.literal.Movlw;
import pic16f84sim.instruction.literal.Sublw;
import pic16f84sim.instruction.literal.Xorlw;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Datasheet: Page 56
 */
public class InstructionDecoder {

    private static final Logger LOGGER = LogManager.getLogger(InstructionDecoder.class);

    public IPicInstruction decode(short opcode) {
        switch (opcode >> 12) {
            case 0b000:
                return decodeOpcodeWithPrefix00(opcode);
            case 0b001:
                return decodeOpcodeWithPrefix01(opcode);
            case 0b010:
                return decodeOpcodeWithPrefix10(opcode);
            case 0b011:
                return decodeOpcodeWithPrefix11(opcode);
            default:
                LOGGER.error("Unknown opcode prefix");
                return null;
        }
    }

    private IPicInstruction decodeOpcodeWithPrefix00(short opcode) {
        switch(opcode >> 8) {
            case 0b0000000:
                return decodeSpecialCasesWithPrefix00(opcode);
            case 0b0000001:
                return ((opcode >> 7) == 0b011) ? new Clrf(opcode) : new Clrw();
            case 0b0000010:
                return new Subwf(opcode);
            case 0b0000011:
                return new Decf(opcode);
            case 0b0000100:
                return new Iorwf(opcode);
            case 0b0000101:
                return new Andwf(opcode);
            case 0b0000110:
                return new Xorwf(opcode);
            case 0b0000111:
                return new Addwf(opcode);
            case 0b0001000:
                return new Movf(opcode);
            case 0b0001001:
                return new Comf(opcode);
            case 0b0001010:
                return new Incf(opcode);
            case 0b0001011:
                return new Decfsz(opcode);
            case 0b0001100:
                return new Rrf(opcode);
            case 0b0001101:
                return new Rlf(opcode);
            case 0b0001110:
                return new Swapf(opcode);
            case 0b0001111:
                return new Incfsz(opcode);
            default:
                LOGGER.error("Unknown opcode with prefix 0b000");
                return null;
        }
    }

    /**
     * Decode instructions, which can not be identified by bit shifting.
     * @param opcode
     * @return
     */
    private IPicInstruction decodeSpecialCasesWithPrefix00(short opcode) {
        if((opcode >> 7) == 0b01) {
            return new Movwf(opcode);
        }

        switch(opcode) {
            case 0b0000000000000000:
            case 0b0000000000100000:
            case 0b0000000001000000:
            case 0b0000000001100000:
                return new Nop();
            case 0b0000000000001000:
                return new Return();
            case 0b0000000000001001:
                return new Retfie();
            case 0b0000000001100011:
                return new Sleep();
            case 0b0000000001100100:
                return new Clrwdt();
            default:
                LOGGER.error("Unknown opcode with prefix 0b000 (special cases)");
                return null;
        }
    }

    private IPicInstruction decodeOpcodeWithPrefix01(short opcode) {
        switch(opcode >> 10) {
            case 0b0100:
                return new Bcf(opcode);
            case 0b0101:
                return new Bsf(opcode);
            case 0b0110:
                return new Btfsc(opcode);
            case 0b0111:
                return new Btfss(opcode);
            default:
                LOGGER.error("Unknown opcode with prefix 0b001");
                return null;
        }
    }

    private IPicInstruction decodeOpcodeWithPrefix10(short opcode) {
        switch(opcode >> 11) {
            case 0b0100:
                return new Call(opcode);
            case 0b0101:
                return new Goto(opcode);
            default:
                LOGGER.error("Unknown opcode with prefix 0b010");
                return null;
        }
    }

    private IPicInstruction decodeOpcodeWithPrefix11(short opcode) {
        switch(opcode >> 8) {
            case 0b0110000:
            case 0b0110001:
            case 0b0110010:
            case 0b0110011:
                return new Movlw(opcode);
            case 0b0110100:
            case 0b0110101:
            case 0b0110110:
            case 0b0110111:
                return new Retlw(opcode);
            case 0b0111000:
                return new Iorlw(opcode);
            case 0b0111001:
                return new Andlw(opcode);
            case 0b0111010:
                return new Xorlw(opcode);
            case 0b0111100:
            case 0b0111101:
                return new Sublw(opcode);
            case 0b0111110:
            case 0b0111111:
                return new Addlw(opcode);
            default:
                LOGGER.error("Unknown opcode with prefix 0b011");
                return null;
        }
    }
}
