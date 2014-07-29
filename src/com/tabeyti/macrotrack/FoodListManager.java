package com.tabeyti.macrotrack;

import android.content.Context;

import com.tabeyti.macrotrack.datamanagement.AddFood;
import com.tabeyti.macrotrack.datamanagement.GetFoodList;
import com.tabeyti.macrotrack.datamanagement.IDataCallBack;
import com.tabeyti.macrotrack.datamanagement.IDataType;
import com.tabeyti.macrotrack.shared.Food;
import com.tabeyti.macrotrack.shared.FoodList;
import com.tabeyti.macrotrack.shared.Id;

public class FoodListManager implements IDataCallBack<FoodList>
{
	private static FoodList foodList = new FoodList();
	private static FoodListManager instance = new FoodListManager();
	
	public static void requestFoodList(Context context)
	{
		// requests the foodlist from the databse
		new GetFoodList(context, instance).execute();	
		
	} // end requestFoodList();
	
	public static void addFood(final Food food)
	{
		foodList.add(food);
		
		// add food to the database
		new AddFood(MainActivity.instance.getApplicationContext(), food, new IDataCallBack<Id>()
		{
			@Override
			public void onTaskComplete(IDataType data)
			{
				if (null == data)
					return;
				
				// retrieve back food ID and set it to the new food item
				int id = ((Id)data).Id;
				food.setId(id);
			}
		}).execute();	
		
	} // end addFood()
	
	public static void deleteFood(Food food)
	{
		foodList.remove(foodList.indexOf(food));
		
		// remove food from the database
		
	} // end deleteFood()
	
	
	public static void updateFood(Food food)
	{
		foodList.set(foodList.indexOf(food),  food);
		
		// update food in the database
		
	} // end deleteFood()

	@Override
	public void onTaskComplete(IDataType data)
	{
		if (null == data)
			return;
		foodList = (FoodList)data;
		
	}
	
	public static FoodList getFoodList()
	{
		return foodList;
	}

} // end class FoodListManager
