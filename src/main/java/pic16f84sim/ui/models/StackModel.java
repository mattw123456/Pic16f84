package pic16f84sim.ui.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StackModel {

    private SimpleIntegerProperty position;
    private SimpleStringProperty hexValue;

    public StackModel(int position, short value) {
        this.position = new SimpleIntegerProperty(position);
        this.hexValue = new SimpleStringProperty(String.format("0x%1$04X", value));
    }

    public Integer getPosition() {
        return position.get();
    }

    public String getHexValue() {
        return hexValue.get();
    }
}
