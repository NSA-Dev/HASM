package hackAssembler;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Parser {
	private static final int start = 16;
	private static final int limit = 16384; // normally we should check addressable space
	private int ffIndex;

	private SymbolTable symbolTable;
	private Code codeModule;
	private String sourceF, targetF;

	public Parser(String sourceF, String targetF) {
		this.sourceF = sourceF;
		this.targetF = targetF;

		codeModule = new Code();
		symbolTable = new SymbolTable();
		ffIndex = start;
	}

	public void firstPass() {
		try {
			Scanner fileScan = new Scanner(Paths.get(this.sourceF));
			int instrCounter = 0;
			while (fileScan.hasNextLine()) {
				String currentLine = fileScan.nextLine().trim();
				if (currentLine.isBlank() || currentLine.startsWith("//")) {
					continue;
				}
				if (currentLine.startsWith("(")) {
					this.resolveLabel(currentLine, instrCounter);
				} else {
					instrCounter++;
				}
			}
			fileScan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void translate() {
		FileWriter targetWriter;
		Scanner fileScan;
		try {
			fileScan = new Scanner(Paths.get(this.sourceF));
			targetWriter = new FileWriter(targetF);
			while (fileScan.hasNextLine()) {
				String currentLine = fileScan.nextLine().trim();
				if (currentLine.isBlank() || currentLine.startsWith("//") || currentLine.startsWith("(")) {
					continue;
				}
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
			fileScan.close();
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
		// CHECK FOR VARIABLE IN THE LUT
		int value;
		String content = line.split("@")[1];
		if (isSymbol(content)) {
			value = resolveSymbol(content);
		} else {
			value = Integer.valueOf(content);
		}
		String address = String.format("%15s", Integer.toBinaryString(value)).replace(' ', '0');
		return "0" + address;
	}

	private boolean isSymbol(String line) {
		if (Character.isLetter(line.charAt(0))) {
			return true;
		}
		return false;
	}

	private void resolveLabel(String line, int lineNum) {
		String symbol = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
		int value = lineNum++;
		symbolTable.addEntry(symbol, value);
	}

	private int resolveSymbol(String line) {
		if (symbolTable.contains(line)) {
			return symbolTable.getAddress(line);
		}
		// allocate space otherwise
		symbolTable.addEntry(line, ffIndex);
		ffIndex++;
		return symbolTable.getAddress(line);
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
