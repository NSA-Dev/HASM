package hackAssembler;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Parser {
	private Code codeModule; 
	private String sourceF, targetF;
	private Scanner fileScan;
// BUG: AM=M-1
	public Parser(String sourceF, String targetF) {
		this.sourceF = sourceF;
		this.targetF = targetF;
		try {
			fileScan = new Scanner(Paths.get(this.sourceF));
		} catch (IOException e) {
			e.printStackTrace();
		}
		codeModule = new Code(); 
	}

	public void translate() {
		FileWriter targetWriter;
		int instrCounter = 0; // needed for the lexer
		try {
			targetWriter = new FileWriter(targetF);
			while (fileScan.hasNextLine()) {
				String currentLine = fileScan.nextLine(); // || currentLine.startsWith("(")
				if (currentLine.isBlank() || currentLine.startsWith("//")) {
					continue;
				}
				// determine command type
				// parse command based on the type
				// write sequence of bits into the destination
				String instruction = "";
				switch (commandtype(currentLine)) {
				case 'A':
					instruction = parseA(currentLine);
					break;
				case 'C':
					instruction = parseC(currentLine);
					break;
				}

				targetWriter.write(instruction);
				targetWriter.write("\n");
			}

			targetWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private char commandtype(String command) {
		if (command.startsWith("@")) {
			return 'A';
		} else {
			return 'C';
		} // add label processing

	}

	private String parseA(String line) {
		// extract number val
		int value = Integer.valueOf(line.split("@")[1]);
		// convert to bin representation
		String address = String.format("%15s", Integer.toBinaryString(value)).replace(' ', '0');;
		return "0" + address;
	}

	private String parseC(String line) {
		String dest, comp, jump;
		String[] instrParts;

		if (line.contains("=")) {
			instrParts = line.split("=");
			dest = codeModule.getDest(instrParts[0]);
			comp = codeModule.getComp(instrParts[1]);
			jump = "000";
		} else {
			instrParts = line.split(";");
			dest = "000";
			comp = codeModule.getComp(instrParts[0]);
			jump = codeModule.getJump(instrParts[1]);
		}
		

		return "111" + comp + dest + jump;
	}


}
