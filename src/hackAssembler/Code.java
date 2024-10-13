package hackAssembler;

import java.util.HashMap;
import java.util.Map;


//optimize later 
// provides convenient map for mnemonics sent to it from the parser module
public class Code {
	private Map <String, String> dest, comp, jump;
	
	public Code() {
		dest = new HashMap<>(); 
		comp = new HashMap<>();
		jump = new HashMap<>();
		this.initDest();
		this.initComp();
		this.initJump(); 
	}
	
	
	private void initDest() {
		dest.put("null", "000");
		dest.put("M", "001");
		dest.put("D", "010");
		dest.put("MD", "011");
		dest.put("A", "100");
		dest.put("AM", "101");
		dest.put("AD", "110");
		dest.put("AMD", "111");		
	}
	private void initComp() {
		comp.put("0", "0101010");
		comp.put("1", "0111111");
		comp.put("-1","0111010");
		comp.put("D", "0001100");
		comp.put("A", "0110000");
		comp.put("!D", "0001101");
		comp.put("!A", "0110001");
		comp.put("-D", "0001111");
		comp.put("-A", "0110011");
		comp.put("D+1", "0011111");
		comp.put("A+1", "0110111");
		comp.put("D-1", "0001110");
		comp.put("A-1", "0110010");
		comp.put("D+A", "0000010");
		comp.put("D-A", "0010011");
		comp.put("A-D", "0000111");
		comp.put("D&A", "0000000");
		comp.put("D|A", "0010101");
		//M
		comp.put("M", "1110000");
		comp.put("!M", "1110001");
		comp.put("-M","1110011");
		comp.put("M+1", "1110111");
		comp.put("M-1", "1110010");
		comp.put("D+M", "1000010");
		comp.put("D-M", "1010011");
		comp.put("M-D", "1000111");
		comp.put("D&M", "1000000");
		comp.put("D|M", "1010101");			
	}
	
	private void initJump() {
		jump.put("null", "000");
		jump.put("JGT", "001");
		jump.put("JEQ", "010");
		jump.put("JGE", "011");
		jump.put("JLT", "100");
		jump.put("JNE", "101");
		jump.put("JLE", "110");
		jump.put("JMP", "111");
	}
	
	public String getDest(String mnemonic) {
		return dest.get(mnemonic);
	}
	
	public String getComp(String mnemonic) {
		return comp.get(mnemonic);
	}
	
	public String getJump(String mnemonic) {
		return jump.get(mnemonic);
	}

}
