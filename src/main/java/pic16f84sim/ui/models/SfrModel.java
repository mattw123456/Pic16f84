package pic16f84sim.ui.models;

import javafx.beans.property.SimpleStringProperty;

public class SfrModel {

    private SimpleStringProperty address;
    private SimpleStringProperty hexValue;
    private SimpleStringProperty binaryValue;

    public SfrModel(String registerName, int address, byte value) {
        this.address = new SimpleStringProperty(String.format("0x%1$02X", address) + " (" + registerName + ")");
        this.hexValue = new SimpleStringProperty(String.format("0x%1$02X", value));
        this.binaryValue = new SimpleStringProperty(String.format("0b%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0'));
    }

    public String getAddress() {
        return address.get();
    }

    public String getHexValue() {
        return hexValue.get();
    }

    public String getBinaryValue() {
        return binaryValue.get();
    }
}
