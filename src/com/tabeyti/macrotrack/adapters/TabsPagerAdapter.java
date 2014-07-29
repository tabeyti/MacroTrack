package com.tabeyti.macrotrack.adapters;

import com.tabeyti.macrotrack.DayEntryPage;
import com.tabeyti.macrotrack.FoodListPage;
import com.tabeyti.macrotrack.Test;
import com.tabeyti.macrotrack.UserSummaryPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter
{

	public TabsPagerAdapter(FragmentManager fm) 
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int index)
	{
		
		switch (index) 
		{
			case 0:
				return new UserSummaryPage();
	        case 1:
	        	return new DayEntryPage();
	        case 2:
	        	return new FoodListPage();
	        case 3:
	        	return new Test();
	        }
	        
		return null;
	}

	@Override
	public int getCount()
	{
		return 4;
	}

} // end class TabsPagerAdapter
