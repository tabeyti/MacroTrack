package com.tabeyti.macrotrack;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tabeyti.macrotrack.adapters.FoodListAdapter;
import com.tabeyti.macrotrack.datamanagement.AddFood;
import com.tabeyti.macrotrack.datamanagement.IDataCallBack;
import com.tabeyti.macrotrack.datamanagement.IDataType;
import com.tabeyti.macrotrack.shared.Food;
import com.tabeyti.macrotrack.shared.FoodList;

public class FoodListPage extends Fragment implements IEntryControl, IDataCallBack<String>
{
	private View view;
	private MainActivity parent;
	private FoodList foodList = new FoodList();
	private FoodListAdapter customAdapter;
	private ListView lv;
	private FoodListControlBar controlBar;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view = inflater.inflate(R.layout.foodlist_page, container, false);
		parent = (MainActivity)getActivity();
				
		lv = (ListView)view.findViewById(R.id.foodlist_maintable);
		lv.setFastScrollEnabled(true);
		 
		controlBar = new FoodListControlBar(this);
		
		// initializes food entries
		//customAdapter = new FoodListAdapter(parent, R.layout.foodlist_row, foodList);
        //lv.setAdapter(customAdapter);
		
        //if (null == savedInstanceState)
    	requestFoodList();
        
		return view;
	}
	
	public View view() {return view;}
	
	private void requestFoodList()
	{
		foodList = FoodListManager.getFoodList();
		lv.setAdapter(new FoodListAdapter(parent, R.layout.foodlist_row, foodList));
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) 
	{
	    super.onSaveInstanceState(savedState); 
	    savedState.putSerializable("foods",  foodList);
	}

	@Override
	public void onTaskComplete(IDataType data)
	{
		
		
	}
	
	public void addFoodDialog()
	{
		DialogFragment dlg = AddFoodDialogFragment.newaddinstance(R.string.add);
		dlg.setTargetFragment(this, 1);
		dlg.show(getActivity().getSupportFragmentManager(), "Add Food");
	}

	@Override
	public void addEntry(Object obj)
	{
		if (null != obj && obj instanceof Food)
		{
			FoodListManager.addFood((Food)obj);
				
		}
	}

	@Override
	public void deleteEntry(Object obj)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEntries()
	{
		customAdapter.notifyDataSetChanged();		
	}

} // end class FoodListPage
