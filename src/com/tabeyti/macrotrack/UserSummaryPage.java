package com.tabeyti.macrotrack;

import com.tabeyti.macrotrack.R;
import com.tabeyti.macrotrack.adapters.DayEntryListAdapter;
import com.tabeyti.macrotrack.shared.FoodEntries;
import com.tabeyti.macrotrack.shared.FoodEntry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UserSummaryPage extends Fragment
{
	private View view;
	private MainActivity parent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view = inflater.inflate(R.layout.usersummary_page, container, false);
		parent = (MainActivity)getActivity();
				       
        
        return view;
	
	}	

} // end class UserSummaryPage
