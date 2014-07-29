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
import android.widget.Toast;

import com.tabeyti.macrotrack.shared.TDate;

public class FoodListControlBar 
{
	private FoodListPage parent = null;;
	private Button addButton;
	private Button saveButton;
	
	public FoodListControlBar(final FoodListPage parent)
	{
		this.parent = parent;

        addButton = (Button)parent.view().findViewById(R.id.foodlist_addbutton);
        saveButton = (Button)parent.view().findViewById(R.id.foodlist_savebutton);
        
        addButton.setOnClickListener(new OnClickListener() 
        {
        	@Override
			public void onClick(View v) 
			{
				parent.addFoodDialog();				
			}
		});
	
	} // end DateBar()

} // end class DateBar
