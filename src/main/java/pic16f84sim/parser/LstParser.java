package pic16f84sim.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LstParser {

	private File lstFile;

	public LstParser(File file) {
		lstFile = file;
	}

	public List<String> parse() {
		List<String> codeLines = new ArrayList();

		try (BufferedReader br = new BufferedReader(new FileReader(lstFile, Charset.forName("ISO-8859-1")))) {
			String currentLine;

			while ((currentLine = br.readLine()) != null) {
				codeLines.add(currentLine);
				// TODO: System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			// TODO: Log
			e.printStackTrace();
		}

		return codeLines;
	}
}
