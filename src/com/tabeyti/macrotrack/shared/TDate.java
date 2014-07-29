package com.tabeyti.macrotrack.shared;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TDate extends Date
{
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yy");
	private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public TDate(int year, int month, int day)
	{
		super(year, month, day);
		
	}

	public TDate(long currDate)
	{
		super(currDate);
	}

	public String displayString()
	{
		return displayFormat.format(this);
		
	} // end displayString()
	
	public String dataString()
	{
		return dataFormat.format(this);
		
	} // end dataString()
	

} // end class TDate
