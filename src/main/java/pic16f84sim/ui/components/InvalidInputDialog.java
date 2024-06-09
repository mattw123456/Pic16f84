package pic16f84sim.ui.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InvalidInputDialog {

    public static Alert getInvalidInputDialog() {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("PIC16F84 Simulator - Invalid input");
        alert.setHeaderText("Invalid input");
        alert.setContentText("Value needs to be in hex format");

        return alert;
    }
}
