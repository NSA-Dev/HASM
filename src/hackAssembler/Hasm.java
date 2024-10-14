package hackAssembler;

import java.io.FileWriter;
import java.io.IOException;

// DONE. CLEANUP AND UPLOAD
public class Hasm {

	public static void main(String[] args) {
		// file handling
		if (args.length != 1) {
			System.out.println("Usage: java Hasm <source_file>");
			return;
		}
		String sourceName = args[0];
		// create new file name from the old one
		String destFile = getTargetName(sourceName);
		try {
			createTarget(destFile);
		} catch (IOException e) {
			System.out.println("Unable to write");
			e.printStackTrace();
		}

		Parser sourceParser = new Parser(sourceName, destFile);
		sourceParser.firstPass();
		sourceParser.translate();

	}

	private static String getTargetName(String sourceName) {
		String[] tempName = sourceName.split("\\.");
		// . is a regex spec.Symb hence escape
		String destFileName = tempName[0] + ".hack";
		return destFileName;
	}

	// caution: overwrites the og file
	private static void createTarget(String name) throws IOException {
		try (FileWriter writer = new FileWriter(name, false)) {
			// FileWriter is closed automatically
		}
	}

}
