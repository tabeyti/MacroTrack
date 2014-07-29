package com.tabeyti.macrotrack.shared;

public enum Measurement 
{
	CALORIES("Calories"),
	PROTEIN("Protein"),
	CARBOHYDRATES("Carbohydrates"),
	FAT("Fat");
	
	private final String str;
	Measurement(String str)
	{
		this.str = str;
	}
	
	public String toString()
	{
		return str.toLowerCase();
	}
	
	public String displayString()
	{
		return str;
	}
	
	public static Measurement findEnum(String value)
	{
		for (Measurement msm : Measurement.values())
		{
			if (msm.toString().equals(value))
				return msm;
		}
		
		return null;
	}
	
	
	/**
	 * Returns the unit representation of the passed in measurement.
	 * @param msm
	 * @return
	 */
	public static Unit getUnit(Measurement msm)
	{
		switch (msm)
		{
			case CALORIES:
				return Unit.CALORIE;
			case PROTEIN:
			case CARBOHYDRATES:
			case FAT:
				return Unit.GRAM;
			default:
				return Unit.UNIT;
		}		
		
	} // end getUnit()

} // end enum Measurement
