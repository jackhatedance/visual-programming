package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.dialect.goo.FieldName;
import com.bluesky.visualprogramming.dialect.goo.FieldName.FieldType;
import com.bluesky.visualprogramming.vm.InstructionType;

/**
 * e.g. a = foo.bar;
 * 
 * @author jackding
 * 
 */
public class AccessField extends Instruction {

	public String varName;
	public String objName;

	public FieldName fieldName;

	public AccessField(int line) {
		super(line);

		this.type = InstructionType.ACCESS_FIELD;
	}

	@Override
	public String toString() {
		String str = null;
		if (fieldName.getType() == FieldType.Constant)
			str = String.format("[access_field] %s ~>%s.%s", varName, objName,
					fieldName.getValue());
		else
			str = String.format("[access_field] %s ~>%s.$%s", varName, objName,
					fieldName.getValue());

		return str;
	}


	public boolean isFieldNameConst() {
		return fieldName.getType() == FieldType.Constant;
	}
}
