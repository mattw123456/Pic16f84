package pic16f84sim.ui;

import java.io.IOException;

import pic16f84sim.simulator.Simulator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SimulatorUi extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = loadFXML();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("PIC16F84 Simulator");
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setOnCloseRequest(event -> {
			Simulator.getInstance().stopSimulation();
			Platform.exit();
		});
		stage.show();
	}

	private Parent loadFXML() throws IOException {
		return (Parent) FXMLLoader.load(getClass().getResource("/pic16f84-simulator-ui.fxml"));
	}
}
