package pic16f84sim.ui.models;

import javafx.beans.property.SimpleStringProperty;

public class CodeModel {

    private SimpleStringProperty breakpoint;
    private SimpleStringProperty isExecuted;
    private SimpleStringProperty codeLine;

    public CodeModel(String breakpoint, String isExecuted, String codeLine) {
        this.breakpoint = new SimpleStringProperty(breakpoint);
        this.isExecuted = new SimpleStringProperty(isExecuted);
        this.codeLine = new SimpleStringProperty(codeLine);
    }

    public String getBreakpoint() {
        return breakpoint.get();
    }

    public void setBreakpoint(String breakpointLine) {
        breakpoint = new SimpleStringProperty(breakpointLine);
    }

    public String getIsExecuted() {
        return isExecuted.get();
    }

    public void setIsExecuted(String executedLine) {
        isExecuted = new SimpleStringProperty(executedLine);
    }

    public String getCodeLine() {
        return codeLine.get();
    }
}
