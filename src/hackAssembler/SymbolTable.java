package hackAssembler;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
	private Map<String, Integer> symbols;

	public SymbolTable() {
		symbols = new HashMap<>();
		this.initTable();
	}

	private void initTable() {
		symbols.put("SP", 0);
		symbols.put("LCL", 1);
		symbols.put("ARG", 2);
		symbols.put("THIS", 3);
		symbols.put("THAT", 4);
		for (int i = 0; i < 16; i++) {
			symbols.put(("R" + i), i);
		}
		symbols.put("SCREEN", 16384);
		symbols.put("KBD", 24576);
	}

	public boolean contains(String symbol) {
		if (symbols.containsKey(symbol)) {
			return true;
		}
		return false;
	}

	public void addEntry(String symbol, int address) {
		if (!symbols.containsKey(symbol)) {
			symbols.put(symbol, address);
		}
	}

	// assumes correct query by design
	public int getAddress(String symbol) {
		return symbols.get(symbol);
	}

}
