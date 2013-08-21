package com.bluesky.visualprogramming.vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluesky.visualprogramming.vm.instruction.Instruction;

/**
 * a procedure compiled to instructions
 * 
 * @author jack
 * 
 */
public class CompiledProcedure {

	List<Instruction> instructions;

	Map<String, Integer> labels = new HashMap<String, Integer>();

	public CompiledProcedure(List<Instruction> instructions) {
		this.instructions = instructions;
		
		createLabelIndex();
	}
	
	private void createLabelIndex() {
		for(int i =0;i<instructions.size();i++)
		{
			Instruction ins = instructions.get(i);
			if(ins.hasLabel())
				labels.put(ins.label, i);
		}
	}

	public List<Instruction> getInstructions() {
		return this.instructions;
	}

	public Integer getLabelIndex(String label) {
		Integer index = labels.get(label);
		return index;
	}
	
	public String getInstructionText(){
		StringBuilder sb = new StringBuilder();
		
		for (Instruction ins : instructions) {
			String lbl = ins.label == null ? "" : ins.label;
			String comment = ins.comment == null ? "" : ins.comment;
			
			
			sb.append(String.format("%1$-20s%2$-50s%3$-50s", lbl,ins,comment));
			sb.append("\r\n");
		}
		
		return sb.toString();
	}
}
