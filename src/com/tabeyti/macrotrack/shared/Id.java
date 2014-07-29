package com.tabeyti.macrotrack.shared;

import com.tabeyti.macrotrack.datamanagement.IDataType;

public class Id implements IDataType
{
	public int Id = 0;
	public Id(int id) {this.Id = id;}
	@Override
	public DataType getDataType()
	{
		return DataType.ID;
	}	
}
