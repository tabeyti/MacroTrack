package com.tabeyti.macrotrack.shared;


public enum Workout 
{
	NONE("NONE"),
	LIFT("Lift"),
	CARDIO("Cardio");
	
	private final String str;
	Workout(String str)
	{
		this.str = str;
	}
	
	public String toString()
	{
		return str;
	}
	
	public static Workout findByValue(String value)
	{
		for (Workout wo : Workout.values())
		{
			if (value.equals(wo.toString()))
				return wo;
		}
		return null;
	}
		
} // end enum Workout