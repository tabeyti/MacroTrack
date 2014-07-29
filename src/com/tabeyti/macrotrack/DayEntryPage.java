package com.tabeyti.macrotrack;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.tabeyti.macrotrack.adapters.DayEntryListAdapter;
import com.tabeyti.macrotrack.datamanagement.GetLogEntry;
import com.tabeyti.macrotrack.datamanagement.IDataCallBack;
import com.tabeyti.macrotrack.datamanagement.IDataType;
import com.tabeyti.macrotrack.shared.FoodEntries;
import com.tabeyti.macrotrack.shared.FoodEntry;
import com.tabeyti.macrotrack.shared.LogEntry;
import com.tabeyti.macrotrack.shared.TDate;
import com.tabeyti.macrotrack.shared.Workout;

public class DayEntryPage extends Fragment implements IEntryControl, IDataCallBack
{
	private DayEntryListAdapter customAdapter;
	private DateBar dateBar;
	private DayEntrySummaryBar summaryBar;
	private View view;
	private ListView lv;

	private LogEntry currentLogEntry = new LogEntry();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view = inflater.inflate(R.layout.dayentry_page, container, false);
				
		dateBar = new DateBar(this);
		summaryBar = new DayEntrySummaryBar(this);
		
		lv = (ListView)view.findViewById(R.id.dayentry_maintable);
		
		// initializes food entries
		customAdapter = new DayEntryListAdapter(getActivity(), R.layout.dayentry_row, currentLogEntry.getFoodEntries());
		
		/*
		if (null == savedInstanceState)
			tempFoodEntries();
		else
			for(FoodEntry fe : (FoodEntries)savedInstanceState.getSerializable("entries"))
				currentLogEntry.getFoodEntries().add(fe);
		*/
		
		updateEntries();
        
        lv.setAdapter(customAdapter);
        registerForContextMenu(lv);
        
        return view;
	}	
	
	@Override
	public void onSaveInstanceState(Bundle savedState) 
	{
	    super.onSaveInstanceState(savedState); 
	    savedState.putSerializable("entries",  currentLogEntry.getFoodEntries());
	}
	
	
	private void tempFoodEntries()
	{
		// TODO: temp entries
		/*
		addFoodEntry(new FoodEntry("Banana", "oz", 1, new double[] {100, 10, 20, 30}));
		addFoodEntry(new FoodEntry("Bacon", "oz", 1, new double[] {100, 10, 20, 30}));
		addFoodEntry(new FoodEntry("Potatos", "oz", 1, new double[] {100, 10, 20, 30}));
		addFoodEntry(new FoodEntry("Eggs", "oz", 1, new double[] {100, 10, 20, 30}));  
		addFoodEntry(new FoodEntry("Toast", "oz", 1, new double[] {100, 10, 20, 30}));  
		addFoodEntry(new FoodEntry("Ham", "oz", 1, new double[] {100, 10, 20, 30}));  
		addFoodEntry(new FoodEntry("Toast", "oz", 1, new double[] {100, 10, 20, 30}));
		*/  
	}
	
	public View view() {return view;}

	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, 
    		ContextMenuInfo menuInfo)
    {
    	super.onCreateContextMenu(menu,  v,  menuInfo);
    	MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
    	
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
    	int itemId = item.getItemId();
		if (itemId == R.id.edit)
		{
			DialogFragment dlg = DayEntryDialogFragment.neweditinstance(R.string.edit,
					currentLogEntry.getFoodEntries().get(info.position));
			dlg.setTargetFragment(this,  1);
			dlg.show(getActivity().getSupportFragmentManager(), "Edit Entry");
			return true;
		} else if (itemId == R.id.delete)
		{
			deleteEntry(currentLogEntry.getFoodEntries().get(info.position));
			return true;
		} else
		{
			return super.onContextItemSelected(item);
		}
    }
	
    /**
     * Starts the add food entry dialog window.
     */
	public void addFoodEntryDialog()
	{
		DialogFragment dlg = DayEntryDialogFragment.newaddinstance(R.string.add);
		dlg.setTargetFragment(this, 1);
		dlg.show(getActivity().getSupportFragmentManager(), "Add Entry");
		
	} // end addFoodEntryDialog()
	
	/**
	 * Creates a log entry object from the entered in day view information, 
	 * and saves it to the server.
	 */
	public void saveLogEntry()
	{
		LogEntry le = new LogEntry(dateBar.getDateStr());
		
		le.setFoodEntries(currentLogEntry.getFoodEntries());
		
		le.setWorkout(Workout.NONE);
		le.setNotes("");
		
		try 
		{
			//new GetFoodList(getActivity(), this).execute();						
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void addEntry(Object obj) 
	{
		if (null != obj && obj instanceof FoodEntry)
		{
			currentLogEntry.getFoodEntries().add((FoodEntry)obj);
			updateEntries();	
		}
	}
	
	@Override
	public void deleteEntry(Object obj)
	{
		if (null != obj && obj instanceof FoodEntry)
		{
			currentLogEntry.getFoodEntries().remove((FoodEntry)obj);
			updateEntries();			
		}
	}
	
	@Override
	public void updateEntries()
	{
		customAdapter.notifyDataSetChanged();
		summaryBar.update(currentLogEntry.getFoodEntries());
		
	} // end editFoodEntry()
	
	/**
	 * Updates the day view with the passed in date
	 * 
	 */
	public void updateDate(TDate date)
	{
		new GetLogEntry(getActivity(), this, date.dataString()).execute();	
		
	} // end updateDate()
	
	
	/**
	 * Loads in the global log entry into the day view.
	 */
	private void loadLogEntry()
	{
		lv.setAdapter(new DayEntryListAdapter(getActivity(), 
				R.layout.dayentry_row, currentLogEntry.getFoodEntries()));	
		
	} // end loadLoagEntry()

	@Override
	public void onTaskComplete(IDataType data)
	{
		switch (data.getDataType())
		{
			case LOGENTRY:
				currentLogEntry = (LogEntry)data;
				loadLogEntry();
				break;
			case GOALS:
				break;
			default:
				break;
		}
		
	}

} // end class DayEntryPage

