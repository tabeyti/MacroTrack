package com.tabeyti.macrotrack.shared;

import com.tabeyti.macrotrack.datamanagement.IDataType;

public class Goals extends Food implements IDataType// since food as all measurement shizz (hacky i know, need better class heiarchy)
{
	private String notes = "";
	
	public String getNotes() 
	{
		return notes;
	}
	public void setNotes(String notes) 
	{
		this.notes = notes;
	}
	@Override
	public DataType getDataType()
	{
		return DataType.GOALS;
	}
		
} // end class Goals
