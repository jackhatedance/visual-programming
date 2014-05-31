package com.bluesky.visualprogramming.core;

public class MountEntry {

	public String fileName;
	public String ownerPath;
	public String fieldName;
	public boolean autoSave;

	public MountEntry(String fileName, String ownerPath,String fieldName,  boolean autoSave) {
		this.fileName = fileName;
		this.ownerPath = ownerPath;
		this.fieldName = fieldName;
		this.autoSave = autoSave;
	}

	public MountEntry(String config) {
		String[] values = config.split(",");
		fileName = values[0];
		
		ownerPath = values[1];
		if("<none>".equals(ownerPath))
			ownerPath="";
		
		fieldName = values[2];
		autoSave = Boolean.valueOf(values[3]);
	}
	
	public String getPath(){
		if(ownerPath!=null && !ownerPath.isEmpty())
			return ownerPath + "."+fieldName;
		else
			return fieldName;
				
	}
}
