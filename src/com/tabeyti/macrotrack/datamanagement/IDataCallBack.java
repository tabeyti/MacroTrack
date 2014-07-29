package com.tabeyti.macrotrack.datamanagement;

import com.tabeyti.macrotrack.shared.DataType;

public interface IDataCallBack<T>
{
	public void onTaskComplete(IDataType data);
}
