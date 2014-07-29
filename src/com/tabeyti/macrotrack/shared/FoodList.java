package com.tabeyti.macrotrack.shared;

import java.util.ArrayList;
import java.util.Collections;

import com.tabeyti.macrotrack.datamanagement.IDataType;


public class FoodList extends ArrayList<Food> implements IDataType
{
	
	/**
	 * Returns the food object associated with the food name.
	 * @param foodName
	 * @return
	 */
	public Food getFood(String foodName)
	{
		for (Food food : this)
			if (food.getName().equals(foodName))
				return food;
		
		return null;
		
	} // end getFood()
	
	public Food getFood(int id)
	{
		for (Food food : this)
			if (food.getId() == id)
				return food;
		return null;
		
	} // end getFood()
	
	
	/**
	 * Sorts the food list based on the comparator implementation of the food
	 */
	public void sort()
	{
		Collections.sort(this);
		
	} // end sort()


	@Override
	public DataType getDataType()
	{
		return DataType.FOODLIST;
	}
	

} // end class FoodList
