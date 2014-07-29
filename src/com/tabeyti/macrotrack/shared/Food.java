package com.tabeyti.macrotrack.shared;

import java.io.Serializable;

/*
 * This class is the structure of a component of food and contains properties
 * relevant to the food (fats, protein, etc.)
 */

public class Food implements Comparable<Food>, Serializable
{
	// main food measurements
	protected String name = "";
	protected String unit = "";
	protected double[] measurements = new double[Measurement.values().length];
	protected int id = -1;
	
	public Food(){}
	
	public Food (String name, String unt, double[] msms, int id)
	{
		this.name = name;
		unit = unt;
		
		this.measurements = msms;			
		
		this.id = id;
				
	} // end Food

	
	///////////////////////////////////////////////////////////////////////////
	// Getters and Setters
	///////////////////////////////////////////////////////////////////////////
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unt) {
		this.unit = unt;
	}
	
	public double getMeasurement(Measurement msm)
	{
		return measurements[msm.ordinal()];
		
	} // end getMeasurement()
	
	public void setMeasurement(Measurement msm, double value)
	{
		// TODO: need to get rid of this switch shit (use Measurement.ordinal() for index)
		measurements[msm.ordinal()] = value;
	}

	@Override
	public int compareTo(Food arg0) 
	{
		return this.name.compareTo(arg0.getName());
		
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}	
	
} // end class Food
