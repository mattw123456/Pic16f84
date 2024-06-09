package pic16f84sim.ui.controller;

import pic16f84sim.microcontroller.memory.DataMemory;
import pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;
import pic16f84sim.simulator.Simulator;
import pic16f84sim.state.SimStateContMode;
import pic16f84sim.state.SimStateFileLoaded;
import pic16f84sim.state.SimStateIdle;
import pic16f84sim.state.SimStateReset;
import pic16f84sim.state.SimStateStepMode;
import pic16f84sim.ui.components.AboutDialog;
import pic16f84sim.ui.components.InvalidInputDialog;
import pic16f84sim.ui.components.LstFileChooser;
import pic16f84sim.ui.models.CodeModel;
import pic16f84sim.ui.models.GprModel;
import pic16f84sim.ui.models.SfrModel;
import pic16f84sim.ui.models.StackModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SimulatorUiController {

	@FXML
	private Button runButton;

	@FXML
	private Button stepButton;

	@FXML
	private Button resetButton;

	@FXML
	private Menu openRecentMenuItem;

	@FXML
	private Slider quartzFrequencySlider;

	@FXML
	private Label quartzFrequencyLabel;

	@FXML
	private Label runtimeCounterLabel;

	@FXML
	private TableView<CodeModel> codeTable;

	@FXML
	private TableColumn<CodeModel, String> codeTableBreakpoint;

	@FXML
	private TableColumn<CodeModel, String> codeTableCurrentLine;

	@FXML
	private TableColumn<CodeModel, String> codeTableLine;

	@FXML
	private Label wLabel;

	@FXML
	private Label pcLabel;

	@FXML
	private Label pclLabel;

	@FXML
	private Label pclathLabel;

	@FXML
	private Label cLabel;

	@FXML
	private Label dcLabel;

	@FXML
	private Label zLabel;

	@FXML
	private TableView<GprModel> gprTable;

	@FXML
	private TableColumn<GprModel, String> gprTableAddress;

	@FXML
	private TableColumn<GprModel, String> gprTableHexValue;

	@FXML
	private TableColumn<GprModel, String> gprTableBinaryValue;

	@FXML
	private TableView<SfrModel> sfrTable;

	@FXML
	private TableColumn<SfrModel, String> sfrTableAddress;

	@FXML
	private TableColumn<SfrModel, String> sfrTableHexValue;

	@FXML
	private TableColumn<SfrModel, String> sfrTableBinaryValue;

	@FXML
	private Label stackPointerLabel;

	@FXML
	private TableView<StackModel> stackTable;

	@FXML
	private TableColumn<StackModel, Integer> stackTablePosition;

	@FXML
	private TableColumn<StackModel, String> stackTableHexValue;

	@FXML
	private ToggleGroup PortA0;

	@FXML
	private ToggleGroup PortA1;

	@FXML
	private ToggleGroup PortA2;

	@FXML
	private ToggleGroup PortA3;

	@FXML
	private ToggleGroup PortA4;

	@FXML
	private ToggleGroup PortB0;

	@FXML
	private ToggleGroup PortB1;

	@FXML
	private ToggleGroup PortB2;

	@FXML
	private ToggleGroup PortB3;

	@FXML
	private ToggleGroup PortB4;

	@FXML
	private ToggleGroup PortB5;

	@FXML
	private ToggleGroup PortB6;

	@FXML
	private ToggleGroup PortB7;

	@FXML
	private TextArea debugConsole;

	private final Simulator simulator;

	public SimulatorUiController() {
		simulator = Simulator.getInstance();

		simulator.getDebugConsole().subscribe(msg -> outputToDebugConsole(msg));

		simulator.getCodeLines().subscribe(codeLines -> outputLstFile(codeLines));
		simulator.getBreakpoints().subscribe(breakpoints -> outputBreakpoints(breakpoints));
		simulator.getCurrentExecutedCode().subscribe(currentExecCode -> outputCurrentExecutedCode(currentExecCode));
		simulator.getRuntimeCounterSubject().subscribe(runtimeCounter -> outputRuntimeCounter(runtimeCounter));

		simulator.getPicController().getWRegisterSubject().subscribe(w -> outputWRegister(w));
		simulator.getPicController().getPcSubject().subscribe(pc -> outputPc(pc));

		simulator.getPicController().getGprSubject().subscribe(gpr -> outputGpr(gpr));
		simulator.getPicController().getSfrSubject().subscribe(sfr -> outputSfr(sfr));
		simulator.getPicController().getStackSubject().subscribe(stack -> outputStack(stack));
		simulator.getPicController().getStackPointerSubject().subscribe(stackPointer -> outputStackPointer(stackPointer));
	}

	@FXML
	private void initialize() {
		simulator.resetSimulation();
		outputGpr(new byte[128]);
		outputStack(new short[8]);

		quartzFrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			quartzFrequencyLabel.setText(newValue.intValue() + "000 Hz");
			simulator.setQuartzFrequency(newValue.longValue() * 1000);
		});

		PortA0.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 0, ((RadioButton) newValue));
		});
		PortA1.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 1, ((RadioButton) newValue));
		});
		PortA2.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 2, ((RadioButton) newValue));
		});
		PortA3.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 3, ((RadioButton) newValue));
		});
		PortA4.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 4, ((RadioButton) newValue));
		});
		PortB0.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 0, ((RadioButton) newValue));
		});
		PortB1.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 1, ((RadioButton) newValue));
		});
		PortB2.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 2, ((RadioButton) newValue));
		});
		PortB3.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 3, ((RadioButton) newValue));
		});
		PortB4.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 4, ((RadioButton) newValue));
		});
		PortB5.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 5, ((RadioButton) newValue));
		});
		PortB6.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 6, ((RadioButton) newValue));
		});
		PortB7.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 7, ((RadioButton) newValue));
		});

		codeTable.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2) {
				CodeModel selectedCode = codeTable.getSelectionModel().selectedItemProperty().get();

				if(selectedCode == null) {
					return;
				}

				if(selectedCode.getBreakpoint().contains("x")) {
					simulator.removeBreakpoint(selectedCode);
				} else {
					simulator.addBreakpoint(selectedCode);
				}
			}
		});

		codeTableBreakpoint.setCellValueFactory(new PropertyValueFactory<>("Breakpoint"));
		codeTableCurrentLine.setCellValueFactory(new PropertyValueFactory<>("IsExecuted"));
		codeTableLine.setCellValueFactory(new PropertyValueFactory<>("CodeLine"));

		gprTableAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
		gprTableHexValue.setCellValueFactory(new PropertyValueFactory<>("HexValue"));
		gprTableBinaryValue.setCellValueFactory(new PropertyValueFactory<>("BinaryValue"));

		gprTableHexValue.setCellFactory(TextFieldTableCell.forTableColumn());

		sfrTableAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
		sfrTableHexValue.setCellValueFactory(new PropertyValueFactory<>("HexValue"));
		sfrTableBinaryValue.setCellValueFactory(new PropertyValueFactory<>("BinaryValue"));

		sfrTableHexValue.setCellFactory(TextFieldTableCell.forTableColumn());

		stackTablePosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
		stackTableHexValue.setCellValueFactory(new PropertyValueFactory<>("HexValue"));
	}

	@FXML
	private void openFileChooser() {
		File lstFile = LstFileChooser.getFileChooser().showOpenDialog(null);

		if (lstFile != null) {
			openRecentMenuItem.getItems().add(new MenuItem(lstFile.getPath()));
			simulator.setCurrentLstFile(lstFile);
			simulator.changeState(new SimStateFileLoaded());
			activateButtons();
		}
	}

	@FXML
	private void runButtonClicked() {
		simulator.changeState(new SimStateContMode());
	}

	@FXML
	private void stopButtonClicked() {
		simulator.changeState(new SimStateIdle());
	}

	@FXML
	private void stepButtonClicked() {
		simulator.changeState(new SimStateStepMode());
	}

	@FXML
	private void resetButtonClicked() {
		simulator.changeState(new SimStateReset());
	}

	@FXML
	private void showHelp() throws IOException {
		Desktop.getDesktop().open(new File(String.valueOf(getClass().getResource("/PIC16F84_Simulator_Dokumentation.pdf")).substring(6)));
	}

	@FXML
	private void showAboutDialog() {
		AboutDialog.getAboutDialog().showAndWait();
	}

	@FXML
	private void exit() {
		simulator.stopSimulation();
		Platform.exit();
	}

	public void onGprHexValueChange(TableColumn.CellEditEvent<GprModel, String> gprModelStringCellEditEvent) {
		GprModel gprModel = gprTable.getSelectionModel().getSelectedItem();

		try {
			byte fileRegister = (byte) Integer.parseInt(gprModel.getAddress().replace("0x", "00"), 16);
			byte newValue = (byte) Integer.parseInt(gprModelStringCellEditEvent.getNewValue().replace("0x", "00"), 16);
			simulator.getPicController().getDataMemory().store(fileRegister, newValue);

		} catch(NumberFormatException e) {
			InvalidInputDialog.getInvalidInputDialog().showAndWait();
		}
	}

	public void onSfrHexValueChange(TableColumn.CellEditEvent<SfrModel, String> sfrModelStringCellEditEvent) {
		SfrModel sfrModel = sfrTable.getSelectionModel().getSelectedItem();

		try {
			String normalizedAddress = sfrModel.getAddress().substring(0, 4).replace("0x", "00");
			byte fileRegister = (byte) Integer.parseInt(normalizedAddress, 16);

			if(fileRegister != 0x0 && fileRegister != 0x7 && fileRegister != 0x80 && fileRegister != 0x87) {
				byte newValue = (byte) Integer.parseInt(sfrModelStringCellEditEvent.getNewValue().replace("0x", "00"), 16);
				simulator.getPicController().getDataMemory().store(fileRegister, newValue);
			}

		} catch(NumberFormatException e) {
			InvalidInputDialog.getInvalidInputDialog().showAndWait();
		}
	}

	private void updateTrisRegister(int fileRegisterAddress, int bitPosition, RadioButton radioButton) {
		DataMemory dataMemory = simulator.getPicController().getDataMemory();
		byte trisValue = dataMemory.load((byte) fileRegisterAddress);
		byte newTrisValue = (byte) (0x1 << bitPosition);

		if("Input".equals(radioButton.getText())) {
			newTrisValue = (byte) (trisValue | newTrisValue);
		} else {
			newTrisValue = (byte) (trisValue & (~newTrisValue));
		}

		dataMemory.store((byte) fileRegisterAddress, newTrisValue);
	}

	private void activateButtons() {
		runButton.setDisable(false);
		stepButton.setDisable(false);
		resetButton.setDisable(false);
	}

	private void outputLstFile(List<String> codeLines) {
		ObservableList<CodeModel> o = FXCollections.observableArrayList();

		for(String codeLine : codeLines) {
			o.add(new CodeModel("", "", codeLine));
		}

		codeTable.setItems(o);
	}

	private void outputCurrentExecutedCode(Integer currentExecCode) {
		Platform.runLater(() -> {
			String pcAsString = String.format("%1$04X", currentExecCode);

			ObservableList<CodeModel> o = FXCollections.observableArrayList();
			for(CodeModel model : codeTable.getItems()) {
				if(model.getCodeLine().substring(0, 4).equals(pcAsString)) {
					model.setIsExecuted("-->");
				} else {
					model.setIsExecuted("");
				}
				o.add(model);
			}

			codeTable.getItems().remove(0, codeTable.getItems().size());
			codeTable.setItems(o);
		});
	}

	private void outputBreakpoints(List<Integer> breakpoints) {
		Platform.runLater(() -> {
			ObservableList<CodeModel> codeList = FXCollections.observableArrayList();
			for(CodeModel model : codeTable.getItems()) {
				String currentCode = model.getCodeLine().substring(0, 4);

				boolean found = false;
				for(Integer x : breakpoints) {
					if(!currentCode.isBlank() && x.intValue() == Integer.parseInt(currentCode, 16)) {
						found = true;
						break;
					}
				}

				if(found) {
					model.setBreakpoint("x");
				} else {
					model.setBreakpoint(" ");
				}

				codeList.add(model);
			}

			codeTable.getItems().remove(0, codeTable.getItems().size());
			codeTable.setItems(codeList);
		});
	}

	private void outputRuntimeCounter(Double runtimeCounter) {
		Platform.runLater(() -> {
			runtimeCounterLabel.setText(String.valueOf((Math.round(runtimeCounter * 100.0) / 100.0)) + " µs");
		});
	}

	private void outputWRegister(Byte w) {
		Platform.runLater(() -> wLabel.setText(String.format("0x%1$02X", w)));
	}

	private void outputPc(Integer pc) {
		Platform.runLater(() -> pcLabel.setText(String.format("0x%1$04X", pc)));
	}

	private void outputGpr(byte[] gpr) {
		ObservableList<GprModel> gprTableContent = FXCollections.observableArrayList();
		for (int i = 0x0C; i < 0x50 ; i++) {
			gprTableContent.add(new GprModel(i, gpr[i]));
		}
		gprTable.setItems(gprTableContent);
	}

	private void outputSfr(byte[] sfr) {
		outputSfrAsTable(sfr);
		outputSfrToQuickAccess(sfr);
	}

	private void outputSfrAsTable(byte[] sfr) {
		ObservableList<SfrModel> sfrTableContent = FXCollections.observableArrayList();
		sfrTableContent.add(new SfrModel("INDR", 0x00, sfr[0x00]));
		sfrTableContent.add(new SfrModel("TMR0", 0x01, sfr[0x01]));
		sfrTableContent.add(new SfrModel("PCL", 0x02, sfr[0x02]));
		sfrTableContent.add(new SfrModel("STATUS", 0x03, sfr[0x03]));
		sfrTableContent.add(new SfrModel("FSR", 0x04, sfr[0x04]));
		sfrTableContent.add(new SfrModel("PORTA", 0x05, sfr[0x05]));
		sfrTableContent.add(new SfrModel("PORTB", 0x06, sfr[0x06]));
		sfrTableContent.add(new SfrModel("undefined", 0x07, sfr[0x07]));
		sfrTableContent.add(new SfrModel("EEDATA", 0x08, sfr[0x08]));
		sfrTableContent.add(new SfrModel("EEADR", 0x09, sfr[0x09]));
		sfrTableContent.add(new SfrModel("PCLATH", 0x0A, sfr[0x0A]));
		sfrTableContent.add(new SfrModel("INTCON", 0x0B, sfr[0x0B]));
		sfrTableContent.add(new SfrModel("INDR", 0x80, sfr[0x80]));
		sfrTableContent.add(new SfrModel("OPTION", 0x81, sfr[0x81]));
		sfrTableContent.add(new SfrModel("TRISA", 0x85, sfr[0x85]));
		sfrTableContent.add(new SfrModel("TRISB", 0x86, sfr[0x86]));
		sfrTableContent.add(new SfrModel("undefined", 0x87, sfr[0x87]));
		sfrTableContent.add(new SfrModel("EECON1", 0x88, sfr[0x88]));
		sfrTableContent.add(new SfrModel("EECON2", 0x89, sfr[0x89]));
		sfrTable.setItems(sfrTableContent);
	}

	private void outputSfrToQuickAccess(byte[] sfr) {
		Platform.runLater(() -> {
			pclLabel.setText(String.format("0x%1$02X", sfr[0x02]));
			pclathLabel.setText(String.format("0x%1$02X", sfr[0x0A]));
			cLabel.setText(String.valueOf(StatusRegisterHelper.getCFlag()));
			dcLabel.setText(String.valueOf(StatusRegisterHelper.getDCFlag()));
			zLabel.setText(String.valueOf(StatusRegisterHelper.getZFlag()));
		});
	}

	private void outputStack(short[] stack) {
		ObservableList<StackModel> stackTableContent = FXCollections.observableArrayList();
		for (int i = 0; i < 8 ; i++) {
			stackTableContent.add(new StackModel(i, stack[i]));
		}
		stackTable.setItems(stackTableContent);
	}

	private void outputStackPointer(Integer stackPointer) {
		Platform.runLater(() -> {
			stackPointerLabel.setText(String.valueOf(stackPointer));
		});
	}

	private void outputToDebugConsole(String msg) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		debugConsole.appendText(dateFormat.format(new Date()) + ": " + msg + "\n");
	}
}
