package pic16f84sim.ui.components;

import javafx.stage.FileChooser;

import java.io.File;

public class LstFileChooser {

    public static FileChooser getFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select LST file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("LST", "*.LST"));

        return fileChooser;
    }
}
