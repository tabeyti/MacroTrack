package com.tabeyti.macrotrack.shared;

import java.io.Serializable;

public class FoodEntry implements Serializable
{
	private double units;
	private boolean custom = false;
	private Food food = null;
	
	public FoodEntry(Food food, int units)
	{
		this.food = food;
		this.units = units;
	}
	
	public FoodEntry()
	{
		this.food = new Food("", "", new double[Measurement.values().length], -1);
		units = 1;
	}
	
	public FoodEntry(Food food)
	{
		this.food = food;
		units = 1;		
	}
	
	public double getUnits()
	{
		return units;
	}
	
	public void setUnits(double units)
	{
		this.units = units;		
	}
	
	/**
	 * Takes in the passed food and copies over all food information.
	 * @param food
	 */
	public void setFood(Food food)
	{
		this.food = food;
		
	} // end updateFood()

	public boolean isCustom()
	{
		return custom;
	}

	public void setCustom(boolean custom)
	{
		this.custom = custom;
	}

	public String getName()
	{
		return food.name;	
	}

	public double getMeasurement(Measurement msm)
	{
		return food.getMeasurement(msm);
	}

	public String getUnit()
	{
		return food.unit;
	}

	public void setMeasurement(Measurement msm, double d)
	{
		food.setMeasurement(msm,  d);
	}

	public void setName(String name)
	{
		food.setName(name);
	}

	public void setUnit(String unit)
	{
		food.setUnit(unit);
	}
	
} // end class FoodEntry
