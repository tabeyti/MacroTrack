package com.tabeyti.macrotrack;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

import com.tabeyti.macrotrack.shared.TDate;

public class DateBar 
{
	private DayEntryPage parent = null;
	private long currDate = 0;
	private Button dateBox;
	private Button addButton;
	private Button saveButton;
	
	public DateBar(final DayEntryPage parent)
	{
		this.parent = parent;
		
		// sets up date box with current date 
        dateBox = (Button)parent.view().findViewById(R.id.dayentry_datebox);
        currDate = System.currentTimeMillis();
        dateBox.setText(new TDate(currDate).displayString());
        
        addButton = (Button)parent.view().findViewById(R.id.dayentry_addbutton);
        saveButton = (Button)parent.view().findViewById(R.id.dayentry_savebutton);
        
        addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				parent.addFoodEntryDialog();				
			}
		});
        saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				parent.saveLogEntry();				
			}
		});
        dateBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				DatePickerFragment newFragment = new DatePickerFragment();
				newFragment.setParent(DateBar.this);
			    newFragment.show(MainActivity.instance.getSupportFragmentManager(), 
			    		"datePicker");		
			}
		});
        
		
	} // end DateBar()

	public void updateDate(TDate date) 
	{
		dateBox.setText(date.displayString());
		// TODO call parent to update new day
		parent.updateDate(date);
		
	} // end updateDate()
	
	public String getDateStr()
	{
		return dateBox.getText().toString();
		
	} // end getDateStr()
	
	
	/**
	 * TDate picker class for the date selection.
	 * @author abeyti_t
	 *
	 */
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener 
	{
		private DateBar parent;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// Use the current date as the default date in the picker
	        final Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);

	        // Create a new instance of DatePickerDialog and return it
	        return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day)
		{
			parent.updateDate(new TDate(year, month, day));
		}
		
		public void setParent(DateBar parent)
		{
			this.parent = parent;
		}
		
	} // end class DatePickerFragment

} // end class DateBar
