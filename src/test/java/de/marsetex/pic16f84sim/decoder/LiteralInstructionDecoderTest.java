package pic16f84sim.decoder;

import pic16f84sim.instruction.IPicInstruction;
import pic16f84sim.instruction.literal.Addlw;
import pic16f84sim.instruction.literal.Andlw;
import pic16f84sim.instruction.literal.Iorlw;
import pic16f84sim.instruction.literal.Movlw;
import pic16f84sim.instruction.literal.Sublw;
import pic16f84sim.instruction.literal.Xorlw;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import static org.junit.Assert.*;

public class LiteralInstructionDecoderTest {

    private final InstructionDecoder decoder = new InstructionDecoder();

    @Test
    public void testDecodeMovlw() {
        String movlwOpcode = "3011";
        IPicInstruction instruction = decoder.decode(Short.parseShort(movlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Movlw.class));
    }

    @Test
    public void testDecodeIorlw() {
        String iorlwOpcode = "380D";
        IPicInstruction instruction = decoder.decode(Short.parseShort(iorlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Iorlw.class));
    }

    @Test
    public void testDecodeAndlw() {
        String andlwOpcode = "3930";
        IPicInstruction instruction = decoder.decode(Short.parseShort(andlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Andlw.class));
    }

    @Test
    public void testDecodeXorlw() {
        String xorlwOpcode = "3A20";
        IPicInstruction instruction = decoder.decode(Short.parseShort(xorlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Xorlw.class));
    }

    @Test
    public void testDecodeSublw() {
        String sublwOpcode = "3C3D";
        IPicInstruction instruction = decoder.decode(Short.parseShort(sublwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Sublw.class));
    }

    @Test
    public void testDecodeAddlw() {
        String addlwOpcode = "3E25";
        IPicInstruction instruction = decoder.decode(Short.parseShort(addlwOpcode, 16));
        assertThat(instruction, IsInstanceOf.instanceOf(Addlw.class));
    }
}